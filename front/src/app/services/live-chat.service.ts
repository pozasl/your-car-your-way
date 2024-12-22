import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LiveChatService {

  setOnline(account: import("../models/UserAccount").UserAccount | undefined) {
    throw new Error('Method not implemented.');
  }

  constructor() { }
}
