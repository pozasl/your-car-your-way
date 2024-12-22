import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { AgenciesComponent } from './pages/agencies/agencies.component';
import { AuthGuard } from './guards/auth.guards';

export const routes: Routes = [
    { path: '', title: 'Your Car Your Way', component: HomeComponent },
    { path: 'register', title: 'Register', component: RegisterComponent },
    { path: 'login', title: 'Login', component: LoginComponent },
    { path: 'agencies', title: 'Agencies', component: AgenciesComponent, canActivate: [AuthGuard] },
];
