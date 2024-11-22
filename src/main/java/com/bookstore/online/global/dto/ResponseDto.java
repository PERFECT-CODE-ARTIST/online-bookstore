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

  public static ResponseEntity<ResponseDto> success() {
    ResponseDto responseBody = new ResponseDto("SU", "success");
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> databaseError() {
    ResponseDto responseBody = new ResponseDto("DBA", "DATABASE_ERROR");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> validationFailed() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.VALIDATION_FAIL,
        ResponseMessage.VALIDATION_FAIL);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> duplicatedUserId() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATED_USER_ID,
        ResponseMessage.DUPLICATED_USER_ID);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> duplicatedUserEmail() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATED_USER_EMAIL,
        ResponseMessage.DUPLICATED_USER_EMAIL);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noExistUserId() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_USER_ID,
        ResponseMessage.NO_EXIST_USER_ID);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noExistCategoryByBook() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_CATEGORY,
        ResponseMessage.NO_EXIST_CATEGORY);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noExistDiscountByBook() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_DISCOUNT,
        ResponseMessage.NO_EXIST_DISCOUNT);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noExistOrderCode() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_ORDER_CODE,
        ResponseMessage.NO_EXIST_ORDER_CODE);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noExistBookId() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_BOOK,
        ResponseMessage.NO_EXIST_BOOK);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noExistReviewNumber() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_REVIEW_NUMBER,
        ResponseMessage.NO_EXIST_REVIEW_NUMBER);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> signInFailed() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.SIGN_IN_FAIL,
        ResponseMessage.SIGN_IN_FAIL);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> tokenCreationFailed() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.TOKEN_CREATE_FAIL,
        ResponseMessage.TOKEN_CREATE_FAIL);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> authenticationFail() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.AUTHENTICATION_FAIL,
        ResponseMessage.AUTHENTICATION_FAIL);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noPermission() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NO_PERMISSION,
        ResponseMessage.NO_PERMISSION);
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> noExistOrderPrice() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_ORDER_PRICE,
        ResponseMessage.NO_EXIST_ORDER_PRICE);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }

  public static ResponseEntity<ResponseDto> lackOfQuantity() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.LACK_OF_QUANTITY,
        ResponseMessage.LACK_OF_QUANTITY);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
  }
}