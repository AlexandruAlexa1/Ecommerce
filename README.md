# 🛍️ Ecommerce Application

This project consists of two separate applications: the **EcommerceBackEnd (Admin Management App)** and the **EcommerceFrontEnd (User Shopping App)**.
Both applications share the same MySQL database and are built using Spring Boot.

## 🏢 1. EcommerceBackEnd (Admin Management App)

This application is designed for administrators to manage the e-commerce platform. It consists of multiple modules, including:

---

🔹 **Users** – Manage user accounts and roles.

🔹 **Categories** – Organize products into categories.

🔹 **Brands** – Manage product brands.

🔹 **Products** – Create, update, and delete products.

🔹 **Reviews** – Moderate customer reviews.

🔹 **Questions** – Manage customer questions about products.

🔹 **Customers** – View and manage registered customers.

🔹 **Shipping** – Define shipping methods and costs.

🔹 **Orders** – Process and track customer orders.

🔹 **Settings** – Configure platform-wide settings.

---

### 🔧 Admin Capabilities

In the **Users** module, an admin can:

✅ View all users  
✅ Add new users  
✅ Update existing users  
✅ Delete users  
✅ Search for users by first or last name  
✅ Sort the user list  

### 🔒 Authentication & Security

The application is secured using **Spring Security**, requiring authentication to access.

### 👥 Test Accounts

| 📧 Email | 🔑 Permissions |
|---------------------|--------------------------------------|
| [admin@yahoo.com](mailto:admin@yahoo.com) | Full access: manage everything |
| [manager@yahoo.com](mailto:manager@yahoo.com) | Manage users: list, edit, add users |
| [editor@yahoo.com](mailto:editor@yahoo.com) | Limited to listing and editing users |
| [user@yahoo.com](mailto:user@yahoo.com) | View only: list users |

🔐 **Password for all accounts:** `password`

Each module is tested using unit and integration tests to ensure proper functionality and reliability.

### 🛠️ Technologies Used

☕ **Java**, **Spring Boot**, **Spring MVC (Thymeleaf)**, **Spring Security**, **MySQL Database**.

## 🛒 2. EcommerceFrontEnd (User Shopping App)

This application allows customers to browse and purchase products online.

### 🎯 Key Features

---

🔍 Viewing all available products

📂 Browsing products by category

📝 Accessing detailed product information

🛍️ Adding products to the shopping cart

👥 Creating a personalized account

📦 Placing and managing orders

📧 Receiving confirmation emails for registration and orders

🚚 Tracking the status of orders in real-time

⭐ Leaving product reviews

❓ Asking questions about products

💳 Making payments (in person or via PayPal)

🔄 Returning products and orders if necessary

---

## 🚀 Installation and Running Instructions

1. 📥 Clone the repository.
2. 🛠️ Ensure **MySQL** is installed and running on your system.
3. 🗄️ Run the database script:
   - In the root directory of the project, execute `schema.sql` to create the database, tables, and populate test data.
4. ⚙️ **Backend (EcommerceBackEnd) Configuration:**
   - Update `application.properties` with your MySQL **username** and **password**.
   - Run the backend application:
   ```bash
   mvn spring-boot:run
   ```
   - Access the app at [http://localhost:8000](http://localhost:8000)
5. 🎨 **Frontend (EcommerceFrontEnd) Configuration:**
   - Update `application.yml` with your MySQL **username** and **password**.
   - Run the frontend application:
   ```bash
   mvn spring-boot:run
   ```
   - Access the app at [http://localhost:9000](http://localhost:9000)

## 📜 License

This project is licensed under the **Creative Commons Attribution-NonCommercial 4.0 International License (CC BY-NC 4.0)**, meaning it **cannot be used for commercial purposes**.

---
✨ This application provides a simple and efficient online shopping experience while offering full administrative control through the backend! 🚀

