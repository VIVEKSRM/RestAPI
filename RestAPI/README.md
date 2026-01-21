# RestAPI Test Framework

This repository contains a small REST API test framework using TestNG and RestAssured.

Quick start
1. Ensure you have Java (JDK) installed. The project is configured for Java 20 in `pom.xml`. If you have a different JDK, either install JDK 20 or update `pom.xml` `maven.compiler.source`/`target` to your JDK.

2. Run smoke tests (default TestNG group):

```powershell
cd D:\Automation_Framework_v0.2\RestAPI
mvn test
```

3. Run integration tests (those that make external network calls):

```powershell
# activates the 'integration' profile which runs group 'integration'
mvn -P integration test
```

Configuration
- `src/test/resources/config.properties` contains base URIs and API key defaults.
- You can override any value with a system property, for example:

```powershell
mvn -Dbase.uri.demoqa=https://your-host -Dapi.key=MYKEY test
```

Structure
- `src/test/java/org/example` - test classes and helpers
  - `Config.java` - loads `config.properties` from test resources
  - `TestBase.java` - test base setup
  - `TestUtils.java` - helper to load payloads
  - `RestAPIBasics.java` - example API tests
  - `AppTest.java` - small sanity test
- `src/test/resources/config.properties` - default config values
- `src/test/resources/payloads` - JSON payloads used by tests

Notes
- Integration tests perform live HTTP calls. Use the `integration` Maven profile to run them selectively.
- `createRecord()` is currently disabled by default to avoid accidental writes; enable it by setting the test to enabled or use the `integration` profile.

If you want, I can:
- Add a GitHub Actions workflow to run smoke tests on push.
- Add more assertions to tests to validate payload contents.
- Convert a few more tests to use shared utilities or extract response models.

