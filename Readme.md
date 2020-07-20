# Movie Tracker

Nothing fancy.Just the same old boring goodreads for movies.

![ Movie Tracker Demo](demo/movie-tracker.gif)

Movie Tracker is a personal movie tracker webpage to track /review/rate movies/tv shows. This is aimed to provide a strictly personal movie tracker for individual use.

- User can track movie & tv shows watched.
- User can rate the movie/tv shows.
- User can write personal reviews for each show.
- Integration with TheMovieDB for additional movie information.

## Real Reason

I wanted to play with Spring boot and angular. Also I like to journal personal reviews for movies/tv shows. Letterboxd is a wonderful app, but it started to push ads.IMDB/Letterboxd/TheMovieDb are good to share with people. I just wanted to have a personal app to track my movies.


## Technology

|          	| Technology      	| Version 	|
|----------	|-----------------	|---------	|
| UI       	| Angular         	| 8       	|
| UI       	| Material UI     	|         	|
| UI       	| Font Awesome    	|         	|
| UI       	| High Charts     	|         	|
| Back End 	| JAVA/SpringBoot 	| 8 /2.5  	|


## How to Build

The frontend & backend code are bundled together using maven front end plugin (thanks to the creator).
- Clone the source
- Build using maven `mvn clean install`
- Run the package using `java -jar movie-tracker-backend-1.0-SNAPSHOT -Dapi.key=<<api_key>> `

## Pre Requisites
- Get an api key from themoviedb
- Key can be given as argument `-Dapi.key=<<api_key>>>>` 
- Create a directory for h2 db. Pass the directory location as `-Dh2.db.folder=<<directory_path>>`
