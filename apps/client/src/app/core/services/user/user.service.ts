import { Injectable } from '@angular/core';
import { of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
  getUserById(uid: string) {
    
  if (uid !== '123') return of(null);
  return of({ id: '123', name: 'Jair' });
  }
}
