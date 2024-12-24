import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { LiveChatComponent } from '../../components/live-chat/live-chat.component';
import { LiveChatService } from '../../services/live-chat.service';
import { SessionService } from '../../services/session.service';
import { LiveMessage, Role, UserOnline } from '../../core/modules/graphql/generated';
import { merge, Observable, Subscription } from 'rxjs';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-customer-live-chat',
  imports: [LiveChatComponent, AsyncPipe],
  templateUrl: './customer-live-chat.component.html',
  styleUrl: './customer-live-chat.component.css'
})
export class CustomerLiveChatComponent implements OnInit, OnDestroy{

  userOnlines: UserOnline[] = []
  subs: Subscription[] = []
  messages: LiveMessage[] = []

  constructor(private sessionService: SessionService, private liveChatService: LiveChatService) {}

  ngOnInit(): void {
    const seekRole:Role = this.sessionService.account!.role == Role.Customer ? Role.CustomerService : Role.Customer
    this.liveChatService.getUserOnline(seekRole).subscribe({
      next: users => {
        this.userOnlines = users
        console.log("Users updated", users)
        this.subToOnlineUsersChange()
        this.setOnline()
      },
      error: console.error,
      complete: () => console.log("getUserOnline::complete")
    })
  }
  
  ngOnDestroy(): void {
    this.disconnectLiveChat()
  }

  public getMessages(withUser: UserOnline):void {
    console.log("fetching messages from", withUser)
     const isCustomer = withUser.role == Role.Customer
     const customerId =  isCustomer ? withUser.id : this.sessionService.account!.id
     const customerServiceId = isCustomer ? this.sessionService.account!.id : withUser.id
     this.liveChatService.getMessages(customerId, customerServiceId).subscribe({
      next: console.log,
      error: console.error
     })
  }

  private subToOnlineUsersChange():void {
    this.subs.push(this.liveChatService.subOnlineUsersFor(this.sessionService.account!.role).subscribe({
      next: users => this.userOnlines = users,
      error: console.error,
      complete: () => console.log("subOnlineUsersFor::ended")
    }))
  }

  private setOnline():void {
    this.liveChatService.setOnline(this.sessionService.account!, true).subscribe({
      next: console.log,
      error: console.error,
    })
  }

  private subToNewMessages():void {
    
  }

  @HostListener('window:beforeunload')
  disconnectLiveChat() {
    this.subs.forEach(sub => sub.unsubscribe())
    this.liveChatService.setOnline(this.sessionService.account!, false).subscribe({
      next: console.log,
      error: console.error,
    })
  }

}
