import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { SessionService } from './services/session.service';
import { AuthService } from './services/auth.service';
import { Observable, take } from 'rxjs';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule} from '@angular/material/toolbar';
import { AsyncPipe } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

/**
 * Main Application component
 */
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

  /**
   * Return logged status's observable
   *
   * @returns logged status's observable
   */
  $isLogged(): Observable<boolean> {
    return this.sessionService.$logged()
  }

  /**
   * Logout user
   */
  logout() {
    this.sessionService.logout();
    this.router.navigate(['/'])
  }

  /**
   * Resume user session if possible
   */
  private resumeSession() {
    this.authService.getAccountFromToken(this.sessionService.token!).pipe(take(1))
    .subscribe({
      next: (usr => this.sessionService.login(usr)),
      error: (err => this.sessionService.logout())
    })
  }
}
