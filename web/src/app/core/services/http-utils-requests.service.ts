import { inject, Injectable } from '@angular/core';
import { HttpBackend, HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import type { GetUrlsResponseInterface } from '../models/http-utils-requests.model';

@Injectable({
  providedIn: 'root'
})
export class HttpUtilsRequestsService {
  private httpBackend = inject(HttpBackend);
  private httpWithoutInterceptor;

  constructor() {
    this.httpWithoutInterceptor = new HttpClient(this.httpBackend);
  }

  getUrls(codigo: number): Observable<GetUrlsResponseInterface[]> {
    return this.httpWithoutInterceptor.get<GetUrlsResponseInterface[]>(`https://computex.com.br/urls/controller.php?action=getUrlsID&codigo=${codigo}`);
  }
}
