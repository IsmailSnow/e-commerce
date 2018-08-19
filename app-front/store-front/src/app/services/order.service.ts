import { AppConst } from './../constants/app-const';
import { Http, Headers } from '@angular/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http:Http) { }

  getOrderList(){
    let url = AppConst.serverPath+"/order/getOrderList"
    let tokenHeader = new Headers({
      'content-type':'application/json',
      'x-auth-token': localStorage.getItem("xAuthToken")
    });
    return this.http.get(url,{headers:tokenHeader});
  }
}
