import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {FeedBackDetails} from '../model/feedback.model';


@Injectable()
export class FeedBackService {

  private url = window["apiBaseUrl"]+"dashboard/feedBackDetails";

  constructor(private http: HttpClient) { }

  get(): Observable<FeedBackDetails[]>{
	return this.http.get<FeedBackDetails[]>(this.url+"/get");
  }

     /** POST: add a new user to the server */
 add(user: FeedBackDetails) : Observable<FeedBackDetails> {
  const headers = new HttpHeaders({'Content-Type':'application/json; charset=utf-8'});
  return this.http.post<FeedBackDetails>(this.url+"/create", JSON.stringify(user), {headers: headers});
 
}

delete(id: number){
  return this.http.delete(this.url+"/"+ id);
}


update(user : FeedBackDetails){
    const headers = new HttpHeaders({'Content-Type':'application/json; charset=utf-8'});
    this.http.put(this.url+"/update", JSON.stringify(user), {headers: headers}).subscribe(() => user);
}

getFeedbackDetails(eventStatus : string): Observable<FeedBackDetails[]>{
	return this.http.get<FeedBackDetails[]>(this.url+"/getDetails/"+eventStatus);
}

saveEventResponse(feedbackDetails: FeedBackDetails[],associateId:string,eventId:string){
  console.log("this-->");
  const headers = new HttpHeaders({'Content-Type':'application/json; charset=utf-8'});
  const url2 = this.url+"/saveResponse/";
  return this.http.put(url2+associateId+"/"+eventId,JSON.stringify(feedbackDetails), {headers: headers});
}



}
