# OpenLibraryArticles

This repository contains a sample project in Kotlin,MVVM, Coroutines, Hilt and Retrofit.


![image](https://user-images.githubusercontent.com/23075249/195327258-0ffb67f1-bca3-40ab-9cf3-221dbecbf54e.png)


### The app has the following base packages:

* di: Hilt classes to work with Network.

* model: Models which are used by UI.

* api: Services and network models.

* repository: Contains all repositories.

* utills: Utility class.

* ui: View classes along with their corresponding ViewModel.

# Run tests:

### (test) packages

* class OpenLibraryServiceTest in (data.remotr package) contains testing the retrofit service.
* class MainViewModelIntegrationTest in (ui package) contains testing viewmodel if there's articles.
* class MainViewModelTest in (ui package) contains testing error, empty and success cases.


# Library reference resources:

* Hilt: https://developer.android.com/training/dependency-injection/hilt-android

* MVVM Architecture : https://developer.android.com/jetpack/guide

* Coroutines: https://developer.android.com/kotlin/coroutines

* Glide: https://github.com/bumptech/glide

* Retrofit: https://square.github.io/retrofit/

