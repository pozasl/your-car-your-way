import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerLiveChatComponent } from './customer-live-chat.component';

describe('CustomerLiveChatComponent', () => {
  let component: CustomerLiveChatComponent;
  let fixture: ComponentFixture<CustomerLiveChatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerLiveChatComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerLiveChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
