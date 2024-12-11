# Important Gradle Tasks

```
// format code
./gradlew ktLintFormat

// run tests
./gradlew test

// generate database classes
./gradlew :core:database:generateSqlDelightInterface
```

# Open TODOs
[ ] provide test fakes of DatabaseTaskGroup
[ ] provide test fakes of DatabaseTask
[ ] provide test fakes of TaskGroup
[ ] provide test fakes of Task
[ ] move DatabaseDefaultValuesFake to proper test package (prbably names *Fakes)