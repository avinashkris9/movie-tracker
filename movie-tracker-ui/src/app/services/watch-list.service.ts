import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ErrorService } from './error.service';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { WatchList } from '../model/watch-list';
import { MovieSearch } from '../model/movie-search';
import { Movie } from '../model/movie';

@Injectable({
  providedIn: 'root'
})
export class WatchListService {
 

  readonly baseUrl=`${environment.apiUrl}/api/watchlist`;
  readonly baseSearchUrl=`${environment.apiUrl}/api/themoviedb`;
  constructor(private httpClient:HttpClient,public datepipe: DatePipe,private errorService:ErrorService) { }



  getAllTvShows():Observable<WatchList>
  {
   
  

    return this.httpClient.get<WatchList>(this.baseUrl)
    .pipe(   
      catchError(this.errorService.handleError<WatchList>('getWatchList',))
    )
  }




 
  addToWatchList(movie:WatchList):Observable<WatchList>
  {

    return this.httpClient.post<WatchList>(this.baseUrl,movie).pipe
    (
      tap((newMovie: WatchList) => console.log(`added movie w/ id=${newMovie.id}`)),
      catchError(this.errorService.handleError<WatchList>('addMaddWatchListovie'))
    )
  }

  deleteFromWatchList(movie:WatchList):Observable<WatchList>
  {
    const url=`${this.baseUrl}/${movie.id}`;

    return this.httpClient.delete<WatchList>(url).pipe
    (
      tap((newMovie: WatchList) => console.log(`deleted movie w/ id=${movie.id}`)),
      catchError(this.errorService.handleError<WatchList>('deleteFromWatchList'))
    )
  }
}
