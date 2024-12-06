package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.dto.request.PatchReviewRequestDto;
import com.bookstore.online.domain.review.dto.request.PostReviewRequestDto;
import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import com.bookstore.online.global.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;

  private final RedisTemplate<String, String> redisTemplate;
  private static final String REVIEW_KEY = "book:review";
  private final ObjectMapper objectMapper;

  // List 타입 사용할 것임. (최신 리뷰를 중점으로 불러올 예정)
  // 리뷰 작성
  public void postReview(PostReviewRequestDto dto, String userId) {

    // 리뷰가 이미 존재하는지 확인
    boolean isExistReview = reviewRepository.existsByBookNumberAndUserId(
        dto.getBookNumber(), userId);
    if (isExistReview) {
      throw new IllegalArgumentException("이미 해당 책에 대한 리뷰를 작성했습니다.");
    }

    // 리뷰 Entity로 변환
    ReviewEntity reviewEntity = new ReviewEntity();
    reviewEntity.setUserId(userId);
    reviewEntity.setBookNumber(dto.getBookNumber());
    reviewEntity.setRating(dto.getRating());
    reviewEntity.setComment(dto.getComment());
    reviewEntity.setCreatedAt(dto.getCreatedAt());

    try {

      String reviewJson = objectMapper.writeValueAsString(reviewEntity);
      // 변환 후 Redis에 저장
      redisTemplate.opsForList().leftPush(REVIEW_KEY, reviewJson);

    } catch (Exception exception) {
      log.error("Error serializing reviewEntity: {}", exception.getMessage());
      throw new RuntimeException("리뷰 저장 중 오류 발생", exception);
    }

    // DB에 저장
    reviewRepository.save(reviewEntity);
  }

  /// ////////////////////////////////////////////////////////////////////////////////////////////

  // 리뷰 조회 (책 기준)
  public List<ReviewEntity> getReviewsOfBookNumber(Integer bookNumber) {
    // 책 번호 확인
    if (bookNumber == null || bookNumber <= 0) {
      throw new IllegalArgumentException("유효하지 않은 책번호입니다.");
    }

    // Redis에서 리뷰 조회
    List<ReviewEntity> filteredReviews = new ArrayList<>();
    try {
      // Redis에서 모든 리뷰 가져오기
      List<String> reviewsFromRedis = redisTemplate.opsForList().range(REVIEW_KEY, 0, -1);
      if (reviewsFromRedis != null) {
        filteredReviews = reviewsFromRedis.stream()
            .map(reviewJson -> {
              try {
                return objectMapper.readValue(reviewJson, ReviewEntity.class);
              } catch (JsonProcessingException e) {
                log.error("리뷰 JSON 변환 실패: {}", e.getMessage());
                return null;
              }
            })
            .filter(review -> review != null && review.getBookNumber().equals(bookNumber)) // 책번호 기준 필터링
            .collect(Collectors.toList());
      }
    } catch (Exception exception) {
      log.error("Redis에서 리뷰 조회 중 오류 발생: {}", exception.getMessage());
    }

    // Redis에 해당 데이터가 없는 경우 DB에서 조회
    if (filteredReviews.isEmpty()) {
      log.info("Redis에서 해당 책 번호({})에 대한 리뷰가 없어 DB에서 조회합니다.", bookNumber);
      filteredReviews = reviewRepository.findByBookNumberOrderByReviewNumberDesc(bookNumber);
    }

    return filteredReviews;
  }

  /// /////////////////////////////////////////////////////////////////////////////////////////

  // 리뷰 조회 (유저 기준)
  public List<ReviewEntity> getReviewsOfUserId(String userId) {
    // Redis에서 리뷰 조회
    List<ReviewEntity> filteredReviews = new ArrayList<>();
    try {
      // Redis에서 모든 리뷰 가져오기
      List<String> reviewsFromRedis = redisTemplate.opsForList().range(REVIEW_KEY, 0, -1);
      if (reviewsFromRedis != null) {
        filteredReviews = reviewsFromRedis.stream()
            .map(reviewJson -> {
              try {
                return objectMapper.readValue(reviewJson, ReviewEntity.class);
              } catch (JsonProcessingException e) {
                log.error("리뷰 JSON 변환 실패: {}", e.getMessage());
                return null;
              }
            })
            .filter(review -> review != null && review.getUserId().equals(userId)) // 책번호 기준 필터링
            .collect(Collectors.toList());
      }
    } catch (Exception exception) {
      log.error("Redis에서 리뷰 조회 중 오류 발생: {}", exception.getMessage());
    }

    // Redis에 해당 데이터가 없는 경우 DB에서 조회
    if (filteredReviews.isEmpty()) {
      log.info("Redis에서 해당 책 번호({})에 대한 리뷰가 없어 DB에서 조회합니다.", userId);
      filteredReviews = reviewRepository.findByUserIdOrderByReviewNumberDesc(userId);
    }

    return filteredReviews;

  }

  /// ////////////////////////////////////////////////////////////////////////////////////////

  // 리뷰 수정하기
  public ResponseEntity<ResponseDto> patchReview(PatchReviewRequestDto dto, Integer reviewNumber, String userId) {
    // 리뷰 존재 확인
    ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
    if (reviewEntity == null && reviewNumber <= 0) throw new IllegalArgumentException("리뷰가 존재하지 않습니다.");

    // 권한 확인
    if (!reviewEntity.getUserId().equals(userId)) throw new SecurityException("리뷰 수정 권한이 없습니다.");

    reviewEntity.patch(dto);

    // 수정된 리뷰 저장
    try {
      String reviewJson = objectMapper.writeValueAsString(reviewEntity);
      // 변환 후 Redis에 저장
      redisTemplate.opsForList().leftPush(REVIEW_KEY, reviewJson);

      reviewRepository.save(reviewEntity);

    } catch (Exception exception) {
      log.error("리뷰 수정 실패: {}", exception.getMessage());
      throw new RuntimeException("리뷰 수정 중 오류가 발생했습니다.", exception);
    }

    return ResponseDto.success();
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////

    // 리뷰 삭제
    public ResponseEntity<ResponseDto> deleteReview(Integer reviewNumber, String userId) {
      // 리뷰 존재 확인
      ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
      if (reviewEntity == null) throw new IllegalArgumentException("리뷰가 존재하지 않습니다.");

      // 권한 확인
      if (!reviewEntity.getUserId().equals(userId)) throw new SecurityException("리뷰 삭제 권한이 없습니다.");

      try {
        // Redis에서 리뷰 제거
        List<String> reviewList = redisTemplate.opsForList().range(REVIEW_KEY, 0, -1);
        if (reviewList != null) {
          for (String reviewJson : reviewList) {
            ReviewEntity redisReview = objectMapper.readValue(reviewJson, ReviewEntity.class);
            if (redisReview.getReviewNumber().equals(reviewNumber)) {
              redisTemplate.opsForList().remove(REVIEW_KEY, 1, reviewJson);
              break;
            }
          }
        }

        reviewRepository.delete(reviewEntity);

      } catch (Exception exception) {
        log.error("리뷰 삭제 실패: {}", exception.getMessage());
        throw new RuntimeException("리뷰 삭제 중 오류가 발생했습니다.", exception);
      }

      return ResponseDto.success();
    }

}

