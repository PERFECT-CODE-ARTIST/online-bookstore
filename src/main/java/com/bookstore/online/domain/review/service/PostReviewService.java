package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.orders.entity.repository.OrdersRepository;
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
  private final OrdersRepository ordersRepository;

  // 존재하는 리뷰인지 검증
  public boolean isExistsReview(Integer bookNumber, String userId) {
    return reviewRepository.existsByBookNumberAndUserId(bookNumber, userId);
  }

  // 유저가 구매한 책인지 검증
  public boolean isExistsPurchase(String userId, Integer orderNumber) {
    return ordersRepository.existsByUserIdAndOrderNumber(userId, orderNumber);
  }

  // 리뷰 작성
  public void postReview(ReviewEntity reviewEntity) {
    reviewRepository.save(reviewEntity);
  }

}
