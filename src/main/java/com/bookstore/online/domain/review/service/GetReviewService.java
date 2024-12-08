package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetReviewService {

  private final ReviewRepository reviewRepository;

  // 리뷰 조회 (책 기준)
  public List<ReviewEntity> getReviewsOfBookNumber(Integer bookNumber) {
    return reviewRepository.findByBookNumberOrderByReviewNumberDesc(bookNumber);
  }

  // 리뷰 조회 (유저 기준)
  public List<ReviewEntity> getReviewsOfUserId(String userId) {
    return reviewRepository.findByUserIdOrderByReviewNumberDesc(userId);
  }

}
