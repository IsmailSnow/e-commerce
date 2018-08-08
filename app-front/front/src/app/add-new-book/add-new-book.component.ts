import { UploadImageService } from './../service/upload-image.service';
import { BookService } from './../service/book.service';
import { Book } from './../models/book';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-new-book',
  templateUrl: './add-new-book.component.html',
  styleUrls: ['./add-new-book.component.css']
})
export class AddNewBookComponent implements OnInit {


  private newBook : Book = new Book();
  private bookAdded: boolean = false;


  constructor(private bookService:BookService, private uploadImageService:UploadImageService) { }
  ngOnInit() {
    this.initialize();

  }
  onSubmit(){
      this.bookService.sendBook(this.newBook).subscribe(res=> {
      this.uploadImageService.upload(JSON.parse(JSON.parse(JSON.stringify(res))._body).id);
      this.bookAdded=true;
      this.newBook = new Book();
      this.initialize();
    },
    error => {
      console.log(error);
    })
  }
  initialize(){
    this.newBook.active=true;
    this.newBook.category="Management";
    this.newBook.language="english";
    this.newBook.format="paperback";
  }

}
