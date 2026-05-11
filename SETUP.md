# Setup Guide

This file provides a practical setup and run guide for the `password-manager` project.

## 1) Prerequisites

- Java JDK 25
- Maven 3.8+
- MySQL 8+
- Optional SMTP account for email verification and password reset

Check versions:

```bash
java -version
mvn -version
mysql --version
```

## 2) Clone and open project

```bash
git clone <your-repo-url>
cd password-mamager-main
```

## 3) Create the database

```sql
CREATE DATABASE password_manager;
```

## 4) Configure the database connection

The project ships with local defaults in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/password_manager?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=
```

Update these values for your environment, or override them with environment variables or a local override file.

> Important: do not commit real credentials to source control.

### Option A — Environment variables (recommended)

Spring Boot supports these standard Spring datasource variables:

| Variable | Purpose |
|----------|---------|
| `SPRING_DATASOURCE_URL` | JDBC URL for MySQL |
| `SPRING_DATASOURCE_USERNAME` | MySQL username |
| `SPRING_DATASOURCE_PASSWORD` | MySQL password |

#### Windows PowerShell (current session)

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/password_manager?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:SPRING_DATASOURCE_USERNAME = "root"
$env:SPRING_DATASOURCE_PASSWORD = "your-mysql-password"
```

#### Windows PowerShell (persist for your user)

```powershell
setx SPRING_DATASOURCE_URL "jdbc:mysql://localhost:3306/password_manager?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
setx SPRING_DATASOURCE_USERNAME "root"
setx SPRING_DATASOURCE_PASSWORD "your-mysql-password"
```

Restart your terminal or IDE after `setx` so values are available.

#### Linux/macOS (bash/zsh)

```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/password_manager?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
export SPRING_DATASOURCE_USERNAME="root"
export SPRING_DATASOURCE_PASSWORD="your-mysql-password"
```

### Option B — Local config override

1. Copy `application-local.properties.example` to `application-local.properties` in the project root.
2. Edit the `spring.datasource.*` values.

This keeps local secrets outside of `src/main/resources/application.properties`.

## 5) Configure SMTP (optional)

The application starts without SMTP configured. If mail is not configured, verification and password-reset email delivery will be disabled, but the app still starts and registration still works.

The default SMTP placeholders in `src/main/resources/application.properties` are:

```properties
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your-email@example.com
spring.mail.password=your-email-password
app.mail.from=your-email@example.com
```

To enable email delivery, set at least these variables:

| Variable | Notes |
|----------|--------|
| `SPRING_MAIL_HOST` | SMTP host |
| `SPRING_MAIL_PORT` | SMTP port |
| `SPRING_MAIL_USERNAME` | SMTP username |
| `SPRING_MAIL_PASSWORD` | SMTP password |
| `APP_MAIL_FROM` | From email address |

#### Windows PowerShell

```powershell
$env:SPRING_MAIL_HOST = "smtp.gmail.com"
$env:SPRING_MAIL_PORT = "587"
$env:SPRING_MAIL_USERNAME = "your-email@gmail.com"
$env:SPRING_MAIL_PASSWORD = "your-app-password"
$env:APP_MAIL_FROM = "your-email@gmail.com"
```

#### Linux/macOS

```bash
export SPRING_MAIL_HOST="smtp.gmail.com"
export SPRING_MAIL_PORT="587"
export SPRING_MAIL_USERNAME="your-email@gmail.com"
export SPRING_MAIL_PASSWORD="your-app-password"
export APP_MAIL_FROM="your-email@gmail.com"
```

## 6) Build the project

```bash
mvn clean install
```

## 7) Run the application

The app is configured with `server.port=0`, so it starts on a random available port. Check the startup logs for the actual URL.

### A) Run with Maven

```bash
mvn spring-boot:run
```

Run from the project root so `application-local.properties` is discovered.

### B) Run the packaged JAR

```bash
mvn clean package
java -jar target/password-manager-0.0.1-SNAPSHOT.jar
```

### C) Run from IDE

Main class:

`com.example.passwordmanager.PasswordManagerApplication`

Set the working directory to the project root or configure environment variables in the run configuration.

## 8) Run tests

```bash
mvn test
```

Tests use the H2 in-memory database and do not require the production MySQL schema.

## 9) Optional fixed port

If you want a fixed port instead of a random one:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
```

or

```bash
java -jar target/password-manager-0.0.1-SNAPSHOT.jar --server.port=8080
```

## Quick startup checklist

- MySQL is running
- `password_manager` database exists
- Database credentials are configured via environment variables or `application-local.properties`
- Optional SMTP variables are configured if email is required
- App starts successfully and logs `Started PasswordManagerApplication`