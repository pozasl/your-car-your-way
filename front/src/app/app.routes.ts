import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';

export const routes: Routes = [
    { path: '', title: '', component: HomeComponent },
    { path: 'register', title: 'Register', component: RegisterComponent },
    { path: 'login', title: 'Login', component: LoginComponent },
];
