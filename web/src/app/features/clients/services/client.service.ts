import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import type { Client, CreateClient } from '../models/client.model';
import { Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = environment.apiUrl;
  private http = inject(HttpClient);

  createClient(data: CreateClient): Observable<Client> {
    return this.http.post<Client>(`${this.apiUrl}clients`, data).pipe(
      tap(response => {
        console.log("Cliente criado: ", response);
      })
    );
  }
}
