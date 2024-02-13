# ms-user-account-service

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## Description

This project contains an implementation on user account service  with the following features:
1. User account registration - allows users to create new accounts by providing their username, active token, and balance. 
2. Cash withdrawal- allows active users to withdraw any amounts that are multiples of 100 and less than or equal 
to their current balance

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Installation

1. Clone the repo 
```git clone https://github.com/kuriofoolio/ms-user-account-service.git```
2. Navigate to the cloned directory 
```cd ms-user-account-service```
3. Build and run the application using Maven Wrapper mvnw clean install spring-boot:run
4. Open your favorite browser and go to http://localhost:8080/swagger-ui.html to see the API documentation

## Usage
The User Account Service provides endpoints for managing users' accounts, such as creating a new user and withdrawing from it

## Contributing

You can contribute to this project  by submitting issues or pull requests. If you want to submit a code change, please follow these
Pull requests are welcome! For major changes, please open an issue first to discuss what you would  like to change. 

## License

This project is licensed under the [MIT License](LICENSE).
