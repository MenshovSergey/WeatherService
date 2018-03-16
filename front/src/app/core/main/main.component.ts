import {Component, OnInit} from '@angular/core';
import {WeatherService} from '../../shared/weather.service';
import {ErrorInfo, InfoResponse, QueueInfo, WeatherInfo} from '../../shared/requests';
import {LoginService} from '../../shared/login.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  searchQuery: string = 'Saint Petersburg';
  searching: boolean = false;

  errorFlag: boolean = false;
  errorMessage: string = '';

  queuePos: string = '';

  list: any[] = [];

  resultFound: boolean = false;

  constructor(private _loginService: LoginService, private _weatherService: WeatherService) {
  }

  ngOnInit() {
    this._weatherService.currentMessage.subscribe(message => this.getSomething(message));
  }

  getSomething(query: string) {
    this.searching = true;
    return this._weatherService.getByCityName(query).subscribe(
      data => this.handleSuccess(data),
      error => console.log(error),
      () => this.searching = false
    );
  }

  handleSuccess(data) {
    let response = <InfoResponse>data;
    if (response.type === 'ERROR') {
      let er = <ErrorInfo>response.info;
      this.errorFlag = true;
      this.errorMessage = er.message;
    }
    if (response.type === 'QUEUE') {
      let qr = <QueueInfo>response.info;
      this.queuePos = qr.pos;
    }
    if (response.type === 'OK') {
      let qr = <WeatherInfo>response.info;
      this.list.unshift(qr);
      this.resultFound = true;
    }
  }

  isLogin(): boolean {
    return this._loginService.isLogin();
  }

}
