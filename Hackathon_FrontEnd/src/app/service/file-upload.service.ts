import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EventEmployeeInfo } from '../model/eventEmployeeInfo.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class UploadFileService {
 constructor (private http: HttpClient){}
 private url = window["apiBaseUrl"]+"eventFileUpload";
    save(data: FormData): Observable<HttpEvent<{}>> {
        const req=new HttpRequest("POST",this.url+"/fileUpload",data,{
            reportProgress:true,
            responseType: 'text'
        }
        );
        return this.http.request(req);
      }

      getEventDropDownList(): Observable<any[]>{
        return this.http.get<any[]>(this.url+"/getDropDownList");
      }

      getSendMailEventList(): Observable<EventEmployeeInfo[]>{
        return this.http.get<EventEmployeeInfo[]>(this.url+"/getEmailList");
      }

      sendEmail(eventEmployeeInfo: EventEmployeeInfo[]) : Observable<any>  {
        console.log("this-->"+eventEmployeeInfo);
        const headers = new HttpHeaders({'Content-Type':'application/json; charset=utf-8'});
        return this.http.put(this.url+"/emailTemplate/sendEmail",
                JSON.stringify(eventEmployeeInfo),{headers: headers});
      }
      
      getSearchList(eventStatus : string): Observable<EventEmployeeInfo[]>{
        return this.http.get<EventEmployeeInfo[]>(this.url+"/getSearchList/"+eventStatus);
      }
}