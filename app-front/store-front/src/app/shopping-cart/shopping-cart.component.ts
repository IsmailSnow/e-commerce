import { CartService } from './../services/cart-service.service';
import { Router } from '@angular/router';
import { ShoppingCart } from './../models/shopping-cart';
import { CartItem } from './../models/cart-item';
import { Book } from './../models/book';
import { AppConst } from './../constants/app-const';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  private serverPath = AppConst.serverPath;
  private selectedBook: Book;
  private cartItemList: CartItem[] = [];
  private cartItemNumber: number;
  private shoppingCart: ShoppingCart = new ShoppingCart();
  private cartItemUpdated: boolean;
  private emptyCart: boolean;
  private notEnoughStock: boolean;

  constructor(private router: Router, private cartService: CartService) { }

  ngOnInit() {
    this.getCartItemList();
    this.getShoppingCart();
  }

  onSelect(book: Book) {
    this.selectedBook = book;
    this.router.navigate(['/bookDetail', this.selectedBook.id]);
  }

  onRemoveCartItem(cartItem: CartItem) {
    this.cartService.removeCartItem(cartItem.id).subscribe(res => {
      console.log(res.text());
      this.getCartItemList();
      this.getShoppingCart();
    }, error => {
      console.log(error.text());
    })
  }
  onUpdateCartItem(cartItem: CartItem) {
    this.cartService.updateCartItem(cartItem.id, cartItem.qty).subscribe(
      res => {
        console.log(res.text());
        this.cartItemUpdated = true;
        this.getShoppingCart();
      },
      error => {
        console.log(error.text());
      }
    )
  }

  getCartItemList() {
    this.cartService.getCartItemList().subscribe(
      res => {
  
        console.log("cartItemList"+res);
        this.cartItemList = res.json();
        this.cartItemNumber = this.cartItemList.length;
      },
      error => {
        console.log(error.text());
      }
    )
  }

  getShoppingCart() {
    this.cartService.getShoppingCart().subscribe(
      res => {
        this.shoppingCart = res.json();
      },
      error => {
        console.log(error.text());
      }
    )
  }

  onCheckout() {
    if (this.cartItemNumber == 0) {
      this.emptyCart = true;
    } else {
      for (let item of this.cartItemList) {
        if (item.qty > item.book.inStockNumber) {
          console.log("not enough stock on some item");
          this.notEnoughStock = true;
          return;
        }
      }

      // this.router.navigate('[/order]');
    }
  }








}
