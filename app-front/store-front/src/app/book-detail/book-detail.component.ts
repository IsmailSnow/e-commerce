import { CartService } from './../services/cart-service.service';
import { Book } from './../models/book';
import { AppConst } from './../constants/app-const';
import { Http } from '@angular/http';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { BookService } from './../services/book.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  private bookId: number;
  private book: Book = new Book();
  private serverPath = AppConst.serverPath;
  private numberList: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9];
  private qty: number;

  private addBookSuccess: boolean = false;
  private notEnoughStock: boolean = false;
  constructor(private bookService: BookService, private router: Router, private http: Http, private route: ActivatedRoute,private cartService:CartService) { }


    onAddToCart() {
    this.cartService.addItem(this.bookId, this.qty).subscribe(
      res => {
        this.addBookSuccess=true;
        this.notEnoughStock=false;
        this.bookService.getBook(this.bookId).subscribe(res=>{
          this.book=res.json();
        },error=>{
          console.log(error.text())
        });
      },
      err => {
        this.notEnoughStock=true;
        this.addBookSuccess=false;  
    }
    );

  }


  ngOnInit() {
    this.route.params.forEach((params:Params)=> {
      this.bookId= Number.parseInt(params['id'])
    });
    this.bookService.getBook(this.bookId).subscribe(res=>{
              this.book=res.json();
    },error=>{
              console.log(error);
    });
    this.qty=1;
  }

}
