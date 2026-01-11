# Scroll Inverter for Android

A simple Android utility to invert the scroll direction of physical trackpads and mice. 

This app uses an **Accessibility Service** to detect scroll events and trigger an opposite scroll action. This is useful for users who prefer "Natural Scrolling" (moving fingers down moves content down) on their physical keyboards/trackpads when the system defaults to the opposite.

## Features
-   **Inverts Scroll Direction**: Automatically detects scroll events and inverts them.
-   **Loop Prevention**: Intelligent debounce and lockout logic to prevent infinite scroll loops.
-   **Lightweight**: Minimal UI, runs as a background service.

## Installation & Setup
1.  **Open the Project**: Open this folder in **Android Studio**.
2.  **Build & Run**: Connect your Android device (Tablet/Phone) and run the app.
3.  **Enable Service**:
    -   Launch the "Scroll Inverter" app.
    -   Tap **"Open Accessibility Settings"**.
    -   Find **"Scroll Inverter"** in the list of Downloaded Apps.
    -   Toggle it **ON**.
    -   Allow the permission ("Control screen", "View and control screen").
4.  **Usage**: 
    -   The service runs in the background.
    -   Try scrolling on your physical trackpad. It should now be inverted.

## Troubleshooting
-   **"Jittery" Scrolling**: Accessibility services perform discrete scroll actions. It may not feel as smooth as native pixel-perfect scrolling. This is a limitation of the Android Accessibility API.
-   **Infinite Loops**: If the screen starts scrolling back and forth uncontrollably, disable the service immediately (or just lift your fingers; the loop breaker should catch it).
-   **Not Working**: ensure you are on Android 9 (Pie) or higher, as this app relies on `AccessibilityEvent.getScrollDeltaY` introduced in API 28.

## Requirement
-   Android 9.0 (API Level 28) or higher.
-   Physical Mouse or Trackpad connected.
