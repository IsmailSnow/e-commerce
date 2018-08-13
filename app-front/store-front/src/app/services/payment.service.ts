import { UserPayment } from './../models/user-payment';
import { Http, Headers } from '@angular/http';
import { AppConst } from './../constants/app-const';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
   private serverPath:String = AppConst.serverPath;

  constructor(private http:Http) { }


  newPayment(payment:UserPayment){
      let url = this.serverPath+'/payment/add';
      let tokenHeader = new Headers({
      'Content-type' :'application/json',
      'x-auth-token' : localStorage.getItem('xAuthToken')
  }); 
     return this.http.post(url,payment,{headers:tokenHeader});
  }
  getUserPayment(){
    let url = this.serverPath+'/payment/getUserPaymentList';

    let tokenHeader= new Headers({
      'Content-type':'application/json',
      'x-auth-token':localStorage.getItem('xAuthToken')
    });
       return this.http.get(url,{headers:tokenHeader});
  }
  removePayment(id:number){
    let url = this.serverPath+'/payment/remove';
    let tokenHeader = new Headers({
      'content-type':'application/json',
      'x-auth-token': localStorage.getItem('xAuthToken')
    });
    return this.http.post(url,id,{headers:tokenHeader});
  }
  setDefaultPayment(id:number){

     let url = this.serverPath+'/payment/setDefault';
     let tokenHeader = new Headers({
       'content-type':'application/json',
       'x-auth-token':localStorage.getItem('xAuthToken')
     });
    return this.http.post(url,id,{headers:tokenHeader});
  }
    
}
