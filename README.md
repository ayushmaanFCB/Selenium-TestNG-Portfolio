# Ayushmaan's Flask Portfolio Tests (Selenium, TestNG and Java)

This repository contains automated tests for my **Flask**-based portfolio application. These tests ensure that key pages of the portfolio website work as expected.

The portfolio application itself is available in another repository: [Portfolio-Ayushmaan](https://github.com/ayushmaanFCB/Portfolio-Ayushmaan) and is hosted at [Ayushmaan's Portfolio](https://homeless-margalo-ayushmaan-personal-109aa799.koyeb.app), deployed using **Koyeb**.

## Running the Tests

1. Clone this repository to your local machine.
2. Ensure you have **Java 8 or higher** and **TestNG** installed.
3. Run the `Main` class in `src/main/java/Main.java` to execute the tests.

## Test Details

The test suite is defined in the testng.xml file, where each test class is listed. The tests cover the following pages of the Flask Portfolio application:

- **_HomePageTests_**: Verifies the Home page functionality.
- **_ProjectsPageTests_**: Verifies the Projects page functionality.
- **_SkillsPageTests_**: Verifies the Skills page functionality.
- **_BlogPageTests_**: Verifies the Blog page functionality.
- **_ContactPageTests_**: Verifies the Contact page functionality.

The suite will automatically run all tests across these pages.

## Logs

Logs are generated during the test execution and saved in the `test-results.log file`. The logging captures important information regarding test execution and any errors encountered during the process.
Log Levels:

- **INFO**: Information about the test execution.
- **SEVERE**: Errors or issues encountered during the execution.
  This helps to monitor the test process, track any issues, and ensure successful execution.

---
