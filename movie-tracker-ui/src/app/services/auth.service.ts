import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authenticated = false;
  readonly baseUrl=`${environment.apiUrl}/api/login`;
  constructor(private httpClient:HttpClient) { }

 
}

