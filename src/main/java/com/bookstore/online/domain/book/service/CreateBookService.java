package com.bookstore.online.domain.book.service;

import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.entity.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateBookService {

  private final BooksRepository booksRepository;

  public void createBook(BooksEntity booksEntity) {
    booksRepository.save(booksEntity);
  }

}
