# PopularMovies
Popular Movies app - As part of Udacity Android nanodegree assignment.

This app uses service - http://api.themoviedb.org/3/movie/popular to fetch most popular / highest rated movies and display to the user.
user can change sort order from settings menu

API KEY
Please put your api key in MoviesLoader class as below.

public class MoviesLoader extends AsyncTaskLoader<Movies>{
    private static final String API_KEY = "put_your_key_here";
}

Tasks done:

- use List view / Arrayadapter- grid layout to display movie posters
- use picasso image library to load image
 - use retrofit library to make network call and connect UI to it
- Implement settings menu
- Implement preferences
- Implement details screen
- implement loader pattern to handle device rotation. use synchronous call .execute instead of .enqueue
- Handle device rotation. make sure call is not made again. change the view to landscape to show 3 columns
- Handle network failure / timeouts
- Show progressbar appropriately
- Show error message if network timeouts/failure

Test cases passed:
- verify when app launches, it reads settings from shared preferences and make a call accordingly.
- verify that movie posters are displayed reasonably well in grid format
- verify user can scroll and load more data by incrementing pages in the service call
- user can change settings and UI is refreshed
- user rotates the device, app should not crash and show data in 3 column grid
- show error when network is down, timeout or response codes other than 200
- progress bar starts before the load and gone after load is finished
- user can scroll up all the way to item 1 and can still see previoulsy loaded records
- images are not downloaded again and again. Thanks to Picasso library's caching mechanism.

Note:
- OnSaveState is not implemented in this version.
