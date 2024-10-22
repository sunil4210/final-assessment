# NIT3213 Final Assignment - Android Application

## Overview
This project is an Android application developed as part of the NIT3213 course assignment. The app demonstrates proficiency in Android development, API integration, user interface design, and Android development best practices. The app interacts with the 'vu-nit3213-api' for user authentication and data retrieval, consisting of three main screens: **Login**, **Dashboard**, and **Details**.

## Features
1. **Login Screen**
   - Users log in using their first name and student ID.
   - Authenticates the user via a POST request to the `/auth` endpoint.
   - Handles login errors and navigates to the dashboard on successful login.

2. **Dashboard Screen**
   - Displays a list of entities from the API using a `RecyclerView`.
   - Retrieves the list using the `keypass` obtained from the login response.
   - Enables navigation to the details screen when an entity is clicked.

3. **Details Screen**
   - Shows detailed information about the selected entity, including its description.
   - Presents the data in a clear and user-friendly format.

## API Integration
- **Login Endpoint**: Authenticates users using their first name as the username and student ID as the password.
- **Dashboard Endpoint**: Retrieves a list of entities using the `keypass` obtained from the login process.

## Technical Requirements
- Dependency Injection using **Hilt** or **Koin**.
- Clean code principles and organized project structure.
- Unit tests for critical components like `ViewModel`.
- Version control with **Git**.

## API Details
- **Base URL**: `https://nit3213-api-h2b3-latest.onrender.com`
- **Login Endpoint**:
  - URL: `/sydney/auth`
  - Method: `POST`
  - Request Body:
    ```json
    {
      "username": "YourFirstName",
      "password": "sYourStudentID"
    }
    ```
- **Dashboard Endpoint**:
  - URL: `/dashboard/{keypass}`
  - Method: `GET`

## Project Structure
- Follows MVVM (Model-View-ViewModel) architecture.
- Implements Dependency Injection using **Hilt**
## How to Build and Run
1. Clone the repository from GitHub `git clone https://github.com/sunil4210/final-assessment.git`.
2. Open the project in **Android Studio**.
3. Ensure **JDK**** is installed and set up in the project.
4. Sync the project with Gradle to install dependencies.
5. Build and run the project on an Android emulator or a physical device.

## Testing
- Unit tests are provided for critical components like `ViewModel`.
- To run tests, open the `Test` directory in Android Studio and execute the test suite.


