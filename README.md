<h1 align="left">Movie Rating Demo </h1>

<p align="left"> This is a demo of movie & TV show that implements <b>MVVM</b> design pattern by following the <b>Clean Architecture</b> principles, using 
  <a href="https://www.themoviedb.org/">TMDB API</a> which is available on <a href="https://github.com/public-apis/public-apis/blob/master/README.md">Github Public APIs</a> page. 
</p>

<img src="appImages/screenshots_banner.png" width="3104"/>

### Tech stack & Open-source libraries
- Minimum SDK level 26 to latest
- Written in [Kotlin](https://kotlinlang.org/)
- Implementing MVVM design pattern with Android Architecture Components by following [clean architecture principles](https://github.com/bbor98/movieapp-mvvm-clean-architecture#-note)
- Dependency injection with [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- Consuming a REST API of [TMDB API](https://www.themoviedb.org/documentation/api)
- Safe API call with [Retrofit](https://github.com/square/retrofit) & [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- Caching API response with [`OkHttpClient`](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/) from [`medium article`](https://medium.com/@bapspatil/caching-with-retrofit-store-responses-offline-71439ed32fda)
- Observing data changes and updating the UI state with [`StateFlow`](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
- Handling common view logic with [`BindingAdapter`](https://developer.android.com/topic/libraries/data-binding/binding-adapters)
- Infinite scrolling with the help of `RecyclerView.OnScrollListener` (no paging library used)
- [Stetho](https://github.com/facebook/stetho) to check API response directly on Brave web browser.
- Used [Jetpack Compose](https://developer.android.com/jetpack/compose) in one of the module with [`Material 3 UI`](https://m3.material.io/develop/android/jetpack-compose)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Jetpack](https://developer.android.com/jetpack) libraries
  - [Navigation](https://developer.android.com/guide/navigation) - Handling navigation between destinations within the app
  - [Jetpack Compose](https://developer.android.com/jetpack/compose)
  - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Handling lifecycles with lifecycle-aware component
  - [DataBinding](https://developer.android.com/topic/libraries/data-binding) - Binding UI components in layouts to data sources using a declarative format
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Storing and managing UI-related data in a lifecycle-conscious way
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Injecting dependencies
  - [Room](https://developer.android.com/training/data-storage/room) - Constructing SQLite database more easily
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Allowing asynchronous programming with Kotlin
- [Retrofit](https://github.com/square/retrofit) - Interacting with the REST API
- [OkHttp](https://github.com/square/okhttp) - Implementing interceptors
- [Gson](https://github.com/google/gson) - Converting JSON to Kotlin data class
- [Glide](https://github.com/bumptech/glide) - Loading and caching images
- [Glide Transformations](https://github.com/wasabeef/glide-transformations) - Providing image transformations for Glide
- [Glide Compose Image](https://bumptech.github.io/glide/int/compose.html) - Loads image directly from compose.
- [ExpandableLayout](https://github.com/cachapa/ExpandableLayout) - Animating the expansion and collapse of its child views
- [Splash Screen API](https://developer.android.com/develop/ui/views/launch/splash-screen) - New Splash screen API
- [Robolectric](https://robolectric.org/) for Unit testing
- [Espresso](https://developer.android.com/training/testing/espresso) for UI testing
- [Mockito](https://site.mockito.org/) mocking framework for unit tests

### Some Demo app Features
- Browse popular and top rated movies/tv shows on the *Home* page
- Search movies/tv shows on the *Search* page
- See the details of a movie/tv show
  - Watch trailers tiles and navigated to youtube
  - You can choose the movie/tv show as favorite by tapping the favourite icon
- View favorite movies/tv shows on the *Favorites* page
- Offline support (if cached data is available)
- Light and Dark mode support

## Demo App Structure
<img src="appImages/app_package_structure.png" width="350"/>

- androidTest (Instrumentation Tests Espresso and Hilt Test Lib)
- debug (HiltTestActivity for UI tests)
- main 
- sharedTest (directory act as bridge between unit and instrumentation tests. It contains files like Fake Hilt Modules, fake Repository, Hilt Test Runner)
- test (Unittest cases Robolectric, Mockito and Hilt)

The project separated into three main layers which covers (Separation of Concerns and Testability) 
- Data
- Presentation
- Domain

### Data
Data layer contains application data that are fetched from either the network source or the local database.

Consists of four packages:
- `local` contains *Room* components to fetch data from the local database
- `mapper` contains mapping functions to map Models data and database entities to domain models
- `remote` contains *Retrofit* components to fetch data from the network source
- `repository` contains **implementations** of repository interfaces that are defined in the domain layer

### Presentation
Presentation layer is responsible for displaying application data on the screen. It contains  UI elements that render the data on the screen and ViewModel classes that store and manage data in a lifecycle-conscious way.

Consists of two packages:
- `adapter` contains RecyclerView & ViewPager2 adapter classes
- `ui` contains Activities & Fragments with their corresponding ViewModel classes

### Domain
Domain layer is the central layer of the project. This layer is a bridge between the data layer and the presentation layer-it retrieves data from the data layer and exposes it to the presentation layer. This layer is independent of other layers-any changes in other layers don't affect this layer.

Consists of three packages:
- `model` contains data classes that hold the data retrieved from the data layer to be used later on in the presentation layer to expose it to UI
- `repository` contains repository **interfaces** to abstract the domain layer from the data layer
- `usecase` contains use cases that handle the business logic, which are reused by multiple ViewModels

## Try the App
Check out the [Releases](https://github.com/ddpatel2606/MovieRatingDemo/releases) and download & install the <a href="apk/app-debug.apk">APK file<a/> to try the app.
