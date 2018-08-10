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


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavBarComponent,
    MyAccountComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    AngularFontAwesomeModule,
    HttpModule,
    MaterialModule,
    BrowserAnimationsModule,
    FormsModule
  ],
  providers: [LoginService,UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
