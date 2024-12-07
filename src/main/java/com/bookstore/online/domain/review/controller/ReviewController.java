package com.bookstore.online.domain.review.controller;

import com.bookstore.online.domain.review.dto.request.PatchReviewRequestDto;
import com.bookstore.online.domain.review.dto.request.PostReviewRequestDto;
import com.bookstore.online.domain.review.dto.response.GetReviewListResponseDto;
import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.service.ReviewService;
import com.bookstore.online.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  // 리뷰 작성
  @Operation(summary = "리뷰 작성", description = "책에 대해 1번만 리뷰를 작성합니다.")
  @PostMapping("/review-write")
  public ResponseEntity<ResponseDto> postReview(
      @RequestBody @Valid PostReviewRequestDto requestBody,
      @AuthenticationPrincipal String userId
  ) {
      reviewService.postReview(requestBody, userId);
      return  ResponseDto.success();
  }

  // 리뷰 불러오기 (책 기준)
  @Operation(summary = "리뷰 조회", description = "책을 기준으로 모든 리뷰 조회")
  @GetMapping("/book-reviews/{bookNumber}")
  public ResponseEntity<? super GetReviewListResponseDto> getReviewListOfBookNumber(
      @PathVariable("bookNumber") Integer bookNumber
  ) {
    // 리뷰 리스트 조회
    List<ReviewEntity> reviewEntities = reviewService.getReviewsOfBookNumber(bookNumber);

    // dto 변환 및 응답 반환
    return GetReviewListResponseDto.success(reviewEntities);
  }

  // 리뷰 불러오기 (유저 기준)
  @Operation(summary = "리뷰 조회", description = "유저를 기준으로 본인이 작성한 모든 리뷰 조회")
  @GetMapping("/user-reviews")
  public ResponseEntity<? super GetReviewListResponseDto> getReviewListOfUserId(
      @AuthenticationPrincipal String userId
  ) {
    List<ReviewEntity> reviewEntities = reviewService.getReviewsOfUserId(userId);

    return GetReviewListResponseDto.success(reviewEntities);
  }

  // 리뷰 수정
  @Operation(summary = "리뷰 수정", description = "선택된 리뷰번호를 기준으로 점수와 내용을 수정")
  @PatchMapping("/review-update/{reviewNumber}")
  public ResponseEntity<ResponseDto> patchReview(
      @RequestBody @Valid PatchReviewRequestDto requestBody,
      @PathVariable("reviewNumber") Integer reviewNumber,
      @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = reviewService.patchReview(requestBody, reviewNumber, userId);
    return response;
  }

  // 리뷰 삭제
  @Operation(summary = "리뷰 삭제", description = "선택된 리뷰번호의 리뷰를 삭제")
  @DeleteMapping("/delete-review/{reviewNumber}")
  public ResponseEntity<ResponseDto> deleteReview(
      @PathVariable("reviewNumber") Integer reviewNumber,
      @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = reviewService.deleteReview(reviewNumber, userId);
    return response;
  }

}
