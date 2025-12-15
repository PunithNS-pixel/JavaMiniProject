# 🚀 Deploy to Render - Quick Start Guide

Your Cipher Encryption App is ready to deploy to Render!

## What's Been Created:

✅ **Dockerfile** - Multi-stage build (Maven → JAR → Alpine Linux)  
✅ **render.yaml** - Render deployment configuration  
✅ **DEPLOYMENT.md** - Detailed deployment guide  
✅ **DEPLOY.sh** - Helper script for GitHub setup  

## Deploy in 5 Minutes:

### Step 1: Create GitHub Repository
```bash
cd "/Users/punithns/Desktop/Java Mini"
git init
git add .
git commit -m "Initial: Cipher Encryption App"
```

Go to https://github.com/new and create `cipher-encryption-app` repository

```bash
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/cipher-encryption-app.git
git push -u origin main
```

### Step 2: Connect to Render
1. Go to https://render.com (create free account if needed)
2. Click **Dashboard** → **New +** → **Web Service**
3. Click **Build and deploy from a Git repository**
4. Click **Connect GitHub** and authorize
5. Select `cipher-encryption-app` repository
6. Configure:
   - **Name**: `cipher-encryption-app`
   - **Environment**: `Docker`
   - **Region**: Choose closest to you
   - **Plan**: `Free`

### Step 3: Add Environment Variables
In Render dashboard, go to **Environment** tab and add:

```
RESEND_API_KEY = re_gDc5Qt7T_8XAdPpZx2dRajsS9CHMJhXiP
RESEND_FROM_EMAIL = onboarding@resend.dev
APP_BASE_URL = https://YOUR-SERVICE-NAME.onrender.com
```

(Replace `YOUR-SERVICE-NAME` with the name Render gives your service)

### Step 4: Deploy
Click **Deploy** button and wait 3-5 minutes

✅ **Done!** Your app is live at `https://YOUR-SERVICE-NAME.onrender.com`

## After Deployment:

- **Login Page**: https://YOUR-SERVICE-NAME.onrender.com/login
- **Create Account**: https://YOUR-SERVICE-NAME.onrender.com/signup
- **Test Flow**: Signup → Login → Encrypt → Send → Decrypt

## Free Tier Limitations:

⚠️ Service spins down after 15 minutes of inactivity  
⚠️ First request takes 30+ seconds to wake up  
⚠️ Limited to 0.5GB RAM  

**For Production**: Upgrade to Starter Plan ($7/month) for always-on service

## Update Your App:

```bash
# Make changes locally
git add .
git commit -m "Your changes"
git push origin main

# Render automatically redeploys!
# Monitor in Render dashboard
```

## Troubleshooting:

**Service won't start?**  
→ Check **Logs** tab in Render dashboard

**Email not sending?**  
→ Verify `RESEND_API_KEY` in Environment variables

**QR codes broken?**  
→ Ensure `APP_BASE_URL` is set to your deployed URL, then redeploy

## Full Guide:

See **DEPLOYMENT.md** for detailed instructions and troubleshooting

---

**Need Help?**
- Render Docs: https://render.com/docs
- GitHub: https://docs.github.com
- Spring Boot: https://spring.io/projects/spring-boot
