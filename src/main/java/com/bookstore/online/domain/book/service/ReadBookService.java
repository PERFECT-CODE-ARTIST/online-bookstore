package com.bookstore.online.domain.book.service;

import com.bookstore.online.domain.book.entity.BooksEntity;
import com.bookstore.online.domain.book.entity.repository.BooksRepository;
import com.bookstore.online.domain.book.entity.resultSet.GetBookOrderCountResultSet;
import com.bookstore.online.domain.book.entity.resultSet.GetUserOrderPurchasedBookResultSet;
//import com.bookstore.online.domain.orders.OrdersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadBookService {

  private final BooksRepository booksRepository;
//  private final OrdersRepository ordersRepository;

  public List<BooksEntity> findCategoryNumber(Integer categoryNumber) {
    return booksRepository.findByCategoryNumber(categoryNumber);
  }

  public BooksEntity findBookNumber(Integer bookNumber) {
    System.out.println(bookNumber);
    return booksRepository.findByBookNumber(bookNumber);
  }

  public List<BooksEntity> BookList() {
    return booksRepository.findAll();
  }

  public List<BooksEntity> searchBookList(Integer categoryNumber, String orderSet) {
    return booksRepository.getBookList(categoryNumber, orderSet);
  }

  public List<BooksEntity> findBookDiscountList() {
    return booksRepository.getBookDisCountList();
  }

  //추후 추가 기능
//  public List<GetBookOrderCountResultSet> bookOrderCount() {
//    return booksRepository.getBookOrderCountList();
//  }

  public List<BooksEntity> resentlyBookList(){
    return booksRepository.getRecentlyBookList();
  }

//  public GetUserOrderPurchasedBookResultSet userPurchasedBookList(String userId){
//    return booksRepository.PurchasedCategoryBookList(userId);
//  }

}
