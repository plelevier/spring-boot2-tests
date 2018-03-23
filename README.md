# Spring Boot 2 WebFlux + HTTP/2 in Undertow

## POST example
- Fails with a `StackOverflowError` on the server side and an error code 500
    - `nghttp -v --data=test.json https://localhost:8443`

## GET example
- Works as expected
    - `nghttp -v https://localhost:8443`