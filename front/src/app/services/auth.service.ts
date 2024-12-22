import { Injectable } from '@angular/core';
import { AccountCredentials, GetMeGQL, GetTokenGQL, NewCustomerAccountInput, RegisterCustomerGQL } from '../core/modules/graphql/generated';
import { map, Observable, take } from 'rxjs';
import { UserAccount } from '../models/UserAccount';


/**
 * Authentication service
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private registerCustomerGQl: RegisterCustomerGQL,
    private getTokenGQl: GetTokenGQL,
    private getMeGQl: GetMeGQL) { }

  /**
   * Register a new customer account
   *
   * @param account New customer's account input
   * @returns Register status message as Observable 
   */
  registerCustomer(account: NewCustomerAccountInput): Observable<string> {
    return this.registerCustomerGQl.mutate(account).pipe(take(1), map(result => result.data!.registerCustomer!.message))
  }

  /**
   * Email + Password login
   * 
   * @param email Account's email
   * @param password Account's password
   */
  login(credentials: AccountCredentials): Observable<string> {
    return this.getTokenGQl.watch({credentials: credentials}).valueChanges.pipe(take(1), map(result => result.data!.token!))
  }

  /**
   * Fetch account info from token
   * 
   * @param token Jwt token
   * @returns User's account obervable
   */
  getAccountFromToken(token: string): Observable<UserAccount> {
    return this.getMeGQl.watch().valueChanges.pipe(take(1), map(result => result.data!.me!))
  }
}
