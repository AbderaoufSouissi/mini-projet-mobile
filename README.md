"# SmartExpense - Android Expense Tracker

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Java](https://img.shields.io/badge/Language-Java-orange.svg)
![SQLite](https://img.shields.io/badge/Database-SQLite-blue.svg)
![MVVM](https://img.shields.io/badge/Architecture-MVVM-purple.svg)

A comprehensive Android mobile application for tracking personal expenses with local authentication and SQLite database storage.

## 📱 Features

### Authentication (Local SQLite)
- ✅ User Registration with email validation
- ✅ Local Login with email/password
- ✅ Password Reset via Security Questions
- ✅ Session Management with SharedPreferences
- ✅ Multi-user Support

### Expense Management
- ✅ Add, Edit, Delete Expenses
- ✅ Category-based Organization (Food, Transport, Shopping, Bills, Other)
- ✅ Search Expenses by Description
- ✅ Filter by Category
- ✅ Date-based Filtering
- ✅ Multi-user Data Isolation

### Dashboard & Analytics
- ✅ Today's, Weekly, and Monthly Spending Totals
- ✅ Category Distribution Pie Chart
- ✅ Daily Spending Bar Chart
- ✅ Visual Category Indicators
- ✅ Real-time Statistics

### Design
- ✅ Material Design Components
- ✅ Clean and Modern UI
- ✅ Responsive Layouts
- ✅ Custom Color Scheme (Primary: #4CAF50)
- ✅ Intuitive Navigation

## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture pattern:

```
app/
├── model/              # Data models (User, Expense, CategoryTotal)
├── view/               # Activities (UI Layer)
├── viewmodel/          # ViewModels (Business Logic)
├── repository/         # Data repositories
├── database/           # SQLite Database & DAOs
├── adapter/            # RecyclerView Adapters
└── utils/              # Utility classes (Session, Validation, Formatting)
```

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT,
    email TEXT UNIQUE,
    password TEXT,
    security_question TEXT,
    security_answer TEXT
)
```

### Expenses Table
```sql
CREATE TABLE expenses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    amount REAL,
    category TEXT,
    description TEXT,
    date INTEGER,
    FOREIGN KEY(user_id) REFERENCES users(id)
)
```

## 🛠️ Tech Stack

- **Language:** Java
- **IDE:** Android Studio (latest version recommended)
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 34 (Android 14)
- **Database:** SQLite
- **Charts:** MPAndroidChart v3.1.0
- **UI:** Material Components

## 📦 Dependencies

```gradle
// AndroidX Libraries
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.11.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'

// Lifecycle Components
implementation 'androidx.lifecycle:lifecycle-viewmodel:2.7.0'
implementation 'androidx.lifecycle:lifecycle-livedata:2.7.0'
implementation 'androidx.lifecycle:lifecycle-runtime:2.7.0'
implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'

// RecyclerView
implementation 'androidx.recyclerview:recyclerview:1.3.2'
implementation 'androidx.cardview:cardview:1.0.0'

// Charts
implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
```

## 🚀 Installation & Setup

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 8 or higher
- Android SDK with API Level 24+

### Steps

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd mini-projet-mobile
   ```

2. **Open in Android Studio:**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the project folder and open it

3. **Sync Gradle:**
   - Android Studio will automatically prompt to sync Gradle
   - Click "Sync Now" or go to File > Sync Project with Gradle Files

4. **Build the Project:**
   - Build > Make Project (Ctrl+F9)
   - Ensure there are no build errors

5. **Run on Device/Emulator:**
   - Connect an Android device or start an emulator
   - Click Run (Shift+F10) or the green play button
   - Select your device and click OK

## 📱 App Screens

### Authentication Flow
1. **Splash Screen** - Auto-navigation based on login state
2. **Login Screen** - Email/password authentication
3. **Signup Screen** - New user registration with security questions
4. **Reset Password** - Password recovery via security questions

### Main App Flow
1. **Dashboard** - Overview of spending with quick stats
2. **Add/Edit Expense** - Form to create or modify expenses
3. **Expense List** - All expenses with search and filter
4. **Statistics** - Detailed charts and analytics

## 🎨 Color Scheme

| Color | Hex Code | Usage |
|-------|----------|-------|
| Primary | #4CAF50 | Main theme color, buttons |
| Primary Dark | #388E3C | Status bar, toolbar |
| Accent | #FF9800 | Highlights, FAB |
| Food | #FF6384 | Category indicator |
| Transport | #36A2EB | Category indicator |
| Shopping | #FFCE56 | Category indicator |
| Bills | #4BC0C0 | Category indicator |
| Other | #9966FF | Category indicator |

## 📖 Usage Guide

### First Time Setup
1. Launch the app
2. Click "Create Account" on the login screen
3. Fill in all required fields:
   - Username
   - Email
   - Password (minimum 6 characters)
   - Confirm Password
   - Select a Security Question
   - Provide Security Answer
4. Click "Sign Up"

### Adding an Expense
1. From Dashboard, click "Add Expense" or the + FAB button
2. Enter the amount
3. Select a category from the dropdown
4. Pick a date (defaults to today)
5. Add a description
6. Click "Save"

### Viewing & Managing Expenses
1. Navigate to "View All" from Dashboard
2. Use the search bar to find specific expenses
3. Filter by category using the dropdown
4. Click on an expense card to reveal Edit/Delete options
5. Click Edit to modify or Delete to remove

### Viewing Statistics
1. Click "Statistics" from the Dashboard
2. View the pie chart for category distribution
3. Check the bar chart for daily spending trends

### Password Reset
1. Click "Forgot Password" on login screen
2. Enter your email
3. Click "Next"
4. Answer your security question
5. Enter a new password
6. Click "Reset Password"

## 🔒 Security Features

- **Local Authentication:** All user data stored securely in SQLite
- **Password Protection:** Passwords stored as plain text (Note: For production, implement BCrypt hashing)
- **Security Questions:** Custom recovery mechanism without email dependency
- **Session Management:** Secure session handling via SharedPreferences
- **Data Isolation:** Each user can only access their own expenses

## 🧪 Testing

### Manual Testing Checklist

**Authentication:**
- [ ] Register new user
- [ ] Login with valid credentials
- [ ] Login with invalid credentials (should fail)
- [ ] Duplicate email registration (should fail)
- [ ] Password reset flow
- [ ] Session persistence
- [ ] Logout functionality

**Expense Management:**
- [ ] Add new expense
- [ ] Edit existing expense
- [ ] Delete expense
- [ ] Search expenses
- [ ] Filter by category
- [ ] View all expenses
- [ ] Empty state display

**Dashboard & Statistics:**
- [ ] Today's total calculation
- [ ] Week total calculation
- [ ] Month total calculation
- [ ] Pie chart rendering
- [ ] Bar chart rendering
- [ ] Category color coding

## 🐛 Known Issues & Limitations

1. **Password Storage:** Passwords are currently stored as plain text. For production, implement secure hashing (BCrypt/Argon2).
2. **No Cloud Backup:** All data is stored locally. Device loss means data loss.
3. **No Export Feature:** Cannot export expenses to CSV/PDF.
4. **Single Currency:** Only supports USD ($) format.
5. **No Recurring Expenses:** Manual entry required for each expense.

## 🔮 Future Enhancements

- [ ] Password hashing for security
- [ ] Biometric authentication (fingerprint/face)
- [ ] Cloud sync with Firebase
- [ ] Export to CSV/PDF
- [ ] Budget setting and alerts
- [ ] Recurring expense support
- [ ] Multiple currency support
- [ ] Dark mode theme
- [ ] Expense receipt photo attachment
- [ ] Advanced analytics and insights

## 📄 License

This project is created for educational purposes.

## 👨‍💻 Developer

Created as part of Android mobile development coursework.

## 🤝 Contributing

This is an educational project. Suggestions and improvements are welcome!

## 📞 Support

For issues or questions, please create an issue in the repository.

---

**Built with ❤️ using Android Studio**" 

