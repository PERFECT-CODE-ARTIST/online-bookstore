package com.bookstore.online.domain.book.dto.response;

import com.bookstore.online.domain.book.object.Book;
import com.bookstore.online.global.dto.ResponseCode;
import com.bookstore.online.global.dto.ResponseDto;
import com.bookstore.online.global.dto.ResponseMessage;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
public class GetBookListResponseDto extends ResponseDto {
  private List<Book> bookList;

  public GetBookListResponseDto(List<Book> bookList) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.bookList = bookList;
  }

  public static ResponseEntity<GetBookListResponseDto> success(List<Book> bookList) {
    GetBookListResponseDto responseBody = new GetBookListResponseDto(bookList);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
