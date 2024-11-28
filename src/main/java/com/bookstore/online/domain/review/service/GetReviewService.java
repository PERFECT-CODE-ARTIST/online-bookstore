package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetReviewService {

  private final ReviewRepository reviewRepository;

  // 리뷰 불러오기 (책 기준)
  public List<ReviewEntity> getReviewOfBookNumber(Integer bookNumber) {
    if (bookNumber == null || bookNumber <= 0) {
      throw new IllegalArgumentException("유효하지 않은 책번호입니다.");
    }
    return reviewRepository.findByBookNumberOrderByReviewNumberDesc(bookNumber);
  }

  // 리뷰 불러오기 (유저 기준)
  public List<ReviewEntity> getReviewOfUserId(String userId) {
    return reviewRepository.findByUserIdOrderByReviewNumberDesc(userId);
  }

}
