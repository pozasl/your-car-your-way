import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { SessionService } from './services/session.service';
import { AuthService } from './services/auth.service';
import { Observable, take } from 'rxjs';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule} from '@angular/material/toolbar';
import { AsyncPipe } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-root',
  imports: [AsyncPipe, RouterOutlet, MatToolbarModule, MatIconModule, MatListModule, RouterLink, RouterLinkActive],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'Your Car Your Way';

  constructor(private sessionService: SessionService, private authService: AuthService, private router: Router) {}
  ngOnInit(): void {
    if (this.sessionService.resuming) {
      this.resumeSession();
    }
  }

  $isLogged(): Observable<boolean> {
    return this.sessionService.$logged()
  }

  logout() {
    this.sessionService.logout();
    this.router.navigate(['/'])
  }

  private resumeSession() {
    this.authService.getAccountFromToken(this.sessionService.token!).pipe(take(1))
    .subscribe({
      next: (usr => this.sessionService.login(usr)),
      error: (err => this.sessionService.logout())
    })
  }
}
