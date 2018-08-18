import { CartService } from './services/cart-service.service';
import { DataFilterPipe } from './book-list/data-filter.pipe';
import { BookService } from './services/book.service';
import { UserShipping } from './models/user-shipping';
import { PaymentService } from './services/payment.service';
import { MaterialModule } from './angularmaterial';
import { UserService } from './services/user.service';
import { LoginService } from './services/login.service';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { appRoutes } from './routing';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';

import 'hammerjs';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { MyAccountComponent } from './my-account/my-account.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MyProfileComponent } from './my-profile/my-profile.component';
import { BookListComponent } from './book-list/book-list.component';
import {DataTableModule} from "angular-6-datatable";
import { BookDetailComponent } from './book-detail/book-detail.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavBarComponent,
    MyAccountComponent,
    MyProfileComponent,
    BookListComponent,
    DataFilterPipe,
    BookDetailComponent,
    ShoppingCartComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    AngularFontAwesomeModule,
    HttpModule,
    MaterialModule,
    BrowserAnimationsModule,
    FormsModule,
    DataTableModule
  ],
  providers: [LoginService,UserService,PaymentService,UserShipping,BookService,CartService],
  bootstrap: [AppComponent]
})
export class AppModule { }
