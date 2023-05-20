# Spring Boot Microservices

This repository is about developing a microservices-based app. More specifically:

1. Use the MVC pattern to design and develop a RESTful microservice that receives text through an HTTP request, redacts it and sends it back in pdf format as a response. The microservice should be discoverable through a registration service (e.g. Netflix’s Eureka service). The redaction should handle at least names, email addresses, and street addresses.

2. Use the MVC pattern to design and develop a web server that obtains input from a UI, generates an appropriate HTTP request containing the text, and sends it over to the redaction microservice. It then receives and saves the returned pdf.