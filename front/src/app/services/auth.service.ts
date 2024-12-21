import { Injectable } from '@angular/core';
import { NewCustomerAccountInput, RegisterCustomerGQL } from '../core/modules/graphql/generated';
import { map, Observable } from 'rxjs';


/**
 * Authentication service
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private registerCustomerGQl: RegisterCustomerGQL) { }

  /**
   * Register a new customer account
   *
   * @param account 
   * @returns an Observable avec 
   */
  registerCustomer(account: NewCustomerAccountInput): Observable<string> {
    return this.registerCustomerGQl.mutate(account).pipe(map(result => result.data!.registerCustomer!.message))
  }

  /**
   * Email + Password login
   * 
   * @param email 
   * @param password
   */
  login(email: string, password: string) {

  }
}
