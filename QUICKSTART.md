# 🚀 SmartExpense - Quick Start Guide

## ⚡ 5-Minute Setup

### Step 1: Prerequisites ✅
- [ ] Android Studio installed (latest version)
- [ ] JDK 8+ installed
- [ ] Android SDK API 24+ installed

### Step 2: Open Project 📂
1. Launch **Android Studio**
2. Click **Open an Existing Project**
3. Navigate to `mini-projet-mobile` folder
4. Click **OK**

### Step 3: Sync Dependencies ⚙️
1. Wait for Gradle sync to start automatically
2. If prompted, click **Sync Now**
3. Wait for completion (1-3 minutes)

### Step 4: Run the App ▶️
1. Click the **Run** button (green play icon) or press **Shift+F10**
2. Select your device/emulator
3. Click **OK**
4. Wait for app to install and launch

---

## 🎮 First Use Tutorial

### Create Your Account
1. App launches to **Splash Screen** (2 seconds)
2. Redirected to **Login Screen**
3. Click **"Don't have an account? Sign up"**
4. Fill in the registration form:
   ```
   Username: Your Name
   Email: yourname@example.com
   Password: yourpassword (min 6 chars)
   Confirm Password: yourpassword
   Security Question: Select from dropdown
   Security Answer: Your answer
   ```
5. Click **Sign Up**
6. Success! Auto-redirected to login

### Login
1. Enter your **email** and **password**
2. Click **Login**
3. Welcomed to the **Dashboard**! 🎉

### Add Your First Expense
1. From Dashboard, click **Add Expense** button
2. Fill in the form:
   ```
   Amount: 50
   Category: Food (select from dropdown)
   Date: Click to select (defaults to today)
   Description: Lunch at restaurant
   ```
3. Click **Save**
4. Expense added! 💰

### View Your Expenses
1. From Dashboard, click **View All** button
2. See your expense listed
3. Click on the expense card to reveal **Edit** and **Delete** options
4. Try searching or filtering by category

### Check Statistics
1. From Dashboard, click **Statistics** button
2. View:
   - **Pie Chart** showing category distribution
   - **Bar Chart** showing daily spending trend

---

## 📱 Navigation Map

```
Splash Screen
    ↓
Login Screen
    ├─→ Sign Up → (back to Login)
    ├─→ Forgot Password → Reset Password
    └─→ Dashboard (after login)
            ├─→ Add Expense
            ├─→ View All Expenses
            │      ├─→ Edit Expense
            │      └─→ Delete Expense
            ├─→ Statistics
            └─→ Logout → Login Screen
```

---

## 🎯 Quick Features Guide

### Dashboard Screen
- **Top Right Icon** - Logout
- **Today/Week/Month Cards** - Auto-calculated totals
- **Pie Chart** - Visual category breakdown
- **Add Expense Button** - Create new expense
- **View All Button** - See all expenses
- **Statistics Button** - Detailed charts

### Expense List Screen
- **Search Bar** - Search by description
- **Filter Dropdown** - Filter by category
- **FAB (+) Button** - Quick add expense
- **Expense Cards** - Click to expand/collapse actions
- **Edit Button** - Modify expense
- **Delete Button** - Remove expense (with confirmation)

### Add/Edit Expense Screen
- **Amount Field** - Enter number (validates positive values)
- **Category Dropdown** - 5 categories (Food, Transport, Shopping, Bills, Other)
- **Date Picker** - Click to select date
- **Description Field** - Text description
- **Save Button** - Validates and saves
- **Cancel Button** - Discard changes

### Statistics Screen
- **Category Pie Chart** - Distribution by category with colors
- **Daily Bar Chart** - Last 7 days spending trend

---

## 🔑 Test Account

For quick testing:
```
Email: test@smartexpense.com
Password: test123
Security Question: What is your pet's name?
Security Answer: fluffy
```

---

## 🎨 Categories & Colors

| Category | Color | Hex Code |
|----------|-------|----------|
| Food | Pink | #FF6384 |
| Transport | Blue | #36A2EB |
| Shopping | Yellow | #FFCE56 |
| Bills | Teal | #4BC0C0 |
| Other | Purple | #9966FF |

---

## ⌨️ Keyboard Shortcuts

| Action | Windows/Linux | macOS |
|--------|---------------|-------|
| Run App | Shift+F10 | Ctrl+R |
| Build Project | Ctrl+F9 | Cmd+F9 |
| Open File | Ctrl+Shift+N | Cmd+Shift+O |
| Find in Files | Ctrl+Shift+F | Cmd+Shift+F |
| Sync Gradle | - | - |

---

## 🐛 Common Issues & Solutions

### Issue: App won't compile
**Solution:**
1. File → Invalidate Caches / Restart
2. Clean Project (Build → Clean Project)
3. Rebuild Project (Build → Rebuild Project)

### Issue: Gradle sync failed
**Solution:**
1. Check internet connection
2. Tools → SDK Manager → Install missing packages
3. Retry sync

### Issue: App crashes on launch
**Solution:**
1. Check Logcat for errors (View → Tool Windows → Logcat)
2. Clear app data on device/emulator
3. Uninstall and reinstall app

### Issue: Charts not displaying
**Solution:**
1. Add some expenses first
2. Ensure expenses have valid amounts
3. Restart the app

### Issue: Can't login after signup
**Solution:**
1. Double-check email and password
2. Passwords are case-sensitive
3. Try password reset if needed

---

## 📊 Sample Data for Testing

### Test Expenses
```
Expense 1:
- Amount: 50.00
- Category: Food
- Description: Groceries
- Date: Today

Expense 2:
- Amount: 20.00
- Category: Transport
- Description: Uber ride
- Date: Today

Expense 3:
- Amount: 100.00
- Category: Shopping
- Description: New shoes
- Date: Yesterday

Expense 4:
- Amount: 75.00
- Category: Bills
- Description: Electricity bill
- Date: 2 days ago
```

---

## 📖 Additional Resources

- **Full Documentation:** README.md
- **Setup Guide:** SETUP.md
- **Architecture:** ARCHITECTURE.md
- **API Reference:** API_DOCUMENTATION.md
- **Project Summary:** PROJECT_SUMMARY.md

---

## ✅ Verification Checklist

After setup, verify everything works:

- [ ] App launches successfully
- [ ] Can create new account
- [ ] Can login with created account
- [ ] Can add new expense
- [ ] Expense appears in list
- [ ] Can search expenses
- [ ] Can filter by category
- [ ] Can edit expense
- [ ] Can delete expense
- [ ] Dashboard shows correct totals
- [ ] Pie chart displays properly
- [ ] Bar chart displays properly
- [ ] Can view statistics
- [ ] Can logout
- [ ] Session persists (stays logged in after restart)
- [ ] Can reset password

---

## 🎓 Learning Path

**Beginner:**
1. Explore the UI and features
2. Add/edit/delete expenses
3. View different screens

**Intermediate:**
1. Review Java source code
2. Understand MVVM pattern
3. Explore database structure

**Advanced:**
1. Modify UI layouts
2. Add new features
3. Customize categories
4. Implement enhancements

---

## 💡 Pro Tips

1. **Use Real Data:** Add your actual expenses to see the app's value
2. **Explore Search:** Try partial matches in search
3. **Check Statistics:** Best with 5+ expenses across different days
4. **Security:** Remember your security question - you'll need it for password reset
5. **Multi-User:** Each email creates a separate account with isolated data

---

## 🎯 Next Steps

After getting familiar:

1. ⭐ Read the architecture documentation
2. 🔍 Explore the codebase
3. 🎨 Customize the theme colors
4. ➕ Add new features (see Future Enhancements in README.md)
5. 📱 Test on different devices
6. 🚀 Consider cloud sync integration

---

## 🆘 Need Help?

1. **Check Documentation** - README.md has comprehensive info
2. **Review Setup Guide** - SETUP.md for detailed installation
3. **Check Logcat** - For runtime errors
4. **Stack Overflow** - Search for Android-specific issues
5. **Create Issue** - Report bugs or ask questions

---

## 🎉 You're All Set!

Enjoy using **SmartExpense** - your personal finance tracker! 💰

**Happy Expense Tracking!** 📊✨

---

*Last Updated: November 2024*
*Version: 1.0*

