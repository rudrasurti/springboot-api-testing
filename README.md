# Spring Boot API Testing

A small Spring Boot REST API (a books catalogue) with an automated API test suite written with RestAssured and JUnit 5. I built it as a learning project while I test Spring Boot Java applications.

## What is tested

| Test | Checks |
| --- | --- |
| create book | 201, Location header, body |
| get existing book | 200 and the right title |
| get unknown book | 404 |
| blank title | 400 (bean validation) |
| zero pages | 400 (bean validation) |
| delete then get | 204, then 404 |
| list books | 200 and a non-empty list |

## Run it

```bash
mvn test
```

Requires Java 17+ and Maven. Tests start the app on a random port, so nothing else needs to run.

## Endpoints

- `GET /api/books`
- `GET /api/books/{id}`
- `POST /api/books` with `{"title": "...", "author": "...", "pages": 100}`
- `DELETE /api/books/{id}`

## CI

GitHub Actions runs `mvn test` on every push and uploads the Surefire reports.

## Author

Rudra Surti, Senior SDET. [LinkedIn](https://www.linkedin.com/in/rudra-surti/) · [GitHub](https://github.com/rudrasurti) · [Site](https://rudrasurti.github.io)
