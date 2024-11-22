package com.bookstore.online.domain.book.service;

import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.entity.repository.BooksRepository;
import com.bookstore.online.domain.book.entity.resultSet.GetBookOrderCountResultSet;
import com.bookstore.online.domain.book.entity.resultSet.GetUserOrderPurchasedBookResultSet;
//import com.bookstore.online.domain.orders.OrdersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadBookService {

  private final BooksRepository booksRepository;

  public BooksEntity findBookNumber(Integer bookNumber) {
    System.out.println(bookNumber);
    return booksRepository.findByBookNumber(bookNumber);
  }

  public List<BooksEntity> BookList(Pageable pageable) {
    return booksRepository.findByOrderByBookNameAsc(pageable);
  }

  public List<BooksEntity> searchBookList(Integer categoryNumber, String orderSet, Integer paging) {
    return booksRepository.getBookList(categoryNumber, orderSet, paging);
  }

  public List<BooksEntity> findBookDiscountList(Integer paging) {
    return booksRepository.getBookDisCountList(paging);
  }

  //추후 추가 기능
//  public List<GetBookOrderCountResultSet> bookOrderCount() {
//    return booksRepository.getBookOrderCountList();
//  }

  public List<BooksEntity> resentlyBookList() {
    return booksRepository.getRecentlyBookList();
  }

//  public GetUserOrderPurchasedBookResultSet userPurchasedBookList(String userId){
//    return booksRepository.PurchasedCategoryBookList(userId);
//  }

}
