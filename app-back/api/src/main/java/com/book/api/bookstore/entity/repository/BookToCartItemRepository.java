package com.book.api.bookstore.entity.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.api.bookstore.entity.BookToCartItem;
import com.book.api.bookstore.entity.CartItem;

@Repository
@Transactional
public interface BookToCartItemRepository extends JpaRepository<BookToCartItem, Long>{
	void deleteByCartItem(CartItem cartItem);

}
