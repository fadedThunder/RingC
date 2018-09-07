# RingC


# Approach

### 1) Used various http libraries.
### 2) Used Rest Assured libraries.
### 3) TestNg

## The framework has
### 1) common : com.rinCentral.commom
### 2) test   : com.rinCentral.test

## TestBase Class:
In common package TestBase class contains all the common classes
like geter setter for environment variables
common authurization authentication methods(using Base64 etc), getAccess token, get getSHA256Hash used for authentication purpose
get responce (rest assured)
get response (using json object)
common- get, post call
written methods: common getCalls using Restassured etc

## TestConstantClass
Consists of all the properties used in the framework for testing

## TestCases
### Sample test case- RestAssured
#### Checking status code, Checking value - RestAssured.
