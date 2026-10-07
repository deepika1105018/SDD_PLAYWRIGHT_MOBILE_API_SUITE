# Known Issues / Final Validation
1. Mobile Popup scenario: visually confirmed popup contains `Dismiss`, but the current emulator did not expose it through the attempted XPath. The test is disabled until `adb shell uiautomator dump` confirms its accessibility locator. Do not claim 9/9 mobile pass until fixed.
2. Web Case 7 (Widget Factory / Go Green) is intentionally pending per candidate decision and must be added before final submission if required.
3. Run the complete suite locally and attach real Allure evidence before submission.
