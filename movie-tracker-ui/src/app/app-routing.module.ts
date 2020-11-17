import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MovieListComponent } from './components/movie-list/movie-list.component';
import { MovieDetailsComponent } from './components/movie-details/movie-details.component';
import { AddMovieComponent } from './components/add-movie/add-movie.component';
import { MovieSearchComponent } from './components/movie-search/movie-search.component';
import { AddTvComponent } from './components/add-tv/add-tv.component';
import { TvListComponent } from './components/tv-list/tv-list.component';
import { TvDetailsComponent } from './components/tv-details/tv-details.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { HelpPageComponent } from './shared/help-page/help-page.component';
import { WatchListComponent } from './components/watch-list/watch-list.component';
import { TvSearchComponent } from './components/tv-search/tv-search.component';
import { DashComponent } from './dash/dash.component';
import { LoginComponent } from './components/login/login.component';


const routes: Routes = [
  {path:'movies/search', component: MovieSearchComponent},
  {path:'tv/search', component: TvSearchComponent},
  {path:'movies/new', component: AddMovieComponent},
  {path: 'movies/details/:id',component: MovieDetailsComponent},
  {path: 'movies', component: MovieListComponent },
  {path:'help',component:HelpPageComponent},
  {path:'tv/new', component: AddTvComponent},
  {path: 'tv/details/:id',component: TvDetailsComponent},
  {path:'tv', component: TvListComponent},
  {path :'admin', component: AdminPanelComponent},
  {path:'watchlist',component: WatchListComponent},
  {path:'dashboard',component: DashComponent},
  {path:'login',component: LoginComponent},
  {path: '', redirectTo: '/admin', pathMatch: 'full' },


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }