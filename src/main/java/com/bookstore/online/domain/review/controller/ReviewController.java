package com.bookstore.online.domain.review.controller;

import com.bookstore.online.domain.review.dto.request.PatchReviewRequestDto;
import com.bookstore.online.domain.review.dto.request.PostReviewRequestDto;
import com.bookstore.online.domain.review.dto.response.GetReviewListResponseDto;
import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.facade.ReviewFacade;
import com.bookstore.online.domain.review.service.CacheService;
import com.bookstore.online.domain.review.service.PostReviewService;
import com.bookstore.online.global.dto.ResponseDto;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
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

  private final ReviewFacade reviewFacade;
  private final CacheService cacheService;
  private final PostReviewService postReviewService;

  // 리뷰 작성
  @PostMapping("/review-write")
  public ResponseEntity<ResponseDto> postReview(
      @RequestBody @Valid ReviewEntity reviewEntity
  ) {
    reviewEntity.setCreatedAt(LocalDateTime.now());
    postReviewService.postReview(reviewEntity);
    return ResponseDto.success();
  }

  // 리뷰 삭제
  @DeleteMapping("/delete-review/{reviewNumber}")
  public ResponseEntity<ResponseDto> deleteReview(
      @PathVariable("reviewNumber") Integer reviewNumber,
      @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = reviewFacade.deleteReview(reviewNumber, userId);
    return response;
  }

  // 리뷰 불러오기 (책 기준)
  @GetMapping("/book-reviews/{bookNumber}")
  public ResponseEntity<? super GetReviewListResponseDto> getReviewListOfBookNumber(
      @PathVariable("bookNumber") Integer bookNumber
  ) {
    ResponseEntity<? super GetReviewListResponseDto> response = reviewFacade.getReviewListOfBookNumber(bookNumber);
    return response;
  }

  // 리뷰 불러오기 (유저 기준)
  @GetMapping("/user-reviews")
  public ResponseEntity<? super GetReviewListResponseDto> getReviewListOfBookNumber(
      @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<? super GetReviewListResponseDto> response = reviewFacade.getReviewListOfUserId(userId);
    return response;
  }

  // 리뷰 수정
  @PatchMapping("/review-update/{reviewNumber}")
  public ResponseEntity<ResponseDto> patchReview(
      @RequestBody @Valid PatchReviewRequestDto requestBody,
      @PathVariable("reviewNumber") Integer reviewNumber,
      @AuthenticationPrincipal String userId
  ) {
    ResponseEntity<ResponseDto> response = reviewFacade.patchReview(requestBody, reviewNumber, userId);
    return response;
  }

}
