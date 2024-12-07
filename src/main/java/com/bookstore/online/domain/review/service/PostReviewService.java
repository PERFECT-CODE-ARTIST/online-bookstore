package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.dto.request.PostReviewRequestDto;
import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
  public void postReview(PostReviewRequestDto dto, String userId) {

    // 리뷰가 이미 존재하는지 확인
    boolean isExistReview = reviewRepository.existsByBookNumberAndUserId(
        dto.getBookNumber(), userId);
    if (isExistReview) {
      throw new IllegalArgumentException("이미 해당 책에 대한 리뷰를 작성했습니다.");
    }

    // 리뷰 Entity로 변환
    ReviewEntity reviewEntity = new ReviewEntity(dto, userId);

    // DB에 저장
    reviewRepository.save(reviewEntity);

    try {
      // 리뷰 객체를 JSON으로 직렬화
      String reviewJson = objectMapper.writeValueAsString(reviewEntity);
      // 변환 후 Redis에 저장
      redisTemplate.opsForList().leftPush(REVIEW_KEY, reviewJson);

    } catch (Exception exception) {
      log.error("Error serializing reviewEntity: {}", exception.getMessage());
      throw new RuntimeException("리뷰 저장 중 오류 발생", exception);
    }
  }

}
