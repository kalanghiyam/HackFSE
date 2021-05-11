export class EventEmployeeInfo{

    associateId: number;
	eventId: string;
	eventStatus: string;
	responded: string;
	associateName:string;
	bu:string;
	select:boolean;
	
	constructor(responded: string) {
        this.responded = responded;
       
    }
}