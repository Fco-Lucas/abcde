import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { LotInterface, LotStatusEnum, type LotCreateInterface, type LotUpdateInterface, type PageableLotList } from '../models/lot.model';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LotService {
  private apiUrl = environment.apiUrl;
  private http = inject(HttpClient);

  createLot(data: LotCreateInterface): Observable<LotInterface> {
    return this.http.post<LotInterface>(`${this.apiUrl}lots`, data);
  }

  getAllLotsUserPageable(
    page: number = 1,
    size: number = 10,
    name: string = "",
    client: string = "",
    clientUser: string = "",
    status: LotStatusEnum | "" = ""
  ): Observable<PageableLotList> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('name', name)
      .set('client', client)
      .set('clientUser', clientUser)
      .set('status', status.toString());
      
    return this.http.get<PageableLotList>(`${this.apiUrl}lots`, { params });
  }

  getLotById(id: number): Observable<LotInterface> {
    return this.http.get<LotInterface>(`${this.apiUrl}lots/${id}`);
  }

  updateLot(id: number, data: LotUpdateInterface): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}lots/${id}`, data);
  }

  deleteLot(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}lots/${id}`);
  }

  downloadTxt(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}lots/${id}/download-txt`, {
      responseType: 'blob'
    });
  }
}
