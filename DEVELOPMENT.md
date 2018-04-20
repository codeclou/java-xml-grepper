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

Use Bazel Watcher for tests: https://github.com/bazelbuild/bazel-watcher

Install
```
cd ~/git
git clone https://github.com/bazelbuild/bazel-watcher.git
cd bazel-watcher
bazel build //ibazel

# Add to .bash_profile
echo 'export PATH="$PATH:$HOME/git/bazel-watcher/bazel-bin/ibazel/darwin_amd64_pure_stripped/"' >> ~/.bash_profile
```

Test with watcher

```
ibazel test :tests
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
