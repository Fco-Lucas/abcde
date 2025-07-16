import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ClientUserInterface, ClientUserStatus, CreateClientUserInterface, PageableClientUsersList, UpdateClientUserInterface } from '../model/clientUsers.model';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClientUsersService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  createClientUser(data: CreateClientUserInterface): Observable<ClientUserInterface> {
    return this.http.post<ClientUserInterface>(`${this.apiUrl}clientsUsers`, data);
  }

  getAllClientsUserPageable(clientId: string, page: number = 1, size: number = 10, name: string = "", cnpj: string = "", status: ClientUserStatus | "" = ""): Observable<PageableClientUsersList> {
    const params = new HttpParams()
      .set("clientId", clientId.toString())
      .set("page", page.toString())
      .set("size", size.toString())
      .set("name", name.toString())
      .set("cnpj", cnpj.toString())
      .set("status", status.toString());

    return this.http.get<PageableClientUsersList>(`${this.apiUrl}clientsUsers`, { params });
  }

  getClientUserById(clientUserId: string): Observable<ClientUserInterface> {
    return this.http.get<ClientUserInterface>(`${this.apiUrl}clientsUsers/${clientUserId}`);
  }

  updateClientUser(clientUserId: string, data: UpdateClientUserInterface): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}clientsUsers/${clientUserId}`, data);
  }

  deleteClientUser(clientUserId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}clientsUsers/${clientUserId}`);
  }
}
