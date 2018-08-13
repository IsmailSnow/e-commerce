import { UserBilling } from './../models/user-billing';
import { UserPayment } from './../models/user-payment';
import { PaymentService } from './../services/payment.service';
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

  private userPayment:UserPayment =new UserPayment;
  private userBilling:UserBilling= new UserBilling();
  private userPaymentList:UserPayment[] =[];
  private selectedProfileTab :number=0;
  private selectedBillingTab: number=0;
  private defaultPaymentSet:boolean ;
	private defaultUserPaymentId: number;
  private stateList : string[]=[];

  constructor(private loginService:LoginService,private userService:UserService,private router:Router,private paymentService:PaymentService) { }

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
    this.userBilling.userBillingState="";
  	this.userPayment.type="";
  	this.userPayment.expiryMonth="";
  	this.userPayment.expiryYear="";
  	this.userPayment.userBilling = this.userBilling;
  	this.defaultPaymentSet = false;

    for(let s in AppConst.usStates )
    this.stateList.push(s);  
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
        this.userPaymentList=  this.user.userPaymentList;
        for(let index in this.userPaymentList){
          if(this.userPaymentList[index].defaultPayment){
            this.defaultUserPaymentId =this.userPaymentList[index].id;
            break;
          }
        }
      },error=>{
           console.log(error);
      }
    )
  }

  onNewPayment(){
    this.paymentService.newPayment(this.userPayment).subscribe(
      res=>{
            this.getCurrentUser();
            this.selectedBillingTab = 0;
      },error=>{
              console.log(error.text());
      }
    )
  }

  selectedBillingChange(val:number){
    this.selectedBillingTab=val;
  }

  onUpdatePayment (payment: UserPayment) {
  	this.userPayment = payment;
  	this.userBilling = payment.userBilling;
  	this.selectedBillingTab = 1;
  }
  onRemovePayment(id:number) {
  	this.paymentService.removePayment(id).subscribe(
  		res => {
  			this.getCurrentUser();
  		},
  		error => {
  			console.log(error.text());
  		}
  	);
  }
  setDefaultPayment() {
  	this.defaultPaymentSet = false;
  	this.paymentService.setDefaultPayment(this.defaultUserPaymentId).subscribe(
  		res => {
  			this.getCurrentUser();
  			this.defaultPaymentSet = true;
  		},
  		error => {
  			console.log(error.text());
  		}
  	);
  }
  

}
