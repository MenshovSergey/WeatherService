import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../shared/login.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit {

  constructor(private _loginService: LoginService) {
  }

  ngOnInit() {
  }

  isLogin(): boolean {
    return this._loginService.isLogin();
  }

}
