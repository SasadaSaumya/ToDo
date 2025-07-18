# Todo App - Android (Offline First)

![Language](https://img.shields.io/badge/Language-Java-blue.svg)
![Platform](https://img.shields.io/badge/Platform-Android-3DDC84.svg?style=flat&logo=android)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)
![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)

A simple, secure, and offline-first Todo application for Android. This app is designed to help users manage their tasks efficiently without needing an internet connection. The core principle is privacy and accessibility, with all user data stored securely and locally on the device.

This project is developed based on the requirements specified in the provided [Requirement Document](link_to_your_document_if_you_host_it).

## ✨ Features

Based on the project requirements, the application includes the following features:

#### 🔐 User Authentication
- **Register:** Create a new account with a unique username and password.
- **Login:** Securely log in to an existing account.
- **Local Validation:** All authentication is handled locally on the device, ensuring functionality without an internet connection.

#### 📝 Task Management (CRUD)
- **Create:** Add new todo items with a title, description, due date, and optional tags.
- **Read/View:** View all todo items in a clean list format.
- **Sort:** Organize tasks by due date or priority for better management.
- **Update:** Edit existing tasks to change their title, description, due date, or tags.
- **Delete:** Remove tasks or mark them as completed.

#### 💾 Local & Secure Storage
- **Offline First:** All application data, including user credentials and todo lists, is stored on the device's local storage.
- **Data Privacy:** No data is sent to any external server, giving users full control over their information.
- **Security:** Passwords are encrypted before being stored to enhance security.

#### 📱 Responsive User Interface
- **Intuitive Design:** A clean, user-friendly, and aesthetically pleasing interface.
- **Simple Navigation:** Easy-to-use navigation to access all features with minimal effort.
- **Adaptive UI:** The layout adapts gracefully to different screen sizes and resolutions.

## 📸 Screenshots

*(You can add your application screenshots here. This is a great way to showcase your work!)*

| Login Screen | Home Screen | Add/Edit Task |
| :---: |:---:|:---:|
| <img src="path/to/your/screenshot_login.png" width="200"/> | <img src="path/to/your/screenshot_home.png" width="200"/> | <img src="path/to/your/screenshot_add_task.png" width="200"/> |

## 🛠️ Tech Stack & Architecture

- **Language:** **Java**
- **Architecture:** **MVVM (Model-View-ViewModel)** - A modern architectural pattern for separating UI logic from business logic, making the app more testable and maintainable.
- **Database:** **Room Persistence Library** - A database abstraction layer over SQLite that provides a fluent API and compile-time verification of SQL queries.
- **UI Components:**
  - **Material Design Components:** For a modern and consistent look and feel.
  - **RecyclerView:** For displaying the list of todo items efficiently.
  - **ViewModel:** To store and manage UI-related data in a lifecycle-conscious way.
  - **LiveData:** To build data objects that notify views when the underlying database changes.
- **Security:** **Android Keystore** or a standard encryption library (like **AES**) for password hashing and encryption.

## 🚀 Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

- Android Studio (latest version recommended)
- An Android Emulator or a physical Android device

### Installation

1. **Clone the repository**
   ```sh
   git clone https://github.com/SasadaSaumya/ToDo.git
