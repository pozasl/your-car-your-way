import { Injectable } from '@angular/core';
import { GetLiveMessagesGQL, GetUserOnlineGQL, LiveMessage, MessageSubGQL, OnlineCustomerServiceSubGQL, OnlineCustomerSubGQL, Role, SendMessageGQL, SetUserOnlineGQL, UserOnline, UserOnlineInput } from '../core/modules/graphql/generated';
import { UserAccount } from '../models/UserAccount';
import { map, Observable, take } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LiveChatService {

  constructor(
    private setOnlineGQL: SetUserOnlineGQL,
    private usersOnline: GetUserOnlineGQL,
    private onlineSubGQL: OnlineCustomerSubGQL,
    private onlineCSSubGQL: OnlineCustomerServiceSubGQL,
    private sendMessageGQL: SendMessageGQL,
    private newMessageSubGQL: MessageSubGQL,
    private getLivesMessages: GetLiveMessagesGQL
  ) {}

  setOnline(user: UserAccount, isOnline: boolean) {
    const userOnline: UserOnlineInput = {
      id: user.id,
      name: user.firstName + " " + user.lastName,
      role: user.role
    }
    return this.setOnlineGQL.mutate({ user: userOnline, online: isOnline }).pipe(take(1), map(result => result.data!.setUserOnline!.message))
  }

  getUserOnline(role: Role): Observable<UserOnline[]> {
    return this.usersOnline.watch({role: role}).valueChanges.pipe(take(1), map(result => result.data!.usersOnline!))
  }

  subOnlineUsersFor(role: Role): Observable<UserOnline[]> {
    if (role == Role.CustomerService)
      return this.onlineSubGQL.subscribe().pipe(map(result => result.data!.customersOnline!))
    else
      return this.onlineCSSubGQL.subscribe().pipe(map(sub => sub.data!.customerServiceOnline!))
  }

  getMessages(customerId: string, customerServiceId: string): Observable<LiveMessage[]> {
    return this.getLivesMessages.watch({customerId, customerServiceId}).valueChanges.pipe(take(1),map(result => result.data!.liveMessages!.map(m => m as LiveMessage)))
  }
}
