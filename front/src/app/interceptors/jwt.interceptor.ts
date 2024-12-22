import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { SessionService } from '../services/session.service';

/**
 * Token Injection Interceptor
 * 
 * @param req Intercepted request
 * @param next Handler function
 * @returns Injected request
 */
export const jwtInterceptor: HttpInterceptorFn = (req, next) => {

  const token = inject(SessionService).token
  console.log("JWT", token)

  if(!token) {
    return next(req)
  }

  const reqWithJwt = req.clone({
    headers: req.headers.append('Authorization', `Bearer ${token}`)
  })

  return next(reqWithJwt);
};
