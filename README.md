[![Build status](https://badge.buildkite.com/a8860e94a7378491ce8f50480e3605b49eb2558cfa851bbf9b.svg)](https://buildkite.com/bazel/kotlin-postsubmit)

[Skydoc documentation](https://bazelbuild.github.io/rules_kotlin)

# Announcements
* <b>Jun 29, 2018.</b> The commits from this date forward are compatible with bazel `>=0.14`. JDK9 host issues were 
  fixed as well some other deprecations. I recommend skipping `0.15.0` if you   are on a Mac. 
* <b>May 25, 2018.</b> Test "friend" support. A single friend dep can be provided to `kt_jvm_test` which allows the test
  to access internal members of the module under test.
* <b>February 15, 2018.</b> Toolchains for the JVM rules. Currently this allow tweaking: 
    * The JVM target (bytecode level).
    * API and Language levels.
    * Coroutines, enabled by default. 
* <b>February 9, 2018.</b> Annotation processing.
* <b>February 5, 2018. JVM rule name change:</b> the prefix has changed from `kotlin_` to `kt_jvm_`.

# Overview 

These rules were initially forked from [pubref/rules_kotlin](http://github.com/pubref/rules_kotlin).
Key changes:

* Replace the macros with three basic rules. `kotlin_binary`, `kotlin_library` and `kotlin_test`.
* Use a single dep attribute instead of `java_dep` and `dep`.
* Add support for the following standard java rules attributes:
  * `data`
  * `resource_jars`
  * `runtime_deps`
  * `resources`
  * `resources_strip_prefix`
  * `exports`
* Persistent worker support.
* Mixed-Mode compilation (compile Java and Kotlin in one pass).

# Quick Guide
Consult the generated [documentation](https://bazelbuild.github.io/rules_kotlin). This section just contains a quick 
overview. 


## WORKSPACE
In the project's `WORKSPACE`, declare the external repository and initialize the toolchains, like
this:

```build
kotlin_release_version="1.2.30"
rules_kotlin_version = "67f4a6050584730ebae7f8a40435a209f8e0b48e"

http_archive(
    name = "io_bazel_rules_kotlin",
    urls = ["https://github.com/bazelbuild/rules_kotlin/archive/%s.zip" % rules_kotlin_version],
    type = "zip",
    strip_prefix = "rules_kotlin-%s" % rules_kotlin_version
)

load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories", "kt_register_toolchains")
kotlin_repositories(kotlin_release_version=kotlin_release_version)
kt_register_toolchains()
```

If you omit `kotlin_release_version` and just call `kotlin_repositories()` with no arguments,
you'll get the current kotlin compiler version (at least as known to the rules_kotlin project).

## BUILD files

In your project's `BUILD` files, load the kotlin rules and use them like so:

```
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "package_name",
    srcs = glob(["*.kt"]),
    deps = [
        "//path/to/dependency",
    ],
)
```

# kotlin-js support

Placeholder docs, this is at proof-of-concept stage only.

To run the kotlin-js support example:

`bazel build //examples/js:hello_lib`

# License

This project is licensed under the [Apache 2.0 license](LICENSE), as are all contributions

# Contributing

See the [CONTRIBUTING](CONTRIBUTING.md) doc for information about how to contribute to
this project.

