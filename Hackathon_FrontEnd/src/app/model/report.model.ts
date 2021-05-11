export interface EventSummaryDetails{
    eventId: string;
	month: string;
	baseLocation: string;
	beneficiaryName: string;
	venueAddress: string;
	councilName: string;
	project: string;
	eventName: string;
	eventDesc: string;
	eventDate: Date;
	noofVolunteer: number; 
	volunteerhrs: number;  
    overallVolRingHrs: number;
    livesImpacted: number;
    activityType: string;
    status: string;
    pocId: string;
    pocName: string;
    pocContactNo: number;
}