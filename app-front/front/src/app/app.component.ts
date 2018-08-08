import { Router ,NavigationEnd } from '@angular/router';
import { Component, OnInit } from '@angular/core';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private _router:Router) { }
  ngOnInit() {
   this.activeRechargeComponentForRouterLink();
  }

  activeRechargeComponentForRouterLink(){
   this._router.routeReuseStrategy.shouldReuseRoute = function(){return false;}; 
   this._router.events.subscribe((evt) => {
    if (evt instanceof NavigationEnd) {
        this._router.navigated = false;
        window.scrollTo(0, 0);
    }
   });
  }
}
