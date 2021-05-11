import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../auth/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  info: any;

  constructor(private router: Router,private token: TokenStorageService) { }

  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      role: this.token.getRole()
    };
    console.log(this.info.role);
    if(this.info.role == null || this.info.token == null || this.info.username == null){
      this.router.navigate(['/denied']);
    }
  }

  logout() {
    this.token.signOut();
    window.location.reload();
  }
}

