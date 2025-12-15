# 📋 Render Deployment Checklist

## Pre-Deployment ✅

- [x] Application runs locally (`mvn spring-boot:run`)
- [x] All features tested (signup, login, encrypt, decrypt, email)
- [x] Dockerfile created for containerization
- [x] render.yaml configuration created
- [x] README.md with setup instructions
- [x] DEPLOYMENT.md with detailed guide
- [x] .gitignore configured to exclude build artifacts

## Deployment Steps

### 1️⃣ Push to GitHub (5 minutes)

```bash
cd "/Users/punithns/Desktop/Java Mini"

# Initialize git
git init
git add .
git commit -m "Initial commit: Cipher Encryption Web App"

# Create repo on GitHub (https://github.com/new)
# Name: cipher-encryption-app
# Then push:
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/cipher-encryption-app.git
git push -u origin main
```

**Status**: ⏳ Waiting

---

### 2️⃣ Create Render Account (2 minutes)

1. Go to https://render.com
2. Click **Sign up**
3. Choose authentication method (GitHub recommended)
4. Complete signup

**Status**: ⏳ Waiting

---

### 3️⃣ Deploy Service (5 minutes)

1. Go to https://render.com/dashboard
2. Click **New +** button
3. Select **Web Service**
4. Click **Build and deploy from a Git repository**
5. Click **Connect GitHub** (authorize if needed)
6. Search for `cipher-encryption-app` and select it
7. Configure settings:
   - **Name**: `cipher-encryption-app`
   - **Environment**: `Docker`
   - **Region**: Choose your region (e.g., Oregon, Frankfurt)
   - **Plan**: `Free` (for testing) or `Starter` ($7/mo for production)
   - **Auto-Deploy**: Enable (auto-deploy on git push)
8. Click **Create Web Service**

**Status**: ⏳ Building (watch the logs)

---

### 4️⃣ Set Environment Variables (3 minutes)

Once the service is created:

1. Go to **Environment** tab in service dashboard
2. Add these variables:

| Key | Value |
|-----|-------|
| `RESEND_API_KEY` | `re_gDc5Qt7T_8XAdPpZx2dRajsS9CHMJhXiP` |
| `RESEND_FROM_EMAIL` | `onboarding@resend.dev` |
| `APP_BASE_URL` | `https://cipher-encryption-app.onrender.com` |

(Replace `cipher-encryption-app` with your actual service name from Render)

3. Click **Save** or **Deploy** to apply changes

**Status**: ⏳ Redeploying with environment variables

---

### 5️⃣ Wait for Deployment (3-5 minutes)

In the **Logs** tab, you should see:
```
Attaching to cipher-encryption-app-1
cipher-encryption-app-1  | Starting CryptoApplication...
cipher-encryption-app-1  | Started CryptoApplication in 1.234 seconds
cipher-encryption-app-1  | Tomcat started on port 10000 with context path ''
```

When you see `Started CryptoApplication`, the app is live!

**Status**: ✅ Running

---

## Post-Deployment ✅

### 6️⃣ Test Your Deployment (5 minutes)

1. **Get Your URL**:
   - From Render dashboard, copy the service URL
   - Format: `https://your-service-name.onrender.com`

2. **Test Login Page**:
   ```bash
   curl https://your-service-name.onrender.com/login
   ```

3. **Create Test Account**:
   - Visit: `https://your-service-name.onrender.com/signup`
   - Create account: username=`testuser`, password=`testpass123`

4. **Test Full Flow**:
   - Login → Encrypt message → Send email → Check email receipt
   - Verify PDF received with QR code

**Status**: ✅ Testing complete

---

### 7️⃣ Monitor & Manage

**View Logs**:
- Render Dashboard → **Logs** tab
- Watch for errors or issues

**Check Metrics**:
- Render Dashboard → **Metrics** tab
- Monitor CPU, memory, uptime

**Update Code** (Auto-Deployment):
```bash
# Make changes locally
git add .
git commit -m "Your update description"
git push origin main

# Render automatically redeploys!
# Check Render dashboard for deployment progress
```

**Restart Service**:
- Render Dashboard → **More** menu → **Restart**
- Useful if service becomes unresponsive

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| **Deployment fails in Docker build** | Check Logs tab; verify pom.xml is valid; ensure Dockerfile is correct |
| **Service won't start (crashes)** | Check environment variables are set; review application logs in Logs tab |
| **Getting 502 Bad Gateway** | Service crashed or still starting; wait a few seconds and refresh |
| **Email not sending** | Verify RESEND_API_KEY in environment variables; check Resend dashboard |
| **QR codes broken** | Ensure APP_BASE_URL matches your deployed URL; redeploy after updating |
| **Service spins down too quickly** | Upgrade to Starter plan ($7/mo) for always-on service |
| **Email address validation failing** | Use valid Resend-verified email addresses as recipients |

---

## Free Tier vs Paid

| Feature | Free | Starter |
|---------|------|---------|
| Cost | Free | $7/month |
| Uptime | ~99% but spins down after 15 min inactivity | Always on |
| Response to first request | 30+ seconds (cold start) | <1 second |
| Memory | 0.5 GB | 1 GB |
| CPU | Shared | Shared |
| Deployments | Unlimited | Unlimited |
| Good for | Testing, demos | Production, real users |

**Recommendation**: Start with Free tier for testing, upgrade to Starter for production use.

---

## Quick Reference

**Render Dashboard**: https://render.com/dashboard  
**Your Service**: https://cipher-encryption-app.onrender.com  
**Login Page**: https://cipher-encryption-app.onrender.com/login  
**API Health Check**: https://cipher-encryption-app.onrender.com/login (should return 200)

---

## Estimated Timeline

| Step | Time | Status |
|------|------|--------|
| Push to GitHub | 5 min | ⏳ |
| Create Render account | 2 min | ⏳ |
| Deploy service | 5 min | ⏳ |
| Set environment vars | 3 min | ⏳ |
| Wait for Docker build | 3-5 min | ⏳ |
| Test deployment | 5 min | ⏳ |
| **Total** | **~25 minutes** | **✅** |

---

## Success Criteria

✅ Service shows "Live" in Render dashboard  
✅ `/login` endpoint returns 200 status  
✅ Can signup and create new account  
✅ Can login with created account  
✅ Can encrypt a message  
✅ Can send encrypted message via email  
✅ Email is received with PDF attachment  
✅ PDF can be opened with password  
✅ QR code in PDF links to decryption page  
✅ Decryption page works and decrypts message correctly

---

**Ready?** Follow the steps above and your app will be live on the internet! 🚀
