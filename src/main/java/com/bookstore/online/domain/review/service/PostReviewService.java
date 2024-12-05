package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostReviewService {

  private final ReviewRepository reviewRepository;

  private final RedisTemplate<String, String> redisTemplate;
  private static final String REVIEW_KEY = "book:review";
  private final ObjectMapper objectMapper;

  // List 타입 사용할 것임. (최신 리뷰를 중점으로 불러올 예정)
  // 리뷰 작성
  @Transactional
  public void postReview(ReviewEntity reviewEntity) {
    boolean isExistReview = reviewRepository.existsByBookNumberAndUserId(
        reviewEntity.getBookNumber(), reviewEntity.getUserId());
    if (isExistReview) {
      throw new IllegalArgumentException("이미 해당 책에 대한 리뷰를 작성했습니다.");
    }

    try {
      // reviewEntity -> JSON 문자열로 변환
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

}
