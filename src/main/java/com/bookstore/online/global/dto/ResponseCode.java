package com.bookstore.online.global.dto;

public interface ResponseCode {
  String SUCCESS = "SU";
  String DATABASE_ERROR = "DBE";
  String VALIDATION_FAIL = "VF";

  String DUPLICATED_USER_ID = "DI";
  String DUPLICATED_USER_EMAIL = "DE";

  String NO_EXIST_USER_ID = "NI";
  String NO_EXIST_CATEGORY = "NC";
  String NO_EXIST_DISCOUNT = "ND";
  String NO_EXIST_ORDER_CODE = "NO";
  String NO_EXIST_BOOK = "NB";
  String NO_EXIST_REVIEW_NUMBER = "NR";
  String NO_EXIST_ORDER_PRICE = "NEOP";
  String LACK_OF_QUANTITY = "LOQ";

  String SIGN_IN_FAIL = "SF";

  String TOKEN_CREATE_FAIL = "TCF";

  String AUTHENTICATION_FAIL = "AF";

  String NO_PERMISSION = "NP";
}
