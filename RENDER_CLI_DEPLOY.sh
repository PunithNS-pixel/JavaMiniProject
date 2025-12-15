#!/bin/bash

# Render CLI Deployment Script
# This script creates a Render service from your GitHub repo

echo "🚀 Deploying to Render using Dashboard..."
echo ""
echo "Since Render CLI doesn't have a 'create service' command,"
echo "you need to complete the setup in the Render Dashboard:"
echo ""
echo "1. Go to: https://dashboard.render.com"
echo "2. Click: New + → Web Service"
echo "3. Click: Build and deploy from a Git repository"
echo "4. Select: PunithNS-pixel/JavaMiniProject"
echo ""
echo "Service Configuration:"
echo "  • Name: cipher-encryption-app"
echo "  • Environment: Docker"
echo "  • Plan: Free"
echo "  • Branch: main"
echo ""
echo "Environment Variables (add in Environment tab):"
echo "  • RESEND_API_KEY=re_gDc5Qt7T_8XAdPpZx2dRajsS9CHMJhXiP"
echo "  • RESEND_FROM_EMAIL=onboarding@resend.dev"
echo "  • APP_BASE_URL=https://cipher-encryption-app.onrender.com"
echo ""
echo "Then click 'Create Web Service' and wait ~5 minutes for deployment."
echo ""
