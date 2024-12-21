import { TestBed } from '@angular/core/testing';

import { LiveChatService } from './live-chat.service';

describe('LiveChatService', () => {
  let service: LiveChatService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LiveChatService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
