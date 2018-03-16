import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import 'rxjs/add/operator/map';
import {HttpClient} from '@angular/common/http';
import {VersionService} from './version.service';
import {Handler, InfoResponse} from './requests';

@Injectable()
export class LoginService {
  private API_URL: string = environment.API_URL;
  private VERSION: string = this._version.getCurrentVersion();

  private token: string = '';
  private loginFlag: boolean = false;


  constructor(private _http: HttpClient, private _version: VersionService) {
  }

  login(username: string, password: string, result: Handler) {
    this.sendCredits(this.VERSION + '/login', username, password, result);
  }

  register(username: string, password: string, result: Handler) {
    this.sendCredits(this.VERSION + '/register', username, password, result);
  }

  sendCredits(path: string, username: string, password: string, result: Handler) {
    const params = {
      'username': username,
      'password': password
    };
    this._http.get(this.API_URL + path, {params}).subscribe(data => {
        let resp = <InfoResponse>data;
        if (resp.type === 'TOKEN') {
          let info = <Token>resp.info;
          this.token = info.token;
          this.loginFlag = true;
          result.handle(true, '');
        }
        if (resp.type === 'ERROR') {

        }
      },
      error => {
        result.handle(false, error.error.text);
      }
    );
  }

  getToken(): string {
    return this.token;
  }

  isLogin(): boolean {
    return this.loginFlag;
  }
}

class Token {
  private _token: string;
  get token(): string {
    return this._token;
  }
}

