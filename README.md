### Project Specific Documentation
This can be used as a starter project for testing out endpoints and functionalities;

Local endpoints active are below - all end points are not mapped here. 
This is to give the user  starting point. 
Please use the swagger for comprehensive list of endpoints.

* http://localhost:60501/swagger-ui/index.html
* http://localhost:60501/v3/api-docs
* http://localhost:60501/status
* http://localhost:60501/h2-console

Env Controller
* http://localhost:60501/api/v1/envinfo
* http://localhost:60501/api/v1/envinfo/rmxbean

View endpoints are as follows 
* http://localhost:60501/
* http://localhost:60501/countries
* http://localhost:60501/starter
* http://localhost:60501/starter-blank

Csv endpoints as follows ( i18n handled by jvm param )
* http://localhost:60501/api/v1/csv/file/users
* http://localhost:60501/api/v1/csv/download/users
* http://localhost:60501/api/v1/csv/read/users ( read csv file )

Upload (Multipart) and Download File
* http://localhost:60501/api/v1/csv/upload/labels?overWrite=true
* http://localhost:60501/api/v1/csv/download/labels

![swagger screenshot of file upload](docs/swagger-fileupload.png)

Json endpoints as follows : 
* http://localhost:60501/api/v1/json/file/users
* http://localhost:60501/api/v1/json/read/users

PDF Controller Using PDF Box : ( i18n achieved with dynamic font loading )
* http://localhost:60501/api/v1/pdf/file/users
* http://localhost:60501/api/v1/pdf/download/users
* http://localhost:60501/api/v1/pdf/download/password/users

Excel Controller using apache poi :
* http://localhost:60501/api/v1/excel/read/users
* http://localhost:60501/api/v1/excel/download/users
* http://localhost:60501/api/v1/excel/download/sample

XML Controller using JAXB :
* http://localhost:60501/api/v1/xml/read/users
* http://localhost:60501/api/v1/xml/file/users
* http://localhost:60501/api/v1/xml/download/users

Xpath Controller : 
* http://localhost:60501/api/v1/xpath/transform/tutorials

XSLT Controller : 
* http://localhost:60501/api/v1/xslt/transform/users
* http://localhost:60501/api/v1/xslt/transform/download/users

Protocol Buffer Controller ( save/read from disk ) :
* http://localhost:60501/api/v1/protobuf/read/users
* http://localhost:60501/api/v1/protobuf/file/users

Global Custom Error Handling Page : 
* http://localhost:60501/api/v1/countries/err
* 

Caching and Scheduling, Conditional On Property ( LabelController ) :

### TODO
* @Cacheable
* @Mockito
* Java File IO

### h2 database configuration
password is 'password'

JVM Params :
- -Duser.timezone=UTC -Dfile.encoding=utf-8

Intellij Configuration :
![intellij-config-screen.png](docs/intellij-config-screen.png)


----
###Features 
- Internationalization added for file encodings
- save to csv locally & download

----
###References
- String Functions in Free Marker : 
  - https://freemarker.apache.org/docs/ref_builtins_string.html
- Data Types in H2 Database : 
  - https://www.h2database.com/html/datatypes.html
- Free Unicode Font
  - https://djmilch.wordpress.com/2016/01/19/free-font-noto-sans-cjk-in-ttf/
- Json Parsing Tutorials 
  - https://devqa.io/how-to-parse-json-in-java/
  - https://www.baeldung.com/guide-to-jayway-jsonpath
  - JSON Path Cheat Sheet -https://support.smartbear.com/alertsite/docs/monitors/api/endpoint/jsonpath.html
  - https://lzone.de/cheat-sheet/JSONPath
- Base64 Tutorial
  - https://www.baeldung.com/java-base64-encode-and-decode
  - URL and Filename safe Alphabet - https://www.ietf.org/rfc/rfc4648.txt
- XSLT Cheat Sheet
  - https://www.technical-recipes.com/2022/xslt-example-cheat-sheet/
  - https://www.baeldung.com/java-extensible-stylesheet-language-transformations
  - https://www.w3schools.com/xml/xsl_choose.asp
  - https://www.ibm.com/support/pages/creating-utf-8-documents-xslt
- Protocol Buffer :
  - https://www.baeldung.com/google-protocol-buffer
  - https://www.baeldung.com/spring-rest-api-with-protocol-buffers#overview
  - https://github.com/protocolbuffers/protobuf/releases
  - $ protoc --java_out=${OUTPUT_DIR} path/to/your/proto/file
    - ..\src\main>protoc --java_out=java resources\protobuf\baeldung.proto
- Global Error Handler :
  - https://stackoverflow.com/questions/63022189/get-stack-trace-from-httpservletrequest-in-spring-boot
  - https://www.baeldung.com/exception-handling-for-rest-with-spring
- Country language Codes ( en, ja, zh ) : ISO 639-1 Code
  - https://www.loc.gov/standards/iso639-2/php/code_list.php
- General Spring Related Info
  - https://stackoverflow.com/questions/21218868/explain-why-constructor-inject-is-better-than-other-options
- Cron Expressions
  - https://www.baeldung.com/cron-expressions
- SPEL Support in JPA
  - https://spring.io/blog/2014/07/15/spel-support-in-spring-data-jpa-query-definitions/
- Hibernate Logging Properties
  - https://thorben-janssen.com/hibernate-logging-guide/
- Using Environment Variables
  - https://www.baeldung.com/spring-boot-properties-env-variables
----
### Spring Boot Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.0/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.0/gradle-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#using.devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#web)
* [Apache Freemarker](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#web.servlet.spring-mvc.template-engines)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

