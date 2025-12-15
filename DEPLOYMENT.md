# Render Deployment Guide for Cipher Encryption App

## Prerequisites
1. **Render Account**: Create free account at https://render.com
2. **GitHub Repository**: Push your code to GitHub (Render integrates with GitHub)
3. **Environment Variables**: Have these ready:
   - `RESEND_API_KEY`: `re_gDc5Qt7T_8XAdPpZx2dRajsS9CHMJhXiP`
   - `RESEND_FROM_EMAIL`: `onboarding@resend.dev`
   - `APP_BASE_URL`: Your Render deployment URL (set after initial deploy)

## Step 1: Push to GitHub

```bash
cd "/Users/punithns/Desktop/Java Mini"

# Initialize git (if not already done)
git init
git add .
git commit -m "Initial commit: Cipher Encryption App"

# Create a new repository on GitHub and push
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/cipher-encryption-app.git
git push -u origin main
```

## Step 2: Connect to Render

1. Go to https://render.com/dashboard
2. Click **New +** → **Web Service**
3. Select **Build and deploy from a Git repository**
4. Click **Connect GitHub account** (authorize Render)
5. Search for and select `cipher-encryption-app` repository
6. Fill in deployment details:
   - **Name**: `cipher-encryption-app`
   - **Environment**: `Docker`
   - **Region**: Select closest to you (e.g., `Oregon`, `Frankfurt`)
   - **Branch**: `main`
   - **Plan**: Start with `Free` (upgrade later if needed)

## Step 3: Set Environment Variables

In the Render dashboard for your service:

1. Go to **Environment** tab
2. Add these variables:
   ```
   RESEND_API_KEY = re_gDc5Qt7T_8XAdPpZx2dRajsS9CHMJhXiP
   RESEND_FROM_EMAIL = onboarding@resend.dev
   APP_BASE_URL = https://YOUR-SERVICE-NAME.onrender.com
   ```

3. Click **Save**

## Step 4: Deploy

1. Click **Deploy** button
2. Render will:
   - Clone your repository
   - Build the Docker image
   - Deploy the application
   - Provide you a public URL

First deployment typically takes 3-5 minutes.

## Access Your App

After deployment completes:
- **Login Page**: `https://YOUR-SERVICE-NAME.onrender.com/login`
- **Signup**: `https://YOUR-SERVICE-NAME.onrender.com/signup`

## Post-Deployment Configuration

### 1. Update APP_BASE_URL
After your first deployment, update the `APP_BASE_URL` environment variable:
- Go to **Environment** in Render dashboard
- Update `APP_BASE_URL` to your actual deployment URL
- Redeploy

This ensures QR codes point to the correct decryption links.

### 2. Test the Deployment
```bash
# Test login page
curl https://YOUR-SERVICE-NAME.onrender.com/login

# Test signup
curl -X POST https://YOUR-SERVICE-NAME.onrender.com/signup \
  -d "username=testuser&password=testpass123&confirmPassword=testpass123"
```

## Monitoring & Logs

In Render dashboard:
- **Logs**: View real-time application logs
- **Metrics**: Monitor CPU, memory, uptime
- **Deployments**: View deployment history

## Important Notes

### Free Tier Limitations (Render)
- Service will **spin down after 15 minutes of inactivity**
- First request may take 30+ seconds to start
- 0.5GB RAM, shared CPU
- Monthly downtime limit

### Upgrade to Paid
For production use:
1. Go to **Plan** in Render dashboard
2. Upgrade to **Starter Plan** ($7/month minimum)
3. Benefits: Always-on, dedicated CPU/RAM, more reliable

### Database (Future Enhancement)
Render provides PostgreSQL integration:
1. Create PostgreSQL instance from dashboard
2. Link to your service via environment variables
3. Migrate UserService to use database

## Troubleshooting

### Service won't start
- Check **Logs** tab in Render dashboard
- Ensure Docker image builds successfully
- Verify all environment variables are set

### Email not sending
- Verify `RESEND_API_KEY` and `RESEND_FROM_EMAIL` in environment
- Check Resend dashboard to see if API calls are being made
- Review application logs for API errors

### QR codes not working
- Ensure `APP_BASE_URL` is set to your deployed URL
- After changing `APP_BASE_URL`, redeploy the service

### Cold start delays
- Free tier services spin down after 15 minutes
- Upgrade to paid plan for always-on service

## Deployment Architecture

```
GitHub Repository
    ↓
Render (monitors branch)
    ↓
Docker Build (Maven → JAR)
    ↓
Docker Container (Alpine Linux + JRE)
    ↓
Spring Boot App (port 10000)
    ↓
Public HTTPS URL
    ↓
Users access via browser
```

## Update & Redeploy

To update after making changes:

```bash
# Make changes locally
# ... edit files ...

# Commit and push
git add .
git commit -m "Update description"
git push origin main

# Render automatically redeploys when it detects new commits
# Monitor in Render dashboard
```

## Rollback

If deployment breaks:
1. Go to **Deployments** tab in Render
2. Find previous working deployment
3. Click **Redeploy** to restore

## Next Steps

1. ✅ Push code to GitHub
2. ✅ Connect GitHub to Render
3. ✅ Set environment variables
4. ✅ Deploy
5. ✅ Test signup/login/encrypt/send flow
6. 🔄 Monitor logs and fix any issues
7. 📈 Upgrade to paid plan for production use
8. 🗄️ Add database for user persistence (future)

## Support

- **Render Docs**: https://render.com/docs
- **Spring Boot**: https://spring.io/projects/spring-boot
- **Resend**: https://resend.com/docs

---

**Estimated Deployment Time**: 5-10 minutes  
**Cost**: Free tier or $7+/month for Starter plan
