import { UploadImageService } from './service/upload-image.service';
import { BookService } from './service/book.service';
import { LoginService } from './service/login.service';
import { MaterialModule } from './material';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule,ModuleWithProviders} from '@angular/core';
import { AppComponent } from './app.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { appRoutes } from './routes';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import 'hammerjs';
import { AddNewBookComponent } from './add-new-book/add-new-book.component';
import { BookListComponent, DialogResultExampleDialog } from './book-list/book-list.component';
import { ViewBookComponent } from './view-book/view-book.component';
import { EditBookComponent } from './edit-book/edit-book.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    LoginComponent,
    AddNewBookComponent,
    BookListComponent,
    ViewBookComponent,
    EditBookComponent,
    DialogResultExampleDialog
  ],
  imports: [
    BrowserModule,
    MaterialModule,
    RouterModule.forRoot(appRoutes),
    HttpModule,
    FormsModule,
    BrowserAnimationsModule
  ],
  providers: [LoginService,BookService,UploadImageService],
  bootstrap: [AppComponent,DialogResultExampleDialog]
})
export class AppModule { }
