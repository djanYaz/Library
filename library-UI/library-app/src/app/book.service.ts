import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private baseUrl = 'http://localhost:8080/springboot-crud-rest/app';
  constructor(private http: HttpClient ) { }

  getBookList(): Observable<any> {
    return this.http.get(`${this.baseUrl}` + `/books`)
  }
}
