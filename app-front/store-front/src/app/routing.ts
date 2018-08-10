import { MyAccountComponent } from './my-account/my-account.component';
import { HomeComponent } from './home/home.component';
import { Routes } from '@angular/router';
import { AppComponent } from './app.component';


export const appRoutes: Routes = [
    { path: 'myAccount', component: MyAccountComponent },
    { path: 'home', component: HomeComponent },
    { path: '', component: HomeComponent }
    ];