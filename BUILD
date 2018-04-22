package(default_visibility = ["//visibility:public"])

java_library(
    name = "java-xml-grepper-lib",
    srcs = glob(["src/main/java/io/codeclou/java/xml/grepper/*.java"]),
    deps = [
        "@commons_cli//jar",
    ],
)

java_binary(
    name = "java-xml-grepper",
    main_class = "io.codeclou.java.xml.grepper.Main",
    runtime_deps = [":java-xml-grepper-lib"],
)

java_test(
    name = "tests",
    srcs = glob(["src/test/java/io/codeclou/java/xml/grepper/*.java"]),
    test_class = "io.codeclou.java.xml.grepper.TestAll",
    resources=glob(['src/test/resources/**/*']),
    deps = [
        ":java-xml-grepper-lib",
        "@commons_cli//jar",
        # TEST SCOPE
        "@openpojo//jar",
        "@commons_io//jar",
        "@junit//jar",
        "@powermock_api_support//jar",
        "@powermock_core//jar",
        "@powermock_module_junit4//jar",
        "@powermock_module_junit4_common//jar",
        "@powermock_api_mockito//jar",
        "@powermock_api_mockito_common//jar",
        "@powermock_reflect//jar",
        "@mockito_all//jar",
        "@javassist//jar",
    ],
)
