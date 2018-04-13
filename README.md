# java-xml-grepper

You want to get value from your xml file using XPath? Then this is for you.

----
&nbsp;

### Is this for me?

If you want to grep e.g. the version of a `pom.xml` file to commandline then this is for you.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>io.codeclou</groupId>
  <artifactId>java-xml-grepper</artifactId>
  <version>1.4.5</version>
</project>
```


----
&nbsp;

### Usage

With current folder containing a `pom.xml` file and you want to print out the version on commandline.
The `-x` parameter accepts XPath expressions that can be cast to String.

```
# INSTALL
curl -L -o java-xml-grepper.jar \
     https://github.com/codeclou/java-xml-grepper/releases/download/1.0.0/java-xml-grepper.jar

# RUN
java -jar java-xml-grepper.jar \
     -i pom.xml \
     -x /project/version
     
# PRINTS (example)
1.0.1
```

-----
&nbsp;

### Demo

```
git clone https://github.com/codeclou/java-xml-grepper.git src
cd src

curl -L -o java-xml-grepper.jar \
     https://github.com/codeclou/java-xml-grepper/releases/download/1.0.0/java-xml-grepper.jar

java -jar java-xml-grepper.jar \
     -i pom.xml \
     -x /project/version

# Should print the version
```


----
&nbsp;

### License

[MIT](https://github.com/codeclou/java-xml-grepper/blob/master/LICENSE) © [Bernhard Grünewaldt](https://github.com/clouless)
