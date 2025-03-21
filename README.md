# FinanceManagement

FinanceManagement is a comprehensive application designed to help users manage and track their financial expenses. The project is built using Kotlin, HTML, and Python, and utilizes various services for handling authentication, encryption, and expense management.

TODO("Implement the service to generate comperhansive reports of the spending habits of each individual")

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Services](#services)
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

            a. Open Docker:
            
            docker run --name expense-tracker-mysql -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=expense_tracker -e MYSQL_USER=expenseapp -e MYSQL_PASSWORD=password -p 3307:3306 -d mysql:8.0
            Import schema.sql and run it in Docker.
            
            b. Open MySQL in Docker:
            
            docker exec -it expense-tracker-mysql mysql -u expenseapp -p expense_tracker
            Enter password: password
            
            d. Start Encryption Service: In IntelJ using the maven config from pom.xml
            
            e. Start Authentication Service: In IntelJ using the maven config from pom.xml
            
            f. Start Expense Service: In IntelJ using the maven config from pom.xml
            
            g. Start Flask: python main.py

## Usage

Once the application is set up, you can access the dashboard and manage your expenses via the web interface. You can sign up, log in, and start adding, editing, and deleting expenses.

## Services

The FinanceManagement application is composed of the following services:

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


## Contact

For any inquiries or issues, please contact [EmanuelPitic](https://github.com/EmanuelPitic).


