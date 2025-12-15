#!/bin/bash
# Quick GitHub setup and deployment guide

echo "🔐 Cipher Encryption App - Render Deployment Setup"
echo "=================================================="
echo ""

# Check if git is installed
if ! command -v git &> /dev/null; then
    echo "❌ Git is not installed. Please install Git first."
    exit 1
fi

cd "/Users/punithns/Desktop/Java Mini"

# Check if already a git repo
if [ ! -d .git ]; then
    echo "📝 Initializing git repository..."
    git init
    git config user.email "you@example.com"
    git config user.name "Your Name"
fi

echo ""
echo "✅ Next Steps:"
echo "=============="
echo ""
echo "1️⃣  Create a new GitHub repository:"
echo "   - Go to https://github.com/new"
echo "   - Name it: cipher-encryption-app"
echo "   - Do NOT initialize with README/gitignore"
echo "   - Click 'Create repository'"
echo ""
echo "2️⃣  Run these commands:"
echo "   cd '/Users/punithns/Desktop/Java Mini'"
echo "   git add ."
echo "   git commit -m 'Initial commit: Cipher Encryption App'"
echo "   git branch -M main"
echo "   git remote add origin https://github.com/YOUR_USERNAME/cipher-encryption-app.git"
echo "   git push -u origin main"
echo ""
echo "3️⃣  Deploy to Render:"
echo "   - Go to https://render.com/dashboard"
echo "   - Click 'New +' → 'Web Service'"
echo "   - Select 'Build and deploy from Git repository'"
echo "   - Connect your GitHub account"
echo "   - Select 'cipher-encryption-app' repository"
echo "   - Fill in details (use Docker environment)"
echo "   - Add environment variables:"
echo "     • RESEND_API_KEY: re_gDc5Qt7T_8XAdPpZx2dRajsS9CHMJhXiP"
echo "     • RESEND_FROM_EMAIL: onboarding@resend.dev"
echo "   - Click Deploy"
echo ""
echo "4️⃣  After deployment, update APP_BASE_URL:"
echo "   - Set to: https://YOUR-SERVICE-NAME.onrender.com"
echo "   - Redeploy the service"
echo ""
echo "📚 Full guide: See DEPLOYMENT.md"
echo ""
