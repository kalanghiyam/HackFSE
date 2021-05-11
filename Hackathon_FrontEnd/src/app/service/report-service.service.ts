import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders,HttpRequest } from '@angular/common/http';
import { EventSummaryDetails } from '../model/report.model';
import { Observable } from 'rxjs'; 
import { Dashboard } from '../model/dahboard.model';
import { detachProjectedView } from '@angular/core/src/view/view_attach';
import { ResponseSummaryDTO } from 'src/app/model/responseSummary.model';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};  

@Injectable() 
export class ReportServiceService {  

  private reportUrl =window["apiBaseUrl"]+"dashboard";
  private dasboardUrl =window["apiBaseUrl"]+"dashboard";
  private eventUrl =window["apiBaseUrl"]+"eventFileUpload";
  
  constructor(private http: HttpClient) {}

  fetchSmileyReport(role:string,associateId:string) :Observable<Dashboard[]>{
    return this.http.get<Dashboard[]>(this.dasboardUrl+"/getSimleyReport/"+role+"/"+associateId);
  } 

  getFeedbackRespondedList():Observable<Dashboard[]>{
    return this.http.get<Dashboard[]>(this.dasboardUrl+"/getResponseList/");
  }
  getEventDashboardReport(role:string,associateId:string) :Observable<Dashboard[]>{
    return this.http.get<Dashboard[]>(this.eventUrl+"/getDashboardReport/"+role+"/"+associateId);
  } 
  

  getEntries(): Observable<EventSummaryDetails[]>{
    return this.http.get<EventSummaryDetails[]>(this.dasboardUrl+"/getSummaryReport");
  } 

  getLocationDropdown() :Observable<any[]>{
    return this.http.get<any[]>(this.dasboardUrl+"/getLocation");
  }
  getProjectDropdown() :Observable<any[]>{  
    return this.http.get<any[]>(this.dasboardUrl+"/getProject");
  }  
  getSearchSummary(location : string, project : string): Observable<EventSummaryDetails[]>{
    return this.http.get<EventSummaryDetails[]>(this.dasboardUrl+"/searchSummary/"+location+"/"+project);
  } 

  getResponses(): Observable<ResponseSummaryDTO[]>{  
      return this.http.get<ResponseSummaryDTO[]>(this.dasboardUrl+"/getResponse");
  }   
      
      

}
