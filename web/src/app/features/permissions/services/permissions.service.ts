import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PermissionInterface } from '../models/permission.model';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PermissionsService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  getPermissions(): Observable<PermissionInterface[]> {
    return this.http.get<PermissionInterface[]>(`${this.apiUrl}permissions`);
  }

  getPermissionById(permissionId: string): Observable<PermissionInterface> {
    return this.http.get<PermissionInterface>(`${this.apiUrl}permissions/${permissionId}`);
  }
}
