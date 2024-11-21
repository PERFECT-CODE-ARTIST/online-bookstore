package com.bookstore.online.domain.review.facade;

import com.bookstore.online.domain.review.dto.request.PatchReviewRequestDto;
import com.bookstore.online.domain.review.dto.request.PostReviewRequestDto;
import com.bookstore.online.domain.review.dto.response.GetReviewListResponseDto;
import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.service.ReviewService;
import com.bookstore.online.global.dto.ResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewFacade {

  private final ReviewService reviewService;

  // 리뷰 작성
  public ResponseEntity<ResponseDto> postReview(PostReviewRequestDto dto, String userId) {

    try {

      ReviewEntity reviewEntity = new ReviewEntity(dto, userId);
      reviewService.postReview(reviewEntity);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return ResponseDto.success();
  }

  // 리뷰 삭제
  public ResponseEntity<ResponseDto> deleteReview(Integer reviewNumber) {

    try {

      reviewService.deleteReview(reviewNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success();

  }

  // 리뷰 불러오기(책 기준)
  public ResponseEntity<ResponseDto> getReviewListOfBookNumber(Integer bookNumber) {

    try {

      reviewService.getReviewOfBookNumber(bookNumber);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetReviewListResponseDto.success();
  }

  // 리뷰 불러오기(유저 기준)
  public ResponseEntity<? super GetReviewListResponseDto> getReviewListOfUserId(String userId) {

    List<ReviewEntity> reviewEntities = new ArrayList<>();

    try {

      reviewEntities = reviewService.getReviewOfUserId(userId);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetReviewListResponseDto.success(reviewEntities);
  }

  // 리뷰 수정하기
  public ResponseEntity<ResponseDto> patchReview(PatchReviewRequestDto dto, Integer reviewNumber, String userId) {

    try {

      reviewService.patchReview(dto, reviewNumber, userId);

    } catch (Exception exception) {
        exception.printStackTrace();
        return ResponseDto.databaseError();
    }

    return ResponseDto.success();
  }

}
