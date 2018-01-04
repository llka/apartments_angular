import {Injectable} from '@angular/core';
import {User} from './User';
import {Apartment} from './Apartment';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class DataService {

  private userUrl = '/user';  // URL to web API
  private apartmentUrl = '/apartment';  // URL to web API
  private hotelUrl = '/hotel';  // URL to web API
  private loginUrl = '/login';
  private logoutUrl = '/logout';
  private registerUrl = '/register';
  private headers = new HttpHeaders({'Content-Type': 'application/json'});
  private loginHeaders = new HttpHeaders({'Content-Type': 'multipart/form-data'});

  private _isAuthorized = false;

  constructor(private httpClient: HttpClient) {
  }

  // -------- login ---------
  sendLoginParams(login: string, password: string): void {
    const formData = new FormData();
    formData.append('login', login);
    formData.append('password', password);
    this.httpClient.post(this.loginUrl, formData)
      .subscribe(
        res => {
          console.log(res);
        },
        err => {
          console.log('Error occurred');
        }
      );
  }

  // -------- logout ---------
  logout(): void {
    const something = '';
    this.httpClient.post(this.logoutUrl, something)
      .subscribe(
        res => {
          console.log(res.toString());
        },
        err => {
          console.log('Error occurred');
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

    this.httpClient.post(this.registerUrl, JSON.stringify(user), {headers: this.headers})
      .subscribe(
        res => {
          console.log(res);
        },
        err => {
          console.log('Error occurred');
        }
      );
  }

  // ---- user service -----

  getAllUsers(): Promise<User[]> {
    return this.httpClient.get(this.userUrl)
      .toPromise()
      .then(response => response as User[])
      .catch(this.handleError);
  }

  getUser(id: number): Promise<User[]> {
    const url = `${this.userUrl}/${id}`;
    return this.httpClient.get(url)
      .toPromise()
      .then(response => response as User)
      .catch(this.handleError);
  }

  createUser(user: User): Promise<User> {
    return this.httpClient
      .post(this.userUrl, JSON.stringify(user), {headers: this.headers})
      .toPromise()
      .then(res => res as User)
      .catch(this.handleError);
  }

  deleteUser(id: number): Promise<void> {
    const url = `${this.userUrl}/${id}`;
    return this.httpClient.delete(url, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  deleteAllUsers(): Promise<void> {
    return this.httpClient.delete(this.userUrl, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  getUserApartments(id: number): Promise<Apartment[]> {
    const url = `${this.userUrl}/${id}/apartments`;
    return this.httpClient.get(url)
      .toPromise()
      .then(response => response as Apartment[])
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
