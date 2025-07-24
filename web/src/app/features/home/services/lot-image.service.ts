import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { lotImageInterface, PageableLotImagesList } from '../models/lot-image.models';

@Injectable({
  providedIn: 'root'
})
export class LotImageService {
  private apiUrl = environment.apiUrl;
  private http = inject(HttpClient);

  processImage(lotId: number, data: FormData): Observable<lotImageInterface> {
    return this.http.post<lotImageInterface>(`${this.apiUrl}lots/${lotId}/images/process_image`, data);
  }

  getAllLotImages(page: number = 1, size: number = 10, lotId: number, student: string = ""): Observable<PageableLotImagesList> {
    const params = new HttpParams()
      .set("page", page)
      .set("size", size)
      .set("student", student);

    return this.http.get<PageableLotImagesList>(`${this.apiUrl}lots/${lotId}/images`, { params });
  }

  getLotImageById(lotId: number, lotImageId: number): Observable<lotImageInterface> {
    return this.http.get<lotImageInterface>(`${this.apiUrl}lots/${lotId}/images/${lotImageId}`);
  }
}
