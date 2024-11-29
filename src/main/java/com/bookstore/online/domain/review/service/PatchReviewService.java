package com.bookstore.online.domain.review.service;

import com.bookstore.online.domain.review.dto.request.PatchReviewRequestDto;
import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatchReviewService {

  private final ReviewRepository reviewRepository;

  // 리뷰 수정하기
  public void patchReview(PatchReviewRequestDto dto, Integer reviewNumber, String userId) {
    ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
    reviewEntity.patch(dto);
    reviewRepository.save(reviewEntity);
  }

}
