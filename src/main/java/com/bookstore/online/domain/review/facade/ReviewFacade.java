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

  // 리뷰 삭제
  public ResponseEntity<ResponseDto> deleteReview(Integer reviewNumber, String userId) {

    try {

      if (reviewNumber == null || reviewNumber <= 0) {
        throw new IllegalArgumentException("유효하지 않은 리뷰입니다.");
      }

      deleteReviewService.deleteReview(reviewNumber, userId);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success();

  }

  // 리뷰 불러오기(책 기준)
  public ResponseEntity<? super GetReviewListResponseDto> getReviewListOfBookNumber(Integer bookNumber) {

    try {

      if (bookNumber == null || bookNumber <= 0) {
        throw new IllegalArgumentException("유효하지 않은 책번호입니다.");
      }

      List<ReviewEntity> reviewEntities = getReviewService.getReviewOfBookNumber(bookNumber);
      return GetReviewListResponseDto.success(reviewEntities);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

  // 리뷰 불러오기(유저 기준)
  public ResponseEntity<? super GetReviewListResponseDto> getReviewListOfUserId(String userId) {

    List<ReviewEntity> reviewEntities = new ArrayList<>();

    try {

      reviewEntities = getReviewService.getReviewOfUserId(userId);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetReviewListResponseDto.success(reviewEntities);
  }

  // 리뷰 수정하기
  public ResponseEntity<ResponseDto> patchReview(PatchReviewRequestDto dto, Integer reviewNumber, String userId) {

    try {

      if (reviewNumber == null || reviewNumber <= 0) {
        throw new IllegalArgumentException("유효하지 않은 리뷰입니다.");
      }

      patchReviewService.patchReview(dto, reviewNumber, userId);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return ResponseDto.success();
  }

}
