# Selenium Test Automation Framework

This project is a scalable Selenium + TestNG automation framework for the [Swag Labs](https://www.saucedemo.com/) demo application.

## What this project covers

- Page Object Model for login, inventory, cart, checkout, and logout flows
- Happy-path coverage for login, sorting, add-to-cart, cart review, checkout, and logout
- Negative coverage for invalid login scenarios and checkout validation errors
- Parallel execution using `testng.xml`
- Thread-safe driver setup with `ThreadLocal<WebDriver>`
- Screenshot capture on failure

## Project structure

```text
src/main/java
  pages         -> Page objects and reusable page behavior
  utils         -> Driver, configuration, and screenshot helpers
  models        -> Test data models

src/test/java
  base          -> Shared test setup and teardown
  data          -> TestNG data providers
  listeners     -> Failure listener for screenshots
  tests         -> Test classes grouped by business flow
```

## How to run

Run the full parallel suite:

```bash
mvn test
```

Run with another browser parameter when your machine has the browser driver support available:

```bash
mvn test -Dbrowser=firefox
```

Run against another environment:

```bash
mvn test -Dbase.url=https://www.saucedemo.com/
```

## Why this framework is portfolio-friendly

- The test code reads like business scenarios rather than raw Selenium steps
- Common setup is centralized and reusable
- Parallel execution is already configured for scale
- The framework is easy to extend with new pages, new data providers, and new suites
