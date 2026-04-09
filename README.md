# Selenium Test Automation Framework

This project is a Selenium WebDriver and TestNG based automation framework for the Swag Labs application. It is designed around reusable Page Object Model structure, thread-safe browser execution, and maintainable test layers so functional scenarios can be expanded without turning the suite into tightly coupled test code.

## Objective

The framework automates core user journeys and validation scenarios for the application, including:

- Login validation
- Product sorting
- Add to cart and remove from cart flows
- Cart review
- Checkout completion
- Logout navigation
- Negative validation coverage for login and checkout forms

## Framework Design

The project follows a layered automation design so responsibilities stay separated:

- `pages`
  Contains page objects and reusable user actions for each application screen.
- `utils`
  Contains driver management, configuration handling, and screenshot utilities.
- `models`
  Contains simple data models used across tests.
- `base`
  Contains shared TestNG setup and teardown behavior.
- `data`
  Contains TestNG data providers for reusable scenario inputs.
- `listeners`
  Contains listener implementations such as screenshot capture on failure.
- `tests`
  Contains business-facing test scenarios grouped by feature area.

## Key Implementation Decisions

### Page Object Model

The UI logic is implemented through dedicated page classes so locators and user actions remain outside the test classes. This keeps the tests focused on business behavior instead of Selenium mechanics.

### Thread-Safe Parallel Execution

The framework uses `ThreadLocal<WebDriver>` so each parallel test thread owns its own browser instance. This avoids session collisions when executing the suite through `testng.xml`.

### Explicit Wait Strategy

The framework uses explicit waits for visibility, clickability, page anchors, and URL transitions. This improves stability for multi-step UI flows such as checkout and logout.

### Failure Diagnostics

When a test fails, a screenshot is captured automatically and stored under the `screenshots/` directory. This supports faster debugging of UI issues.

### Data-Driven Negative Testing

Negative login and checkout validation scenarios are implemented through TestNG data providers so additional edge cases can be added with minimal duplication.

## Technology Stack

- Java 21
- Selenium WebDriver 4
- TestNG 7
- Maven Surefire Plugin
- Chrome / Firefox / Edge browser support through Selenium drivers available on the local machine

## Project Structure

```text
Automation_Framework_Selenium
├── pom.xml
├── testng.xml
├── screenshots/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── models/
│   │       ├── pages/
│   │       └── utils/
│   └── test/
│       └── java/
│           ├── base/
│           ├── data/
│           ├── listeners/
│           └── tests/
└── target/
```

## Covered Test Scenarios

### Happy Path Coverage

- Valid login
- Product sort by price low to high
- Add multiple products to cart
- Review products in cart
- Remove product from cart
- Complete checkout successfully
- Logout and return to login page

### Negative Coverage

- Locked out user login attempt
- Invalid username
- Invalid password
- Blank username
- Blank password
- Checkout with missing first name
- Checkout with missing last name
- Checkout with missing postal code

## Prerequisites

Before running the project locally, make sure the following are installed and configured:

- Java 21 or later
- Maven 3.9 or later
- Google Chrome installed for default execution
- Internet access to open the application under test

To verify the local setup:

```bash
java -version
mvn -version
```

## How To Run Locally

### Run the complete suite

```bash
mvn test
```

This runs the TestNG suite defined in `testng.xml`.

### Run with a specific browser

```bash
mvn test -Dbrowser=firefox
```

Supported browser values depend on the browser and matching driver availability on the local machine.

### Run against a specific environment URL

```bash
mvn test -Dbase.url=https://www.saucedemo.com/
```

## Parallel Execution

Parallel execution is configured in `testng.xml` at the class level.

- Suite name: `SwagLabsParallelSuite`
- Parallel mode: `classes`
- Thread count: `3`

This allows different test classes to run in parallel while maintaining isolated browser sessions.

## Reporting

After execution, TestNG and Surefire reports can be reviewed under:

- `target/surefire-reports/`
- `test-output/`

Failure screenshots are stored under:

- `screenshots/`

## Maintenance Guidelines

- Add new UI behavior inside page classes, not directly inside tests.
- Keep assertions in the test layer so test intent stays clear.
- Reuse shared waits and helper methods from the base page utilities.
- Add new negative combinations through data providers when possible.
- Keep test methods independent so they remain safe for parallel execution.

## Notes

- The default application URL is `https://www.saucedemo.com/`.
- The framework is configured to use explicit waits instead of implicit waits.
- Browser startup behavior depends on local machine configuration and installed browser versions.
