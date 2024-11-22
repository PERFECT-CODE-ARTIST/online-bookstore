package com.bookstore.online.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
  private String code;
  private String message;


  public static ResponseEntity<ResponseDto> success(){
    ResponseDto responseBody = new ResponseDto("SU", "success");
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> databaseError(){
    ResponseDto responseBody = new ResponseDto("DBA", "DATABASE_ERROR");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> notOrderNumber() {
    ResponseDto responseBody = new ResponseDto("NON","not order-number");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }
}
