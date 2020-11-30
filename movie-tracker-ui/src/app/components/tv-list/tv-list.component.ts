import { Component, OnInit } from '@angular/core';
import { Movie } from 'src/app/model/movie';
import { PageEvent } from '@angular/material';
import { MovieService } from 'src/app/services/movie.service';
import { faStar } from '@fortawesome/free-solid-svg-icons';
import { TvService } from 'src/app/services/tv.service';

@Component({
  selector: 'app-tv-list',
  templateUrl: './tv-list.component.html',
  styleUrls: ['./tv-list.component.css']
})
export class TvListComponent implements OnInit {

   movies:Movie[]=null;;
  length: number =1000;
   pageSize:number =8;
   page: number =0;
   pageEvent: PageEvent;
   previousPageSize: number=8;;
  constructor( private tvService:TvService) { }
  faCoffee = faStar;
  ngOnInit() {

   this.getAllWatchedMovies();

  }

//@TODO --> ng build prod was throwing error if event variable is type PageEvent
  updatePageSize(event?: any) {
    
    
    
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
  

     this.tvService.getAllTvShows(this.page,this.pageSize).subscribe
     (
         data => 
         {
          this.movies =data.movieDetails;
          this.length=data.totalElements;
          
    
         
         }
     );
  }

}
