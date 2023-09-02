# Compose News App

This is a news app that consumes the news API. The app is written in  Jetpack compose 

The project also utilizes the Dagger hilt version(2.48) with the new KSP support (No more dagger)

## Libraries
1. Jetpack compose (Modern UI Library)
2. Room (Local Persistence)
3. Retrofit (Making network calls)
4. Dagger hilt (For dependency injection)
5. Paging & Paging Compose (To handle paginated data)
6. Chucker Dev Extension Interceptor (To debug network requests)
7. Kotlin Coroutines & Flows
8. Coil (For Image Loading)
9. DataStore (For simple Key value Pair Persistence)
10. Kotlin serialization Converter factory for Retrofit
11. Navigation Compose

## Run the app
To run the app be sure add your own API KEY form the News API in the `local.properties`
as shown below
```agsl

API_KEY="YOUR_API_KEY"

```


## Screenshots of the App

<img src="screenshots/all_news.png" width="250"/> <img src="screenshots/news.png" width="250"/> <img src="screenshots/saved_news.png" width="250"/>