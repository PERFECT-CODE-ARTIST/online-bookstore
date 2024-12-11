package com.bookstore.online.domain.review.facade;

import com.bookstore.online.domain.review.dto.request.PatchReviewRequestDto;
import com.bookstore.online.domain.review.dto.request.PostReviewRequestDto;
import com.bookstore.online.domain.review.dto.response.GetReviewListResponseDto;
import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.service.DeleteReviewService;
import com.bookstore.online.domain.review.service.GetReviewService;
import com.bookstore.online.domain.review.service.PatchReviewService;
import com.bookstore.online.domain.review.service.PostReviewService;
import com.bookstore.online.global.dto.ResponseDto;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewFacade {

  private final PostReviewService postReviewService;
  private final GetReviewService getReviewService;
  private final PatchReviewService patchReviewService;
  private final DeleteReviewService deleteReviewService;
  
  private final RedisTemplate<String, String> redisTemplate;
  private static final Integer LOCK_EXPIRE_SECONDS = 10; // 10초

  // 리뷰 작성
  public ResponseEntity<ResponseDto> postReview(PostReviewRequestDto dto, String userId) {
    String lockKey = "review:lock" + userId + ":" + dto.getBookNumber(); // review:lock:qwer1234:1234

    // redis 락 확인
    Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", Duration.ofSeconds(LOCK_EXPIRE_SECONDS));
    if (Boolean.FALSE.equals(isLocked)) throw new IllegalArgumentException("이미 리뷰를 작성 중이거나 존재합니다.");

    try {
      // 작성된 리뷰인지 검증
      boolean isExistsReview = postReviewService.isExistsReview(dto.getBookNumber(), userId);
      if (isExistsReview) throw new IllegalArgumentException("이미 작성된 리뷰입니다.");

      // 구매한 책인지 검증
      boolean isExistsPurchase = postReviewService.isExistsPurchase(userId, dto.getOrderNumber());
      if (!isExistsPurchase) throw new IllegalArgumentException("구매한 책에 대해서만 리뷰가 가능합니다.");

      // 리뷰 저장
      ReviewEntity reviewEntity = new ReviewEntity(dto, userId);
      postReviewService.postReview(reviewEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    } finally {
      // 락 해제
      redisTemplate.delete(lockKey);
    }

    return ResponseDto.success();
  }

  // 리뷰 조회 (책 기준)
  public ResponseEntity<? super GetReviewListResponseDto> getReviewsByBookNumber(Integer bookNumber) {
    List<ReviewEntity> reviewEntities = new ArrayList<>();

    try {
      reviewEntities = getReviewService.getReviewsOfBookNumber(bookNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      ResponseDto.databaseError();
    }

    return GetReviewListResponseDto.success(reviewEntities);
  }

  // 리뷰 조회 (유저 기준)
  public ResponseEntity<? super GetReviewListResponseDto> getReviewsByUserId(String userId) {
    List<ReviewEntity> reviewEntities = new ArrayList<>();

    try {
      reviewEntities = getReviewService.getReviewsOfUserId(userId);

    } catch (Exception exception) {
      exception.printStackTrace();
      ResponseDto.databaseError();
    }

    return GetReviewListResponseDto.success(reviewEntities);
  }

  // 리뷰 수정
  public ResponseEntity<ResponseDto> patchReview(PatchReviewRequestDto dto, Integer reviewNumber, String userId) {

    try {
      ReviewEntity reviewEntity = patchReviewService.findReviewNumber(reviewNumber);
      if (!reviewEntity.getUserId().equals(userId)) throw new RuntimeException("수정 권한이 없습니다.");

      reviewEntity.patch(dto);
      patchReviewService.patchReview(reviewEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      ResponseDto.databaseError();
    }

    return ResponseDto.success();
  }

  // 리뷰 삭제
  public ResponseEntity<ResponseDto> deleteReview(Integer reviewNumber, String userId) {
    try {
      ReviewEntity reviewEntity = deleteReviewService.findReviewNumber(reviewNumber);
      if (reviewEntity == null) throw new Error("존재하지 않는 리뷰입니다.");

      if (!reviewEntity.getUserId().equals(userId)) throw new Error("삭제 권한이 없습니다.");

      deleteReviewService.deleteReview(reviewEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      ResponseDto.databaseError();
    }

    return ResponseDto.success();
  }

}
