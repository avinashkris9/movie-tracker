import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatSliderModule } from '@angular/material/slider';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSidenavModule, MatButtonModule, MatIconModule, MatCardModule, MatListModule} from '@angular/material';
import {MatToolbarModule} from '@angular/material/toolbar';
import { HttpClientModule }    from '@angular/common/http';
import { MovieListComponent } from './components/movie-list/movie-list.component';
import {MatGridListModule} from '@angular/material/grid-list';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {MatDividerModule} from '@angular/material/divider';
import { MovieDetailsComponent } from './components/movie-details/movie-details.component';
import {MatDatepickerModule } from '@angular/material/datepicker';
import { ReactiveFormsModule } from '@angular/forms';
import {MatMenuModule} from '@angular/material/menu';
import { AppRoutingModule } from './app-routing.module';
import { AddMovieComponent } from './components/add-movie/add-movie.component';
import {MatInputModule} from '@angular/material/input';
import { MovieSearchComponent } from './components/movie-search/movie-search.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete'
import { FormsModule } from '@angular/forms';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatNativeDateModule } from '@angular/material/core';
@NgModule({
  declarations: [
    AppComponent,
    MovieListComponent,
    MovieDetailsComponent,
    AddMovieComponent,
    MovieSearchComponent
    
 
  ],
  imports: [
    BrowserModule,
    MatInputModule,
    MatMenuModule,
    BrowserAnimationsModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatToolbarModule,
    ReactiveFormsModule,
    MatSidenavModule,
    MatIconModule,
    MatCardModule,
    MatListModule,
    MatSnackBarModule,
    HttpClientModule,
    MatGridListModule,
    FontAwesomeModule,
    MatDividerModule,
    AppRoutingModule,
    MatDatepickerModule,
    FormsModule,MatNativeDateModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
