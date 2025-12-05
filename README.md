# Spring Boot Azure App Configuration Integration

**Seamlessly integrate Azure App Configuration with Spring Boot for dynamic configuration management** - A production-ready Maven project demonstrating cloud-native configuration patterns with Azure services.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.15-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Azure](https://img.shields.io/badge/Azure-App%20Configuration-0078D4.svg)](https://azure.microsoft.com/services/app-configuration/)
[![Java](https://img.shields.io/badge/Java-11%2B-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-red.svg)](https://maven.apache.org/)

##  Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Troubleshooting](#troubleshooting)
- [Best Practices](#best-practices)
- [Contributing](#contributing)
- [License](#license)

##  Overview

This Spring Boot application demonstrates **enterprise-grade integration** with **Azure App Configuration**, enabling centralized configuration management, dynamic updates, and feature flag control without application restarts. Perfect for microservices architectures and cloud-native applications.

### Key Benefits

 **Centralized Configuration** - Manage all application settings from Azure  
 **Dynamic Updates** - Refresh configurations without redeployment  
 **Feature Flags** - Control feature rollouts dynamically  
 **Secure Secrets** - Integration with Azure Key Vault  
 **Environment Isolation** - Separate configs for dev, staging, production

##  Features

-  **Spring Boot 2.7.15** with Maven build system
-  **Azure App Configuration** integration via Spring Cloud Azure
-  **Bootstrap configuration** for early-stage property loading
-  **RESTful API endpoints** for configuration testing
-  **Managed Identity support** for Azure authentication
-  **Development-ready** with debug endpoints
-  **Production-ready** packaging and deployment

##  Prerequisites

Before running this application, ensure you have:

- **Java Development Kit (JDK) 11** or higher ([Download](https://adoptium.net/))
- **Apache Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- **Azure Account** with App Configuration instance ([Create Free Account](https://azure.microsoft.com/free/))
- **Git** for version control ([Download](https://git-scm.com/))

### Verify Installation

```bash
java -version    # Should show Java 11 or higher
mvn -version     # Should show Maven 3.6 or higher
```

##  Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/spring-boot-azure-app-configuration.git
cd spring-boot-azure-app-configuration
```

### 2. Configure Azure Connection

Edit `src/main/resources/bootstrap.yml` and add your Azure App Configuration connection string:

```yaml
spring:
  cloud:
    azure:
      appconfiguration:
        stores:
          - connection-string: "Endpoint=https://your-config-store.azconfig.io;Id=XXXX;Secret=XXXX"
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on **http://localhost:8080**

##  Project Structure

```
spring-boot-azure-app-configuration/
├── src/
│   ├── main/
│   │   ├── java/com/example/nftclubcoinapp/
│   │   │   ├── NftClubcoinAppApplication.java    # Main application class
│   │   │   ├── controller/
│   │   │   │   ├── ConfigController.java         # Configuration endpoints
│   │   │   │   ├── AuthController.java           # Authentication endpoints
│   │   │   │   ├── UserController.java           # User management
│   │   │   │   └── ...                           # Other controllers
│   │   │   └── util/
│   │   │       └── StaticResponseUtil.java       # Utility classes
│   │   └── resources/
│   │       └── bootstrap.yml                     # Azure configuration
│   └── test/                                     # Test files
├── target/                                       # Maven build output
├── pom.xml                                       # Maven dependencies
├── MAVEN_README.md                               # Technical documentation
└── README.md                                     # This file
```

##  API Endpoints

Once running, the following endpoints are available:

| Endpoint | Method | Description | Example Response |
|----------|--------|-------------|------------------|
| `/config` | GET | Get single configuration value | `"Value from Azure App Configuration: myValue"` |
| `/config/json` | GET | **Get all configurations as JSON** | `{"name1": "value1", "name2": "value2"}` |
| `/env` | GET | Get environment property | `"Environment property 'name1': value1"` |
| `/debug` | GET | Debug Azure App Configuration | Detailed configuration sources |

### Example Request

```bash
# Test the JSON configuration endpoint
curl http://localhost:8080/config/json
```

**Expected Response:**

```json
{
  "name1": "value-from-azure",
  "name2": "another-value",
  "source": "Azure App Configuration"
}
```

##  Configuration

### Azure App Configuration Setup

1. **Create App Configuration Store** in Azure Portal
2. **Add Configuration Keys:**
   - Key: `name1`, Value: `your-first-value`
   - Key: `name2`, Value: `your-second-value`
3. **Get Connection String** from Azure Portal → Access Keys
4. **Update** `bootstrap.yml` with your connection string

### bootstrap.yml Configuration

```yaml
spring:
  cloud:
    azure:
      appconfiguration:
        stores:
          - connection-string: ${AZURE_APPCONFIG_CONNECTION_STRING}
      credential:
        managed-identity-enabled: false  # Disable if not using managed identity
```

### Environment Variables (Optional)

For production deployments, use environment variables:

```bash
# Windows PowerShell
$env:AZURE_APPCONFIG_CONNECTION_STRING="Endpoint=https://..."

# Linux/Mac
export AZURE_APPCONFIG_CONNECTION_STRING="Endpoint=https://..."
```

##  Running the Application

### Option 1: Maven Spring Boot Plugin (Recommended)

```bash
mvn spring-boot:run
```

### Option 2: Packaged JAR

```bash
# Build the JAR
mvn clean package

# Run the JAR
java -jar target/nftclubcoin-app-0.0.1-SNAPSHOT.jar
```

### Option 3: IDE

**IntelliJ IDEA:**
1. Open the project (File → Open → Select `pom.xml`)
2. Right-click on `NftClubcoinAppApplication.java`
3. Select "Run 'NftClubcoinAppApplication'"

**VS Code:**
1. Install "Extension Pack for Java"
2. Open the project folder
3. Press `F5` to run

**Eclipse:**
1. Import → Existing Maven Project
2. Right-click project → Run As → Spring Boot App

## 🧪 Testing

### Run All Tests

```bash
mvn test
```

### Skip Tests During Build

```bash
mvn clean install -DskipTests
```

### Manual API Testing

```bash
# Test configuration endpoint
curl http://localhost:8080/config

# Test JSON endpoint (MAIN ENDPOINT)
curl http://localhost:8080/config/json

# Test environment endpoint
curl http://localhost:8080/env

# Debug configuration
curl http://localhost:8080/debug
```

## 🔧 Troubleshooting

### Error: "missing required environment variable AZURE_CLIENT_ID"

**Cause:** The application is trying to use Managed Identity authentication but the required environment variables are not set.

**Solution 1 - Disable Managed Identity (Recommended for local development):**

Add to `src/main/resources/bootstrap.yml`:

```yaml
spring:
  cloud:
    azure:
      credential:
        managed-identity-enabled: false
```

**Solution 2 - Provide Service Principal Credentials:**

```bash
# Windows PowerShell
$env:AZURE_CLIENT_ID="your-client-id"
$env:AZURE_CLIENT_SECRET="your-client-secret"
$env:AZURE_TENANT_ID="your-tenant-id"

# Linux/Mac
export AZURE_CLIENT_ID="your-client-id"
export AZURE_CLIENT_SECRET="your-client-secret"
export AZURE_TENANT_ID="your-tenant-id"
```

### Error: "Failed to load configuration from Azure App Configuration"

**Solutions:**
1. Verify connection string is correct in `bootstrap.yml`
2. Check network connectivity to Azure
3. Ensure keys exist in Azure App Configuration
4. Check Azure App Configuration access permissions

### Application Won't Start

**Check:**
1. Java version: `java -version` (Must be 11+)
2. Maven version: `mvn -version` (Must be 3.6+)
3. Port 8080 is not in use: `netstat -ano | findstr :8080`
4. Build the project: `mvn clean install`

## 💡 Best Practices

### Security

- ✅ Never commit connection strings to version control
- ✅ Use environment variables for sensitive data
- ✅ Use Managed Identity in Azure deployments
- ✅ Integrate with Azure Key Vault for secrets

### Performance

- ✅ Enable caching for configuration values
- ✅ Use refresh strategies for dynamic updates
- ✅ Monitor Azure App Configuration requests

### Development

- ✅ Use different App Configuration stores for each environment
- ✅ Implement proper error handling
- ✅ Log configuration loading events
- ✅ Test with mock configurations locally

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🔗 Useful Links

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Azure App Configuration Documentation](https://docs.microsoft.com/azure/azure-app-configuration/)
- [Spring Cloud Azure Documentation](https://learn.microsoft.com/azure/developer/java/spring-framework/)
- [Maven Documentation](https://maven.apache.org/guides/)

## 📞 Support

- **Issues:** [GitHub Issues](https://github.com/codiebyheaart/spring-boot-azure-app-configuration/issues)
- **Discussions:** [GitHub Discussions](https://github.com/codiebyheaart/spring-boot-azure-app-configuration/discussions)
- **Email:** dilsecodie@gmail.com

---

**Built with ❤️ using Spring Boot and Azure**

*Last Updated: December 2025*

### Keywords
`spring boot azure`, `azure app configuration`, `spring cloud azure`, `java configuration management`, `azure integration`, `microservices configuration`, `cloud native java`, `spring boot maven`, `azure spring boot example`, `centralized configuration`, `feature flags azure`, `azure key vault integration`
