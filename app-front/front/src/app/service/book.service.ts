import { Book } from './../models/book';
import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http:Http) { }


  sendBook(book:Book){
	  let url = "http://localhost:8080/book/add";
  	let headers = new Headers ({
      'Content-type' : 'application/json',
  		 'x-auth-token' : localStorage.getItem('xAuthToken')
  	});
  	return this.http.post(url,JSON.stringify(book), {headers: headers});
  }

  getBookList(){
	  let url = "http://localhost:8080/book/bookList";
  	let headers = new Headers ({
      'Content-type' : 'application/json',
  		 'x-auth-token' : localStorage.getItem('xAuthToken')
  	});
  	return this.http.get(url, {headers: headers});
  }
   getBook(id:number) {
  	let url = "http://localhost:8080/book/"+id;
    let headers = new Headers ({
      'Content-Type': 'application/json',
      'x-auth-token' : localStorage.getItem('xAuthToken')
    });
    return this.http.get(url, {headers: headers});
  }

  editBook(book:Book){
    let url = "http://localhost:8080/book/update";
  	let headers = new Headers ({
      'Content-type' : 'application/json',
  		 'x-auth-token' : localStorage.getItem('xAuthToken')
  	});
  	return this.http.post(url,JSON.stringify(book), {headers: headers});

  }
   removeBook(bookId: number) {
  	let url = "http://localhost:8080/book/remove";
    let headers = new Headers ({
      'Content-Type': 'application/json',
      'x-auth-token' : localStorage.getItem('xAuthToken')
    });

    return this.http.post(url, bookId, {headers: headers});
  }





}
