import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ClientUserInterface, ClientUserStatus, CreateClientUserInterface, PageableClientUsersList, UpdateClientUserInterface, type UpdateClientUserPasswordInterface } from '../models/clientUsers.model';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClientUsersService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  createClientUser(clientId: string, data: CreateClientUserInterface): Observable<ClientUserInterface> {
    return this.http.post<ClientUserInterface>(`${this.apiUrl}clients/${clientId}/users`, data);
  }

  getAllClientsUserPageable(clientId: string, page: number = 1, size: number = 10, name: string = "", email: string = "", status: ClientUserStatus | "" = ClientUserStatus.ACTIVE): Observable<PageableClientUsersList> {
    const params = new HttpParams()
      .set("clientId", clientId.toString())
      .set("page", page.toString())
      .set("size", size.toString())
      .set("name", name.toString())
      .set("email", email.toString())
      .set("status", status.toString());

    return this.http.get<PageableClientUsersList>(`${this.apiUrl}clients/${clientId}/users`, { params });
  }

  getClientUserById(clientId: string, clientUserId: string): Observable<ClientUserInterface> {
    return this.http.get<ClientUserInterface>(`${this.apiUrl}clients/${clientId}/users/${clientUserId}`);
  }

  updateClientUser(clientId: string, clientUserId: string, data: UpdateClientUserInterface): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}clients/${clientId}/users/${clientUserId}`, data);
  }

  deleteClientUser(clientId: string, clientUserId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}clients/${clientId}/users/${clientUserId}`);
  }

  restoreClientUser(clientId: string, clientUserId: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}clients/${clientId}/users/${clientUserId}/restore`, {});
  }

  updatePasswordClientUser(clientId: string, clientUserId: string, data: UpdateClientUserPasswordInterface): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}clients/${clientId}/users/updatePassword/${clientUserId}`, data);
  }

  updatePasswordClientUserCustom(clientId: string, clientUserId: string, data: UpdateClientUserPasswordInterface, token: string): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Skip-Interceptor': 'true'
    });

    return this.http.post<void>(`${this.apiUrl}clients/${clientId}/users/updatePassword/${clientUserId}`, data, { headers });
  }

  restorePasswordClientUser(clientId: string, clientUserId: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}clients/${clientId}/users/restorePassword/${clientUserId}`, {  });
  }
}
