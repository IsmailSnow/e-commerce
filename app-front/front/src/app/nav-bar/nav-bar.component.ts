import { Router } from '@angular/router';
import { LoginService } from './../service/login.service';
import { LoginComponent } from './../login/login.component';
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
   this.loginService.checkSession().subscribe(res => {  this.loggedIn=true ;},error =>{this.loggedIn=false});
  }
  logout(){
    this.loginService.logout().subscribe(res=>{   this.router.navigateByUrl('login')},error=>console.log(error));
  }
  toggleDisplay() {
  	this.loggedIn = !this.loggedIn;
  }

}
