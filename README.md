# SDD SDET Automation Assignment

Automation framework developed for the SDD Senior SDET technical assessment.

## Technologies Used

- Java 17
- Maven
- TestNG
- Cucumber BDD
- Appium
- Playwright
- REST Assured
- Allure Report
- GitHub Actions

## Project Coverage

The framework covers:

- Mobile application testing using Appium
- Web UI testing using Playwright
- API testing using REST Assured
- Cucumber BDD scenarios
- Smoke, Sanity and Regression tagging
- Retry mechanism
- Screenshot capture for failed tests
- Test reporting using Cucumber and Allure

## Project Structure

```text
src
├── test
│   ├── java
│   │   └── ae.sharjah.sdd
│   │       ├── mobile
│   │       ├── web
│   │       ├── api
│   │       ├── core
│   │       └── runner
│   │
│   └── resources
│       ├── features
│       └── schemas
│
├── testng.xml
└── pom.xml