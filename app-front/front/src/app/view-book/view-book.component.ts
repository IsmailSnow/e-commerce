import { Router ,Params , ActivatedRoute} from '@angular/router';
import { Book } from './../models/book';
import { BookService } from './../service/book.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-view-book',
  templateUrl: './view-book.component.html',
  styleUrls: ['./view-book.component.css']
})
export class ViewBookComponent implements OnInit {

  private book:Book = new Book();
  private bookId : number;

  constructor(private bookService:BookService,private router:Router,private route:ActivatedRoute) { }

  ngOnInit() {

    this.route.params.forEach((params: Params) => {
  		this.bookId = Number.parseInt(params['id']);
  	});
    	this.bookService.getBook(this.bookId).subscribe(
  		res => {
  			this.book = res.json();
  		},
  		error => {
  			console.log(error);
  		}
  	); }

		onSelect(book:Book){
			this.router.navigate(['/editBook',this.book.id]);//.then(s=>location.reload());// reload to refresh the page if we want it depend on the utilisation of the component
		}

}
