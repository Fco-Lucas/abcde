import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PageableAuditLogQuestionList } from '../models/audit-log-questions.model';

@Injectable({
  providedIn: 'root'
})
export class AuditLogQuestionService {
  private apiUrl = environment.apiUrl;
  private http = inject(HttpClient);

  getAllAuditLogQuestionPageable(
    page: number = 1,
    size: number = 10,
    imageId: number,
    user: string = "",
    startDate: string = "",
    endDate: string = "",
  ): Observable<PageableAuditLogQuestionList> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('imageId', imageId.toString())
      .set('user', user)
      .set('startDate', startDate)
      .set('endDate', endDate);
      
    return this.http.get<PageableAuditLogQuestionList>(`${this.apiUrl}logs-questions`, { params });
  }
}
