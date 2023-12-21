### Project Specific Documentation
This can be used as a starter project for testing out endpoints and functionalities;

Local endpoints active are below
* http://localhost:60501/swagger-ui/index.html
* http://localhost:60501/v3/api-docs
* http://localhost:60501/status
* http://localhost:60501/h2-console

View endpoints are as follows 
* http://localhost:60501/
* http://localhost:60501/countries
* http://localhost:60501/starter
* http://localhost:60501/starter-blank

Csv endpoints as follows ( i18n handled by jvm param )
* http://localhost:60501/api/v1/csv/file/users
* http://localhost:60501/api/v1/csv/download/users
* http://localhost:60501/api/v1/csv/read/users ( read csv file )

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

### h2 database configuration
password is 'password'

JVM Params :
- -Duser.timezone=UTC -Dfile.encoding=utf-8

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

