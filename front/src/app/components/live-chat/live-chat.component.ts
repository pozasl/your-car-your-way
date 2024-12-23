import { Component, EventEmitter, Input, input, Output } from '@angular/core';
import { LiveMessage, UserOnline } from '../../core/modules/graphql/generated';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-live-chat',
  imports: [MatIconModule, MatListModule, MatCardModule, MatButtonModule, DatePipe],
  templateUrl: './live-chat.component.html',
  styleUrl: './live-chat.component.css'
})
export class LiveChatComponent {
  users: UserOnline[] = []
  messages: LiveMessage[] = []
  to: UserOnline | null = null

  constructor() {}

  @Input()
  set usersOnline(users: UserOnline[]) {
    this.users = users;
  }

  @Input()
  set liveMessages(messages: LiveMessage[]) {
    this.messages = messages;
  }
  
  @Output()
  onUserSelect = new EventEmitter<UserOnline>();

  @Output()
  sendMessageEvent = new EventEmitter<string>();

  onChatUserClick(user: UserOnline):void {
    this.to = user;
  }

  onSendMessage(message: string) {
    this.sendMessageEvent.emit(message)
  }

}
