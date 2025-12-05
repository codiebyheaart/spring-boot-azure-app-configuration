# 🔧 Emergency Security Fix - Credential Rotation Guide

## ⚠️ Your credentials were exposed on GitHub. Follow these steps:

---

## STEP 1: Rotate Azure Credentials (DO THIS FIRST!)

### Option A: Azure Portal (Easiest)

1. **Login to Azure Portal**: https://portal.azure.com
2. **Navigate to**: Your App Configuration → `pankajconfigstore`
3. **Go to**: Settings → **Access keys**
4. **Regenerate keys**:
   - Click **"Regenerate"** next to **Primary Key**
   - Copy the new **Connection string-Primary**
5. **Update local file**: Paste new connection string into `src/main/resources/bootstrap.yml`
6. **Test**: Run `mvn spring-boot:run` to verify it works

### Option B: Azure CLI (Advanced)

```bash
# Login to Azure
az login

# Regenerate the primary key
az appconfig credential regenerate \
  --name pankajconfigstore \
  --key primary

# Get the new connection string
az appconfig credential list \
  --name pankajconfigstore \
  --query "[?name=='Primary'].connectionString" \
  --output tsv
```

✅ **Result**: Old exposed credentials are now **INVALID** and useless to attackers

---

## STEP 2: Remove File from Git History

The exposed credentials are still visible in GitHub commit history. Clean it up:

### Method 1: Remove Specific File from All Commits

```bash
# Navigate to your project
cd e:\github_projects\spring-boot-azure-app-configuration

# Make sure everything is committed
git status

# Remove the file from ALL commit history
git filter-branch --force --index-filter \
  "git rm --cached --ignore-unmatch src/main/resources/bootstrap.yml" \
  --prune-empty --tag-name-filter cat -- --all

# Verify bootstrap.yml is now ignored
git status

# Force push to GitHub (rewrites history)
git push origin --force --all
```

### Method 2: Use BFG Repo Cleaner (Faster for large repos)

```bash
# Download BFG
# From: https://rtyley.github.io/bfg-repo-cleaner/

# Remove the file
java -jar bfg.jar --delete-files bootstrap.yml

# Clean up
git reflog expire --expire=now --all
git gc --prune=now --aggressive

# Force push
git push origin --force --all
```

---

## STEP 3: Add Template File (Already Done ✅)

Replace the sensitive file with a safe template:

```bash
# The template is already created
git add src/main/resources/bootstrap.yml.example
git add .gitignore
git add SECURITY_SETUP.md
git commit -m "Add secure bootstrap template and update .gitignore"
git push origin main
```

---

## STEP 4: Verify GitHub

1. Go to your GitHub repo
2. Click on **"Commits"**
3. Search through old commits for `bootstrap.yml`
4. **It should NOT appear** after the fix

---

## STEP 5: Enable GitHub Secret Scanning (Optional but Recommended)

1. Go to: GitHub Repo → Settings → Security
2. Enable **"Secret scanning"**
3. Enable **"Push protection"**

This prevents future accidents!

---

## ✅ VERIFICATION CHECKLIST

- [ ] Regenerated Azure App Configuration credentials
- [ ] Updated local `bootstrap.yml` with new credentials
- [ ] Tested app still works with new credentials
- [ ] Removed `bootstrap.yml` from Git history
- [ ] Force-pushed cleaned history to GitHub
- [ ] Verified old commits don't show credentials on GitHub
- [ ] Added template file (`bootstrap.yml.example`)
- [ ] Updated `.gitignore` to prevent future leaks

---

## 🎯 Why Both Steps Matter

| What | Why It's Important |
|------|-------------------|
| **Rotate Azure Keys** | Makes exposed credentials useless immediately |
| **Clean Git History** | Prevents credentials from being visible forever in old commits |

**Even "demo data" credentials can:**
- Be used to test attacks
- Guess patterns for your production credentials
- Access resources if accidentally connected to real data later

---

## 📞 Questions?

If you're unsure about any step, review `SECURITY_SETUP.md` for ongoing security practices.

**Security is not optional!** 🔐
