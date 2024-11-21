package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.dto.request.PatchReviewRequestDto;
import com.bookstore.online.domain.review.dto.response.GetReviewListResponseDto;
import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;

  // 리뷰 작성
  public void postReview(ReviewEntity reviewEntity) {
    reviewRepository.save(reviewEntity);
  }

  // 리뷰 삭제
  public void deleteReview(Integer reviewNumber) {
    ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
    if (reviewEntity == null) return;

    reviewRepository.delete(reviewEntity);
  }

  // 리뷰 불러오기 (책 기준)
  public void getReviewOfBookNumber(Integer bookNumber) {
    List<ReviewEntity> reviewEntities = new ArrayList<>();
    reviewRepository.findByBookNumberOrderByReviewNumberDesc(bookNumber);
    GetReviewListResponseDto.success(reviewEntities);
  }

  // 리뷰 불러오기 (유저 기준)
  public void getReviewOfUserId(String userId) {
    List<ReviewEntity> reviewEntities = new ArrayList<>();
    reviewRepository.findByUserIdOrderByReviewNumberDesc(userId);
    GetReviewListResponseDto.success(reviewEntities);
  }

  // 리뷰 수정하기
  public void patchReview(PatchReviewRequestDto dto, Integer reviewNumber, String userId) {
    ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
    reviewEntity.patch(dto);
    reviewRepository.save(reviewEntity);
  }
}