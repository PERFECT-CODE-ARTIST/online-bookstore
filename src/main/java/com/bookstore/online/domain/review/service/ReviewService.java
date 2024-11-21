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
    if (reviewRepository.existsByBookNumberAndUserId(reviewEntity.getBookNumber(), reviewEntity.getUserId())) {
      throw new IllegalArgumentException("이미 해당 책에 대한 리뷰를 작성했습니다.");
    }
    reviewRepository.save(reviewEntity);
  }

  // 리뷰 삭제
  public void deleteReview(Integer reviewNumber) {
    ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
    if (reviewEntity == null) return;

    reviewRepository.delete(reviewEntity);
  }

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

  // 리뷰 수정하기
  public void patchReview(PatchReviewRequestDto dto, Integer reviewNumber, String userId) {
    ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
    reviewEntity.patch(dto);
    reviewRepository.save(reviewEntity);
  }
}
