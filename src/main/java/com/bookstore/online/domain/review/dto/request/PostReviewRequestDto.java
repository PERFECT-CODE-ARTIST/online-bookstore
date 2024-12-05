package com.bookstore.online.domain.review.dto.request;

// 리뷰 작성 요청을 위한 dto

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostReviewRequestDto {

  @NotNull
  private Integer bookNumber;

  @NotBlank(message = "최소 1점을 선택해야 합니다.")
  @Min(1)
  @Max(5)
  private String rating;

  @NotBlank
  private String comment;

  @NotNull
  private LocalDateTime createdAt;

}
