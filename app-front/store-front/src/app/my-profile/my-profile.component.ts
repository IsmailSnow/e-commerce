import { Router } from '@angular/router';
import { UserService } from './../services/user.service';
import { LoginService } from './../services/login.service';
import { User } from './../models/user';
import { AppConst } from './../constants/app-const';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {


  private serverPath = AppConst.serverPath;
  private dataFeteched =true;
  private loginError:boolean;
  private loggedIn:boolean;
  private credential = {'username':'','password':''};

  private user:User = new User();
  private updateSuccess:boolean;
  private newPassword: string;
  private incorrectPassword: boolean;
  private currentPassword:string;

  constructor(private loginService:LoginService,private userService:UserService,private router:Router) { }

  ngOnInit() {
    this.loginService.checkSession().subscribe(
      res=>{

      },error=>{
        console.log(error.text());
        this.loggedIn=false;
        console.log("Inactive Session");
        localStorage.clear();
        this.router.navigateByUrl('/myAccount');

      }
    );
    this.getCurrentUser();
  }

  onUpdateUserInfo(){
    this.userService.updateUserInfo(this.user,this.newPassword,this.currentPassword).subscribe(
      res=>{
        console.log(res.text());
        this.updateSuccess=true;
      },error=>{
        console.log(error.text());
        this.updateSuccess=false;
        let errorMessage=error.text();
        if(errorMessage==="Incorrect current password!") this.incorrectPassword=true;

      }
    )
  }

  getCurrentUser(){
    this.userService.getCurrentUser().subscribe(
      res=>{
        console.log(res.json());
        this.user=res.json();
        this.dataFeteched =true;
      },error=>{
           console.log(error);
      }
    )
  }

}
