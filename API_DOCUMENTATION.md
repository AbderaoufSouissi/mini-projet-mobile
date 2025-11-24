# API Documentation - SmartExpense

This document describes the internal API structure of the SmartExpense application.

## Database API

### UserDao

#### `createUser(User user): long`
Creates a new user in the database.

**Parameters:**
- `user`: User object containing username, email, password, security question, and answer

**Returns:**
- `long`: The row ID of the newly inserted user, or -1 if an error occurred

**Example:**
```java
User user = new User("John Doe", "john@example.com", "password123", 
                     "What is your pet's name?", "Fluffy");
long userId = userDao.createUser(user);
```

---

#### `getUserByEmail(String email): User`
Retrieves a user by email address.

**Parameters:**
- `email`: The email address to search for

**Returns:**
- `User`: User object if found, null otherwise

**Example:**
```java
User user = userDao.getUserByEmail("john@example.com");
```

---

#### `getUserById(int userId): User`
Retrieves a user by ID.

**Parameters:**
- `userId`: The user's ID

**Returns:**
- `User`: User object if found, null otherwise

---

#### `isEmailExists(String email): boolean`
Checks if an email already exists in the database.

**Parameters:**
- `email`: The email to check

**Returns:**
- `boolean`: true if email exists, false otherwise

---

#### `validateLogin(String email, String password): User`
Validates user login credentials.

**Parameters:**
- `email`: User's email
- `password`: User's password

**Returns:**
- `User`: User object if credentials are valid, null otherwise

---

#### `validateSecurityAnswer(String email, String answer): boolean`
Validates the security answer for password reset.

**Parameters:**
- `email`: User's email
- `answer`: Security answer to validate

**Returns:**
- `boolean`: true if answer is correct, false otherwise

---

#### `updatePassword(String email, String newPassword): boolean`
Updates a user's password.

**Parameters:**
- `email`: User's email
- `newPassword`: New password to set

**Returns:**
- `boolean`: true if update was successful, false otherwise

---

### ExpenseDao

#### `createExpense(Expense expense): long`
Creates a new expense record.

**Parameters:**
- `expense`: Expense object containing amount, category, description, date, and user ID

**Returns:**
- `long`: The row ID of the newly inserted expense, or -1 if an error occurred

**Example:**
```java
Expense expense = new Expense(userId, 50.00, "Food", "Lunch", System.currentTimeMillis());
long expenseId = expenseDao.createExpense(expense);
```

---

#### `getAllExpenses(int userId): List<Expense>`
Retrieves all expenses for a specific user.

**Parameters:**
- `userId`: The user's ID

**Returns:**
- `List<Expense>`: List of all expenses, ordered by date descending

---

#### `getExpenseById(int expenseId): Expense`
Retrieves a specific expense by ID.

**Parameters:**
- `expenseId`: The expense ID

**Returns:**
- `Expense`: Expense object if found, null otherwise

---

#### `updateExpense(Expense expense): boolean`
Updates an existing expense.

**Parameters:**
- `expense`: Expense object with updated values

**Returns:**
- `boolean`: true if update was successful, false otherwise

---

#### `deleteExpense(int expenseId): boolean`
Deletes an expense.

**Parameters:**
- `expenseId`: The expense ID to delete

**Returns:**
- `boolean`: true if deletion was successful, false otherwise

---

#### `searchExpenses(int userId, String query): List<Expense>`
Searches expenses by description.

**Parameters:**
- `userId`: The user's ID
- `query`: Search query string

**Returns:**
- `List<Expense>`: List of matching expenses

---

#### `getExpensesByCategory(int userId, String category): List<Expense>`
Filters expenses by category.

**Parameters:**
- `userId`: The user's ID
- `category`: Category to filter by (Food, Transport, Shopping, Bills, Other)

**Returns:**
- `List<Expense>`: List of expenses in the specified category

---

#### `getExpensesByDateRange(int userId, long startDate, long endDate): List<Expense>`
Gets expenses within a date range.

**Parameters:**
- `userId`: The user's ID
- `startDate`: Start timestamp (milliseconds)
- `endDate`: End timestamp (milliseconds)

**Returns:**
- `List<Expense>`: List of expenses within the date range

---

#### `getTodayTotal(int userId): double`
Calculates total spending for today.

**Parameters:**
- `userId`: The user's ID

**Returns:**
- `double`: Total amount spent today

---

#### `getWeekTotal(int userId): double`
Calculates total spending for current week.

**Parameters:**
- `userId`: The user's ID

**Returns:**
- `double`: Total amount spent this week

---

#### `getMonthTotal(int userId): double`
Calculates total spending for current month.

**Parameters:**
- `userId`: The user's ID

**Returns:**
- `double`: Total amount spent this month

---

#### `getCategoryTotals(int userId): List<CategoryTotal>`
Gets spending totals grouped by category.

**Parameters:**
- `userId`: The user's ID

**Returns:**
- `List<CategoryTotal>`: List of category totals

---

## Repository API

### AuthRepository

#### `registerUser(User user): long`
Registers a new user.

**Returns:**
- `long`: User ID if successful, -1 if email already exists

---

#### `loginUser(String email, String password): User`
Authenticates user login.

**Returns:**
- `User`: User object if login successful, null otherwise

---

#### `getUserByEmail(String email): User`
Gets user information by email.

---

#### `validateSecurityAnswer(String email, String answer): boolean`
Validates security answer for password reset.

---

#### `resetPassword(String email, String newPassword): boolean`
Resets user password.

---

### ExpenseRepository

Wraps ExpenseDao methods with the same signatures.

---

## ViewModel API

### AuthViewModel

#### LiveData Observables

- `getLoginSuccess(): LiveData<Boolean>`
- `getLoginError(): LiveData<String>`
- `getRegisterSuccess(): LiveData<Boolean>`
- `getRegisterError(): LiveData<String>`
- `getLoggedInUser(): LiveData<User>`

#### Methods

- `login(String email, String password): void`
- `register(User user): void`

---

### ExpenseViewModel

#### LiveData Observables

- `getExpensesList(): LiveData<List<Expense>>`
- `getTodayTotal(): LiveData<Double>`
- `getWeekTotal(): LiveData<Double>`
- `getMonthTotal(): LiveData<Double>`
- `getCategoryTotals(): LiveData<List<CategoryTotal>>`
- `getOperationSuccess(): LiveData<Boolean>`
- `getOperationError(): LiveData<String>`

#### Methods

- `loadExpenses(int userId): void`
- `loadTotals(int userId): void`
- `loadCategoryTotals(int userId): void`
- `addExpense(Expense expense): void`
- `updateExpense(Expense expense): void`
- `deleteExpense(int expenseId): void`
- `searchExpenses(int userId, String query): void`
- `filterByCategory(int userId, String category): void`

---

## Utility API

### SessionManager

- `createLoginSession(int userId, String email, String userName): void`
- `isLoggedIn(): boolean`
- `getUserId(): int`
- `getUserEmail(): String`
- `getUserName(): String`
- `logout(): void`

### ValidationUtils

- `isValidEmail(String email): boolean`
- `isValidPassword(String password): boolean`
- `doPasswordsMatch(String password, String confirmPassword): boolean`
- `isValidAmount(String amount): boolean`
- `isNotEmpty(String text): boolean`

### FormatUtils

- `formatCurrency(double amount): String` - Returns formatted currency (e.g., "$50.00")
- `formatDate(long timestamp): String` - Returns formatted date (e.g., "Jan 15, 2024")
- `formatDateTime(long timestamp): String` - Returns date with time
- `formatShortDate(long timestamp): String` - Returns short date (e.g., "15/01/24")

---

## Constants

### Categories
```java
public static final String CATEGORY_FOOD = "Food";
public static final String CATEGORY_TRANSPORT = "Transport";
public static final String CATEGORY_SHOPPING = "Shopping";
public static final String CATEGORY_BILLS = "Bills";
public static final String CATEGORY_OTHER = "Other";
```

### SharedPreferences Keys
```java
private static final String PREF_NAME = "SmartExpenseSession";
private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
private static final String KEY_USER_ID = "userId";
private static final String KEY_USER_EMAIL = "userEmail";
private static final String KEY_USER_NAME = "userName";
```

---

## Error Codes

While the app doesn't use explicit error codes, common return values are:

- `-1`: Operation failed (insert/update/delete)
- `0`: No rows affected
- `> 0`: Success (row ID or number of rows affected)
- `null`: Record not found

---

## Usage Examples

### Complete User Registration Flow

```java
// In SignupActivity
String username = "John Doe";
String email = "john@example.com";
String password = "password123";
String securityQuestion = "What is your pet's name?";
String securityAnswer = "Fluffy";

User user = new User(username, email, password, securityQuestion, securityAnswer);
authViewModel.register(user);

// Observe result
authViewModel.getRegisterSuccess().observe(this, success -> {
    if (success) {
        // Registration successful
        Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show();
        finish();
    }
});

authViewModel.getRegisterError().observe(this, error -> {
    if (error != null) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
});
```

### Adding an Expense

```java
// In AddExpenseActivity
int userId = sessionManager.getUserId();
double amount = 50.00;
String category = "Food";
String description = "Lunch at restaurant";
long date = System.currentTimeMillis();

Expense expense = new Expense(userId, amount, category, description, date);
expenseViewModel.addExpense(expense);

// Observe result
expenseViewModel.getOperationSuccess().observe(this, success -> {
    if (success) {
        Toast.makeText(this, "Expense added!", Toast.LENGTH_SHORT).show();
        finish();
    }
});
```

### Loading Dashboard Data

```java
// In DashboardActivity
int userId = sessionManager.getUserId();

expenseViewModel.loadTotals(userId);
expenseViewModel.loadCategoryTotals(userId);

// Observe totals
expenseViewModel.getTodayTotal().observe(this, total -> {
    todayTotalText.setText(FormatUtils.formatCurrency(total));
});

expenseViewModel.getWeekTotal().observe(this, total -> {
    weekTotalText.setText(FormatUtils.formatCurrency(total));
});

expenseViewModel.getCategoryTotals().observe(this, categoryTotals -> {
    setupPieChart(categoryTotals);
});
```

---

**For more details, refer to the source code and inline documentation.**

