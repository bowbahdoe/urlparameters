# urlparameters

[![javadoc](https://javadoc.io/badge2/dev.mccue/urlparameters/javadoc.svg)](https://javadoc.io/doc/dev.mccue/urlparameters)

Utility for parsing url parameters.

## Dependency Information

### Maven

```xml
<dependency>
    <groupId>dev.mccue</groupId>
    <artifactId>urlparameters</artifactId>
    <version>0.2.0</version>
</dependency>
```

### Gradle

```groovy
dependencies {
    implementation("dev.mccue:urlparameters:0.2.0")
}
```

## Usage

### Reading Parameters
```java
// Get this string from a form submit or the query params on a URI.
var params = UrlParameters.parse("name=bob&age=44");
System.out.println(params.firstValue("name"));
// Optional[bob]
System.out.println(params.firstValue("age"));
// Optional[44]
System.out.println(params.firstValue("hair"));
// Optional.empty
        
// Or directly from a URI
var params2 = UrlParameters.parse(URI.create("https://example.com/path?name=bob&age=44"));
System.out.println(params2.firstValue("name"));
System.out.println(params2.firstValue("age"));
System.out.println(params2.firstValue("hair"));
```

### Writing Parameters

```java
var params = new UrlParameters(List.of(
    new UrlParameter("message", "hello world"),
    new UrlParameter("time", "now")
));

System.out.println(params);
// message=hello%20world&time=now
```
