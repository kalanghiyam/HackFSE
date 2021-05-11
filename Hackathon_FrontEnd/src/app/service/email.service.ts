import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders,HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmailTemplate } from '../model/emailTemplate.model';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class EmailService {

    private url = window["apiBaseUrl"]+"eventFileUpload/emailTemplate";

    constructor(private http: HttpClient) { }
  
    getTemplateList(): Observable<EmailTemplate[]>{
      return this.http.get<EmailTemplate[]>(this.url+"/get");
    }

    update(emailTemplate : EmailTemplate){
      console.log("sindie");
      const headers = new HttpHeaders({'Content-Type':'application/json; charset=utf-8'});
    return  this.http.put(this.url+"/update", JSON.stringify(emailTemplate), {headers: headers});
  }
}
