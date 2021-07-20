# Assignment

### Prerequisites

* Java 8
* Maven 3

### Installing

```
mvn clean package
```

execute

```
mvn spring-boot:run
```

### Swagger UI

The documentations for the APIs can be found at

```
localhost:8081/swagger-ui.html
```

### PostMan

Some of the Response body, would not work so here are the details.

Add User
```
{"user":{
      "userName": "Joh Doe",
      "password": "password",
      "emailAddress": "john@gmail.com",
      "preferredPhoneNumber": "98750666",
      "phones":[
         {
            "phoneName": "Johns Phone",
            "phoneNumber": "907868950",
            "phoneModel": "Iphone"
         }
         ]
}
}
```

### H2 Database Link

```
http://localhost:8081/h2-console/
```
