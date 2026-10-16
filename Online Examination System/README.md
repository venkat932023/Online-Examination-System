# Online Examination System

A Core Java console application designed to manage user authentication, record operations, workflow processing, and report generation as described in the project requirements.

## Features

- User registration and login
- Admin login access
- Add and update records
- Search records in admin section
- Report generation and summary counts
- Menu-driven console interface
- File handling for persistent storage
- Built using Core Java, Collections, OOPs, and exception handling

## Project Structure

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

## Run the Project

From the project root in PowerShell:

```powershell
$files = Get-ChildItem -Path .\src -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
javac -d out $files
java -cp out com.exam.Main
```

## Default Admin Login

- Username: `admin`
- Password: `admin123`
