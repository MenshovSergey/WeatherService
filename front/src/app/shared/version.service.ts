import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import 'rxjs/add/operator/map';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class VersionService {
  private API_VERSION_URL: string = environment.API_URL + environment.API_VERSION_PATH;
  private VERSION: string = 'v1';

  constructor(private _http: HttpClient) {
    this._http.get(this.API_VERSION_URL).subscribe(data => {
      this.VERSION = (<Version> data).version;
    });
  }

  getCurrentVersion() {
    return this.VERSION;
  }
}

class Version {
  private _version: string;
  get version(): string {
    return this._version;
  }
}
