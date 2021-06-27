app works on port: 8050

added auth0 to generate Bearer Tokens
so I authorize tokens from there.

auth0.audience=https://dev-celil.eu.auth0.com/api/v2/
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://dev-celil.eu.auth0.com/

Docker Image 

