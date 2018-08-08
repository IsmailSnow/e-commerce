import { RouterModule , Router } from '@angular/router';
import { LoginService } from './../service/login.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  private credential = {'username':'','password':''};
  private loggedIn = false;

  constructor(private loginService:LoginService,private router:Router) { }

  ngOnInit() {
    this.loginService.checkSession().subscribe(res => {  this.loggedIn=true ;},error =>{this.loggedIn=false});
  }

  onSubmit(){
    console.log("test");
    console.log(this.credential);
    this.loginService.sendCredential(this.credential.username,this.credential.password)
                     .subscribe(res => {
                        console.log(res);
                        localStorage.setItem("xAuthToken",res.json().token);
                        this.loggedIn =true ;
                        //location.reload();
                        this.router.navigateByUrl('home')
                     },
                     error=>{
                             console.log(error);
                     });
  }
  cancel(){
    this.credential={'username':'','password':''};
  }

}
