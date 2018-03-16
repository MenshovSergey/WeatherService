import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';
import {SidenavComponent} from './core/sidenav/sidenav.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import {HeaderComponent} from './core/header/header.component';
import {WeatherService} from './shared/weather.service';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MainComponent} from './core/main/main.component';
import {LoginComponent} from './core/login/login.component';
import {Error404Component} from './core/error404/error-404.component';
import {LoginService} from './shared/login.service';
import {VersionService} from './shared/version.service';
import {HistoryComponent} from './core/history/history.component';

@NgModule({
  declarations: [
    AppComponent,
    SidenavComponent,
    HeaderComponent,
    MainComponent,
    LoginComponent,
    Error404Component,
    HistoryComponent
  ],
  imports: [
    BrowserModule.withServerTransition({appId: 'front'}),
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [WeatherService, LoginService, VersionService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
