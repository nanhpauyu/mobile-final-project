## Runing Application

1. Start Server
``` bash
    cd backend
    docker build . -t mobile-api
    docker run -it -d --name my-mobile-api -p 5000:5000 mobile-api:latest
```
2. Config `baseUrl` in file `core.data.network.ApiProvider`


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

