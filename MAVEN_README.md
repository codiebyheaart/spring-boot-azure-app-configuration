# Azure App Configuration - Maven Project

This is the **Maven version** of the Azure App Configuration demo project.

## Project Structure
```
coin_base_api_temp/
├── pom.xml                    (Maven configuration - NEW!)
├── build.gradle               (Old Gradle config - can be removed if needed)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/nftclubcoinapp/
│   │   └── resources/
│   │       └── bootstrap.yml  (Azure App Configuration settings)
│   └── test/
└── target/                    (Maven build output)
```

## Prerequisites
- Java 11 or higher
- Maven 3.6+ installed
- Azure App Configuration instance

## Maven Commands

### Build the project
```bash
mvn clean install
```

### Run the application
```bash
mvn spring-boot:run
```

### Package as JAR
```bash
mvn clean package
```

### Run the JAR
```bash
java -jar target/nftclubcoin-app-0.0.1-SNAPSHOT.jar
```

### Skip tests during build
```bash
mvn clean install -DskipTests
```

## API Endpoints

Once the application is running (default port 8080):

- `http://localhost:8080/config` - Get single config value
- `http://localhost:8080/config/json` - Get all config values as JSON
- `http://localhost:8080/env` - Get environment property
- `http://localhost:8080/debug` - Debug Azure App Configuration

## Dependencies (pom.xml)

- **Spring Boot** 2.7.15
- **Azure Spring Cloud App Configuration** 4.12.0
- **Spring Cloud Bootstrap** 3.1.7

## Configuration

Azure connection is configured in `src/main/resources/bootstrap.yml`:
```yaml
spring:
  cloud:
    azure:
      appconfiguration:
        stores:
          - connection-string: <your-connection-string>
```

## Troubleshooting

### Error: "missing required environment variable AZURE_CLIENT_ID"

**Solution 1**: Add to `bootstrap.yml`:
```yaml
spring:
  cloud:
    azure:
      credential:
        managed-identity-enabled: false
```

**Solution 2**: Set environment variables:
```powershell
# Windows PowerShell
$env:AZURE_CLIENT_ID="your-client-id"
$env:AZURE_CLIENT_SECRET="your-client-secret"
$env:AZURE_TENANT_ID="your-tenant-id"
```

```bash
# Linux/Mac
export AZURE_CLIENT_ID="your-client-id"
export AZURE_CLIENT_SECRET="your-client-secret"
export AZURE_TENANT_ID="your-tenant-id"
```

## Gradle vs Maven

Both `build.gradle` and `pom.xml` exist in this project. You can use either:

- **Use Maven**: Run `mvn` commands
- **Use Gradle**: Run `./gradlew` commands

If you only need Maven, you can delete:
- `build.gradle`
- `gradlew`, `gradlew.bat`
- `.gradle/` folder

## IDE Support

### IntelliJ IDEA
1. File → Open → Select `pom.xml`
2. IntelliJ will auto-detect Maven project
3. Use Maven tool window to run goals

### VS Code
1. Install "Extension Pack for Java"
2. Open project folder
3. Maven commands available in Command Palette

### Eclipse
1. File → Import → Existing Maven Projects
2. Select project folder
3. Use Run As → Maven Build

---

**Created**: 2025-12-05
**Spring Boot Version**: 2.7.15
**Java Version**: 11
