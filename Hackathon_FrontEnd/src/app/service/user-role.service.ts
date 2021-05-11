import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders,HttpEvent,HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user.model';

@Injectable()
export class UserRoleService {

  private userUrl = window["apiBaseUrl"]+"auth/userDetails";

  constructor(private http: HttpClient) { }

  saveUpload(data: FormData): Observable<HttpEvent<{}>> {
    const req=new HttpRequest("POST",this.userUrl+"/fileUpload",data,{
        reportProgress:true,
        responseType: 'text'
    }
    );
    return this.http.request(req);
  }

  getUsers(): Observable<User[]>{
	return this.http.get<User[]>(this.userUrl+"/get");
  }

     /** POST: add a new user to the server */
 addUser (user: User) : Observable<User> {
  return this.http.post<User>(this.userUrl+"/create", user);
 
}

deleteUser (associateId: number) {
  return this.http.delete(this.userUrl+"/"+ associateId);
}


updateUser(user : User){
    const headers = new HttpHeaders({'Content-Type':'application/json; charset=utf-8'});

    this.http.put(this.userUrl+"/update", JSON.stringify(user), {headers: headers}).subscribe(() => user);
}

 validateEventUser(assoicateId : number) : Observable<String>{
  
  return this.http.get<String>(this.userUrl+"/eventfeedbackResponse"+assoicateId);
 }

}
