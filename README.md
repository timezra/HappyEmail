Happy Email [![Build Status](https://travis-ci.org/timezra/happy_email.png)](https://travis-ci.org/timezra/happy_email)
==================

An API endpoint that accepts first name, last name, and email address (JSON encoded) to generate either a happy birthday or a happy holidays email. This service is able to send this email to the recipient using one of two third party providers. It provides an abstraction between two different email service providers. If one of the services goes down, this service fails over to a different provider without affecting customers.

Configuration:
----------------------------------------------------
In order to build and deploy this service, you will need to drop an application.properties file into the src/main/resources folder. This file contains email-provider specific connection data. For example,

```properties
timezra.samples.happy_email.Emailer.from=timezra@gmail.com

timezra.samples.happy\_email.PrimaryMailSender.host=smtp.mailgun.org
timezra.samples.happy\_email.PrimaryMailSender.port=587
timezra.samples.happy\_email.PrimaryMailSender.username=<YOUR_MAILGUN_USERNAME>
timezra.samples.happy\_email.PrimaryMailSender.password=<YOUR_MAILGUN_PASSWORD>

timezra.samples.happy\_email.SecondaryMailSender.host=smtp.mandrillapp.com
timezra.samples.happy\_email.SecondaryMailSender.port=587
timezra.samples.happy\_email.SecondaryMailSender.username=<YOUR_MANDRILLA_USERNAME>
timezra.samples.happy\_email.SecondaryMailSender.password=<YOUR_MANDRILLA_PASSWORD>
```

Usage:
----------------------------------------------------
You can access the restful endpoints using any HTTP client

### Examples: ###

#### Send Happy Birthday Email
* URL: http://localhost:8080/email/birthday
* Params: locale \[Optional\] \(currently only en\_US and hu\_HU are supported\)
* Method: POST
* Body:
```json
{
    "firstName": "Tim",
    "lastName": "Myer",
    "emailAddress": "timezra@gmail.com"
}
```
* Returns: the UUID of the email that was sent, along with a "Location" header indicating where you can look for the resource.

#### Send Happy Holidays Email
* URL: http://localhost:8080/email/holidays
* Params: locale \[Optional\] \(currently only en\_US and hu\_HU are supported\)
* Method: POST
* Body:
```json
{
    "firstName": "Tim",
    "lastName": "Myer",
    "emailAddress": "timezra@gmail.com"
}
```
* Returns: the UUID of the email that was sent, along with a "Location" header indicating where you can look for the resource.

#### Get Email That Has Been Sent
* URL: http://localhost:8080/email/0073c1e0-eedc-4727-b619-502c827124b4
* Method: GET
* Returns:
```json
{
    "id": "419840a7-0609-45f4-86ef-17cbffa5bd06",
    "recipient": {
        "firstName": "Tim",
        "lastName": "Myer",
        "emailAddress": "timezra@gmail.com"
    },
    "type": "birthday",
    "locale": "en_US",
    "body": "<html>\ <body>\ <h3>Happy birthday, Tim Myer!</h3>\ </body>\ </html>\ "
}
``` 

### Docker:
----------------------------------------------------
This project is configured to run within a docker container. To build and deploy the project inside a docker container, run the following commands:

```bash
> mvn clean package docker:build
> docker run -p 8080:8080 -t timezra/timezra.samples.happy_email
```

To deploy this application to docker hub, you will need a docker hub account. You can deploy it using a command similar to the following:

```bash
> sudo docker push <YOUR_DOCKER_ACCOUNT>/timezra.samples.happy_email
```

To pull the latest docker image from docker hub, run the following:
```bash
> docker pull timezra/timezra.samples.happy_email
```
