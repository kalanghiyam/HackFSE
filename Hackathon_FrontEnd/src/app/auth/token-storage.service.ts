import { Injectable } from '@angular/core';

const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'AuthUsername';
const ROLE_KEY = 'AuthRole';
const EVENT_ID = 'AuthEvent';
const EVENTSTATUS_KEY = "AuthEventStatus";
const EVENT_RESPONDED = "AuthResponsded";
const ASSOCIATEID_KEY="AuthAssociateId";

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  signOut() {
    window.sessionStorage.clear();
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveAssociateId(associateId: string) {
    window.sessionStorage.removeItem(ASSOCIATEID_KEY);
    window.sessionStorage.setItem(ASSOCIATEID_KEY, associateId);
  }

  public getAssociateId(): string {
    return sessionStorage.getItem(ASSOCIATEID_KEY);
  }

  public saveUsername(username: string) {
    window.sessionStorage.removeItem(USERNAME_KEY);
    window.sessionStorage.setItem(USERNAME_KEY, username);
  }

  public getUsername(): string {
    return sessionStorage.getItem(USERNAME_KEY);
  }

  public saveRole(role: string) {
    window.sessionStorage.removeItem(ROLE_KEY);
    window.sessionStorage.setItem(ROLE_KEY, role);
  }

  public getRole(): string {
    return sessionStorage.getItem(ROLE_KEY);
  }

  public saveEvent(eventId: string) {
    window.sessionStorage.removeItem(EVENT_ID);
    window.sessionStorage.setItem(EVENT_ID, eventId);
  }
  public getEvent(): string {
    return sessionStorage.getItem(EVENT_ID);
  }
  
  public saveEventStatus(eventStatus: string) {
    window.sessionStorage.removeItem(EVENTSTATUS_KEY);
    window.sessionStorage.setItem(EVENTSTATUS_KEY, eventStatus);
  }
 
  public getEventStatus(): string {
    return sessionStorage.getItem(EVENTSTATUS_KEY);
  }

  public saveEventResponded(responded: string) {
    window.sessionStorage.removeItem(EVENT_RESPONDED);
    window.sessionStorage.setItem(EVENT_RESPONDED, responded);
  }

  public getEventResponded(): string {
    return sessionStorage.getItem(EVENT_RESPONDED);
  }
}
