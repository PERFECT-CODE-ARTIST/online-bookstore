package com.bookstore.online.domain.review.entity;

// 리뷰 엔터티

import com.bookstore.online.domain.review.dto.request.PatchReviewRequestDto;
import com.bookstore.online.domain.review.dto.request.PostReviewRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reviews")
@Table(name = "reviews")
public class ReviewEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter
  private Integer reviewNumber;

  @Setter
  private Integer bookNumber;
  @Setter
  private String userId;
  @Setter
  private String rating;
  @Setter
  private String comment;

  // 리뷰 작성 시 데이터를 삽입하기 위한 생성자
  public ReviewEntity(PostReviewRequestDto dto, String userId) {
    this.bookNumber = dto.getBookNumber();
    this.rating = dto.getRating();
    this.comment = dto.getComment();
    this.userId = userId;
  }

  // 리뷰 수정을 위한 생성자
  public void patch(PatchReviewRequestDto dto) {
    this.rating = dto.getRating();
    this.comment = dto.getComment();
  }

}
