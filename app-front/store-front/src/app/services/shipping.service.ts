import { UserShipping } from './../models/user-shipping';
import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { AppConst } from './../constants/app-const';


@Injectable({
  providedIn: 'root'
})
export class ShippingService {

  private serverPath:String = AppConst.serverPath;

  constructor(private http:Http) { }

  newShipping(shipping:UserShipping){
      let url = this.serverPath+'/shipping/add';
      let tokenHeader = new Headers({
      'Content-type' :'application/json',
      'x-auth-token' : localStorage.getItem('xAuthToken')
  }); 
     return this.http.post(url,JSON.stringify(shipping),{headers:tokenHeader});
  }

  getUserShippingList(){
      let url = this.serverPath+'/shipping/getUserShippingList';
      let tokenHeader = new Headers({
      'Content-type' :'application/json',
      'x-auth-token' : localStorage.getItem('xAuthToken')
  }); 
     return this.http.get(url,{headers:tokenHeader});

  }

  removeShipping(id:number){
      let url = this.serverPath+'/shipping/remove';
      let tokenHeader = new Headers({
      'Content-type' :'application/json',
      'x-auth-token' : localStorage.getItem('xAuthToken')
  }); 
     return this.http.post(url,id,{headers:tokenHeader});

  }
   setDefaultShipping(id:number){
      let url = this.serverPath+'/shipping/setDefault';
      let tokenHeader = new Headers({
      'Content-type' :'application/json',
      'x-auth-token' : localStorage.getItem('xAuthToken')
  }); 
     return this.http.post(url,id,{headers:tokenHeader});

  }






}