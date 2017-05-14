# Getting started with Play (Java)

Operation which are present
* This project demonstrate how to create a simple CRUD application with Play. A screencast to the project exist on [playframework.com](http://playframework.com)
* Create sample json response for REST APIs
* Sample scrape code


Useful commands
* Running play in activator ui mode: "activator ui"

## Some concepts:
* The Java Persistence API (JPA) is a Java specification for accessing, persisting, and managing data between Java objects / classes and a relational database. 
* JDBC is a standard for Database Access, JPA is a standard for ORM
* There is no built-in JPA implementation in Play; you can choose any available implementation.
* Every JPA call must be done in a transaction so, to enable JPA for a particular action, annotate it with @play.db.jpa.Transactional. 