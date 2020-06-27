import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MovieListComponent } from './components/movie-list/movie-list.component';
import { MovieDetailsComponent } from './components/movie-details/movie-details.component';
import { AddMovieComponent } from './components/add-movie/add-movie.component';
import { MovieSearchComponent } from './components/movie-search/movie-search.component';
import { AddTvComponent } from './components/add-tv/add-tv.component';
import { TvListComponent } from './components/tv-list/tv-list.component';
import { TvDetailsComponent } from './components/tv-details/tv-details.component';


const routes: Routes = [
  {path:'movies/search', component: MovieSearchComponent},
  {path:'movies/new', component: AddMovieComponent},
  {path: 'movies/details/:id',component: MovieDetailsComponent},
  {path: 'movies', component: MovieListComponent },
  
  {path:'tv/new', component: AddTvComponent},
  {path: 'tv/details/:id',component: TvDetailsComponent},
  {path:'tv', component: TvListComponent},
  {path: '', redirectTo: '/movies', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }