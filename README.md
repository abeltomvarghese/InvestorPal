# InvestorPal

## Introduction 

This is a **Prototype** application that was developed as part of my BSc dissertation. The application uses: <br> 
> Java Spring Backend <br>
> Python Script for data visualisation <br>
> Postgres database hosted on a Docker Image. <br>

## Setting up & Running the application 

### Java Spring 
This program uses the Maven Build tool to manage the different Java Packages that are used throughout this project<br>
Ensure you have Java 8 installed & the following dependencies for the Java Spring application <br> 
- Java Spring Boot 
    - spring-boot-starter-web
    - spring-boot-starter-jdbc
    - spring-boot-starter-data-jpa
    - spring-boot-starter-test
- Apache Commons
    - commons-collections4
    - commons-lang3
- Org postgresql
    - postgresql
    
- flywaydb
    - flyway-core

- fasterxml
    - jackson-annotations

- waikato
    -timeseriesForecasting (1.1.27)

### Python Script 
Ensure you have the following dependencies installed 
- Bokeh 

> Remember to create a VENV with the packages mentioned above


### Database 
Install a docker image using the following instruction: <br>
`docker run --name postgres-demo -e POSTGRES_PASSWORD=Welcome -p 5432:5432`

> IMPORTANT: If you are running Docker from Windows, you may require [Oracle VM](https://www.virtualbox.org/)

### Running the program
1. Ensure the docker image is running & you can access the postgres database via the CLI 
2. Ensure the Java Spring application is also running 
3. Start the python script from the CLI by: 
    1. navigate to the scripts folder
    2. execute the command `. activate`
    3. navigate to the directory with the main.py file & run the commmand `python main.py`
