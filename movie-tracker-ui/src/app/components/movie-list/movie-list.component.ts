import { Component, OnInit } from '@angular/core';
import { MovieService } from 'src/app/services/movie.service';
import { Movie } from 'src/app/model/movie';
import { faStar } from '@fortawesome/free-solid-svg-icons';
import { PageEvent } from '@angular/material';
@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {

   movies:Movie[]=null;;
  length: number =1000;
   pageSize:number =8;
   page: number =0;
   pageEvent: PageEvent;
   previousPageSize: number=8;;
  
  constructor( private movieService:MovieService) { }
  faCoffee = faStar;
  ngOnInit() {

   this.getAllWatchedMovies();

  }


  updatePageSize(event: any) {
    
    
 
    this.pageSize = event.pageSize;
    if(event.pageSize != this.previousPageSize)
    {
      this.previousPageSize=event.pageSize;

      this.page=0;
    }
    else
    {
      this.pageSize = event.pageSize;
      this.page = event.pageIndex;
    }
   
    this.getAllWatchedMovies();
    

  }


  getAllWatchedMovies()
  {
  

     this.movieService.getAllMovies(this.page,this.pageSize).subscribe
     (
         data => 
         {
          this.movies =data.movieDetails;
          this.length=data.totalElements;
          
    
         
         }
     );
  }


}
