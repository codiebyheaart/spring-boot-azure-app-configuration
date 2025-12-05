# 🔐 Security Configuration Guide

## ⚠️ IMPORTANT: Never Commit Secrets to Git!

This project uses Azure App Configuration which requires sensitive credentials. Follow this guide to set up your local environment securely.

## 📋 Files Explained

| File | Purpose | Commit to Git? |
|------|---------|----------------|
| `bootstrap.yml.example` | Template with placeholders | ✅ YES - Safe to commit |
| `bootstrap.yml` | **Your actual credentials** | ❌ NO - Ignored by .gitignore |
| `application.properties.example` | Template | ✅ YES - Safe to commit |
| `application.properties` | **Your actual config** | ❌ NO - Ignored by .gitignore |

## 🚀 Quick Setup (First Time)

### Step 1: Copy Template Files

```bash
# Copy the template and add your real credentials
cp src/main/resources/bootstrap.yml.example src/main/resources/bootstrap.yml
```

### Step 2: Edit with Your Credentials

Edit `src/main/resources/bootstrap.yml` and replace placeholders:

```yaml
spring:
  cloud:
    azure:
      appconfiguration:
        stores:
          - connection-string: Endpoint=https://YOUR-ACTUAL-STORE.azconfig.io;Id=REAL-ID;Secret=REAL-SECRET
```

### Step 3: Verify It's Ignored

```bash
git status
# bootstrap.yml should NOT appear in the list
```

## 🔒 Security Best Practices

### Option 1: Environment Variables (RECOMMENDED for Production)

Set environment variable instead of hardcoding:

**Windows PowerShell:**
```powershell
$env:AZURE_APPCONFIG_CONNECTION_STRING="Endpoint=https://yourstore.azconfig.io;Id=XXX;Secret=XXX"
mvn spring-boot:run
```

**Linux/Mac:**
```bash
export AZURE_APPCONFIG_CONNECTION_STRING="Endpoint=https://yourstore.azconfig.io;Id=XXX;Secret=XXX"
mvn spring-boot:run
```

Then use in `bootstrap.yml`:
```yaml
connection-string: ${AZURE_APPCONFIG_CONNECTION_STRING}
```

### Option 2: Azure Managed Identity (BEST for Production)

For Azure deployments, use Managed Identity:

```yaml
spring:
  cloud:
    azure:
      credential:
        managed-identity-enabled: true
      appconfiguration:
        stores:
          - endpoint: https://yourstore.azconfig.io
```

No secrets needed! Azure handles authentication automatically.

### Option 3: Azure Key Vault (Enterprise)

Store connection strings in Azure Key Vault and reference them:

```yaml
spring:
  cloud:
    azure:
      keyvault:
        secret:
          property-sources:
            - endpoint: https://yourvault.vault.azure.net/
```

## 🚨 What If Credentials Were Already Committed?

If you accidentally committed secrets:

### 1. Rotate Credentials IMMEDIATELY
- Go to Azure Portal > App Configuration > Access Keys
- Click "Regenerate" for both Primary and Secondary keys
- Update your local `bootstrap.yml` with new credentials

### 2. Remove from Git History
```bash
# Remove the file from Git history
git filter-branch --force --index-filter \
  "git rm --cached --ignore-unmatch src/main/resources/bootstrap.yml" \
  --prune-empty --tag-name-filter cat -- --all

# Force push (WARNING: This rewrites history)
git push origin --force --all
```

### 3. Verify on GitHub
- Check that the file doesn't appear in commit history
- Enable GitHub secret scanning alerts

## ✅ Checklist Before Committing

- [ ] `bootstrap.yml` is listed in `.gitignore`
- [ ] Run `git status` - `bootstrap.yml` should NOT appear
- [ ] Only `bootstrap.yml.example` with placeholders is committed
- [ ] No real connection strings in any committed files
- [ ] Environment variables are documented in this guide

## 🔍 How to Share With Team

When teammates clone the repository:

1. They copy `bootstrap.yml.example` to `bootstrap.yml`
2. They add their own Azure credentials (or use environment variables)
3. The real `bootstrap.yml` stays local and never gets committed

## 📚 Additional Resources

- [Azure App Configuration Best Practices](https://learn.microsoft.com/azure/azure-app-configuration/howto-best-practices)
- [Managed Identity Documentation](https://learn.microsoft.com/azure/active-directory/managed-identities-azure-resources/)
- [Azure Key Vault Integration](https://learn.microsoft.com/azure/developer/java/spring-framework/configure-spring-boot-starter-java-app-with-azure-key-vault)

---

**Remember: Credentials in Git = Compromised Credentials** 🔐
