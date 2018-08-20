import { Book } from './../models/book';
import { BookService } from './../services/book.service';
import { Router, NavigationExtras } from '@angular/router';
import { LoginService } from './../services/login.service';
import { Component, OnInit ,Input} from '@angular/core';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  private loggedIn:boolean=false;
  private keyword:string;
  private bookList:Book[]=[];

  constructor(private loginService:LoginService,private router:Router,private bookService:BookService) { }

  ngOnInit() {
    this.loginService.checkSession().subscribe(
      res=>{this.loggedIn=true;},
      error=>{
        this.loggedIn=false;
      }
    )
  }

  logout(){
    this.loginService.logout().subscribe(
      res=>{
           localStorage.clear();
           this.router.navigateByUrl("/myAccount");
           this.loggedIn=false;
      },error=>{
        console.log(error);
      }
    )
  }

  onSearchByTitle(){
    this.bookService.searchBook(this.keyword).subscribe(
      res=>{
        this.bookList=res.json();
        console.log(this.bookList);
        let navigationExtras :NavigationExtras ={
          queryParams :{
            "bookList" :JSON.stringify(this.bookList)
          }
        };
        this.router.navigate(['/bookList'],navigationExtras);

      },error=>{
        console.log(error);
      }
    )
  }


}
