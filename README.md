# 🎓 Online Examination System | Core Java

A **menu-driven Online Examination System** developed using Core Java to demonstrate user authentication, record management, file handling, and report generation. This project follows Object-Oriented Programming (OOP) principles and uses Java Collections and exception handling to organize application logic.

## 🚀 Features

- 👤 **User Registration & Login** – Register new users and authenticate existing users.
- 🔐 **Admin Authentication** – Secure admin access to administrative operations.
- ➕ **Add Records** – Create and store new records.
- ✏️ **Update Records** – Modify existing records.
- 🔍 **Search Records** – Search for records through the admin section.
- 📊 **Report Generation** – Generate reports and display summary counts.
- 💾 **File Handling** – Store user and record data in text files for persistence.
- 🧭 **Menu-Driven Interface** – Navigate application features through a console menu.
- ⚠️ **Exception Handling** – Handle invalid login attempts and duplicate records using custom exceptions.

## 🛠️ Technologies Used

- **Language:** Core Java
- **Concepts:** OOP, Collections Framework, Exception Handling, File Handling
- **Interfaces:** Custom CRUD Operations Interface
- **Storage:** Text Files
- **IDE:** Visual Studio Code / IntelliJ IDEA / Eclipse
- **Version Control:** Git & GitHub

## 📁 Project Structure

```text
java 10k/
├── README.md
├── data/
│   ├── users.txt
│   └── records.txt
└── src/
    └── com/
        └── exam/
            ├── Main.java
            ├── exceptions/
            │   ├── DuplicateRecordException.java
            │   └── InvalidLoginException.java
            ├── interfaces/
            │   └── CrudOperations.java
            ├── model/
            │   ├── Record.java
            │   └── User.java
            ├── service/
            │   ├── AuthService.java
            │   └── RecordService.java
            └── util/
                └── FileStorage.java
```

## ⚙️ Prerequisites

Before running the project, ensure you have:

- Java Development Kit (JDK) installed.
- A terminal or PowerShell.
- Basic knowledge of compiling and executing Java programs.

Verify your Java installation:

```powershell
java -version
javac -version
```

## ▶️ How to Run the Project

**Step 1: Clone the repository**

```bash
git clone <your-repository-url>
```

**Step 2: Navigate to the project directory**

```powershell
cd "java 10k"
```

**Step 3: Compile the Java source files**

```powershell
$files = Get-ChildItem -Path .\src -Recurse -Filter *.java |
    Select-Object -ExpandProperty FullName

javac -d out $files
```

**Step 4: Run the application**

```powershell
java -cp out com.exam.Main
```

## 🔑 Default Admin Credentials

For local testing, the default admin credentials are:

| Field | Value |
|---|---|
| Username | `admin` |
| Password | `admin123` |

> ⚠️ **Security Note:** These are development credentials. Change them before sharing or deploying the application. Do not use real passwords in plain-text storage.

## 🧠 Concepts Demonstrated

- Classes and Objects
- Encapsulation and Abstraction
- Interfaces and CRUD Operations
- Java Collections Framework
- Custom Exception Handling
- File Input/Output Operations
- Modular Package Structure
- Separation of Model, Service, and Utility Layers

## 🎯 Project Objective

The objective of this project is to strengthen Core Java programming skills by building a structured console application that demonstrates authentication, record management, persistent storage, exception handling, and report generation.

## 🔮 Future Enhancements

- Implement an actual examination module with questions and multiple-choice answers.
- Add exam timers and automatic result calculation.
- Introduce role-based access control.
- Replace text-file storage with a MySQL database.
- Develop a graphical user interface or web-based frontend.
- Improve password security and input validation.

## 👨‍💻 Author

**Your Name**

- GitHub: [Your GitHub Profile](https://github.com/)

---

⭐ If you find this project useful, consider giving the repository a star!

*Built with Java to practice object-oriented programming and application development.*
