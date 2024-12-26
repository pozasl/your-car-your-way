import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { LiveMessage, UserOnline } from '../../core/modules/graphql/generated';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { DatePipe } from '@angular/common';
import {  FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

/**
 * Live chat UI component
 */
@Component({
  selector: 'app-live-chat',
  imports: [MatIconModule, MatListModule, MatCardModule, MatButtonModule, MatFormFieldModule, MatInputModule, DatePipe, ReactiveFormsModule],
  templateUrl: './live-chat.component.html',
  styleUrl: './live-chat.component.css'
})
export class LiveChatComponent implements OnInit{
  users: UserOnline[] = []
  messages: LiveMessage[] = []
  to: UserOnline | null = null
  form!: FormGroup

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      message: ['', [Validators.required]],
    })
  }

  @Input()
  set usersOnline(users: UserOnline[]) {
    this.users = users;
  }

  @Input()
  set liveMessages(messages: LiveMessage[]) {
    this.messages = messages
  }
  
  @Output()
  onUserSelect = new EventEmitter<UserOnline>();

  @Output()
  onMessageSubmit = new EventEmitter<string>();

  /**
   * Chat user selection callback
   * @param user 
   */
  onChatUserClick(user: UserOnline):void {
    this.to = user;
    this.onUserSelect.emit(user)
  }

  /**
   * SUbmit message
   */
  submit() {
    this.onMessageSubmit.emit(this.form.value.message)
    this.form.reset();
  }

}
