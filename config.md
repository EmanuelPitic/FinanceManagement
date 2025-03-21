### Program Configuration:

1. **Database Configuration**  

   Open Docker:  
   ```sh
   docker run --name expense-tracker-mysql -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=expense_tracker -e MYSQL_USER=expenseapp -e MYSQL_PASSWORD=password -p 3307:3306 -d mysql:8.0
   ```

   Import `schema.sql` and run it in Docker.  

   Open MySQL in Docker:  
   ```sh
   docker exec -it expense-tracker-mysql mysql -u expenseapp -p expense_tracker
   ```
   Enter password: `password`  

2. **Start Encryption Service**  
3. **Start Authentication Service**  
4. **Start Expense Service**  
5. **Start Flask**  

**TODO:** Implement reports