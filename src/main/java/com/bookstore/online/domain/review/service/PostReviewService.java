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

  // 존재하는 리뷰인지 검증
  public boolean isExistsReview(Integer bookNumber, String userId) {
    return reviewRepository.existsByBookNumberAndUserId(bookNumber, userId);
  }

  // 리뷰 작성
  public void postReview(ReviewEntity reviewEntity) {
    reviewRepository.save(reviewEntity);
  }

}
