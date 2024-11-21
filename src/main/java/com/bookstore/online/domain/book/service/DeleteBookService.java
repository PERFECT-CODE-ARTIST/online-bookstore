package com.bookstore.online.domain.book.service;

import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.entity.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteBookService {
  private final BooksRepository booksRepository;

  public void deleteBook(BooksEntity booksEntity) {

    booksRepository.delete(booksEntity);
  }
}
