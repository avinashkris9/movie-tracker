import { Component, OnInit, Inject } from '@angular/core';
import { MovieService } from 'src/app/services/movie.service';
import { ActivatedRoute } from '@angular/router';
import { Movie } from 'src/app/model/movie';
import { faCalendar,faStar } from '@fortawesome/free-solid-svg-icons';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MovieEditComponent } from '../movie-edit/movie-edit.component';
@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent implements OnInit {

  private movieId: number;
   movie:Movie=new Movie();
  faCalendar = faCalendar;
  faStar=faStar;
  constructor( private route:ActivatedRoute , private movieService:MovieService,
    public dialog: MatDialog) { }


  ngOnInit() {
    this.getMovieDetails();
  }
  getMovieDetails() {
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has("id");
    if (hasCategoryId) {

      this.movieId = +this.route.snapshot.paramMap.get("id");
      this.movieService.getByMovieId(this.movieId).subscribe
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

