import { Component, OnInit } from '@angular/core'
import { AuthService } from '../../services/auth.service'
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms'
import { MatDatepickerModule } from '@angular/material/datepicker'
import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatInputModule } from '@angular/material/input'
import { MatFormFieldModule } from "@angular/material/form-field"
import { MatIconModule } from '@angular/material/icon'
import { passwordStrengthReg } from '../../shared/validators/passworsStrengthReg'
import { BisTer, CustomerAccountInput, Title } from '../../core/modules/graphql/generated'
import { provideNativeDateAdapter } from '@angular/material/core';

/**
 * Customer registration page
 */
@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, MatInputModule, MatButtonModule, MatIconModule, MatFormFieldModule, MatCardModule, MatDatepickerModule],
  providers: [provideNativeDateAdapter()],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {

  public form!: FormGroup
  public hide = true
  public titles = Object.keys(Title)
  public bisTers = Object.keys(BisTer)
  

  constructor(private authService: AuthService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.createForm();
  }

  public submit() {
    const customerAccount: CustomerAccountInput = this.form.value
    this.authService.registerCustomer(customerAccount)
  }

  private createForm() {
    this.form = this.fb.group({
      title: ['', [Validators.required]],
      firstname: ['', [Validators.required]],
      lastname: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      birthDate: [Date.now(), [Validators.required]],
      password: ['', [Validators.required, Validators.pattern(passwordStrengthReg)]],
      street: ['', [Validators.required]],
      complement: ['', [Validators.required]],
      city: ['', [Validators.required]],
      postalCode: ['', [Validators.required]],
      region: [''],
      countryCode: ['', [Validators.required]],
    });
  }

}