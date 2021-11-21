import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {catchError, Observable, retry, throwError} from "rxjs";
import {Message} from "@angular/compiler/src/i18n/i18n_ast";

@Injectable({
  providedIn: 'root'
})
export class ReaderService {
  private baseUrl = 'http://localhost:8080/springboot-crud-rest/customer';
  constructor(private http: HttpClient ) { }
  getReaderList(): Observable<any> {
    return this.http.get(`${this.baseUrl}` + `/readers`)
  }
 /* deleteBookByID(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}` + '/delete/' + `${id}`, { responseType: Stock });
  }*/
  getPageableReaders(pageNumber: number,
                   pageSize: number,
                   city: string): Observable<any> {

    let params = new HttpParams();
    params = params.append('page', pageNumber.toString());
    params = params.append('size', pageSize.toString());
    params = params.append('city', city.toString());

    return this.http.get<any>(`${this.baseUrl}` + `/readers/pageable`, { params })
      .pipe(
        retry(3),
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.');
  }
  getListCities(): Observable<Array<string>> {
    debugger;
    return this.http.get<Array<string>>(`${this.baseUrl}` + `/cities`)
      .pipe(
        retry(3),
        catchError(this.handleError)
      );
   }
  }
