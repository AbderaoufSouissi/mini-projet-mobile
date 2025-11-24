# SmartExpense - Setup Instructions

## System Requirements

- **Operating System:** Windows 10/11, macOS 10.14+, or Linux
- **RAM:** Minimum 8GB (16GB recommended)
- **Disk Space:** At least 10GB free space
- **Internet Connection:** Required for initial setup and downloading dependencies

## Required Software

### 1. Java Development Kit (JDK)
- **Version:** JDK 8 or higher (JDK 11 or 17 recommended)
- **Download:** https://www.oracle.com/java/technologies/downloads/
- **Verify Installation:**
  ```bash
  java -version
  javac -version
  ```

### 2. Android Studio
- **Version:** Arctic Fox (2020.3.1) or later (Latest version recommended)
- **Download:** https://developer.android.com/studio
- **Installation Steps:**
  1. Download the installer for your OS
  2. Run the installer
  3. Follow the setup wizard
  4. Install Android SDK, Android SDK Platform-Tools, and Android SDK Build-Tools

### 3. Android SDK
- **Min SDK Level:** API 24 (Android 7.0)
- **Target SDK Level:** API 34 (Android 14)
- Install via Android Studio SDK Manager:
  - Tools > SDK Manager
  - Install Android 7.0 (API 24) and Android 14 (API 34)

## Project Setup

### Step 1: Clone/Download the Project

**Option A: Using Git**
```bash
git clone <repository-url>
cd mini-projet-mobile
```

**Option B: Download ZIP**
1. Download the project ZIP file
2. Extract to your desired location
3. Navigate to the extracted folder

### Step 2: Open Project in Android Studio

1. Launch Android Studio
2. Click "Open" or "Open an Existing Project"
3. Navigate to the `mini-projet-mobile` folder
4. Click "OK"
5. Wait for Android Studio to index the project

### Step 3: Gradle Sync

1. Android Studio will automatically detect the Gradle files
2. Click "Sync Now" when prompted
3. Wait for Gradle to download all dependencies (this may take a few minutes)
4. If sync fails, click File > Invalidate Caches / Restart

### Step 4: Configure Android Emulator (Optional)

**If you don't have a physical device:**

1. Open AVD Manager: Tools > Device Manager
2. Click "Create Device"
3. Select a device (e.g., Pixel 5)
4. Click "Next"
5. Select a system image (API 24 or higher)
6. Click "Next" then "Finish"
7. Start the emulator

### Step 5: Build the Project

1. Click Build > Make Project (or press Ctrl+F9 / Cmd+F9)
2. Check the Build output panel for any errors
3. Ensure the build completes successfully

### Step 6: Run the Application

**On Emulator:**
1. Start your emulator from AVD Manager
2. Click Run > Run 'app' (or press Shift+F10)
3. Select the running emulator
4. Click "OK"

**On Physical Device:**
1. Enable Developer Options on your device:
   - Settings > About Phone
   - Tap "Build Number" 7 times
2. Enable USB Debugging:
   - Settings > Developer Options > USB Debugging
3. Connect device via USB
4. Click Run > Run 'app'
5. Select your device
6. Click "OK"

## Troubleshooting

### Gradle Sync Failed

**Solution 1: Check Internet Connection**
- Ensure you have a stable internet connection
- Retry sync

**Solution 2: Clear Gradle Cache**
```bash
# Windows
rd /s /q %USERPROFILE%\.gradle\caches

# macOS/Linux
rm -rf ~/.gradle/caches
```

**Solution 3: Update Gradle Wrapper**
- File > Project Structure > Project
- Update Gradle version to the latest
- Click "OK" and sync again

### Build Errors

**Error: SDK not found**
- Open SDK Manager (Tools > SDK Manager)
- Install missing SDK platforms
- Sync project again

**Error: Dependency resolution failed**
- Check your internet connection
- File > Invalidate Caches / Restart
- Try again

### App Crashes on Launch

**Check Logcat:**
- View > Tool Windows > Logcat
- Look for red error messages
- Common issues:
  - Missing permissions (check AndroidManifest.xml)
  - Database initialization errors
  - Layout inflation errors

### Emulator Issues

**Emulator won't start:**
- Ensure hardware acceleration is enabled (Intel HAXM or AMD-V)
- Try creating a new virtual device
- Allocate more RAM to the emulator

**Emulator is slow:**
- Enable hardware acceleration
- Allocate more RAM and CPU cores
- Use an x86 system image instead of ARM

## Verifying Installation

### Run a Test User Flow

1. **Launch the app** - Should show splash screen then login
2. **Create an account:**
   - Username: testuser
   - Email: test@example.com
   - Password: test123
   - Security Question: What is your pet's name?
   - Security Answer: fluffy
3. **Login** with the created account
4. **Add an expense:**
   - Amount: 50
   - Category: Food
   - Description: Lunch
5. **View Dashboard** - Should show the expense in pie chart
6. **Check Expense List** - Should display the added expense
7. **View Statistics** - Charts should render properly

If all steps work, the installation is successful! ✅

## Environment Variables (Optional)

For advanced configuration, you can set environment variables:

```bash
# Windows (PowerShell)
$env:ANDROID_HOME = "C:\Users\YourName\AppData\Local\Android\Sdk"

# macOS/Linux
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

## IDE Configuration Recommendations

### Android Studio Settings

1. **Enable Auto-Import:**
   - Settings > Editor > General > Auto Import
   - Check "Add unambiguous imports on the fly"

2. **Code Style:**
   - Settings > Editor > Code Style > Java
   - Set indent to 4 spaces

3. **Increase Memory:**
   - Help > Edit Custom VM Options
   - Set: `-Xmx4096m` (or higher if you have more RAM)

4. **Enable Instant Run:**
   - Settings > Build, Execution, Deployment > Instant Run
   - Enable Instant Run

## Next Steps

After successful setup:

1. Read the main README.md for feature overview
2. Review the code structure in `app/src/main/java`
3. Explore the UI layouts in `app/src/main/res/layout`
4. Check the database schema in `DatabaseHelper.java`
5. Start developing or testing!

## Getting Help

If you encounter issues:

1. Check the error messages in Logcat
2. Search for the error on Stack Overflow
3. Review Android Studio's Event Log (bottom right corner)
4. Consult the Android Developer documentation
5. Create an issue in the project repository

## Additional Resources

- [Android Developer Guide](https://developer.android.com/guide)
- [Material Design Guidelines](https://material.io/design)
- [MPAndroidChart Documentation](https://github.com/PhilJay/MPAndroidChart)
- [SQLite Tutorial](https://www.sqlitetutorial.net/)
- [MVVM Architecture](https://developer.android.com/topic/architecture)

---

**Ready to develop! 🚀**

