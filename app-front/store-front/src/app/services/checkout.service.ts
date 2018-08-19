import { BillingAddress } from './../models/billing-adress';
import { ShippingAddress } from './../models/shipping-adress';
import { Payment } from './../models/payment';
import { AppConst } from './../constants/app-const';
import { Http, Headers } from '@angular/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  constructor(private http:Http) { }
    checkout(
  	shippingAddress: ShippingAddress,
  	billingAddress: BillingAddress,
  	payment:Payment,
  	shippingMethod:string
  	){
		let url = AppConst.serverPath+"/checkout/checkout";
		let order ={
			"shippingAddress" : shippingAddress,
			"billingAddress" : billingAddress,
			"payment" : payment,
			"shippingMethod" : shippingMethod
		}

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, order, {headers:tokenHeader});
  }

  getUserOrder() {
  	let url = AppConst.serverPath+"/checkout/getUserOrder";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.get(url, {headers: tokenHeader});

  }
  





}
