import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';


@Injectable()
export class LoginService {

  constructor(private http:Http) { }

   checkSession(){
	let url = "http://localhost:8080/check";
	console.log(localStorage.getItem('xAuthToken'));
  	let headers = new Headers ({
  		 'x-auth-token' : localStorage.getItem('xAuthToken')
  	});
  	return this.http.get(url, {headers: headers});

  }

  logout(){
	let url = "http://localhost:8080/user/logout";
  	let headers = new Headers ({
  		 'x-auth-token' : localStorage.getItem('xAuthToken')
  	});
	localStorage.clear();
  	return this.http.post(url,'', {headers: headers});

  }

 sendCredential(username: string, password: string) {
  	let url = "http://localhost:8080/token";
  	let encodedCredentials = btoa(username+":"+password);
  	let basicHeader = "Basic "+encodedCredentials;
	  console.log(basicHeader);
  	let headers = new Headers ({
  		'Content-Type' : 'application/x-www-form-urlencoded',
  		'Authorization' : basicHeader
  	});
  	return this.http.get(url, {headers: headers});
  }
}
