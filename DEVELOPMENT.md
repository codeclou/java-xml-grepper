# Development

### Doc

We build with bazel:

 * https://docs.bazel.build/versions/master/tutorial/java.html
 * https://github.com/bazelbuild/examples/tree/master/java-maven
 * https://github.com/johnynek/bazel-deps

"Fat-Jar":

 * https://docs.bazel.build/versions/master/tutorial/java.html#package-a-java-target-for-deployment


### Run Tests

```
bazel test :tests
```

### Build

```
# JAR / BINARY
bazel build //:java-xml-grepper

./bazel-bin/java-xml-grepper


# FAT JAR
bazel build //:java-xml-grepper_deploy.jar

java -jar bazel-bin/java-xml-grepper_deploy.jar
```
