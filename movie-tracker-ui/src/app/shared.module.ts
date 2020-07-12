import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './shared/footer/footer.component';
import { HeaderComponent } from './shared/header/header.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';

import {  MatDividerModule, MatSidenavModule, MatToolbarModule, MatIconModule, MatButtonModule, MatMenuModule, MatListModule } from '@angular/material';
import {FlexLayoutModule} from '@angular/flex-layout'
import { RouterModule } from '@angular/router';
import { HelpPageComponent } from './shared/help-page/help-page.component';

@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent,
    SidebarComponent,
    HelpPageComponent,
    
  ],
  imports: [
    CommonModule,
    MatDividerModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    FlexLayoutModule,
    MatMenuModule,
    MatListModule,
    RouterModule

  ],
  exports:
  [
    FooterComponent,
    HeaderComponent,
    SidebarComponent

  ]
})
export class SharedModule { }
