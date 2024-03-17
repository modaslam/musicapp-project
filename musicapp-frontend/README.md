# MusicApp Frontend

### [Main documentation](../DeveloperNotes.md).

## Introduction
This is the frontend for the MusicApp, a responsive web application that allows users to manage their favorite songs. It's built with React and communicates with the MusicApp Backend for data management.

## Key Dependencies
- **React:** Utilized for building the user interface in a component-driven manner.
- **Material-UI (@mui/material and @mui/icons-material):** Adopted for the UI design system, providing pre-designed components for a cohesive look and feel.
- **Axios:** Used for making HTTP requests to the backend service.
- **React Router Dom:** Employs for routing within the application, allowing navigation without page refresh.
- **React Query:** Utilized for fetching, caching, and updating data in the background, enhancing the user experience by making the application more responsive.
- **Lodash:** A modern JavaScript utility library delivering modularity, performance, & extras.

## Getting Started

### Prerequisites
- Node.js (v14 or later)
- npm

### Configuration (Not required and can be ignored)
Create an `.env` file if required to set the backend API URL if you want to deploy to different environments:

```dotenv
REACT_APP_API_BASE_URL={http://environment-url-example:8080}
```
Then in the `SongService.js`, the `API_BASE_URL` could be changed to refer to this `process.env.REACT_APP_API_BASE_URL` instead.
### Running the Application Locally

1. Install the dependencies:
```
npm install
```
2. Start the application:
```
npm start
```
This will launch the app on http://localhost:3000.
## Testing
Run the following command to execute the unit tests:
```
npm test
```
## Implemented Features
- UI for adding a song with success and error alert notifications
- UI for infinite scroll of songs (with increments of 10 songs)
- UI for viewing songs with pagination. Includes filtering by artist(partial search) and year(full search). Can be sorted in ascending or descending order.