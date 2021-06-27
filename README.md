app works on port: 8050

added auth0 to generate Bearer Tokens
so I authorize tokens from there.

auth0.audience=https://dev-celil.eu.auth0.com/api/v2/
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://dev-celil.eu.auth0.com/

build app with gradle

command -> gradlew clean build

Docker Image info 

As  I am new to dockers. Here how I managed to work in my local with docker


command ->  docker network create springbootmongo

command ->  docker run -d --name mongocontainer --network=springbootmongo -v ~/mongo-data:/data/db mongo

command ->  docker build -t readingisgoodapp:latest .

command ->  docker run -p 8050:8050 --name readingisgoodappcontainer --network=springbootmongo readingisgoodapp

now APP and mongo is working on two different containers. You can reach swagger from localhost:8050/swagger-ui.html
You can use postman collection by importing /Reading Is Good.postman_collection.json file 

There is a request named 'Get Bearer Token and Set to GLOBAL VARIABLE' in the collection. It will get token and set as global variable {{token}}

You will need Bearer tokens to reach the other endpoints.

You can get using POST https://dev-celil.eu.auth0.com/oauth/token

with body 
{
    "client_id": "3EeOJ8PiWsETtZ2hasYxYKRquuydSROL",
    "client_secret": "qWzXhJSuSn2XJew2cjNG7x3oNI9JYGiVcgkM22AHru3CRoxzR4Wi-ycTu3kS_-XF",
    "audience": "https://dev-celil.eu.auth0.com/api/v2/",
    "grant_type": "client_credentials"
}
