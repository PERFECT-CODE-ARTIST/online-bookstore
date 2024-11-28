package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteReviewService {

  private final ReviewRepository reviewRepository;

  // 리뷰 삭제
  public void deleteReview(Integer reviewNumber, String userId) {
    ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
    if (reviewEntity == null) return;

    reviewRepository.delete(reviewEntity);
  }

}
