# BestShows - Android TV Show Tracking App

## 1. Overview

BestShows is an Android application designed to allow users to browse and discover TV shows. It fetches data from the [TVMaze API](https://www.tvmaze.com/api) to display a list of shows and their detailed information, including ratings, summaries, genres, and schedules. This project demonstrates a modern Android development approach using Kotlin, MVVM architecture, and popular Jetpack libraries.

## 2. Key Features

*   Browse a list of current TV shows.
*   View detailed information for each show, including:
    *   Name and image.
    *   Rating.
    *   Summary.
    *   Network and schedule.
    *   Status and type.
    *   Genres.
    *   Official website link.
*   Clean, user-friendly interface.
*   Error handling for network requests and data loading.
*   Loading indicators for a better user experience.

## 3. Technologies Used & Architecture

### 3.1. Core Technologies:
*   **Kotlin:** Primary programming language.
*   **Android SDK:** Targetting Android API Level 36 (Android 16). Min SDK 21.
*   **Jetpack Libraries:**
    *   **ViewModel:** For UI-related data and state management, lifecycle-aware.
    *   **LiveData:** For observable data patterns.
    *   **Hilt:** For dependency injection.
    *   **AndroidX AppCompat, Core KTX, Activity KTX:** For core functionalities and compatibility.
    *   **ConstraintLayout & RecyclerView:** For building dynamic and flexible UIs.
    *   **Material Components:** For modern UI elements.
*   **Retrofit 2:** For type-safe HTTP networking to consume the TVMaze API.
*   **Gson:** For JSON serialization/deserialization (version 2.13.1).
*   **OkHttp 3 (OkHttp 4.12.0 used via Retrofit):** For efficient HTTP requests (configured with basic timeouts).
*   **Coil:** For image loading and caching.
*   **ViewBinding:** To replace `findViewById` safely.
*   **Gradle:** For build automation (version 8.11.1).

### 3.2. Architecture:
*   **MVVM (Model-View-ViewModel):** The app follows the MVVM architectural pattern to separate UI logic from business logic.
    *   **View (Activities - `MainActivity`, `DetailActivity`):** Observes data from ViewModels and updates the UI.
    *   **ViewModel (`ShowsViewModel`, `DetailViewModel`):** Holds and prepares data for the UI, interacts with the Repository, and survives configuration changes.
    *   **Model (Data classes in `data/model`, Repository):** Represents the data and business logic.
*   **Repository Pattern (`BestShowsRepository`):** Abstracts the data source (network API in this case).
*   **Dependency Injection (`Hilt`):** Manages dependencies throughout the app, improving modularity and testability.
*   **`Resource<T>` Wrapper:** Used to communicate data states (Loading, Success, Error) from ViewModels to the UI.

## 4. Setup Instructions

### 4.1. Prerequisites:
*   **Android Studio:** Latest stable version recommended (e.g., Android Studio Koala Patch 1 2024.1.2 or newer, compatible with AGP 8.10.0).
*   **JDK:** Java Development Kit 17 (Project is configured for Java 17).
*   **Android SDK:**
    *   Compile SDK: Version 36 (Android 16).
    *   Build Tools: Version 35.0.0 or compatible.
    *   Min SDK: Version 21.
    *   (Android Studio's SDK Manager can be used to install these.)

### 4.2. Cloning the Repository:
```bash
git clone <repository_url>
cd <project_directory>
```
Replace `<repository_url>` with the actual URL of this Git repository and `<project_directory>` with the cloned folder name.

### 4.3. Importing into Android Studio:
1.  Open Android Studio.
2.  Select "Open" (or "Open an Existing Project").
3.  Navigate to the directory where you cloned the repository and select it.
4.  Android Studio will import the project and Gradle will sync the dependencies. This might take a few minutes.
5.  Ensure Android Studio recognizes the installed JDK 17 (File > Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK).

## 5. Build Instructions

### 5.1. Building a Debug APK:
1.  Ensure the project has been successfully synced in Android Studio.
2.  Select the `app` module in the "Build Variants" window (usually on the left side, or View > Tool Windows > Build Variants). Select the `debug` variant.
3.  From the menu bar, select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
4.  Android Studio will build the debug APK. You can find it in `app/build/outputs/apk/debug/app-debug.apk`.
5.  You can run this APK on an emulator or a physical Android device.

### 5.2. Building a Release APK:
Building a release APK involves additional steps for signing and typically enabling code shrinking/obfuscation.

1.  **Configure Signing:**
    *   You'll need a signing key (keystore file). If you don't have one, you can generate it using Android Studio: **Build > Generate Signed Bundle / APK...**. Choose "APK", then "Create new..." for the key store path.
    *   Configure your `app/build.gradle` file to use this signing key for release builds, or use Android Studio's "Generate Signed Bundle / APK" wizard which can help manage this.
    *   **Important:** Never commit your signing keys or passwords to version control. Use environment variables or a secure properties file (excluded by `.gitignore`) to store sensitive signing information if automating builds.

2.  **Build the APK:**
    *   Select the `release` build variant in the "Build Variants" window.
    *   From the menu bar, select **Build > Generate Signed Bundle / APK...** (and follow the steps, selecting your key).
    *   Alternatively, if signing is configured in `build.gradle`, you can run the Gradle task `./gradlew assembleRelease` from the terminal.
    *   The signed release APK will be located in `app/build/outputs/apk/release/app-release.apk`.

3.  **Code Shrinking & Obfuscation (ProGuard/R8):**
    *   The project currently has `minifyEnabled false` for the release build type in `app/build.gradle`. For a production release, you would typically set this to `true` to reduce app size and obfuscate code.
    *   `proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'` are already included. You might need to add custom ProGuard rules in `proguard-rules.pro` if `minifyEnabled` is set to `true`.

## 6. API Details (TVMaze)

This application uses the public [TVMaze API](https://www.tvmaze.com/api) to fetch TV show information.

*   **Base URL:** `https://api.tvmaze.com/`
*   **Key Endpoints Used:**
    *   `GET /shows`: Fetches a list of all shows.
    *   `GET /shows/{id}`: Fetches details for a specific show by its ID.

No API key is required for basic use of the TVMaze API. Please be mindful of their rate limits if you intend to make many requests.

## 7. Project Structure

The project follows a standard Android application structure, with code organized into packages based on features and layers:

*   **`app/`**: Contains application-specific setup, including the main Application class (`BestShowsApplication.kt`).
*   **`data/`**: Handles data operations.
    *   **`model/`**: Contains Kotlin data classes that define the structure of the data received from the API (e.g., `Show.kt`, `Rating.kt`).
    *   **`remote/`**: Contains the Retrofit API interface (`ShowsApi.kt`) defining the network endpoints.
*   **`di/`**: Dependency Injection setup using Hilt.
    *   `BestShowsModule.kt`: Provides dependencies like Retrofit, OkHttpClient, and the API service.
*   **`repo/`**: Contains the Repository class.
    *   `BestShowsRepository.kt`: Abstracts data operations and provides a clean API for ViewModels to access data.
*   **`ui/`**: Contains UI-related components (Activities, ViewModels, Adapters).
    *   **`activities/`**: Contains `MainActivity.kt` (for displaying the list of shows), `ShowsViewModel.kt`, and `ShowAdapter.kt`.
    *   **`detail/`**: Contains `DetailActivity.kt` (for displaying show details) and `DetailViewModel.kt`.
*   **`util/`**: Contains utility classes and constants.
    *   `Constants.kt`: Stores global constants like API base URL.
    *   `Resource.kt`: A sealed class used to represent data states (Loading, Success, Error).
*   **`res/`**: Standard Android resource directory (layouts, values, mipmap for icons, etc.).
*   **`gradle/` & Build Scripts (`build.gradle` files):** Gradle configuration.

## 8. Deployment & Play Store Considerations

If you intend to publish this app to the Google Play Store or distribute it otherwise, consider the following:

*   **Application ID:** The `applicationId` in `app/build.gradle` (`com.davidodhiambo.bestshows`) must be unique.
*   **Versioning:** Increment `versionCode` and update `versionName` for each release.
*   **Signing:** Release builds must be signed with your private key.
*   **Code Shrinking:** Enable `minifyEnabled true` for release builds and manage ProGuard rules.
*   **Testing:** Thoroughly test the release build. (Currently, the project lacks automated tests).
*   **App Icons & Screenshots:** Prepare production-quality assets for the store listing.
*   **Privacy Policy:** Required if collecting any user data.
*   **API Usage:** Be mindful of TVMaze API terms and rate limits.
*   **Error Reporting:** Consider adding a crash reporting library (e.g., Firebase Crashlytics).

## 9. Further Development / Potential Improvements

*   **Implement Local Caching:** Use Room to provide offline access and reduce API calls.
*   **Pagination:** Load the main shows list incrementally.
*   **Search Functionality.**
*   **User Accounts/Favorites.**
*   **Enhanced UI/UX:** Tablet layouts, animations.
*   **Add Automated Tests:** Unit and instrumented tests.
*   **Refine Error Handling:** More specific error messages and retry mechanisms.
*   **Periodically update dependencies.**

---

This README provides a guide to understanding, building, and running the BestShows application.
