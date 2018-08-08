import { EditBookComponent } from './edit-book/edit-book.component';
import { ViewBookComponent } from './view-book/view-book.component';
import { BookListComponent } from './book-list/book-list.component';
import { AddNewBookComponent } from './add-new-book/add-new-book.component';
import { LoginComponent } from './login/login.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { Routes } from '@angular/router';
import { AppComponent } from './app.component';


export const appRoutes: Routes = [
    { path: 'home', component: NavBarComponent },
    { path: 'login', component: LoginComponent },
    { path: 'addNewBook', component: AddNewBookComponent },
    { path: 'bookList', component: BookListComponent },
    { path: 'viewBook/:id', component: ViewBookComponent },
    { path: 'editBook/:id', component: EditBookComponent }
    ];