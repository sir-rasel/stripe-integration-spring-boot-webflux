# stripe-integration-spring-boot-webflux

### Intro:

    This is a almost production ready project for stripe api gateway 
    integration using spring boot and postgresql. Anyone can use this
    with little modification based on their requirment.

### Technology used:

- Java
- Spring boot
- Reactive programming or Web-flux
- Postgresql
- Docker
- Reactive Db Repository
- Web-flux spring security (JWT authentication)

### Pre-requisites:

- JDK 17
- IDE (intellij recommended)
- Docker version 24+
- API testing client (postman recommended)

### Build instructions:

- Clone the repo
- Open in IDE and load the `pom.xml` file dependency
- run `docker-compose up --build` command at the project root directory

### Testing or run process:

- run `docker-compose up --build` command at the project root directory
  (you can skip this step if you already run this command)
- set active profile for application run
- set the following property on `resources` folder by your stripe keys
    - `stripe.key.public`
    - `stripe.key.private`
    - `stripe.key.webhook`
- if you use postman then import the postman collections from `resources` folder
- run the project from the IDE or cli

### Auth

- Code will insert 1 user data when start, you can login using following credentials
    - Email : abc.test@abc.com
    - password : 12345

### Functionality:

- Login
- Registration
- Stripe
    - Customer
    - PaymentIntent
    - PaymentMethod
    - Product
    - ProductPrice
    - Subscription
    - Webhook