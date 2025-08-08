import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import type { Observable } from 'rxjs';
import type { AuditLogAction, AuditLogProgram, PageableAuditLotListInterface } from '../models/audit-log.model';

@Injectable({
  providedIn: 'root'
})
export class AuditLogService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  getAllAuditLogPageable(
    page: number = 0, 
    size: number = 10,
    action: AuditLogAction | "" = "",
    client: string = "",
    user: string = "", 
    program: AuditLogProgram | "" = "", 
    details: string = "", 
    startDate: string = "", 
    endDate: string = "", 
  ): Observable<PageableAuditLotListInterface> {
    const params = new HttpParams()
      .set("page", page.toString())
      .set("size", size.toString())
      .set("action", action)
      .set("client", client)
      .set("user", user)
      .set("program", program)
      .set("details", details)
      .set("startDate", startDate)
      .set("endDate", endDate);

    return this.http.get<PageableAuditLotListInterface>(`${this.apiUrl}logs`, { params });
  }
}
