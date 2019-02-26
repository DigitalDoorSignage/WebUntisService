# WebUntisService

Java SE Application that periodically checks the webuntis api for updates and manages the epapers via mqtt

# Webuntis API

The webuntis api uses jsonrpc.

## Placeholders explained

### Base URL

`<PREFIX>.webuntis.com/WebUntis/jsonrpc.do`

### Prefix

I think the prefix is school internal. So basically we would use 'mese'.

### School

This value is the same as the name you would enter in the search box below but it is urlencoded.

TODO: INSERT IMAGE

### Encoded School

The school name base64 encoded.

### Id

I don't think that the id is important to make successful requests, but I still included it for completeness.

We use a uuid which is generated at startup.

### Username

Username for webuntis

### Password

Password for webuntis

### Client

Same as Id.

We use 'MY CLIENT'

### Session Id

This id is returned by the authenticate method and has to be valid to make request that require authentication.

## Login

### Description

Login to Webuntis to acquire a Session Id which can be used to make requests to the authentication protected API.

### Request URL

`<BASEURL>?school=<SCHOOL>`

### Request Method

`POST`

### Request Body

```Json
{
    id: "<ID>",
    method: "authenticate",
    params: {
        user: "<USERNAME>",
        password: "<PASSWORD>",
        client: "<CLIENT>"
    },
    jsonrpc: "2.0"
}
```

### Response Body

TODO: Incomplete

```Json
{
    sessionId: "<SESSIONID>"
}
```

## Logout

### Request URL

`<BASEURL>?school=<SCHOOL>`

### Request Method

`POST`

### Request Body

```Json
{
    id: this.id,
    method: "logout",
    params: {},
    jsonrpc: "2.0"
}
```

## Latest Import Time

### Request URL

`<BASEURL>?school=<SCHOOL>`

### Request Method

`POST`

### Request Headers

```Json
{
    "Cookie": "JSESSIONID=<SESSIONID>; SCHOOLNAME=<ENCODEDSCHOOL>"
}
```

### Request Body

```Json
{
    id: "<ID>",
    method: "getLatestImportTime",
    params: {},
    jsonrpc: "2.0"
}
```
