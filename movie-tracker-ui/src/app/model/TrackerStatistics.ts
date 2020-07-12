export class TrackerStatistics
{

    movieCount : number;

    tvCount: number;
    movieWithMaxRating: number;
    tvWithMaxRating: number;
    monthlyCount:MonthlyCount ;
    
}
interface  MonthlyCount
{

    January:string;
    February:string
    March:string
    April:string;
    May:string;
    June:string;
    July:string;
    August:string;
    September:string;
    October:string;
    November:string;
    December:string;


}