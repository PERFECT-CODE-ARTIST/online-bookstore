package com.bookstore.online.domain.review.repository;

// 리뷰 리포지토리

import com.bookstore.online.domain.review.entity.ReviewEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

  // 유저가 이미 작성한 리뷰인지 확인 (사용자, 책 번호)
  boolean existsByBookNumberAndUserId(Integer bookNumber, String userId);

  // 리뷰 번호 찾기
  ReviewEntity findByReviewNumber(Integer reviewNumber);

  // 리뷰 리스트 불러오기에 사용할 쿼리 (책 기준, 한 책에 대한 리뷰들)
  List<ReviewEntity> findByBookNumberOrderByReviewNumberDesc(Integer bookNumber);

  // 리뷰 리스트 불러오기에 사용할 쿼리 (유저 기준, 한 유저의 리뷰들)
  List<ReviewEntity> findByUserIdOrderByReviewNumberDesc(String userId);

}
