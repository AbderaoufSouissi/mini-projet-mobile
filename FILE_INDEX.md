# SmartExpense - Complete File Index

## 📋 Project File Listing

### 📁 Root Directory

```
mini-projet-mobile/
├── .git/                           # Git version control
├── .gitignore                      # Git ignore rules
├── .idea/                          # Android Studio IDE settings
├── README.md                       # Main project documentation
├── SETUP.md                        # Detailed setup instructions
├── QUICKSTART.md                   # Quick start guide
├── ARCHITECTURE.md                 # Architecture & class diagrams
├── API_DOCUMENTATION.md            # Internal API reference
├── PROJECT_SUMMARY.md              # Complete project summary
├── build.gradle                    # Project-level Gradle config
├── settings.gradle                 # Gradle settings
└── gradle.properties               # Gradle properties
```

### 📁 App Module

```
app/
├── build.gradle                    # App-level Gradle config (dependencies)
├── proguard-rules.pro             # ProGuard configuration
└── src/
    └── main/
        ├── AndroidManifest.xml     # App manifest (permissions, activities)
        ├── java/com/smartexpense/app/
        │   ├── adapter/
        │   │   └── ExpenseAdapter.java              # RecyclerView adapter for expenses
        │   │
        │   ├── database/
        │   │   ├── DatabaseHelper.java              # SQLite database helper
        │   │   ├── UserDao.java                     # User data access object
        │   │   └── ExpenseDao.java                  # Expense data access object
        │   │
        │   ├── model/
        │   │   ├── User.java                        # User entity model
        │   │   ├── Expense.java                     # Expense entity model
        │   │   └── CategoryTotal.java               # Category aggregation model
        │   │
        │   ├── repository/
        │   │   ├── AuthRepository.java              # Authentication repository
        │   │   └── ExpenseRepository.java           # Expense repository
        │   │
        │   ├── utils/
        │   │   ├── SessionManager.java              # SharedPreferences session manager
        │   │   ├── ValidationUtils.java             # Input validation utilities
        │   │   └── FormatUtils.java                 # Date & currency formatting
        │   │
        │   ├── view/
        │   │   ├── SplashActivity.java              # Splash screen
        │   │   ├── LoginActivity.java               # User login screen
        │   │   ├── SignupActivity.java              # User registration screen
        │   │   ├── ResetPasswordActivity.java       # Password reset screen
        │   │   ├── DashboardActivity.java           # Main dashboard
        │   │   ├── AddExpenseActivity.java          # Add/Edit expense screen
        │   │   ├── ExpenseListActivity.java         # Expense list with search/filter
        │   │   └── StatisticsActivity.java          # Statistics & charts screen
        │   │
        │   └── viewmodel/
        │       ├── AuthViewModel.java               # Authentication ViewModel
        │       └── ExpenseViewModel.java            # Expense ViewModel
        │
        └── res/
            ├── layout/
            │   ├── activity_splash.xml              # Splash screen layout
            │   ├── activity_login.xml               # Login screen layout
            │   ├── activity_signup.xml              # Signup screen layout
            │   ├── activity_reset_password.xml      # Password reset layout
            │   ├── activity_dashboard.xml           # Dashboard layout
            │   ├── activity_add_expense.xml         # Add/Edit expense layout
            │   ├── activity_expense_list.xml        # Expense list layout
            │   ├── activity_statistics.xml          # Statistics layout
            │   └── item_expense.xml                 # RecyclerView item layout
            │
            ├── values/
            │   ├── strings.xml                      # All string resources
            │   ├── colors.xml                       # Color palette
            │   └── themes.xml                       # Material Design theme
            │
            ├── mipmap/                              # App icons (launcher icons)
            │   ├── ic_launcher.png
            │   └── ic_launcher_round.png
            │
            └── drawable/                            # Drawable resources
```

---

## 📊 File Statistics

### By Category

| Category | Count | Description |
|----------|-------|-------------|
| **Java Classes** | 24 | Source code files |
| **XML Layouts** | 9 | UI layout files |
| **XML Resources** | 3 | Strings, colors, themes |
| **Gradle Files** | 4 | Build configuration |
| **Documentation** | 6 | Markdown files |
| **Manifests** | 1 | AndroidManifest.xml |
| **Total** | **47+** | Core project files |

### By Package

| Package | Files | Purpose |
|---------|-------|---------|
| `adapter` | 1 | RecyclerView adapters |
| `database` | 3 | SQLite & DAOs |
| `model` | 3 | Data models |
| `repository` | 2 | Data repositories |
| `utils` | 3 | Utility classes |
| `view` | 8 | Activities (UI) |
| `viewmodel` | 2 | ViewModels |

---

## 🎯 Key Files Reference

### Must-Read Files (For Understanding)

1. **README.md** - Start here! Complete project overview
2. **QUICKSTART.md** - Get up and running in 5 minutes
3. **ARCHITECTURE.md** - Understand the code structure
4. **API_DOCUMENTATION.md** - Reference for all classes and methods

### Must-Edit Files (For Customization)

1. **colors.xml** - Change app color scheme
2. **strings.xml** - Modify text and labels
3. **themes.xml** - Adjust Material Design theme
4. **DatabaseHelper.java** - Modify database schema

### Core Logic Files

1. **DatabaseHelper.java** - Database creation and migration
2. **UserDao.java** - User database operations
3. **ExpenseDao.java** - Expense database operations
4. **AuthViewModel.java** - Authentication business logic
5. **ExpenseViewModel.java** - Expense business logic

### UI Files

1. **DashboardActivity.java** - Main screen logic
2. **activity_dashboard.xml** - Main screen layout
3. **ExpenseAdapter.java** - List item rendering
4. **item_expense.xml** - List item layout

---

## 📝 File Descriptions

### Java Source Files (24 files)

#### Adapter Package (1 file)
- **ExpenseAdapter.java** - RecyclerView adapter for displaying expense list with edit/delete actions

#### Database Package (3 files)
- **DatabaseHelper.java** - SQLite database singleton, creates tables, handles upgrades
- **UserDao.java** - User CRUD operations, login validation, password reset
- **ExpenseDao.java** - Expense CRUD, search, filter, totals calculation

#### Model Package (3 files)
- **User.java** - User entity (id, username, email, password, security Q&A)
- **Expense.java** - Expense entity (id, userId, amount, category, description, date)
- **CategoryTotal.java** - Aggregation model for category spending totals

#### Repository Package (2 files)
- **AuthRepository.java** - Authentication business logic layer
- **ExpenseRepository.java** - Expense business logic layer

#### Utils Package (3 files)
- **SessionManager.java** - Manages user session with SharedPreferences
- **ValidationUtils.java** - Input validation (email, password, amount, etc.)
- **FormatUtils.java** - Formatting helpers (currency, dates)

#### View Package (8 files)
- **SplashActivity.java** - Entry point, checks login status, navigates accordingly
- **LoginActivity.java** - User login with email/password
- **SignupActivity.java** - New user registration with security question
- **ResetPasswordActivity.java** - Password reset via security question
- **DashboardActivity.java** - Main screen with stats and pie chart
- **AddExpenseActivity.java** - Add new or edit existing expense
- **ExpenseListActivity.java** - Display all expenses with search and filter
- **StatisticsActivity.java** - Detailed charts (pie chart and bar chart)

#### ViewModel Package (2 files)
- **AuthViewModel.java** - Manages authentication state, LiveData for login/register
- **ExpenseViewModel.java** - Manages expense data, LiveData for expenses and totals

### XML Layout Files (9 files)

- **activity_splash.xml** - Splash screen with app logo and loading indicator
- **activity_login.xml** - Login form with email/password fields
- **activity_signup.xml** - Registration form with all fields + security question
- **activity_reset_password.xml** - Password reset form with email and security answer
- **activity_dashboard.xml** - Dashboard with stats cards and pie chart
- **activity_add_expense.xml** - Expense form with amount, category, date, description
- **activity_expense_list.xml** - RecyclerView with search bar and filter dropdown
- **activity_statistics.xml** - Two charts (pie and bar)
- **item_expense.xml** - Individual expense card for RecyclerView

### XML Resource Files (3 files)

- **strings.xml** - All app text (labels, messages, errors, hints)
- **colors.xml** - Color palette (primary, accent, category colors)
- **themes.xml** - Material Design theme with custom styles

### Configuration Files (5 files)

- **AndroidManifest.xml** - App configuration, permissions, activity declarations
- **build.gradle** (project) - Project-level build configuration
- **build.gradle** (app) - App dependencies and SDK versions
- **settings.gradle** - Gradle project settings
- **gradle.properties** - Gradle JVM arguments and properties

### Documentation Files (6 files)

- **README.md** - Main project documentation (features, setup, usage)
- **SETUP.md** - Detailed installation and setup guide
- **QUICKSTART.md** - Quick start tutorial and first use guide
- **ARCHITECTURE.md** - Class diagrams, package structure, design patterns
- **API_DOCUMENTATION.md** - Internal API reference for all classes
- **PROJECT_SUMMARY.md** - Complete project overview and statistics

---

## 🔍 Finding Specific Code

### Authentication Related
```
/view/SplashActivity.java           - Entry point
/view/LoginActivity.java            - Login UI
/view/SignupActivity.java           - Registration UI
/view/ResetPasswordActivity.java    - Password reset UI
/viewmodel/AuthViewModel.java       - Auth business logic
/repository/AuthRepository.java     - Auth data layer
/database/UserDao.java              - User database operations
/utils/SessionManager.java          - Session management
```

### Expense Management
```
/view/DashboardActivity.java        - Main screen
/view/AddExpenseActivity.java       - Add/Edit expense
/view/ExpenseListActivity.java      - List all expenses
/view/StatisticsActivity.java       - Charts and analytics
/viewmodel/ExpenseViewModel.java    - Expense business logic
/repository/ExpenseRepository.java  - Expense data layer
/database/ExpenseDao.java           - Expense database operations
/adapter/ExpenseAdapter.java        - List display adapter
```

### Database & Data
```
/database/DatabaseHelper.java       - Database setup
/database/UserDao.java              - User operations
/database/ExpenseDao.java           - Expense operations
/model/User.java                    - User model
/model/Expense.java                 - Expense model
/model/CategoryTotal.java           - Category aggregation
```

### UI & Resources
```
/res/layout/*.xml                   - All screen layouts
/res/values/strings.xml             - Text content
/res/values/colors.xml              - Color scheme
/res/values/themes.xml              - App styling
```

---

## 📦 Dependencies Location

All dependencies are defined in:
```
app/build.gradle
```

Key dependencies:
- AndroidX libraries
- Material Components
- Lifecycle components (ViewModel, LiveData)
- RecyclerView
- MPAndroidChart

---

## 🎨 Customization Guide

| What to Change | File to Edit |
|----------------|--------------|
| App name | strings.xml |
| Color scheme | colors.xml |
| Theme | themes.xml |
| Database schema | DatabaseHelper.java |
| Categories | strings.xml + Update logic |
| Security questions | strings.xml |
| Date format | FormatUtils.java |
| Currency format | FormatUtils.java |
| Validation rules | ValidationUtils.java |

---

## 🚀 Build Process Files

```
build.gradle                    # Project build config
app/build.gradle               # App build config
settings.gradle                # Module settings
gradle.properties              # Gradle properties
app/proguard-rules.pro        # Code shrinking rules
```

---

## 📱 Runtime Generated Files

These files are generated at runtime and should NOT be edited:

```
/build/                        # Build output (ignored in git)
/.gradle/                      # Gradle cache (ignored in git)
/app/build/                    # App build output (ignored in git)
*.iml                          # IntelliJ module files
.idea/                         # IDE settings
local.properties               # Local SDK path
```

---

## ✅ Verification

To verify all files are present, check for:

✅ 8 Activity classes in `/view/`
✅ 2 ViewModel classes in `/viewmodel/`
✅ 3 DAO classes in `/database/`
✅ 9 Layout XML files in `/res/layout/`
✅ 3 Value XML files in `/res/values/`
✅ 6 Documentation MD files in root

---

## 📖 Reading Order for New Developers

1. **README.md** - Project overview
2. **QUICKSTART.md** - Get it running
3. **ARCHITECTURE.md** - Understand structure
4. **DatabaseHelper.java** - See database schema
5. **Model classes** - Understand data structure
6. **DAO classes** - See database operations
7. **ViewModels** - See business logic
8. **Activities** - See UI implementation
9. **API_DOCUMENTATION.md** - Detailed API reference

---

**Total Project Size:** ~4,500+ lines of code across 50+ files

**Last Updated:** November 2024
**Version:** 1.0

