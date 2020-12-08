export class Movie {

    id: number;
    name : string;
    rating: string;
    externalId: number;
    numberOfWatch:number;
    review:string;
    overView:string;
    imdbId: number;
    lastWatched:string;
    posterPath: string;
    originalLanguage:string;
    reviews:MovieReviews;
    releaseDate: String;
    

}

export class MovieReviews{

    reviewId: number;
    review: string;
    lastReviewed: string;

    
}