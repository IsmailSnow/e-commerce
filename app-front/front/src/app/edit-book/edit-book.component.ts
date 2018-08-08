import { Router, ActivatedRoute,Params } from '@angular/router';
import { BookService } from './../service/book.service';
import { UploadImageService } from './../service/upload-image.service';
import { Book } from './../models/book';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html',
  styleUrls: ['./edit-book.component.css']
})
export class EditBookComponent implements OnInit {

  private bookId:number;
  private book:Book = new Book();
  private bookUpdated : boolean =false;

  constructor(private uploadImageService: UploadImageService,private bookService:BookService,private router:Router, private route:ActivatedRoute) { }

  ngOnInit() {
    this.route.params.forEach((params:Params)=>{
      this.bookId=Number.parseInt(params['id']);
    });
    this.bookService.getBook(this.bookId).subscribe(res=>{
        this.book=res.json();
    },
    error=>{console.log(error);
    });
  }

  onSubmit(){
    this.bookService.editBook(this.book).subscribe(res=>{
      this.uploadImageService.modify(JSON.parse(JSON.parse(JSON.stringify(res))._body).id);
      this.bookUpdated=true;

    },error=>{console.log(error);})
  }

  openDialog(book:Book){

  }

}
