import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BookListComponent } from './book-list/book-list.component';
import { MyProfileComponent } from './my-profile/my-profile.component';
import { MyAccountComponent } from './my-account/my-account.component';
import { HomeComponent } from './home/home.component';
import { Routes } from '@angular/router';
import { AppComponent } from './app.component';


export const appRoutes: Routes = [
    { path: 'shoppingCart', component: ShoppingCartComponent   },
    { path: 'bookDetail/:id', component: BookDetailComponent   },
    { path: 'bookList', component: BookListComponent   },
    { path: 'myProfile', component: MyProfileComponent },
    { path: 'myAccount', component: MyAccountComponent },
    { path: 'home', component: HomeComponent },
    { path: '', component: HomeComponent }
    ];