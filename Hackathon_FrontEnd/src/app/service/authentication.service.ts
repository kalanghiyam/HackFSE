import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../model/user.model';


@Injectable()
export class AuthenticationService {
    private userUrl = window["apiBaseUrl"]+"userDetails";
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;

    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(sessionStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    login(assoicateId : number) {
        console.log("inside"+assoicateId);
        return this.http.get<User>(this.userUrl+"/getLoginUser/"+assoicateId).subscribe(user => {
            console.log("-----------"+user);
            if (user && user.token) {
            sessionStorage.setItem('currentUser', JSON.stringify(user));
            console.log("-----------"+user);
            this.currentUserSubject.next(user);
            }
            return user;
            });
    }

}