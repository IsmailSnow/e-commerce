import { Router } from '@angular/router';
import { LoginService } from './../services/login.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  private loggedIn = false;


  constructor(private loginService:LoginService,private router:Router) { }

  ngOnInit() {
    this.loginService.checkSession().subscribe(
      res=>{
        this.loggedIn=true;
      },
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
      },error=>{
        console.log(error);
      }
    )
  }

}
