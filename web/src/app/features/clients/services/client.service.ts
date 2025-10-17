import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { Client, ClientStatus, CreateClientInterface, type PageableClientList, type UpdateClientInterface, type UpdateClientPasswordInterface } from '../models/client.model';
import { Observable, tap } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = environment.apiUrl;
  private http = inject(HttpClient);

  createClient(data: CreateClientInterface): Observable<Client> {
    return this.http.post<Client>(`${this.apiUrl}clients`, data);
  }

  getAllClientsPageable(page: number = 0, size: number = 10, cnpj: string = "", status: ClientStatus | "" = ClientStatus.ACTIVE): Observable<PageableClientList> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('cnpj', cnpj.toString())
      .set('status', status.toString());

    return this.http.get<PageableClientList>(`${this.apiUrl}clients`, { params });
  }

  getClientById(clientId: string): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}clients/${clientId}`);
  }

  getByCnpj(clientCnpj: string): Observable<Client> {
    return this.http.get<Client>(`${this.apiUrl}clients/getByCnpj/${clientCnpj}`);
  }

  updateClient(clientId: string, data: UpdateClientInterface): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}clients/${clientId}`, data);
  }
  
  deleteClient(clientId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}clients/${clientId}`);
  }

  restoreClient(clientId: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}clients/${clientId}/restore`, {});
  }

  updatePasswordClient(clientId: string, data: UpdateClientPasswordInterface): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}clients/updatePassword/${clientId}`, data);
  }

  updatePasswordClientCustom(clientId: string, data: UpdateClientPasswordInterface, token: string): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Skip-Interceptor': 'true'
    });

    // O segundo parâmetro do post é o body, o terceiro são as opções (headers, params etc.)
    return this.http.post<void>(`${this.apiUrl}clients/updatePassword/${clientId}`, data, { headers });
  }

  restorePasswordClient(clientId: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}clients/restorePassword/${clientId}`, {  });
  }
}
