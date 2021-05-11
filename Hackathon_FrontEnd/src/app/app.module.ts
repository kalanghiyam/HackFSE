import { BrowserModule } from '@angular/platform-browser';
import { NgModule,CUSTOM_ELEMENTS_SCHEMA  } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from "@angular/router";
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import {MatTableModule,MatIconModule,MatSortModule,MatPaginatorModule,MatDialogModule,MatRadioModule,MatListModule,
  MatGridListModule, MatFormFieldModule, MatInputModule,MatSelectModule,MatAutocompleteModule,MatCheckboxModule} from '@angular/material';
import { MatTabsModule } from '@angular/material';
import { CdkTableModule} from '@angular/cdk/table';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {ReportServiceService} from './service/report-service.service';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { FileUploadComponent } from './file-upload/file-upload.component';

import { UserRoleComponent } from './user/user-role/user-role.component';
import { ViewUserComponent } from './user/view-user/view-user.component';
import { AddUserComponent } from './user/add-user/add-user.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { ReportComponent } from './report/report.component';

import { FeedbackResponseComponent } from './feedback-response/feedback-response.component';
import { SendEmailComponent } from './send-email/send-email.component';
import { EmailTemplateComponent } from './email-template/email-template.component';
import { FeedbackDetailsComponent } from './feedback/feedback-details/feedback-details.component';
import { ViewFeedbackComponent } from './feedback/view-feedback/view-feedback.component';
import { AddFeedbackComponent } from './feedback/add-feedback/add-feedback.component';
import { EditFeedbackComponent } from './feedback/edit-feedback/edit-feedback.component';

import { AuthInterceptor } from './auth/auth-interceptor';
import { TokenStorageService } from './auth/token-storage.service';
import { UserRoleService } from './service/user-role.service';
import { FeedBackService } from './service/feedback-details.service';
import { UploadFileService } from './service/file-upload.service';
import { EmailService } from './service/email.service';
import { ChartComponent} from 'src/app/report/chart/chart.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import { SummaryComponent } from './report/summary/summary.component';
import { ResponseComponent } from './report/response/response.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent,
    FileUploadComponent,
    UserRoleComponent,
    AddUserComponent,
    ViewUserComponent,
    FeedbackResponseComponent,
    SendEmailComponent,
    EditUserComponent,
    ViewFeedbackComponent,
    FeedbackDetailsComponent,
    AddFeedbackComponent,
    EditFeedbackComponent,
    EmailTemplateComponent,
    ChartComponent,
    ReportComponent,
    SummaryComponent,
    AccessDeniedComponent,
    ResponseComponent  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    MatTabsModule,
    CdkTableModule,
    MatTableModule,
    MatIconModule,
    MatSortModule,
    BrowserAnimationsModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatDialogModule,
    MatSelectModule,
    MatInputModule,
    HttpClientModule,
    MatAutocompleteModule,
    MatCheckboxModule,
    MatRadioModule,
    MatGridListModule,
    MatListModule
  ],
  entryComponents: [EditUserComponent,EditFeedbackComponent],
   providers: [TokenStorageService,AuthInterceptor,UploadFileService,
    UserRoleService,FeedBackService,EmailService,ReportServiceService],
//   providers: [
//     { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
//     { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
//     // provider used to create fake backend
//     UploadFileService,UserRoleService,FeedBackService,AuthenticationService
// ],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
  bootstrap: [AppComponent]
})
export class AppModule { }
