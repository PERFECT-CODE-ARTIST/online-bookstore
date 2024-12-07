package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import com.bookstore.online.global.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteReviewService {
  private final ReviewRepository reviewRepository;

  private final RedisTemplate<String, String> redisTemplate;
  private static final String REVIEW_KEY = "book:review";
  private final ObjectMapper objectMapper;

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
