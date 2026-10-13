# AGENTS.md

## Project overview

PremierLeagueService is a small REST CRUD API for Premier League teams and players. It uses Java 17, Spring Boot 3.3.3, Maven, Spring Web, Spring Data JPA, PostgreSQL, and MapStruct 1.5.5.Final.

## Architecture

Java code lives under `src/main/java/org/nunostudios/premierleagueservice`, split into `Controller`, `Service`, `Repository`, `Model` (JPA entities), `DTO`, and `Mapper`. Controllers expose `/api/team` and `/api/player`, delegate business and persistence work to services, and return DTOs. Services use Spring Data repositories and MapStruct mappers; repositories extend `JpaRepository`. Keep entity-to-DTO mapping at the API boundary and follow this flow for CRUD changes.

## Coding and implementation

- Preserve the existing package names and capitalization, class names in PascalCase, methods and fields in camelCase, and the current layer responsibilities.
- Keep changes focused. Extend the existing team/player patterns where suitable; avoid introducing another mapping or persistence approach without a concrete need.
- Existing components use field-based `@Autowired`; follow the local style when modifying them. For new components, prefer constructor injection where it fits without broad refactoring.
- Use DTOs for request/response data and MapStruct mappers for entity conversion. Do not expose entities directly from controllers.
- Add validation or shared exception handling only where needed for the feature; the current code has no Bean Validation constraints or global exception handler.

## REST API conventions

Current routes use singular lowercase resources (`/api/team`, `/api/player`) with collection GET/POST and `/{id}` GET/PUT/DELETE; teams also have `/api/team/tableLeader`. Follow these routes and the existing DTO response shapes unless the task explicitly changes the API. Current controllers return HTTP 200 for successful operations, including create and delete, and use `ResponseStatusException` with 404 for missing resources. Preserve existing behavior for routine changes; use suitable HTTP status codes for new behavior and document intentional API changes. Request bodies currently use `@RequestBody` without validation annotations.

## Persistence and errors

Entities are JPA `Team` and `Player`; PostgreSQL is the configured database, with Hibernate `ddl-auto=update`. Repositories own database queries; put multi-step business behavior in services and keep writes consistent, using transactions where an operation requires atomicity. Respect entity relationships and existing repository query patterns. Missing records are represented by `Optional`, null, or boolean results in services and translated to 404 in controllers. Avoid leaking internal exception details; keep client-facing errors consistent with the existing 404 handling.

## Tests, build, and run

Tests are under `src/test/java`; the current test suite uses JUnit 5 and Spring Boot Test and contains a context-load smoke test. Add or update focused tests for changed behavior. Use the Maven wrapper from the repository root:

```sh
./mvnw test
./mvnw package
./mvnw spring-boot:run
```

On Windows, use `mvnw.cmd` with the same goals. The README also documents `docker-compose up --build`; the container maps host port 8000 to application port 8080. Run relevant tests after changes and report the actual result.

## Security and configuration

Database settings are in `src/main/resources/application.properties` and `docker-compose.yml`; the current configuration includes inline credentials. Never copy credentials or secrets into source, documentation, logs, or commits. Do not repeat existing secret values. Use environment-based configuration for new or changed credentials and keep sensitive values out of examples. Database endpoint and credentials should be treated as configuration, not application logic.

## Instructions for Codex

Before editing, inspect the relevant code and configuration. Make small, focused changes that follow the existing architecture, add or update tests when behavior changes, and run relevant checks. In the final report, summarize the changes and state which commands ran and whether they passed.
