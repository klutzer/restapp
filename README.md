# RestApp
A powerful Java RESTful application based on Jersey using Mentabean ORM

Feel free to use this project as template for your RESTful web application.
Enjoy it!

## Technology Stack
- Java 8+
- [Jersey](https://jersey.java.net/) (JAX-RS)
- [MentaBean](http://mentabean.soliveirajr.com/) (ORM)
- [MentaContainer](http://mentacontainer.soliveirajr.com/) (IoC)
- [HikariCP](https://brettwooldridge.github.io/HikariCP/) (Connection pool)
- [Jackson](https://github.com/FasterXML/jackson) (JSON provider)
- [JodaTime](http://www.joda.org/joda-time/) (Date/Time)
- [Jetty](http://www.eclipse.org/jetty/) (Web servers)
- [H2 Database](http://www.h2database.com/) (Database engine)
- [Swagger](http://swagger.io/) (interactive API documentation)

### For JUnit tests:
- Grizzly (as embedded web server)
- H2 Database (as embedded in-memory database)
- Jersey Client (as REST client)

## Getting Started
To run this RESTful web service you can just clone this repository and run
```
mvn jetty:run
```
Then, access your API docs in `http://localhost:8080/RestApp/docs`
