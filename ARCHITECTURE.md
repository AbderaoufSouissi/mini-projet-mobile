# SmartExpense - Class Diagram

## Architecture Overview

This document provides class diagrams and architecture documentation for the SmartExpense application.

## Package Structure

```
com.smartexpense.app/
│
├── model/                  # Data Models
│   ├── User
│   ├── Expense
│   └── CategoryTotal
│
├── view/                   # Activities (UI Layer)
│   ├── SplashActivity
│   ├── LoginActivity
│   ├── SignupActivity
│   ├── ResetPasswordActivity
│   ├── DashboardActivity
│   ├── AddExpenseActivity
│   ├── ExpenseListActivity
│   └── StatisticsActivity
│
├── viewmodel/              # ViewModels
│   ├── AuthViewModel
│   └── ExpenseViewModel
│
├── repository/             # Data Access Layer
│   ├── AuthRepository
│   └── ExpenseRepository
│
├── database/               # Database Layer
│   ├── DatabaseHelper
│   ├── UserDao
│   └── ExpenseDao
│
├── adapter/                # RecyclerView Adapters
│   └── ExpenseAdapter
│
└── utils/                  # Utility Classes
    ├── SessionManager
    ├── ValidationUtils
    └── FormatUtils
```

## Class Diagrams

### 1. Model Layer

```
┌─────────────────────────┐
│        User             │
├─────────────────────────┤
│ - id: int               │
│ - username: String      │
│ - email: String         │
│ - password: String      │
│ - securityQuestion: String │
│ - securityAnswer: String  │
├─────────────────────────┤
│ + User()                │
│ + User(params...)       │
│ + getId(): int          │
│ + setId(int): void      │
│ + getters/setters...    │
└─────────────────────────┘

┌─────────────────────────┐
│       Expense           │
├─────────────────────────┤
│ - id: int               │
│ - userId: int           │
│ - amount: double        │
│ - category: String      │
│ - description: String   │
│ - date: long            │
├─────────────────────────┤
│ + Expense()             │
│ + Expense(params...)    │
│ + getters/setters...    │
└─────────────────────────┘

┌─────────────────────────┐
│    CategoryTotal        │
├─────────────────────────┤
│ - category: String      │
│ - total: double         │
├─────────────────────────┤
│ + CategoryTotal(...)    │
│ + getters/setters...    │
└─────────────────────────┘
```

### 2. Database Layer

```
┌────────────────────────────────┐
│      DatabaseHelper            │
├────────────────────────────────┤
│ - DATABASE_NAME: String        │
│ - DATABASE_VERSION: int        │
│ - instance: DatabaseHelper     │
├────────────────────────────────┤
│ + getInstance(Context): DatabaseHelper │
│ + onCreate(SQLiteDatabase): void       │
│ + onUpgrade(SQLiteDatabase, int, int): void │
│ + onConfigure(SQLiteDatabase): void    │
└────────────────────────────────┘
              △
              │
    ┌─────────┴──────────┐
    │                    │
┌───────────────┐   ┌──────────────┐
│   UserDao     │   │  ExpenseDao  │
├───────────────┤   ├──────────────┤
│ - dbHelper    │   │ - dbHelper   │
├───────────────┤   ├──────────────┤
│ + createUser()│   │ + createExpense() │
│ + getUserById()│  │ + getAllExpenses() │
│ + getUserByEmail()│ │ + updateExpense() │
│ + validateLogin()│  │ + deleteExpense() │
│ + updatePassword()│ │ + searchExpenses() │
│ + isEmailExists()│  │ + getCategoryTotals() │
└───────────────┘   │ + getTodayTotal() │
                    │ + getWeekTotal()  │
                    │ + getMonthTotal() │
                    └──────────────┘
```

### 3. Repository Layer

```
┌──────────────────────────┐
│    AuthRepository        │
├──────────────────────────┤
│ - userDao: UserDao       │
├──────────────────────────┤
│ + AuthRepository(Context)│
│ + registerUser(User): long │
│ + loginUser(String, String): User │
│ + getUserByEmail(String): User │
│ + validateSecurityAnswer(...): boolean │
│ + resetPassword(...): boolean │
└──────────────────────────┘

┌──────────────────────────┐
│   ExpenseRepository      │
├──────────────────────────┤
│ - expenseDao: ExpenseDao │
├──────────────────────────┤
│ + ExpenseRepository(Context) │
│ + addExpense(Expense): long │
│ + getAllExpenses(int): List<Expense> │
│ + updateExpense(Expense): boolean │
│ + deleteExpense(int): boolean │
│ + searchExpenses(...): List<Expense> │
│ + getCategoryTotals(int): List<CategoryTotal> │
│ + getTodayTotal(int): double │
│ + getWeekTotal(int): double │
│ + getMonthTotal(int): double │
└──────────────────────────┘
```

### 4. ViewModel Layer

```
┌────────────────────────────────┐
│       AuthViewModel            │
│   extends AndroidViewModel     │
├────────────────────────────────┤
│ - authRepository: AuthRepository │
│ - executorService: ExecutorService │
│ - loginSuccess: MutableLiveData<Boolean> │
│ - loginError: MutableLiveData<String> │
│ - registerSuccess: MutableLiveData<Boolean> │
│ - loggedInUser: MutableLiveData<User> │
├────────────────────────────────┤
│ + AuthViewModel(Application)   │
│ + login(String, String): void  │
│ + register(User): void         │
│ + getLoginSuccess(): LiveData<Boolean> │
│ + getLoggedInUser(): LiveData<User> │
└────────────────────────────────┘

┌────────────────────────────────┐
│      ExpenseViewModel          │
│   extends AndroidViewModel     │
├────────────────────────────────┤
│ - expenseRepository: ExpenseRepository │
│ - executorService: ExecutorService │
│ - expensesList: MutableLiveData<List<Expense>> │
│ - todayTotal: MutableLiveData<Double> │
│ - weekTotal: MutableLiveData<Double> │
│ - monthTotal: MutableLiveData<Double> │
│ - categoryTotals: MutableLiveData<List<CategoryTotal>> │
├────────────────────────────────┤
│ + ExpenseViewModel(Application) │
│ + loadExpenses(int): void      │
│ + loadTotals(int): void        │
│ + addExpense(Expense): void    │
│ + updateExpense(Expense): void │
│ + deleteExpense(int): void     │
│ + searchExpenses(int, String): void │
│ + getExpensesList(): LiveData<List<Expense>> │
└────────────────────────────────┘
```

### 5. Utility Classes

```
┌──────────────────────────┐
│    SessionManager        │
├──────────────────────────┤
│ - sharedPreferences      │
│ - editor                 │
├──────────────────────────┤
│ + SessionManager(Context)│
│ + createLoginSession(...): void │
│ + isLoggedIn(): boolean  │
│ + getUserId(): int       │
│ + getUserEmail(): String │
│ + logout(): void         │
└──────────────────────────┘

┌──────────────────────────┐
│   ValidationUtils        │
├──────────────────────────┤
│ + isValidEmail(String): boolean │
│ + isValidPassword(String): boolean │
│ + doPasswordsMatch(...): boolean │
│ + isValidAmount(String): boolean │
│ + isNotEmpty(String): boolean │
└──────────────────────────┘

┌──────────────────────────┐
│     FormatUtils          │
├──────────────────────────┤
│ + formatCurrency(double): String │
│ + formatDate(long): String │
│ + formatDateTime(long): String │
│ + formatShortDate(long): String │
└──────────────────────────┘
```

## Data Flow Diagram

```
┌──────────┐
│   View   │  (Activity)
│ (UI Layer)│
└─────┬────┘
      │
      ↓ (observes LiveData)
┌─────────────┐
│  ViewModel  │  (Business Logic)
└──────┬──────┘
       │
       ↓ (calls methods)
┌──────────────┐
│  Repository  │  (Data Access)
└──────┬───────┘
       │
       ↓ (CRUD operations)
┌──────────┐
│   DAO    │  (Database Access Object)
└─────┬────┘
      │
      ↓ (SQL queries)
┌────────────┐
│  SQLite DB │  (smartexpense.db)
└────────────┘
```

## Authentication Flow

```
┌─────────────┐
│ SplashActivity │
└──────┬──────┘
       │
       ↓ (checks session)
┌──────────────┐
│SessionManager│
└──────┬───────┘
       │
   ┌───┴───┐
   │       │
   ↓       ↓
Logged    Not
  In    Logged In
   │       │
   ↓       ↓
Dashboard Login
Activity  Activity
           │
       ┌───┴───┐
       │       │
       ↓       ↓
   Sign Up  Reset
   Activity Password
```

## Expense Management Flow

```
┌──────────────┐
│  Dashboard   │
└──────┬───────┘
       │
   ┌───┴────────────┐
   │                │
   ↓                ↓
Add Expense    View All
Activity      Expenses
   │              │
   │              ↓
   │         ┌────────┐
   │         │ Search │
   │         │ Filter │
   │         └───┬────┘
   │             │
   │         ┌───┴────┐
   │         │        │
   │         ↓        ↓
   │       Edit    Delete
   │         │        │
   └─────────┴────────┘
             │
             ↓
        Save to DB
```

## Technologies & Patterns

### Design Patterns Used

1. **Singleton Pattern**
   - `DatabaseHelper.getInstance()`
   
2. **Repository Pattern**
   - `AuthRepository`
   - `ExpenseRepository`
   
3. **Observer Pattern**
   - LiveData observers in Activities
   
4. **View Holder Pattern**
   - `ExpenseAdapter.ExpenseViewHolder`

### Architecture Components

- **LiveData:** Observable data holder
- **ViewModel:** Survives configuration changes
- **Repository:** Single source of truth
- **DAO:** Database access abstraction

---

**Created with Android MVVM Architecture Best Practices**

