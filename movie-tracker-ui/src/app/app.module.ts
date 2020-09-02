import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatSliderModule } from '@angular/material/slider';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSidenavModule, MatButtonModule, MatIconModule, MatCardModule, MatListModule, MatTabsModule, MatExpansionModule, MatProgressBarModule} from '@angular/material';
import {MatToolbarModule} from '@angular/material/toolbar';
import { HttpClientModule }    from '@angular/common/http';
import { MovieListComponent } from './components/movie-list/movie-list.component';
import {MatGridListModule} from '@angular/material/grid-list';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {MatDividerModule} from '@angular/material/divider';
import { MovieDetailsComponent } from './components/movie-details/movie-details.component';
import {MatBadgeModule} from '@angular/material/badge';
import {MatDatepickerModule } from '@angular/material/datepicker';
import { ReactiveFormsModule } from '@angular/forms';
import {MatMenuModule} from '@angular/material/menu';
import { AppRoutingModule } from './app-routing.module';
import { AddMovieComponent } from './components/add-movie/add-movie.component';
import {MatInputModule} from '@angular/material/input';
import { MovieSearchComponent } from './components/movie-search/movie-search.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete'
import { FormsModule } from '@angular/forms';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatNativeDateModule } from '@angular/material/core';
import { MessagesComponent } from './components/messages/messages.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import { MatDialogModule } from '@angular/material/dialog';
import { MovieEditComponent } from './components/movie-edit/movie-edit.component';
import { DatePipe } from '@angular/common';
import { AddTvComponent } from './components/add-tv/add-tv.component';
import { TvListComponent } from './components/tv-list/tv-list.component';
import { TvDetailsComponent } from './components/tv-details/tv-details.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { SharedModule } from './shared.module';
import { DefaultLayoutComponent } from './components/layouts/default-layout/default-layout.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HighchartsChartModule } from 'highcharts-angular';
import { WatchListComponent } from './components/watch-list/watch-list.component';
import { TvSearchComponent } from './components/tv-search/tv-search.component';
import { TvEditComponent } from './components/tv-edit/tv-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    MovieListComponent,
    MovieDetailsComponent,
    AddMovieComponent,
    MovieSearchComponent,
    MessagesComponent,
    AdminPanelComponent,
    MovieEditComponent,
     AddTvComponent,
    TvListComponent,
    TvDetailsComponent,
    DefaultLayoutComponent,
    WatchListComponent,
    TvSearchComponent,
    TvEditComponent
   

  ],
  imports: [
    SharedModule,
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
    MatBadgeModule,
    HttpClientModule,
    MatGridListModule,
    FontAwesomeModule,
    MatDividerModule,
    AppRoutingModule,
    MatDatepickerModule,
    MatPaginatorModule,
    FormsModule,MatNativeDateModule,MatDialogModule,MatTooltipModule,MatCardModule,FlexLayoutModule,HighchartsChartModule,MatTabsModule,MatExpansionModule,MatProgressBarModule
  ],
  providers:[DatePipe]      ,
  bootstrap: [AppComponent],
  // since we are dynamically adding this component
  entryComponents: [ MessagesComponent,MovieEditComponent,TvEditComponent ]
})


export class AppModule { }
