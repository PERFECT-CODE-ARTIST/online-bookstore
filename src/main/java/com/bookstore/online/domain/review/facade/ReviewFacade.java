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
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewFacade {

  private final PostReviewService postReviewService;
  private final GetReviewService getReviewService;
  private final PatchReviewService patchReviewService;
  private final DeleteReviewService deleteReviewService;

  // 리뷰 작성
  public ResponseEntity<ResponseDto> postReview(PostReviewRequestDto dto, String userId) {
    try {
      ReviewEntity reviewEntity = new ReviewEntity(dto, userId);
      postReviewService.postReview(reviewEntity);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
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
