import { Router } from '@angular/router';
import { BookService } from './../service/book.service';
import { Book } from './../models/book';
import { Component, OnInit } from '@angular/core';
import {MatDialog,MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  private selectedBook:Book;
  private checked : boolean;
  private bookList: Book[];
  private allChecked : boolean;
  private removeBookList : Book[] = new Array();


  constructor(private bookService:BookService , private router:Router, public dialog:MatDialog) { }

  ngOnInit() {
    this.getBookList();
  }
    onSelect(book:Book) {
    console.log("the book is "+book);
    this.selectedBook=book;
    this.router.navigate(['/viewBook', this.selectedBook.id]);
  }

  openDialog(book:Book) {
    let dialogRef = this.dialog.open(DialogResultExampleDialog);
    dialogRef.afterClosed().subscribe(
      result => {
        console.log(result);
        if(result=="yes") {
          this.bookService.removeBook(book.id).subscribe(
            res => {
              console.log(res);
              this.getBookList();
            }, 
            err => {
              console.log(err);
            }
          );
        }
      }
    );
  }

  getBookList(){
      this.bookService.getBookList().subscribe(res=>{
      console.log(res.json());
      this.bookList = res.json();
    },error=>{
          console.log(error);
    })
  }

  updateRemoveBookList(checked:boolean,book:Book){
    if(checked){
      this.removeBookList.push(book);
    }else{
      this.removeBookList.splice(this.removeBookList.indexOf(book),1);
    }
  }

  updateSelected(checked: boolean){
    if(checked){
      this.allChecked=true;
      this.removeBookList=this.bookList.slice();// to not lose information of the lest 
    }else{
      this.allChecked=false;
      this.removeBookList=[];
    }
  }
  removeSelectedBooks(){
    let dialogRef = this.dialog.open(DialogResultExampleDialog);
    dialogRef.afterClosed().subscribe(
      result => {
        console.log(result);
        if(result=="yes") {
          for(let book of this.removeBookList){
               this.bookService.removeBook(book.id).subscribe(
            res => {
              console.log(res);
            }, 
            err => {
              console.log(err);
            }
          );
        }
        location.reload(); //this.getBookList();  on peut utiliser ca aussi / le but location.reload est de rafraichir la list des book list
        }
      }
    );

  }
}




@Component({
  selector: 'dialog-result-example-dialog',
  templateUrl: './dialog-result-example-dialog.html'
})
export class DialogResultExampleDialog {
  constructor(public dialogRef: MatDialogRef<DialogResultExampleDialog>) {}
}
