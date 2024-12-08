package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatchReviewService {
  private final ReviewRepository reviewRepository;

  // 리뷰 번호 찾기
  public ReviewEntity findReviewNumber(Integer reviewNumber) {
    return reviewRepository.findByReviewNumber(reviewNumber);
  }

  // 리뷰 수정
  public void patchReview(ReviewEntity reviewEntity) {
    reviewRepository.save(reviewEntity);
  }

}
