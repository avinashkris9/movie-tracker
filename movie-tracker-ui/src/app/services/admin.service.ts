import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { TrackerStatistics } from '../model/TrackerStatistics';
import { catchError } from 'rxjs/operators';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  
   readonly baseUrl=`${environment.apiUrl}/api/statistics`;
  constructor(private httpClient:HttpClient,private errorService:ErrorService) { }

  /**
   *  Get Statistics 
   */

  getStatistics():Observable<TrackerStatistics>
  {
   
   

   
    return this.httpClient.get<TrackerStatistics>(this.baseUrl)
    .pipe(
   
      catchError(this.errorService.handleError<TrackerStatistics>('getStatistics',))
    )
  }





    /**
 * Handle Http operation that failed.
 * Let the app continue.
 * @param operation - name of the operation that failed
 * @param result - optional value to return as the observable result
 */
private handleError<T>(operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {

    // TODO: send the error to remote logging infrastructure
    console.error(error); // log to console instead

    // TODO: better job of transforming error for user consumption
    console.log(`${operation} failed: ${error.message}`);

    // Let the app keep running by returning an empty result.
    // return of(result as T);

    return throwError(error);
  };
}

}






