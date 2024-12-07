package com.bookstore.online.global.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class StartMainController {
  @GetMapping("")
  public String start() {
    return "Online Book Store Server start";
  }
}
