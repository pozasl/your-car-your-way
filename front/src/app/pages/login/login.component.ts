import { Component, OnInit } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { Router, RouterLink } from '@angular/router'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { MatCardModule } from '@angular/material/card'
import { MatInputModule } from '@angular/material/input'
import { MatFormFieldModule } from "@angular/material/form-field"
import { MatIconModule } from '@angular/material/icon'
import { MatButtonModule } from '@angular/material/button'
import { AuthService } from '../../services/auth.service'
import { SessionService } from '../../services/session.service'
import { AccountCredentials } from '../../core/modules/graphql/generated'
import { UserAccount } from '../../models/UserAccount'
import { mergeMap } from 'rxjs'

/**
 * Login page component
 */
@Component({
  selector: 'app-login',
  imports: [FormsModule, MatCardModule, ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatIconModule, MatButtonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  public hide = true
  public onError = false
  public form!: FormGroup

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private sessionService: SessionService,
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.min(8)]]
    })
  }

  /**
   * Log the user in
   */
  public submit() {
    const credentials: AccountCredentials = this.form.value
    this.authService.login(credentials).pipe(
      mergeMap(token => {
        if (token) {
          this.sessionService.token = token;
          return this.authService.getAccountFromToken(token);
        }
        throw new Error("Empty jwt token recieved");
      })
    )
    .subscribe({
      next: (usr) => this.onLoginSuccess(usr),
      error: (err) => this.onLoginError(err)
    })
  }


  /**
   * Successfull login callback
   *
   * @param user the User's account data
   */
  private onLoginSuccess(user:  UserAccount) {
    this.sessionService.login(user)
    this.router.navigate(['/'])
  }

  /**
   * Login failure callback
   * 
   * @param error The error
   */
  private onLoginError(error: Error) {
    console.error(error)
  }
}