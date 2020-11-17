import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  credentials = {username: '', password: ''};

  constructor( private http: HttpClient, private router: Router,private app:AuthService) {
    this.app.authenticate(undefined, undefined);
  }
  login() {
    this.app.authenticate(this.credentials, () => {
        this.router.navigateByUrl('/');
    });
    return false;
  }

  ngOnInit() {
  }

}
