import { UserService } from './../services/user.service';
import { LoginService } from './../services/login.service';
import { AppConst } from './../constants/app-const';
import { Component, OnInit ,Output ,EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {


  private servicePath = AppConst.serverPath;
  private loginError:boolean=false;
  private loggedIn = false;
  private credential ={'username':'','password':''};

  private emailSent :boolean = false;
  private usernameExists:boolean;
  private username:string;
  private email:string;
  private emailExists:boolean;

  private emailNotExists : boolean = false;
  private forgetPasswordEmailSent : boolean;
  private recoverEmail:string;
  
  constructor(private router:Router,private loginService:LoginService,private userService:UserService) { }
  
  ngOnInit() {
  }

  onLogin(){
    console.log(this.credential.password);
    this.loginService.sendCredential(this.credential.username,this.credential.password).subscribe(
      res=>{
      localStorage.setItem("xAuthToken",res.json().token);
      this.loggedIn=true;
      location.reload();
      this.router.navigate(['/home']);
    },error=>{
        this.loggedIn=false;
        this.loginError=true;
   
      }
    );
  }
  onNewAccount(){
    this.usernameExists=false;
    this.emailSent=false;
    this.emailExists=false;

    this.userService.newUser(this.username,this.email).subscribe(
      res=>{
        console.log(res);
        this.forgetPasswordEmailSent = true;
      },
      error=>{
        console.log(error.text());
        let erroMessage=error.text();
        if(erroMessage==="usernameExists") this.usernameExists=true;
  			if(erroMessage==="Email not found") this.emailNotExists=true;

      }
    );
  }
    onForgetPassword() {
  	this.forgetPasswordEmailSent = false;
  	this.emailNotExists = false;

  	this.userService.retrievePassword(this.recoverEmail).subscribe(
  		res => {
  			console.log(res);
  			this.emailSent = true;
  		},
  		error => {
  			console.log(error.text());
  			let errorMessage = error.text();
  			if(errorMessage==="emailExists") this.emailExists=true;
        if(errorMessage==="Email not found") this.emailNotExists=true;
  		}
  	);
  }




  

  
  

}
