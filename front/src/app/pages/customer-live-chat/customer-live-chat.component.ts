import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { LiveChatComponent } from '../../components/live-chat/live-chat.component';
import { LiveChatService } from '../../services/live-chat.service';
import { SessionService } from '../../services/session.service';
import { LiveMessage, Role, UserOnline } from '../../core/modules/graphql/generated';
import { filter, Subscription } from 'rxjs';

/**
 * Customer service live chat page
 */
@Component({
  selector: 'app-customer-live-chat',
  imports: [LiveChatComponent],
  templateUrl: './customer-live-chat.component.html',
  styleUrl: './customer-live-chat.component.css'
})
export class CustomerLiveChatComponent implements OnInit, OnDestroy {

  userOnlines: UserOnline[] = []
  subs: Subscription[] = []
  messages: LiveMessage[] = []
  to?: UserOnline;

  constructor(private sessionService: SessionService, private liveChatService: LiveChatService) { }

  ngOnInit(): void {
    const seekRole: Role = this.sessionService.account!.role == Role.Customer ? Role.CustomerService : Role.Customer
    this.liveChatService.getUserOnline(seekRole).subscribe({
      next: users => {
        this.userOnlines = users
        this.subToOnlineUsersChange()
        this.subToNewMessages()
        this.setOnline()
      },
      error: console.error
    })
  }

  ngOnDestroy(): void {
    this.disconnectLiveChat()
  }

  /**
   * Fetch messages exchanged with selected user
   * 
   * @param withUser 
   */
  public getMessages(withUser: UserOnline): void {
    this.to = withUser
    const isCustomer = withUser.role == Role.Customer
    const customerId = isCustomer ? withUser.id : this.sessionService.account!.id
    const customerServiceId = isCustomer ? this.sessionService.account!.id : withUser.id
    this.liveChatService.getMessages(customerId, customerServiceId).subscribe({
      next: messages => this.messages = messages,
      error: console.error
    })
  }

  /**
   * Send a message
   *
   * @param message 
   */
  public sendMessage(message: string) {
    if (this.to !== undefined) {
      this.liveChatService.sendMessage(message, this.sessionService.account!.id, this.to.id).subscribe({
        next: msg => this.messages.unshift(msg as LiveMessage),
        error: console.error
      })
    }

  }

  /**
   * Users online list update subscription
   */
  private subToOnlineUsersChange(): void {
    this.subs.push(this.liveChatService.subOnlineUsersFor(this.sessionService.account!.role).subscribe({
      next: users => this.userOnlines = users,
      error: console.error
    }))
  }

  /**
   * Set logged user status as online
   */
  private setOnline(): void {
    this.liveChatService.setOnline(this.sessionService.account!, true).subscribe({
      next: console.info,
      error: console.error,
    })
  }

  /**
   * Incomming new message subscription
   */
  private subToNewMessages(): void {
    this.subs.push(
      this.liveChatService.subToIncommingMessage(this.sessionService.account!.id)
        // Filtering message from selected user's conversation
        .pipe(filter(msg => msg.from.id == this.to?.id))
        .subscribe({
          next: (msg) => this.messages.unshift(msg),
          error: console.error
        })
    )
  }

  /**
   * Page close listener
   */
  @HostListener('window:beforeunload')
  onBeforeUnload() {
    this.disconnectLiveChat()
  }

  /**
   * Remove all subscriptions and disconnect from the chat and 
   */
  disconnectLiveChat() {
    this.subs.forEach(sub => sub.unsubscribe())
    return this.liveChatService.setOnline(this.sessionService.account!, false).subscribe({
      next: console.info,
      error: console.error,
    })
  }

}
