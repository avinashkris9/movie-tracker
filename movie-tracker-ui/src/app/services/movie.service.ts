import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { Movie } from '../model/movie';
import { catchError, map, tap } from 'rxjs/operators';
import { MovieSearch } from '../model/movie-search';
import { DatePipe } from '@angular/common';
@Injectable({
  providedIn: 'root'
})
export class MovieService {
  


  private baseUrl="http://localhost:8081/movies";

  constructor(private httpClient:HttpClient,public datepipe: DatePipe) { }

  getAllMovies(page:number,pageSize:number):Observable<GetAllMovies>
  {
   
    const paginatedUrl = `${this.baseUrl}?size=${pageSize}&page=${page}`;
    console.log(" Requesting through "+paginatedUrl)
    return this.httpClient.get<GetAllMovies>(paginatedUrl)
    .pipe
    (
      
      catchError(this.handleError<GetAllMovies>('getHeroes',))
    )
  }



  getByMovieId(movieId: number):Observable<Movie> {

    const url=`${this.baseUrl}/${movieId}`;
    return this.httpClient.get<Movie>(url).pipe
    (
     
      catchError(this.handleError<Movie>('getMovies'))
    )
    
  }


  updateMovie(movie:Movie ):Observable<any>
  {
    JSON.stringify(movie);
    console.log(` Calling API for update using ${movie}`);
    console.log(` Calling API for update using ${ JSON.stringify(movie)}`);
    
    
    const url=`${this.baseUrl}/${movie.id}`;
    return this.httpClient.put(url,movie).pipe
    (
      catchError(this.handleError<Movie>('getMovies'))
    )

  }

  addMovie(movie:Movie):Observable<Movie>
  {

    return this.httpClient.post<Movie>(this.baseUrl,movie).pipe
    (
      tap((newMovie: Movie) => console.log(`added movie w/ id=${newMovie.id}`)),
      catchError(this.handleError<Movie>('addMovie'))
    )
  }


  searchMovie(term:string ): Observable<MovieSearch[]>
  {
    if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }

    const theMovieDbUrl = `http://localhost:8081/themoviedb/search?showType=movie&name=${term}`;
    console.log(theMovieDbUrl);
    return this.httpClient.get<any>(theMovieDbUrl).pipe(
      map(response => response.results),
      
        
      catchError(this.handleError<MovieSearch[]>('searchHeroes'))
    );

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



interface GetAllMovies 

{

  movieDetails:Movie[];
  totalElements: number;
  totalPages: number; 

}
