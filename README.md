# Social Network Application
This application is for anyone who want to share an idea and interact with other.

## Contrubutors
|Name | StudentID | Entry |
| -- | -- | -- |
| Sarun Tapee | 618056 | Apr 2024 |
| Hpauyu Nan Shawng | 618081 | Apr 2024 |


## Feature Overview
- Intregrate with API using retrofit
- Rendering each screen using Navigation 3

| Feature | Implementaion |
| -- | --|
| Authentication | store accessToken in file with DataStore preferences.
| Publish Post (with AI improving the article) | Gemini generative AI
| View Posts | keep pulling latest posts using WorkManager
| Comment to the post | --
| Profile Editor | --

## System Architecture
The file structure of the program is implement according to clean architecture as follows diagram.
```
.
├── core // store package that share across the app. e.g. logged in user detail
├── features
│   └── feature-name
│       ├── data // data access layer
│       ├── domain // store interface and entities
│       └── ui
│           ├── screen
│           ├── state
│           └── viewmodel
└── nav
```

## Use Case Diagram
![use case diagram](./screenshot/project-use-case-diagram.png)

## Tech Stack
|Component| Tech stack |
|--| -- |
|Frontend| Kotlin, Jetpack Compose, DataStore, Retrofit, WorkManager, Navigation 3, Gemini AI
|Backend| Python, Flask
|Deployment| Docker, AWS EC2


## Setup Application

1. Start Server
``` bash
    cd backend
    docker build . -t mobile-api
    docker run -it -d --name my-mobile-api -p 5000:5000 mobile-api:latest
```
2. Config `baseUrl` in file `core.data.network.ApiProvider`

## API endpoint
Endpoint
|path | header | payload | response |
|--|--|--|--|
| POST /login | - | AuthLoginDto | AuthResponseDto
| POST /register | - | AuthRegisterDto | AuthResponseDto
| GET /users/:userId | - | - | ProfileResponseDto
| POST /update_profile | Authorization: Bearer {access_token} | UpdateProfileRequestDto | ProfileResponseDto
| GET /posts| - | - |PostListResponseDto |
| POST /posts| Authorization: Bearer {access_token} | PostRequestDto | - |
| GET posts/:postId/comments |-|-| CommentListResponseDto |
| POST posts/:postId/comments |Authorization: Bearer {access_token}| CommentRequestDto |-|

Dto

|Dto| Field | Type |
|--| --| -- |
|AuthLoginDto| Field | Type |
|  | email | String | 
|  | password | String | 
|AuthRegisterDto|  |  |
|  | username | String | 
|  | email | String | 
|  | password | String | 
|  | bio | String | 
|AuthResponseDto|  |  |
|  | data | DataDto | 
|  | status | 'success', 'fail' | 
|DataDto|  |  |
|  | access_token | DataDto | 
|  | id | String | 
|  | bio | String | 
|  | email | String | 
|  | username | String | 
|UpdateProfileRequestDto|  |  |
|  | username | String | 
|  | bio | String | 
|ProfileResponseDto|  |  |
|  | data | ProfileDataDto | 
|  | status | 'success', 'fail' | 
|ProfileDataDto|  |  |
|  | id | String | 
|  | bio | String | 
|  | email | String | 
|  | username | String | 
|PostListResponseDto | | |
|  | data | list(PostResponseDto) | 
|  | status | 'success', 'fail' | 
|PostResponseDto| |
|  | id | String | 
|  | userId | String | 
|  | username | String | 
|  | text | String | 
|PostRequestDto| |
|  | userId | String | 
|  | username | String | 
|  | text | String | 
|CommentListResponseDto| | |
|  | success | Boolean | 
|  | data | list(CommentResponseDto) | 
|CommentResponseDto| | |
|  | id | String | 
|  | userId | String | 
|  | username | String | 
|  | text | String | 
|CommentRequestDto| | |
|  | userId | String | 
|  | username | String | 
|  | text | String | 

## Generative AI Intregration

Gemini text generate AI is used to imprve post content
![generative AI](./screenshot/project-create-post-1.png)

## Feature Enhancements/Limitation
- Implemented API interceptor. allow easy to intregrate with access_token and refresh_token for higher security.
- Only work on android

## Wholeness / SCI Connection (MIU context)
**In Android Kotlin development**, every coding action—whether designing UI with Jetpack Compose, structuring ViewModels, or debugging—contributes to the creation of a complete, functional app. Each step builds upon the previous, guiding the developer from concept to realization.

**Science and Technology of Consciousness**: Just as right action in harmony with natural law leads to fulfillment in life, each mindful action in coding leads to the fulfillment of a seamless, user-friendly Android application. Acting with clear purpose and awareness in development brings both professional success and inner satisfaction.

## Screen Short
Login
![login](./screenshot/project-login.png)

Register
![register](./screenshot/project-register.png)

Post List
![post list](./screenshot/project-post-list.png)

Create Post
![create post 0](./screenshot/project-create-post-0.png)
![create post 1](./screenshot/project-create-post-1.png)
![create post 2](./screenshot/project-create-post--2.png)

Comment
![comment 0](./screenshot/project-comment-0.png)
![comment 1](./screenshot/project-comment-1.png)
![comment 3](./screenshot/project-comment-3.png)
![comment 4](./screenshot/project-comment-4.png)

Profile
![profile 0](./screenshot/project-profile-0.png)
![profile 1](./screenshot/project-profile-1.png)
![profile 2](./screenshot/project-profile-2.png)

## Testing
![testing](./screenshot/project-test.png)

