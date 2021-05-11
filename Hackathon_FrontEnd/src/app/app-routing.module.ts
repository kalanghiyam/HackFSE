import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FileUploadComponent} from './file-upload/file-upload.component';
import { HomeComponent } from './home/home.component';
import {HeaderComponent} from './header/header.component';
import {LoginComponent} from './login/login.component';
import {UserRoleComponent} from './user/user-role/user-role.component';
import {AddUserComponent} from './user/add-user/add-user.component';
import { ViewUserComponent } from './user/view-user/view-user.component';
import {FeedbackResponseComponent} from './feedback-response/feedback-response.component';
import { SendEmailComponent } from './send-email/send-email.component';
import { ViewFeedbackComponent } from './feedback/view-feedback/view-feedback.component';
import { FeedbackDetailsComponent } from './feedback/feedback-details/feedback-details.component';
import { AddFeedbackComponent } from './feedback/add-feedback/add-feedback.component';
import { EmailTemplateComponent } from './email-template/email-template.component';
import {ReportComponent} from './report/report.component'
import { ChartComponent } from './report/chart/chart.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import { SummaryComponent } from './report/summary/summary.component';
import { ResponseComponent } from './report/response/response.component';

const routes: Routes = [
   {path: '',redirectTo: 'login', pathMatch: 'full'},
  {path: 'login',component: LoginComponent},
  {path: "denied", component: AccessDeniedComponent} ,
   { 
    path: "header",   
    component: HeaderComponent,
    children: [
        {path: "report", component: ReportComponent, children:[
          {path: "", component: ChartComponent},
          {path: "chart", component: ChartComponent},
          {path: "summary", component: SummaryComponent},
          {path: "response", component: ResponseComponent}
          ] },
      { path: "home", component: HomeComponent },
      { path: "fileUpload", component: FileUploadComponent },
      { path: "eventResponse", component: FeedbackResponseComponent },
      { path: "sendEmail", component: SendEmailComponent },
      { path: "emailTemplate", component: EmailTemplateComponent },
      { path: "feedBackDetails", component: FeedbackDetailsComponent, children:[
        {path: "", component: ViewFeedbackComponent},
        {path: "addFeedBack", component: AddFeedbackComponent},
        {path: "viewFeedBack", component: ViewFeedbackComponent}
          ] },
      { path: "userRole", component: UserRoleComponent,children:[
          {path: "", component: ViewUserComponent},
          {path: "addUser", component: AddUserComponent},
          {path: "viewUser", component: ViewUserComponent}
          ] },
      {path: "denied", component: AccessDeniedComponent} ,
     ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
