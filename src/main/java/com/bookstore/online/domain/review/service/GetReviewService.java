package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetReviewService {

  private final ReviewRepository reviewRepository;

  private final RedisTemplate<String, String> redisTemplate;
  private static final String REVIEW_KEY = "book:review";
  private final ObjectMapper objectMapper;

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

}
