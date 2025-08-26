import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import type { Observable } from 'rxjs';
import type { TourScreenEnum, TourScreenInterface, TourScreenUpdateInterface } from '../models/tour-screen.models';

@Injectable({
  providedIn: 'root'
})
export class TourScreenService {
  private apiUrl = environment.apiUrl;
  private http = inject(HttpClient);

  getByAuthUserIdAndScreen(screen: TourScreenEnum): Observable<TourScreenInterface> {
    const params = new HttpParams().set('screen', screen.toString())
    return this.http.get<TourScreenInterface>(`${this.apiUrl}tour-screen`, { params });
  }

  updateTourScreen(id: number, data: TourScreenUpdateInterface): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}tour-screen/${id}`, data);
  }
}
