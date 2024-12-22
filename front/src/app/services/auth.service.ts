import { Injectable } from '@angular/core';
import { AccountCredentials, GetTokenGQL, NewCustomerAccountInput, RegisterCustomerGQL } from '../core/modules/graphql/generated';
import { map, Observable, take } from 'rxjs';


/**
 * Authentication service
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private registerCustomerGQl: RegisterCustomerGQL,
    private getToken: GetTokenGQL) { }

  /**
   * Register a new customer account
   *
   * @param account 
   * @returns an Observable avec 
   */
  registerCustomer(account: NewCustomerAccountInput): Observable<string> {
    return this.registerCustomerGQl.mutate(account).pipe(take(1), map(result => result.data!.registerCustomer!.message))
  }

  /**
   * Email + Password login
   * 
   * @param email 
   * @param password
   */
  login(credentials: AccountCredentials): Observable<string> {
    return this.getToken.watch().valueChanges.pipe(take(1), map(result => result.data!.token!))
  }
}
