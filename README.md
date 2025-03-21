# FinanceManagement

FinanceManagement is a comprehensive application designed to help users manage and track their financial expenses. The project is built using Kotlin, HTML, and Python, and utilizes various microservices for handling authentication, encryption, and expense management.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Microservices](#microservices)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Features

- User authentication and authorization
- Expense tracking and management
- Data encryption for sensitive information
- Dashboard for viewing expenses
- REST API for interaction with the backend services

## Installation

To set up the FinanceManagement application, follow these steps:

1. Clone the repository:
    ```sh
    git clone https://github.com/EmanuelPitic/FinanceManagement.git
    cd FinanceManagement
    ```

2. Set up the required services:
    - Ensure you have Docker and Docker Compose installed.
    - Start the services using Docker Compose:
      ```sh
      docker-compose up
      ```

3. Run the backend services:
    - Navigate to the `ExpenseService` directory and start the service:
      ```sh
      cd ExpenseService
      ./gradlew bootRun
      ```

    - Navigate to the `AuthService` directory and start the service:
      ```sh
      cd AuthService
      ./gradlew bootRun
      ```

    - Navigate to the `EncryptionService` directory and start the service:
      ```sh
      cd EncryptionService
      ./gradlew bootRun
      ```

4. Run the frontend:
    - Navigate to the `frontend` directory and start the application:
      ```sh
      cd frontend
      npm install
      npm start
      ```

## Usage

Once the application is set up, you can access the dashboard and manage your expenses via the web interface. You can sign up, log in, and start adding, editing, and deleting expenses.

## Microservices

The FinanceManagement application is composed of the following microservices:

- **AuthService**: Handles user authentication and authorization.
- **ExpenseService**: Manages expenses, including adding, updating, retrieving, and deleting expenses.
- **EncryptionService**: Encrypts and decrypts sensitive user data.

## Contributing

Contributions are welcome! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any inquiries or issues, please contact [EmanuelPitic](https://github.com/EmanuelPitic).


