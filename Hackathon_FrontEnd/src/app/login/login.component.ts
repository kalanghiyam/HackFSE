import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup,FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../auth/auth.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { AuthLoginInfo } from '../auth/login-info';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  isLoggedIn = false;
  invalidLogin = false;
  errorMessage = '';
  private loginInfo: AuthLoginInfo;

  constructor(private formBuilder: FormBuilder,private router: Router,   
     private authService: AuthService, private tokenStorage: TokenStorageService ) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
        userName: ['', Validators.required],
        password: ['', Validators.required]
    });

    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
  }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
            return;
    }else{
    this.loginInfo = new AuthLoginInfo(this.loginForm.controls.userName.value,this.loginForm.controls.password.value);
    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveAssociateId(this.loginForm.controls.userName.value);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveRole(data.role);
        this.tokenStorage.saveEvent(data.eventId);
        this.tokenStorage.saveEventStatus(data.eventStatus);
        this.tokenStorage.saveEventResponded(data.responded);
        this.isLoggedIn = false;
        this.router.navigate(['/header']);
      },
      error => {
        console.log(error);
        console.log("inside error");
       
        this.invalidLogin = true;
      }
    );
    }
  }

  reloadPage() {
    window.location.reload();
  }
 
}
