package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostReviewService {
  private final ReviewRepository reviewRepository;

  // 리뷰 작성
  public void postReview(ReviewEntity reviewEntity) {

    // 리뷰가 이미 존재하는지 확인
    boolean isExistReview = reviewRepository.existsByBookNumberAndUserId(
        reviewEntity.getBookNumber(), reviewEntity.getUserId());
    if (isExistReview) {
      throw new IllegalArgumentException("이미 해당 책에 대한 리뷰를 작성했습니다.");
    }

    // DB에 저장
    reviewRepository.save(reviewEntity);

  }

}
