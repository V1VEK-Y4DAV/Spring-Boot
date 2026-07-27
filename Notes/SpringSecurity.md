# Spring Security Overview

Spring Security is a powerful and highly customizable security framework that is often used in Spring Boot applications **to handle authentication and authorization.**

---

## Core Concepts

### 1. Authentication
> **The process of verifying a user's identity** (e.g., username and password).

### 2. Authorization
> **The process of granting or denying access to specific resources or actions** based on the authenticated user's roles and permissions.

---

## Default Behavior in Spring Boot

* Once the dependency is added, Spring Boot's **auto-configuration** feature will automatically apply security to the application.
* By default, **all endpoints will be secured**.
* Spring Security will generate a default user with a **random password that is printed in the console logs on startup**.
* By default, Spring Security uses **HTTP Basic Authentication**.

---

## HTTP Basic Authentication Workflow

1. **Encoding:** Credentials are combined into a string like `username:password`, which is then encoded using **Base64**.
2. **Request Header:** The client sends an `Authorization` header:
   ```http
   Authorization: Basic <encoded-string> ```

3. Verification: The server decodes the string, extracts the username and password, and verifies them.If correct $\rightarrow$ Access is granted.If incorrect $\rightarrow$ An "Unauthorized" response is sent back.

## Configuration & Setup
```azure
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

# Customize Authentication

## Configuration Properties (`application.properties`)

```properties
spring.security.user.name=user
spring.security.user.password=password
 ```

## Security Configuration Class (SecurityConfig.java)

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/hello").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin();
    }
}
```
### Why we are creating this because
- now my all endpoints are secured, but I don't want every endpoint.
- we are having just one user I need to add more also

# Spring Security Notes

## 1. Basic Authentication & Statelessness
* Basic Authentication, by its design, is stateless.
* Some applications do mix Basic Authentication with session management for various reasons.
* This isn't standard behavior and requires additional setup and logic.
* In such scenarios, once credentials are verified via Basic Authentication, a session might be established, and the client is provided a session cookie.
* This way, the client won't need to send the `Authorization` header with every request, and the server can rely on the session cookie to identify the authenticated user.

---

## 2. Enabling Web Security & Configuration
* `@EnableWebSecurity` is an annotation that signals Spring to enable its web security support.
* This is what makes your application secured, and it is used in conjunction with `@Configuration`.
* `securityFilterChain` is a utility class in the Spring Security framework that provides default configurations and allows customization of certain features.
* By extending it, you can configure and customize Spring Security for your application needs.

---

## 3. Configuring Request Security (`HttpSecurity`)
* The configuration method provides a way to configure how requests are secured.
* It defines how request matching should be done and what security actions should be applied.

### Common Configuration Methods:
* `http.authorizeRequests():` Tells Spring Security to start authorizing requests.
* `.antMatchers("/hello").permitAll():` Specifies that HTTP requests matching the path `/hello` should be permitted (allowed) for all users, whether authenticated or not.
* `.anyRequest().authenticated():` A general matcher specifying that any request not matched by previous matchers should be authenticated (users must provide valid credentials).
* `.and():` A method to join several configurations, helping to continue configuration from the root (`HttpSecurity`).
* `.formLogin():` Enables form-based authentication, providing a default form to enter username and password, and redirecting unauthenticated users to the default login form.

---

## 4. Login and Logout Functionality
* Spring Security provides an in-built controller that handles the `/login` path.
* This controller is responsible for rendering the default login form when a `GET` request is made to `/login`.
* When `.formLogin()` is used in security configuration without specifying `.loginPage("/custom-path")`, the default login page becomes active.
* By default, Spring Security also provides logout functionality.
* When `.logout()` is configured, a `POST` request to `/logout` will log the user out and invalidate their session.

---

## 5. Session Management & Authentication Lifecycle
When you log in with Spring Security, it manages authentication across multiple requests despite HTTP being stateless:

1. **Session Creation:** After successful authentication, an HTTP session is formed, and authentication details are stored in it.
2. **Session Cookie:** A `JSESSIONID` cookie is sent to your browser and sent back with subsequent requests to help the server recognize your session.
3. **SecurityContext:** Using the `JSESSIONID`, Spring Security fetches authentication details for each request.
4. **Session Timeout:** Sessions have a limited life; if inactive past the limit, you are logged out.
5. **Logout:** When logging out, your session ends, and the related cookie is removed.
6. **Remember-Me:** Spring Security can remember you even after the session ends using a different persistent cookie (typically with a longer lifespan).

*In essence, Spring Security leverages sessions and cookies, mainly `JSESSIONID`, to ensure you remain authenticated across requests.*

# Spring Security with MongoDB Notes

## 1. Authentication with MongoDB Overview
* We want our Spring Boot application to authenticate users based on their credentials stored in a MongoDB database.
* This means that our users and their passwords (hashed) will be stored in MongoDB.
* When a user tries to log in, the system should check the provided credentials against what's stored in the database.

---

## 2. Required Components
To implement custom authentication with Spring Security and MongoDB, you need the following components:
* A `User` entity to represent the user data model.
* A repository `UserRepository` to interact with MongoDB.
* A `UserDetailsService` implementation to fetch user details.
* A configuration `SecurityConfig` to integrate everything with Spring Security.

---

# Spring Security Authentication Flow (HTTP Basic/Form Login)

This document explains the complete authentication flow in a Spring Boot application using Spring Security, `UserDetailsService`, and a database.

---

# High-Level Flow

```text
Client (Browser/Postman)
        │
        ▼
Security Filter Chain
        │
        ▼
Authentication Filter (BasicAuthenticationFilter / UsernamePasswordAuthenticationFilter)
        │
        ▼
AuthenticationManager
        │
        ▼
DaoAuthenticationProvider
        │
        ▼
UserDetailsServiceImpl
        │
        ▼
UserRepository
        │
        ▼
Database
        │
        ▼
UserDetails
        │
        ▼
PasswordEncoder.matches()
        │
        ▼
Authentication Successful
        │
        ▼
Controller
        │
        ▼
Service
        │
        ▼
Repository
        │
        ▼
Database
        │
        ▼
Response
```

---

# Step 1: Client Sends Request

Suppose Postman sends:

```http
GET /users
Authorization: Basic base64(mohit:12345)
```

or if using Form Login:

```http
POST /login

username=mohit
password=12345
```

The request **does not go directly to the controller**.

It first enters the **Spring Security Filter Chain**.

---

# Step 2: Security Filter Chain

Every incoming HTTP request passes through the Security Filter Chain.

```text
Incoming Request
        │
        ▼
Security Filter Chain
```

This chain contains many filters.

Some important ones are:

- BasicAuthenticationFilter
- UsernamePasswordAuthenticationFilter
- CsrfFilter
- AuthorizationFilter
- ExceptionTranslationFilter

Spring automatically creates these filters.

---

# Step 3: Authentication Filter

Depending on your configuration:

```java
http.httpBasic(Customizer.withDefaults());
```

Spring uses

```
BasicAuthenticationFilter
```

If you use

```java
http.formLogin(Customizer.withDefaults());
```

Spring uses

```
UsernamePasswordAuthenticationFilter
```

These filters extract the username and password.

Example:

```
Authorization:
Basic bW9oaXQ6MTIzNDU=
```

↓

decoded into

```
username = mohit
password = 12345
```

---

# Step 4: UsernamePasswordAuthenticationToken

The filter creates an Authentication object.

```java
UsernamePasswordAuthenticationToken token =
    new UsernamePasswordAuthenticationToken(
        "mohit",
        "12345"
    );
```

This object only contains the credentials.

At this point, the user is **NOT authenticated**.

---

# Step 5: Who Calls AuthenticationManager?

This is one of the most important concepts.

You never write:

```java
authenticationManager.authenticate(...);
```

when using HTTP Basic or Form Login.

Instead,

**BasicAuthenticationFilter** calls it automatically.

Internally Spring executes something like:

```java
authenticationManager.authenticate(token);
```

Flow:

```text
Client
   │
   ▼
BasicAuthenticationFilter
   │
   ▼
AuthenticationManager.authenticate()
```

---

# Step 6: AuthenticationManager

AuthenticationManager itself does not verify usernames or passwords.

Its job is to delegate authentication to an AuthenticationProvider.

```text
AuthenticationManager
        │
        ▼
AuthenticationProvider
```

The default implementation is

```
DaoAuthenticationProvider
```

---

# Step 7: DaoAuthenticationProvider

This provider is responsible for database authentication.

It calls

```java
loadUserByUsername(username)
```

on your custom

```java
UserDetailsServiceImpl
```

Flow:

```text
AuthenticationManager
        │
        ▼
DaoAuthenticationProvider
        │
        ▼
UserDetailsServiceImpl
```

---

# Step 8: UserDetailsServiceImpl

Your implementation:

```java
@Service
public class UserDetailsServiceImpl
        implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = repository.findByUserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}
```

Responsibilities:

- Search the database
- Find the user
- Convert your User entity into Spring Security's UserDetails

---

# Step 9: Repository

```java
repository.findByUserName(username);
```

Database:

| ID | Username | Password | Roles |
|----|----------|----------|-------|
| 1 | mohit | BCrypt Hash | USER |

If the user is not found:

```java
throw new UsernameNotFoundException(...);
```

Spring returns

```
401 Unauthorized
```

---

# Step 10: UserDetails

Spring Security understands only UserDetails.

It does NOT understand your User entity.

So we convert

```
User Entity
```

↓

into

```
UserDetails
```

which contains

- Username
- Encrypted Password
- Roles
- Authorities

---

# Step 11: Password Verification

Suppose the user typed

```
12345
```

Database contains

```
$2a$10$Wm0....
```

Spring automatically calls

```java
passwordEncoder.matches(
        enteredPassword,
        storedPassword
);
```

Internally

```java
passwordEncoder.matches(
        "12345",
        "$2a$10$Wm0..."
);
```

You NEVER compare passwords manually.

---

# Step 12: Authentication Successful

If passwords match

Spring creates

```java
Authentication authentication
```

and marks it as

```
Authenticated = true
```

Then it stores it in

```
SecurityContextHolder
```

Current request now knows

```
Current Logged-in User
```

---

# Step 13: Authorization

Now Spring checks

```java
.authorizeHttpRequests(...)
```

Example

```java
.authorizeHttpRequests(auth -> auth

.requestMatchers(HttpMethod.POST,
"/users/create")
.permitAll()

.anyRequest()
.authenticated()

)
```

Meaning

```
POST /users/create

↓

Anyone

------------------------

GET /users

↓

Authenticated User

------------------------

PUT /users/1

↓

Authenticated User

------------------------

DELETE /users/1

↓

Authenticated User
```

If authentication fails

```
401 Unauthorized
```

If authenticated but lacking permissions

```
403 Forbidden
```

---

# Step 14: Controller Executes

Only after authentication and authorization succeed does Spring call

```java
UserController
```

Example

```java
@GetMapping("/users")
public List<User> getAll() {

    return service.getAll();

}
```

Flow

```
Controller

↓

Service

↓

Repository

↓

Database

↓

Response
```

---

# Complete Internal Flow

```text
Client
   │
   ▼
Security Filter Chain
   │
   ▼
BasicAuthenticationFilter
   │
   ▼
Extract Username & Password
   │
   ▼
UsernamePasswordAuthenticationToken
   │
   ▼
AuthenticationManager.authenticate()
   │
   ▼
DaoAuthenticationProvider
   │
   ▼
UserDetailsServiceImpl
   │
   ▼
UserRepository
   │
   ▼
Database
   │
   ▼
User Entity
   │
   ▼
UserDetails
   │
   ▼
PasswordEncoder.matches()
   │
   ▼
Authentication Successful
   │
   ▼
SecurityContextHolder
   │
   ▼
Authorization Check
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Database
   │
   ▼
Response
```

---

# SecurityConfig Explained

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
```

## @Configuration

Marks this class as a Spring configuration class.

Spring scans it and creates all beans inside it.

---

## @EnableWebSecurity

Enables Spring Security.

Without it, Spring Security configuration won't be applied.

---

## PasswordEncoder Bean

```java
@Bean
public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();

}
```

Purpose:

- Encrypt passwords
- Verify passwords during login

Example

```
12345

↓

$2a$10$abcxyz...
```

---

## AuthenticationManager Bean

```java
@Bean
public AuthenticationManager authenticationManager(
AuthenticationConfiguration configuration)
throws Exception {

    return configuration.getAuthenticationManager();

}
```

Purpose:

Expose Spring's AuthenticationManager as a bean.

Normally Spring Security uses it internally.

You only inject it yourself for custom login endpoints like JWT authentication.

---

## userDetailsService()

```java
.userDetailsService(userDetailsService)
```

Tells Spring

```
Do NOT use in-memory users.

Use my database.
```

---

## authorizeHttpRequests()

```java
.authorizeHttpRequests(auth -> auth

.requestMatchers(HttpMethod.POST,
"/users/create")
.permitAll()

.anyRequest()
.authenticated()

)
```

This defines authorization rules.

Example

```
POST /users/create

↓

Permit Everyone

GET /users

↓

Login Required

PUT /users

↓

Login Required

DELETE /users

↓

Login Required
```

---

## httpBasic()

```java
http.httpBasic(Customizer.withDefaults());
```

Enables HTTP Basic Authentication.

Example

```
Authorization

Basic base64(username:password)
```

Useful for Postman and REST APIs.

---

## formLogin()

```java
http.formLogin(Customizer.withDefaults());
```

Enables Spring Security's default login page.

If you visit

```
localhost:8080/users
```

without authentication,

Spring automatically redirects to

```
/login
```

where a default login page is shown.

---

# UserDetailsServiceImpl Explained

```java
@Service
public class UserDetailsServiceImpl
        implements UserDetailsService
```

This class acts as the bridge between Spring Security and your database.

Responsibilities:

- Load user by username
- Fetch encrypted password
- Fetch user roles
- Convert User entity into UserDetails

Spring Security automatically calls

```java
loadUserByUsername(username);
```

during authentication.

You never call this method manually.

---

# Summary

```text
Request

↓

Security Filter Chain

↓

Authentication Filter

↓

AuthenticationManager

↓

DaoAuthenticationProvider

↓

UserDetailsServiceImpl

↓

Database

↓

Password Verification

↓

Authentication Success

↓

Authorization Rules

↓

Controller

↓

Service

↓

Repository

↓

Database

↓

Response
```

This is the complete authentication and authorization lifecycle for a Spring Boot application using Spring Security with HTTP Basic or Form Login.
