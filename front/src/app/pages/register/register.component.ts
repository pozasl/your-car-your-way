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
import { CountryCodes } from '../../models/CountryCodes'
import { KeyValuePipe } from '@angular/common'

/**
 * Customer registration page
 */
@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, MatInputModule, MatButtonModule, MatIconModule, MatFormFieldModule, MatCardModule, MatDatepickerModule, KeyValuePipe],
  providers: [provideNativeDateAdapter()],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {

  public form!: FormGroup
  public hide = true
  public titles = Title
  public countryCodes = Object.entries(CountryCodes)
  .sort((t1, t2) => t1[1] > t2[1] ? 1 :  -1)
  

  constructor(private authService: AuthService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.createForm();
  }

  public submit() {
    const customerAccount: CustomerAccountInput = {
      title: this.form.value.title,
      firstName: this.form.value.firstname,
      lastName: this.form.value.lastname,
      birthDate: this.form.value.birthDate,
      email: this.form.value.email,
      password: this.form.value.password,
      address: {
        city: this.form.value.city,
        postalCode: this.form.value.postalCode,
        region: this.form.value.region,
        countryCode: this.form.value.countryCode
      }
    }
    console.log(customerAccount);
    this.authService.registerCustomer(customerAccount)
  }

  private createForm() {
    this.form = this.fb.group({
      title: ['', [Validators.required]],
      firstname: ['', [Validators.required]],
      lastname: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      birthDate: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.pattern(passwordStrengthReg)]],
      street: ['', [Validators.required]],
      complement: [''],
      city: ['', [Validators.required]],
      postalCode: ['', [Validators.required]],
      region: [''],
      countryCode: ['', [Validators.required]],
    });
  }

}