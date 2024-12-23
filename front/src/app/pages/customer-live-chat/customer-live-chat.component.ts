import { Component, OnDestroy, OnInit } from '@angular/core';
import { LiveChatComponent } from '../../components/live-chat/live-chat.component';
import { LiveChatService } from '../../services/live-chat.service';
import { SessionService } from '../../services/session.service';
import { UserOnline } from '../../core/modules/graphql/generated';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-customer-live-chat',
  imports: [LiveChatComponent, AsyncPipe],
  templateUrl: './customer-live-chat.component.html',
  styleUrl: './customer-live-chat.component.css'
})
export class CustomerLiveChatComponent implements OnInit, OnDestroy{

  $userOnlines!: Observable<UserOnline[]>

  constructor(private sessionService: SessionService, private liveChatService: LiveChatService) {}

  ngOnInit(): void {
    this.$userOnlines = this.liveChatService.subOnlineUsersFor(this.sessionService.account!.role);
    this.liveChatService.setOnline(this.sessionService.account!, true)
  }

  ngOnDestroy(): void {
    this.liveChatService.setOnline(this.sessionService.account!, false)
 }

}
