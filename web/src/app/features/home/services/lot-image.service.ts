import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { lotImageInterface, PageableLotImagesList, type LotImageHashInterface, type UpdateLotImageQuetionInterface } from '../models/lot-image.models';

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

  deleteLotImage(lotId: number, lotImageId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}lots/${lotId}/images/${lotImageId}`);
  }

  updateLotImageQuestions(lotId: number, lotImageId: number, data: UpdateLotImageQuetionInterface[]): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}lots/${lotId}/images/${lotImageId}`, data);
  }

  getHashs(lotId: number): Observable<LotImageHashInterface[]> {
    return this.http.get<LotImageHashInterface[]>(`${this.apiUrl}lots/${lotId}/images/getHashs`);
  }

  downloadAll(lotId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}lots/${lotId}/images/download-all`, {
      responseType: 'blob'
    });
  }
}
