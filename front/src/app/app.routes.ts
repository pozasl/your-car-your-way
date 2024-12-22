import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { AgenciesComponent } from './pages/agencies/agencies.component';
import { AuthGuard } from './guards/auth.guards';
import { UnauthGuard } from './guards/unauth.guards';
import { OffersComponent } from './pages/offers/offers.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { LiveChatComponent } from './pages/live-chat/live-chat.component';

export const routes: Routes = [
    { path: '', title: 'Your Car Your Way', component: HomeComponent, canActivate: [UnauthGuard] },
    { path: 'register', title: 'Register', component: RegisterComponent, canActivate: [UnauthGuard] },
    { path: 'login', title: 'Login', component: LoginComponent, canActivate: [UnauthGuard] },
    { path: 'agencies', title: 'Agencies list', component: AgenciesComponent, canActivate: [AuthGuard] },
    { path: 'offers', title: 'Rental offers', component: OffersComponent, canActivate: [AuthGuard] },
    { path: 'live-chat', title: 'Live client service chat', component: LiveChatComponent, canActivate: [AuthGuard] },
    { path: '404', title: 'Introuvable', component: NotFoundComponent},
    { path: '**', redirectTo: '404'}
];
