# Almost — Android inventory reminder app

**Tagline:** Never run out unexpectedly.

This is an Android Studio project for the Almost essentials tracker, built with Kotlin, Jetpack Compose Material 3, Room, Navigation Compose, WorkManager and MVVM-style repositories/ViewModels. Inventory is stored locally; no backend or sign-in is required.

## Open and run
1. Install Android Studio and a JDK 17-compatible Android Studio setup.
2. Extract this ZIP and open the `AlmostProject` folder (the folder containing `settings.gradle.kts`).
3. Allow Gradle to sync and download dependencies.
4. Run the `app` configuration on an Android 8.0+ device or emulator.
5. Run unit tests with **Gradle > app > Tasks > verification > test** or `./gradlew test` if you add the Gradle wrapper through Android Studio.

## Features included
- Add and list household/office essentials, search/filter inventory.
- Track quantities, unit, category, daily usage, alert lead time and minimum threshold.
- Estimate days remaining and predicted finish date when daily usage is known.
- Record consumption and restocks with local history.
- Settings for System/Light/Dark theme, notifications and default lead time.
- Periodic WorkManager checks and local low-stock/empty-item notifications.
- Unit tests for basic prediction cases.

## Important notes
- The source was assembled from the supplied *Almost App Phase 1 Setup.pdf* and organized as a project. It has **not been built in Android Studio in this environment**; perform Gradle sync/build and resolve any environment-specific SDK issues before relying on it.
- Editing an existing item currently opens the form without pre-populating saved values; avoid using Edit until that workflow is completed. Adding, detail, consumption and restock flows are present.
- Notifications are periodic, not exact-time alarms. On Android 13+, grant notification permission when requested by the OS/app settings. The current code handles a missing permission without crashing.
- Room uses destructive migration fallback for this initial prototype; uninstalling/clearing app data or a schema upgrade can remove locally stored data. Export/backup is not included yet.
- For an actual release, add runtime notification permission prompting, robust migrations, accessibility/device testing, and verify the complete app on target Android versions.
