import { Injectable } from '@angular/core'
import { BehaviorSubject, Observable } from 'rxjs';
import { UserAccount } from '../models/UserAccount';

/**
 * User session service
 */
@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public logged = false;
  public account: UserAccount | undefined
  private _token: string | null
  private loggedSubject = new BehaviorSubject<boolean>(this.logged);

  constructor() {
    this._token = localStorage.getItem('token')
  }

  public set token(tokenStr: string) {
    localStorage.setItem('token', tokenStr)
    this._token = tokenStr
  }

  public get token(): string | null {
    return this._token
  }

  public get resuming(): boolean {
    return !this.logged && this._token != null;
  }

  /**
   * Login session
   * @param account 
   */
  public login(account: UserAccount) {
    this.account = account
    this.logged = true
    this.next()
  }

  /**
   * Logout session
   */
  public logout() {
    this._token = null;
    localStorage.removeItem('token');
    this.account = undefined;
    this.logged = false;
    this.next();
  }

  /**
   * Return the session logged status
   * @returns 
   */
  public $logged(): Observable<boolean> {
    return this.loggedSubject.asObservable()
  }

  /**
   * Logged status mutation
   */
  private next() {
    this.loggedSubject.next(this.logged)
  }
}
