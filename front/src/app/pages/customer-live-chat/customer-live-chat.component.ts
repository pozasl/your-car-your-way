import { Component, OnInit } from '@angular/core';
import { LiveChatComponent } from '../../components/live-chat/live-chat.component';
import { LiveChatService } from '../../services/live-chat.service';
import { UserAccount } from '../../models/UserAccount';
import { SessionService } from '../../services/session.service';

@Component({
  selector: 'app-customer-live-chat',
  imports: [LiveChatComponent],
  templateUrl: './customer-live-chat.component.html',
  styleUrl: './customer-live-chat.component.css'
})
export class CustomerLiveChatComponent implements OnInit{

  usersOnline: UserAccount[] = []

  constructor(private sessionService: SessionService, private liveChatService: LiveChatService) {}

  ngOnInit(): void {
    this.liveChatService.setOnline(this.sessionService.account)
  }

  

}
