import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BorrowBookService {

  private baseUrl = 'http://localhost:8080/springboot-crud-rest/borrows';
  constructor(private http: HttpClient ) { }

  borrowBook(reader_id: number, book_id: number): Observable<any> {
    const params = new HttpParams()
      .set('reader_id', reader_id)
      .set('book_id', book_id)
    return this.http.post(`${this.baseUrl}` + `/givebook`,null, {params: params, responseType: "text"});
  }
}
