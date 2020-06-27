import { Component, OnInit } from '@angular/core';
import { TvService } from 'src/app/services/tv.service';
import { Movie } from 'src/app/model/movie';
import { faCalendar, faStar } from '@fortawesome/free-solid-svg-icons';
import { ActivatedRoute } from '@angular/router';
import { MatDialog } from '@angular/material';
import { MovieEditComponent } from '../movie-edit/movie-edit.component';

@Component({
  selector: 'app-tv-details',
  templateUrl: './tv-details.component.html',
  styleUrls: ['./tv-details.component.css']
})
export class TvDetailsComponent implements OnInit {

 
  private movieId: number;
  private movie:Movie=new Movie();
  faCalendar = faCalendar;
  faStar=faStar;
  constructor( private route:ActivatedRoute , private movieService:TvService,
    public dialog: MatDialog) { }


  ngOnInit() {
    this.getMovieDetails();
  }
  getMovieDetails() {
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has("id");
    if (hasCategoryId) {

      this.movieId = +this.route.snapshot.paramMap.get("id");
      this.movieService.getByTvId(this.movieId).subscribe
      (
 
         data => 
         {
           this.movie =data;
           console.log(this.movie)
        
         }
           )
    }
  }
 
 
  openUpdateDialog()
  {
    let dialogRef = this.dialog.open(MovieEditComponent, {
      width: '250px',
      data: this.movie,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.movie.lastWatched=result.lastWatched;
      this.movie.numberOfWatch=result.numberOfWatch;
      console.log(this.movie);
    });
  }


}
