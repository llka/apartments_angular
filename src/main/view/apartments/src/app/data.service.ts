import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';
import {User} from './User';
import {Apartment} from './Apartment';
import 'rxjs/add/operator/toPromise';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class DataService {

  private userUrl = '/user';  // URL to web API
  private apartmentUrl = '/apartment';  // URL to web API
  private hotelUrl = '/hotel';  // URL to web API
  private loginUrl = '/login';
  private logoutUrl = '/logout';
  private registerUrl = '/register';
  private headers = new Headers({'Content-Type': 'application/json'});
  private loginHeaders = new Headers({'Content-Type': 'multipart/form-data'});

  private _isAuthorized = false;

  constructor(private http: Http, private httpNew: HttpClient) {
  }

  // -------- login ---------
  sendLoginParams(login: string, password: string): void {
    const formData = new FormData();
    formData.append('login', login);
    formData.append('password', password);
    this.httpNew.post(this.loginUrl, formData)
      .subscribe(
        res => {
          console.log(res);
        },
        err => {
          console.log('Error occured');
        }
      );
  }

  // -------- logout ---------
  logout(): void {
    const something = '';
    this.httpNew.post(this.logoutUrl, something)
      .subscribe(
        res => {
          console.log(res.toString());
        },
        err => {
          console.log('Error occured');
        }
      );
  }

  // -------- register ---------
  sendRegistrationParams(loginParam: string, passwordParam: string): void {
    const user = {
      id: 100,
      login: loginParam,
      password: passwordParam,
      ban: false,
      role: 'USER'
    };

    this.http.post(this.registerUrl, JSON.stringify(user), {headers: this.headers})
      .subscribe(
        res => {
          console.log(res);
        },
        err => {
          console.log('Error occured');
        }
      );
  }

  // ---- user service -----

  getAllUsers(): Promise<User[]> {
    return this.http.get(this.userUrl)
      .toPromise()
      .then(response => response.json() as User[])
      .catch(this.handleError);
  }

  getUser(id: number): Promise<User[]> {
    const url = `${this.userUrl}/${id}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as User)
      .catch(this.handleError);
  }

  createUser(user: User): Promise<User> {
    return this.http
      .post(this.userUrl, JSON.stringify(user), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as User)
      .catch(this.handleError);
  }

  deleteUser(id: number): Promise<void> {
    const url = `${this.userUrl}/${id}`;
    return this.http.delete(url, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  deleteAllUsers(): Promise<void> {
    return this.http.delete(this.userUrl, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  getUserApartments(id: number): Promise<Apartment[]> {
    const url = `${this.userUrl}/${id}/apartments`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as Apartment[])
      .catch(this.handleError);
  }

  // ---- apartment service -----


  // ---- booking service -----


  private handleError(error: any): Promise<any> {
    console.error('Error', error);
    return Promise.reject(error.message || error);
  }

  get isAuthorized(): boolean {
    return this._isAuthorized;
  }

  set isAuthorized(value: boolean) {
    this._isAuthorized = value;
  }
}
