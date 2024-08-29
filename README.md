# Shutter Shop

![ScreenRecording2024-08-29at15 29 12-ezgif com-cut](https://github.com/user-attachments/assets/ea5546c8-debf-4303-b5fa-85eac9c56ffc)

Explore a wide range of cameras in Shutter Shop and make purchases directly within the app. Enjoy a secure transaction experience with real-time payment status updates powered by Firebase Remote Config. The app provides an ultimate user experience with smooth animations from Lottie and seamless Shimmer Loading effects.

Built with Jetpack Compose, Shutter Shop is optimized for modern Android experiences and supports multiple languages, available for devices running Android 13+.

## Table of Contens
- [Shutter Shop](#shutter-shop)
  * [Demo](#demo)
  * [Table of Contents](#table-of-contens)
  * [Tech Stack](#tech-stack)
  * [Features](#features)
  * [Project Modules](#project-modules)
  * [Architecture Pattern](#architecture-pattern)
  * [Dependency Injection](#dependency-injection)
  * [Prerequisites to Run This Project](#prerequisites-to-run-this-project)
  * [Installation](#installation)


## Demo

https://github.com/user-attachments/assets/d2a36c8c-02f1-4035-a0e4-7e8be0eb7e88

## Tech Stack

![New Tech Stack](https://github.com/user-attachments/assets/4f3172d7-a4b5-4deb-9792-adca20bc8cad)


## Features

- **Authentication:** Experience seamless authentication with integrated auto-refresh tokens, eliminating the need to re-login as long as the refresh token remains valid.
- **Smooth Loading:** Enjoy a polished user experience with Shimmer and Lottie animations during loading.
- **Firebase Crashlytics:** Stay informed about app crashes with Firebase Crashlytics integration, ensuring quick issue resolution.
- **Firebase Analytics:** Effortlessly track user behavior with Firebase Analytics, providing valuable insights.
- **Local Database:** Securely store data locally using Room Database, a modern local storage solution.
- **DataStore Preferences:** Save user preferences with DataStore, the latest technology for shared preferences.
- **Jetpack Compose Navigation:** Navigate between screens with ease and fluidity using Jetpack Compose Navigation.
- **API Logging:** Monitor API network activity with Logging Interceptor from OkHttp.
- **Network:** Perform API calls efficiently using Retrofit.
- **Localization:** Support for multiple languages, currently available for Android 13+.
- **Infinite Scrolling:** Enjoy infinite scrolling with Paging 3 Compose, perfect for long lists.
- **Notifications:** Receive notifications for successful or failed transactions, keeping user updated in real-time.
- **Share Product:** Share a product to another user, because this app is already supported with deeplink.
- **Dark Mode:** Enjoy an application with dark mode or light mode.
- **Image Loader:** Thanks to Coil to load image from network.
- **Smooth Ui and Animation:** Jetpack Compose is really good in slicing and animate this application.
  
## Project Modules

Shutter Shop is built using a layered modular architecture, with the application divided into three main modules: **UI Module**, **Domain Module**, and **Data Module**.

- **UI Module:** Responsible for presenting the user interface, including UI screens, ViewModels, and managing the app's startup process. This module handles all user interactions and visual components.

- **Domain Module:** Contains the domain models, repository interfaces, and use cases. This module is independent and isolated from external libraries, making it more stable and focused solely on the business logic and core functionality of the app.

- **Data Module:** Handles network requests and local data transactions. It includes response and entity models, as well as repository implementations. This is where data retrieval and persistence are managed, whether from a remote server or a local database.

![Depedeny Map](https://github.com/user-attachments/assets/771809fd-d2d3-4c19-af42-6ab2a57e1169)

## Design Pattern

MVVM (Model-View-ViewModel) is chosen to **build** Shutter Shop. The MVVM design pattern is commonly used in building Android applications as it promotes a clear separation of concerns, making the app easier to manage and test.

- **Model:** Represents the data layer of the application, including data models, data handling logic, and business rules. It interacts with data sources like APIs or databases and serves as the source of truth for data used within the application.
- **View:** The UI layer that displays data to the user and sends user inputs back to the ViewModel.
- **ViewModel:** Serves as an intermediary between the Model and the View. It handles the logic for preparing data for display, managing the state of the UI, and handling user interactions. The ViewModel exposes data to the View via observable data holders like LiveData or StateFlow, allowing the View to react to changes in the underlying data without directly accessing the Model.

![New MVVM Map](https://github.com/user-attachments/assets/d6846abc-5398-4e4e-95ef-d932605db052)


## Dependency Injection

Dagger Hilt is used for dependency injection in this project. Dependency Injection is crucial when using MVVM and modular architecture because it helps manage dependencies between components, promoting loose coupling and making the codebase more maintainable and testable.

## Prerequisites to Run This Project
To build and run the project locally, ensure you have the following installed:
- **Java 8**
- **Gradle 7.8**
- **Android Studio**

**Clone the Project**
```bash
gh repo clone andremoore123/ShutterShop
```

**Navigate to the ShutterShop Directory**
```bash
cd /[Your Directory]/ShutterShop
```

**Build the Debug APK**
```bash
./gradlew assembleDebug
```

**Important Notes**

It's recommended to build the project using Android Studio for better development experience. Before building the APK, you need to set up your own Firebase project and add the `google-services.json` file, as this project requires **Firebase**.


## Installation

Minimum requirement to run this application is Android 10. Some Feature is only supported in Android 13+.
    
