package com.bookstore.online.domain.review.dto.response;

// 리뷰 리스트를 불러오기 위한 응답 dto

import com.bookstore.online.domain.review.common.object.ReviewListObject;
import com.bookstore.online.domain.review.entity.ReviewEntity;
import com.bookstore.online.global.dto.ResponseCode;
import com.bookstore.online.global.dto.ResponseDto;
import com.bookstore.online.global.dto.ResponseMessage;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetReviewListResponseDto extends ResponseDto {

  private List<ReviewListObject> reviewListObjects;

  private GetReviewListResponseDto(List<ReviewEntity> reviewEntities) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.reviewListObjects = ReviewListObject.getList(reviewEntities);
  }

  public static ResponseEntity<? super GetReviewListResponseDto> success(List<ReviewEntity> reviewEntities) {
    GetReviewListResponseDto responseBody = new GetReviewListResponseDto(reviewEntities);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
