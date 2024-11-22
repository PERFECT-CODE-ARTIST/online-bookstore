package com.bookstore.online.domain.review.common.object;

// 리뷰를 불러오기 위한 리뷰 리스트 객체

import com.bookstore.online.domain.review.entity.ReviewEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ReviewListObject {

  private Integer reviewNumber;
  private Integer bookNumber;
  private String userId;
  private String rating;
  private String comment;

  private ReviewListObject(ReviewEntity reviewEntity) {
    this.reviewNumber = reviewEntity.getReviewNumber();
    this.bookNumber = reviewEntity.getBookNumber();
    this.userId = reviewEntity.getUserId();
    this.rating = reviewEntity.getRating();
    this.comment = reviewEntity.getComment();
  }

  public static List<ReviewListObject> getList(List<ReviewEntity> reviewEntities) {
    List<ReviewListObject> reviewListObjects = new ArrayList<>();

    for (ReviewEntity reviewEntity: reviewEntities) {
      ReviewListObject reviewListObject = new ReviewListObject(reviewEntity);
      reviewListObjects.add(reviewListObject);
    }

    return reviewListObjects;

  }

}
