import { ShippingService } from './../services/shipping.service';
import { UserShipping } from './../models/user-shipping';
import { UserBilling } from './../models/user-billing';
import { UserPayment } from './../models/user-payment';
import { PaymentService } from './../services/payment.service';
import { Router } from '@angular/router';
import { UserService } from './../services/user.service';
import { LoginService } from './../services/login.service';
import { User } from './../models/user';
import { AppConst } from './../constants/app-const';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from "@angular/material";


@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {


  private serverPath = AppConst.serverPath;
  private dataFeteched = true;
  private loginError: boolean;
  private loggedIn: boolean;
  private credential = { 'username': '', 'password': '' };
  private user: User = new User();
  private updateSuccess: boolean = false;
  private newPassword: string;
  private incorrectPassword: boolean;
  private currentPassword: string;

  private userPayment: UserPayment = new UserPayment;
  private userBilling: UserBilling = new UserBilling();
  private userShipping: UserShipping = new UserShipping();

  private userPaymentList: UserPayment[] = [];
  private userShippingList: UserShipping[] = [];

  private selectedProfileTab: number = 0;
  private selectedBillingTab: number = 0;
  private selectedShippingTab: number = 0;

  private defaultPaymentSet: boolean;
  private defaultUserPaymentId: number;
  private defautltUserShippingId: number;
  private defaultShippingSet: boolean;

  private stateList: string[] = [];
  private cardAddedSuccess: boolean = false;

  constructor(private loginService: LoginService, private userService: UserService, private router: Router,
    private paymentService: PaymentService, private shippingService: ShippingService) { }

  ngOnInit() {
    this.loginService.checkSession().subscribe(
      res => {
        this.getCurrentUser();
        this.userBilling.userBillingState = "";
        this.userPayment.type = "";
        this.userPayment.expiryMonth = "";
        this.userPayment.expiryYear = "";
        this.userPayment.userBilling = this.userBilling;
        this.defaultPaymentSet = false;
        this.userShipping.userShippingState = "";
        this.defaultShippingSet = false;


        for (let s in AppConst.usStates) this.stateList.push(s);



      }, error => {
        console.log(error.text());
        this.loggedIn = false;
        console.log("Inactive Session");
        localStorage.clear();
        this.router.navigateByUrl('/myAccount');

      }
    );

  }




  onNewShipping() {
    this.shippingService.newShipping(this.userShipping).subscribe(
      res => {
        this.getCurrentUser();
        this.selectedShippingTab = 0;
        this.userShipping = new UserShipping();
      },
      error => {
        console.log(error.text());
      }
    )
  }

  onUpdateShipping(shipping: UserShipping) {
    this.userShipping = shipping;
    this.selectedShippingTab = 1;
  }

  onRemoveShipping(id: number) {
    this.shippingService.removeShipping(id).subscribe(
      res => {
        this.getCurrentUser();
      }, error => {
        console.log(error.text());
      }
    )
  }

  setDefaultShipping() {
    this.defaultShippingSet = false;
    this.shippingService.setDefaultShipping(this.defautltUserShippingId).subscribe(
      res => {
        this.getCurrentUser();
        this.defaultShippingSet = true;
      },
      error => {
        console.log(error.text());
      }
    );
  }




  onUpdateUserInfo() {
    this.userService.updateUserInfo(this.user, this.newPassword, this.currentPassword).subscribe(
      res => {
        console.log(res.text());
        this.updateSuccess = true;
      }, error => {
        console.log(error.text());
        this.updateSuccess = false;
        let errorMessage = error.text();
        if (errorMessage === "Incorrect current password!") this.incorrectPassword = true;

      }
    )
  }

  getCurrentUser() {
    this.userService.getCurrentUser().subscribe(
      res => {
        console.log(res.json());
        this.user = res.json();
        console.log(this.user);
        this.dataFeteched = true;
        this.userPaymentList = this.user.userPaymentList;
        this.userShippingList = this.user.userShippingList;


        for (let index in this.userPaymentList) {
          if (this.userPaymentList[index].defaultPayment) {
            this.defaultUserPaymentId = this.userPaymentList[index].id;
            break;
          }
        }

        for (let index in this.userShippingList) {
          if (this.userShippingList[index].userShippingDefault) {
            this.defaultUserPaymentId = this.userShippingList[index].id;
            break;
          }
        }




      }, error => {
        console.log(error);
      }
    )
  }

  onNewPayment() {
    this.paymentService.newPayment(this.userPayment).subscribe(
      res => {
        this.getCurrentUser();
        this.selectedBillingTab = 0;
        this.cardAddedSuccess = true;
        this.userPayment = new UserPayment();
        window.scrollTo(0, 0);

      }, error => {
        console.log(error.text());
        this.cardAddedSuccess = false;
      }
    )
  }

  selectedBillingChange(val: number) {
    this.selectedBillingTab = val;
  }
  selectedShippingChange(val: number) {
    this.selectedShippingTab = val;
  }

  onUpdatePayment(payment: UserPayment) {
    this.selectedBillingTab = 1;
    this.userPayment = payment;
    this.userBilling = payment.userBilling;
  }
  onRemovePayment(id: number) {
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
