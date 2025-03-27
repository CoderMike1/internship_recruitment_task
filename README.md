# REMITLY Summer Internship 2025 Recruitment Exercise


![Java](https://img.shields.io/badge/java-red?style=for-the-badge&logo=java)
![Docker](https://img.shields.io/badge/Docker-blue?style=for-the-badge&logo=docker)
![Postgresql](https://img.shields.io/badge/POSTGRESQL-yellow?style=for-the-badge&logo=postgresql)

## 📝 **Table of contents**
* [General info](#-general-info)
* [Setup](#-setup)
* [Run](#-run)
* [Test](#-test)
---

## General Info

This application was made for the recruitment process at Remitly.
It exposes a REST API that manages SWIFT codes and allows users to:
- retrieve bank information by SWIFT code or country code
- add new Swift Code
- delete records

All data is stored in PostgreSQL database.<br>
Application can be deployed on any device thanks to docker containerization.


## Setup

Clone the repository and select proper directory

```
git clone https://github.com/CoderMike1/internship_recruitment_task.git

cd internship_recruitment_task
```

Run docker compose, make sure to have your docker installed and you are logged in

```
docker login    
docker-compose up --build
```

After a while, app is launched and ready!<br>
It's listening on http://localhost:8080/

## Run


### 1. Retrieve information of a bank by swift code

#### Request:
- Request method:<br>
``
GET /v1/swift-codes/{swift-code}
``
- Example request:<br>
``
http://localhost:8080/v1/swift-codes/BIGBPLPWCUS
``
#### Response:
- Response success code:<br>
``200``
- Response structure:<br>
    - for headquarter swift code
    ```
   {
    "address": string,
    "bankName": string,
    "countryISO2": string,
    "countryName": string,
    "isHeadquarter": bool,
    "swiftCode": string
    “branches”: [
             {
               "address": string,
               "bankName": string,
               "countryISO2": string,
               "isHeadquarter": bool,
               "swiftCode": string
             },
             {
               "address": string,
               "bankName": string,
               "countryISO2": string,
               "isHeadquarter": bool,
               "swiftCode": string
             }, . . .
               ]
    }
    ```
    - for branch swift code
  ```
  {
    "address": string,
    "bankName": string,
    "countryISO2": string,
    "countryName": string,
    "isHeadquarter": bool,
    "swiftCode": string
  }
  ```
- Response example:<br>
    - for headquarter swift code
  ```
    {
    "address": "LOPUSZANSKA BUSINESS PARK LOPUSZANSKA 38 D WARSZAWA, MAZOWIECKIE, 02-232",
    "bankName": "ALIOR BANK SPOLKA AKCYJNA",
    "countryISO2": "PL",
    "countryName": "POLAND",
    "isHeadquarter": true,
    "swiftCode": "ALBPPLPWXXX",
    "branches": [
        {
            "address": "LOPUSZANSKA BUSINESS PARK LOPUSZANSKA 38 D WARSZAWA, MAZOWIECKIE, 02-232",
            "bankName": "ALIOR BANK SPOLKA AKCYJNA",
            "countryISO2": "PL",
            "isHeadquarter": false,
            "swiftCode": "ALBPPLPWCUS"
        }
      ]
    }
    ```
   - for branch swift code
  ```
  {
    "address": "  WARSZAWA, MAZOWIECKIE",
    "bankName": "ALIOR BANK SPOLKA AKCYJNA",
    "countryISO2": "PL",
    "countryName": "POLAND",
    "isHeadquarter": false,
    "swiftCode": "ALBPPLP1BMW"
  }  
  ```

### 2. Retrieve information of a banks by country code

#### Request:
- Request method:<br>
  ``
  GET /v1/swift-codes/country/{countryISO2code}
  ``
- Example request:<br>
  ``
  http://localhost:8080/v1/swift-codes/country/PL
  ``
#### Response:
- Response success code:<br>
  ``200``
- Response structure:<br>
    ```
    {
    "countryISO2": string,
    "countryName": string,
    "swiftCodes": [
        {
            "address": string,
    		 "bankName": string,
    		 "countryISO2": string,
    		 "isHeadquarter": bool,
    		 "swiftCode": string
        },
        {
            "address": string,
    		 "bankName": string,
    		 "countryISO2": string,
    		 "isHeadquarter": bool,
    		 "swiftCode": string
        }, . . .
      ]
    }
    ```
- Response example:
    ```
  {
    "countryISO2": "CA",
    "countryName": "CANADA",
    "swiftCodes": [
        {
            "address": "Gold 89b",
            "bankName": "GoldenBank",
            "countryISO2": "CA",
            "isHeadquarter": false,
            "swiftCode": "AABBCCDDEE99"
        }
      ]
  }
    ```