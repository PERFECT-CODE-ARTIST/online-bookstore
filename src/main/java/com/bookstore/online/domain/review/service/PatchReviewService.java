package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.dto.request.PatchReviewRequestDto;
import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import com.bookstore.online.global.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatchReviewService {
  private final ReviewRepository reviewRepository;

  private final RedisTemplate<String, String> redisTemplate;
  private static final String REVIEW_KEY = "book:review";
  private final ObjectMapper objectMapper;

  // 리뷰 수정하기
  public ResponseEntity<ResponseDto> patchReview(PatchReviewRequestDto dto, Integer reviewNumber, String userId) {
    // 리뷰 존재 확인
    ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
    if (reviewEntity == null && reviewNumber <= 0) throw new IllegalArgumentException("리뷰가 존재하지 않습니다.");

    // 권한 확인
    if (!reviewEntity.getUserId().equals(userId)) throw new SecurityException("리뷰 수정 권한이 없습니다.");

    // DB에 수정 저장
    reviewEntity.patch(dto);
    reviewRepository.save(reviewEntity);

    // Redis에 수정된 리뷰 저장
    try {

      // redis에서 기존 리뷰 삭제 후 수정된 리뷰로 다시 넣어주기
      redisTemplate.delete(REVIEW_KEY);
      // 리뷰 객체 -> JSON으로 직렬화
      String reviewJson = objectMapper.writeValueAsString(reviewEntity);
      // 변환 후 Redis에 저장
      redisTemplate.opsForList().leftPush(REVIEW_KEY, reviewJson);

    } catch (Exception exception) {
      log.error("리뷰 수정 실패: {}", exception.getMessage());
      throw new RuntimeException("리뷰 수정 중 오류가 발생했습니다.", exception);
    }

    return ResponseDto.success();
  }

}
