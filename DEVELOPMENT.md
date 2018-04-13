# Development

### Build

```
mvn package -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true
java -jar target/java-xml-grepper.jar
```

### Testcoverage

[Run OpenClover](http://openclover.org/) with maven:

```bash
mvn clean clover:setup test clover:aggregate clover:clover
```

Now look into `target/site/clover/`
