package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReviewService {

  private final ReviewRepository reviewRepository;

  // 리뷰 작성
  public void postReview(ReviewEntity reviewEntity) {
    boolean isExistReview = reviewRepository.existsByBookNumberAndUserId(
        reviewEntity.getBookNumber(), reviewEntity.getUserId());
//    System.out.println("Review exists: " + isExistReview); 에러 확인용
    if (isExistReview) {
      throw new IllegalArgumentException("이미 해당 책에 대한 리뷰를 작성했습니다.");
    }
    reviewRepository.save(reviewEntity);
  }

}
