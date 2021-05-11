import { Component, OnInit } from '@angular/core';
import { EmailTemplate } from '../model/emailTemplate.model';
import { EmailService } from '../service/email.service';
import { Observable } from 'rxjs';
import {MatPaginator, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-email-template',
  templateUrl: './email-template.component.html',
  styleUrls: ['./email-template.component.css']
})
export class EmailTemplateComponent implements OnInit {

  emailTemplates: EmailTemplate[];
  displayedColumns: string[] = ['status','emailTemplate','emailNotification','notificationInterval','Save'];
  dataSource = new MatTableDataSource<EmailTemplate>();
  errorMessage:string;
  constructor(private emailService:EmailService) { }

  ngOnInit() {
    this.getTemplates();
  }

  getTemplates() {
     this.emailService.getTemplateList().
       subscribe(emailTemplates => this.dataSource.data = emailTemplates as EmailTemplate[]);
  }

  save(emailTemplate:EmailTemplate){
    this.errorMessage = '';
    console.log("sindie");
    this.emailService.update(emailTemplate).subscribe(data=>{
      this.errorMessage = 'Record Save succesfully for the status  '+emailTemplate.status;
      this.getTemplates();
    }, error => {
      this.errorMessage = 'Error while saving the record';
     console.log("error"+error);
   });
  }
}
