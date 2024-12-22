import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SessionService } from './services/session.service';
import { AuthService } from './services/auth.service';
import { take } from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'Your Car Your Way';

  constructor(private sessionService: SessionService, private authService: AuthService) {}
  ngOnInit(): void {
    if (this.sessionService.resuming) {
      this.resumeSession();
    }
  }

  private resumeSession() {
    this.authService.getAccountFromToken(this.sessionService.token!).pipe(take(1))
    .subscribe({
      next: (usr => this.sessionService.login(usr)),
      error: (err => console.log(err))
    })

  }


}
