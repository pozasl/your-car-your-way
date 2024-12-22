import { Injectable } from '@angular/core';
import {  OnlineCustomerServiceSubDocument, OnlineCustomerServiceSubGQL, OnlineCustomerSubGQL, Role, SetUserOnlineGQL, UserOnline, UserOnlineInput } from '../core/modules/graphql/generated';
import { UserAccount } from '../models/UserAccount';
import { map, Observable, take } from 'rxjs';
import { GraphQLWsLink } from "@apollo/client/link/subscriptions/index.js";
import { createClient } from "graphql-ws";
import { ApolloClient, InMemoryCache } from '@apollo/client/core';

@Injectable({
  providedIn: 'root'
})
export class LiveChatService {

  constructor(private setOnlineGQL:SetUserOnlineGQL, private onlineSubGQL: OnlineCustomerSubGQL, private onlineCSSubGQL: OnlineCustomerServiceSubGQL) {
    const client = new ApolloClient({
      cache: new InMemoryCache(),
      link: new GraphQLWsLink(
        createClient({
          url: "http://localhost:4200/graphql",
          lazy: true,
        })
      ),
    });

    // client.subscribe(
    //   {
    //     query: OnlineCustomerServiceSubDocument
    //   }
    // ).subscribe({
    //   next: r => console.log,
    //   error: err => console.error
    // })
  }

  setOnline(user:UserAccount, isOnline: boolean) {
    const userOnline: UserOnlineInput = {
      id: user.id,
      name: user.firstName + " " +user.lastName,
      role: user.role
    }
    return this.setOnlineGQL.mutate({user: userOnline, online: isOnline}).pipe(take(1), map(result => result.data!.setUserOnline!.message))
  }

  subOnlineUsersFor(role: Role): Observable<UserOnline[]> {
      const sub = this.onlineSubGQL.subscribe()
      sub.subscribe({
        next: console.log,
        error: console.error
      })
      return this.onlineSubGQL.subscribe().pipe(map(result => result.data!.customersOnline!))
    // return this.onlineCSSubGQL.subscribe().pipe(map(sub => sub.data!.customerServiceOnline))
  }
}
