import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../shared/login.service';
import {Handler} from '../../shared/requests';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  showSpinner: boolean = false;

  username: string = 'guest';
  password: string = 'guest';

  errorMessage = '';

  constructor(private _loginService: LoginService, private _router: Router) {
  }

  ngOnInit() {
  }

  private resultHandler: Handler = {
    handle(done, msg): void {
      if (!done) {
        this.errorMessage = msg;
        console.log(msg);
        alert(msg);
      }
    }
  };

  login() {
    this._loginService.login(this.username, this.password, this.resultHandler);
  }

  register() {
    this._loginService.register(this.username, this.password, this.resultHandler);
  }

  isLogin(): boolean {
    return this._loginService.isLogin();
  }

}
