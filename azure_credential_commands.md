# Azure CLI Commands to Rotate Credentials

## Issue Found
- ✅ Correct store name: **`naveenindia`** (not `pankajconfigstore`)
- ❌ The `--id` parameter needs the actual credential ID

## Step-by-Step Commands

### Step 1: List Current Credentials with IDs
```bash
az appconfig credential list --name naveenindia --output table
```

This will show you something like:
```
Id                                    Name                      ConnectionString
------------------------------------  ------------------------  ------------------
xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx  Primary                   Endpoint=https://...
yyyyyyyy-yyyy-yyyy-yyyy-yyyyyyyyyyyy  Secondary                 Endpoint=https://...
```

### Step 2: Regenerate Using the Actual ID
Copy the ID from the output above (the long UUID), then:

```bash
# Replace <the-actual-id> with the ID you copied
az appconfig credential regenerate --name naveenindia --id <the-actual-id>
```

Example:
```bash
az appconfig credential regenerate --name naveenindia --id xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
```

### Step 3: Get the New Connection String
```bash
az appconfig credential list --name naveenindia --output table
```

Copy the new **ConnectionString** from the Primary row.

### Step 4: Update Your Local File
Edit `src/main/resources/bootstrap.yml` and replace the old connection string with the new one.

---

## Quick All-in-One Commands

```bash
# 1. List credentials and their IDs
az appconfig credential list --name naveedindia --output table

# 2. Copy the Primary ID and regenerate (replace YOUR-PRIMARY-ID)
az appconfig credential regenerate --name naveedindia --id YOUR-PRIMARY-ID

# 3. Get the new connection string
az appconfig credential list --name naveedindia --query "[0].connectionString" -o tsv
```

---

## Alternative: Use Azure Portal (Easier!)

If CLI is giving issues, use the Azure Portal:

1. Go to: https://portal.azure.com
2. Search: **`naveedindia`**
3. Click: **Settings** → **Access keys**
4. Click: **Regenerate** button next to Primary Key
5. Copy: **Connection string**
6. Update: Your local `bootstrap.yml`

---

## What This Does

✅ **Invalidates the old exposed credentials** from the leaked `bootstrap.yml`  
✅ **Generates new credentials** that only you have  
✅ **Makes the GitHub leak harmless** - old credentials won't work anymore
