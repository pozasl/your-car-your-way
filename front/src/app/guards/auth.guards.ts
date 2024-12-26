import { Injectable } from "@angular/core";
import { CanActivate, GuardResult, MaybeAsync, Router } from "@angular/router";
import { SessionService } from "../services/session.service";
import { catchError, map, of, skip, take } from "rxjs";

/**
 * Route guard for authenticated user
 */
@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private sessionService: SessionService
  ) { }

  canActivate(): MaybeAsync<GuardResult> {
    const skipNbr = this.sessionService.resuming ? 1 : 0;
    
    return this.sessionService.$logged().pipe(
      skip(skipNbr),
      take(1),
      map(isLogged => {
        if (!isLogged) {
          this.router.navigate(['/login']);
        }
        return isLogged
      }),
      catchError((err) => {
        this.router.navigate(['/login']);
        console.error(err);
        return of(false);
      })
    );
  }
}