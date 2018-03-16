import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import 'rxjs/add/operator/map';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {VersionService} from './version.service';
import {LoginService} from './login.service';

@Injectable()
export class WeatherService {
  private API_URL: string = environment.API_URL;
  private VERSION: string = this._version.getCurrentVersion();

  private messageSource = new BehaviorSubject<string>('Saint Petersburg');
  currentMessage = this.messageSource.asObservable();

  constructor(private _http: HttpClient, private _version: VersionService, private _loginService: LoginService) {
  }

  changeMessage(message: string) {
    this.messageSource.next(message);
  }

  getByCityName(query: string) {
    let token: string = this._loginService.getToken();
    const params = {'token': token};
    let mainUri: string = this.API_URL + this.VERSION + '/city/' + query;
    console.log(mainUri);
    return this._http.get(mainUri, {params});
  }

  getByHistory() {
    let token: string = this._loginService.getToken();
    const params = {'token': token};
    let mainUri: string = this.API_URL + this.VERSION + '/history';
    console.log(mainUri);
    return this._http.get(mainUri, {params});
  }

}
