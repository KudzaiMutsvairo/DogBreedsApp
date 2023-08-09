# DogBreedApp

This Android app allows users to explore a variety of dog breeds, add them to their favorites, and view images of specific breeds. It is built using Kotlin and Jetpack Compose.

## Features

- Fetch Dog Breeds: The app fetches a list of dog breeds from a remote API and displays them in a list.

- Favorites: Users can mark dog breeds as favorites by tapping a "Favorite" button next to each breed.

- View Breed Image: Users can select a dog breed from the list to view a an image associated with that breed.

## App Structure

`app/src/main/java/com/kmutswairo/dogbreedsapp`: Contains the main source code for the app.
- app: Contains the Application class for the app.
- feature_dogbreeds: Contains source code related to the feature including its:
  - data: Contains data-related classes, such as data models and repositories.
  - domain: Contains business logic and use cases.
  - presentation: Contains UI-related code using Jetpack Compose.
  - utils: Contains utility classes and extensions.
- di: Contains Dagger-Hilt dependency injection modules.

## Screenshot 
<img src="https://github.com/KudzaiMutsvairo/DogBreedsApp/blob/main/screenshots/screenshot.png" alt="Screenshot" width="300"/>
