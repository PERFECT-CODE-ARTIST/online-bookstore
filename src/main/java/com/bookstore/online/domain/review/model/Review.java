package com.bookstore.online.domain.review.model;

// redis에 활용할 리뷰 모델

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review implements Serializable {

  private Integer reviewNumber;
  private Integer bookNumber;
  private String userId;
  private String rating;
  private String comment;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

}
