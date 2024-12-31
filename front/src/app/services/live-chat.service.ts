import { Injectable } from '@angular/core';
import { GetLiveMessagesGQL, GetUserOnlineGQL, LiveMessage, MessageSubGQL, OnlineCustomerServiceSubGQL, OnlineCustomerSubGQL, Role, SendMessageGQL, SetUserOnlineGQL, UserOnline, UserOnlineInput } from '../core/modules/graphql/generated';
import { UserAccount } from '../models/UserAccount';
import { from, map, Observable, take } from 'rxjs';

/**
 * LiveChat service to manage users online and messages
 */
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
    private getLivesMessages: GetLiveMessagesGQL,
  ) {}

  /**
   * Set the logged user online
   *
   * @param user The logged user
   * @param isOnline Set online or offline
   * @returns The operation result
   */
  setOnline(user: UserAccount, isOnline: boolean) {
    const userOnline: UserOnlineInput = {
      id: user.id,
      name: user.firstName + " " + user.lastName,
      role: user.role
    }
    return this.setOnlineGQL.mutate({ user: userOnline, online: isOnline }).pipe(take(1), map(result => result.data!.setUserOnline!.message))
  }

  /**
   * Get the users online filtered by role
   *
   * @param role user role filter
   * @returns Filtered users online list
   */
  getUserOnline(role: Role): Observable<UserOnline[]> {
    // This request is cached by Apollo
    // return this.usersOnline.watch({role: role}).valueChanges.pipe(take(1), map(result => result.data!.usersOnline!))
    return from(this.usersOnline.watch({role: role}).setOptions({fetchPolicy: 'no-cache'})).pipe(map(r => r.data!.usersOnline!))

  }

  /**
   * Users onlines subscription
   * @param role user role filter
   * @returns Filtered users online list update's observable
   */
  subOnlineUsersFor(role: Role): Observable<UserOnline[]> {
    if (role == Role.CustomerService)
      return this.onlineSubGQL.subscribe().pipe(map(result => result.data!.customersOnline!))
    else
      return this.onlineCSSubGQL.subscribe().pipe(map(sub => sub.data!.customerServiceOnline!))
  }

  /**
   * Fetch the live messages exchanged between the customer and the customer service staff
   * 
   * @param customerId the customer id
   * @param customerServiceId the customer service staff id
   * @returns Live messages exchanged
   */
  getMessages(customerId: string, customerServiceId: string): Observable<LiveMessage[]> {
    // This request is cached by Apollo
    // return this.getLivesMessages.watch({customerId, customerServiceId}).valueChanges.pipe(take(1),map(result => result.data!.liveMessages!.map(m => m as LiveMessage)))
    //
    return from(this.getLivesMessages.watch({customerId, customerServiceId}).setOptions({fetchPolicy: 'no-cache'})).pipe(map(r => r.data!.liveMessages!.map(m => m as LiveMessage)))
    
  }

  /**
   * In coming message subscription
   * 
   * @param toUserId Message destination user's id
   * @returns Incoming message update's observable
   */
  subToIncommingMessage(toUserId: string): Observable<LiveMessage> {
    return this.newMessageSubGQL.subscribe({to: toUserId}).pipe(map(result => result.data!.newLiveMessage! as LiveMessage))
  }

  /**
   * Send a live message
   *
   * @param message The message
   * @param from from user Id
   * @param to to user id
   * @returns The recorded live message
   */
  sendMessage(message: string, from: string, to: string) {
    return this.sendMessageGQL.mutate({from, to, content: message}).pipe(take(1), map(result => result.data!.sendLiveMessage!))
  }

}
