# API documentation of BasicBike

The API in this documentation is focused on bike rental business. 
There is no bike management API as it is outside the scope.

## Policy of the API
- To protect privacy, no one can view ID of the renter. However, users can still 
  view if the bike is rented or not.
- This API is intended for staffs working for BasicBike. Therefore, users must
  authenticate before requesting any endpoint except for getting the token.
- Registration of staffs are not handled by this API. Users can only get token from
  given username and password. Users can change password in this API.


## Authentication

Users must provide token in the authorization header. The token can be retrieved 
from [get token](#get-token) endpoint.

```http request
Authorization: Bearer <your-token-here>
```

## Schema

### Bike
In this context, it means physical bike with rental status. Each bike has
a ID and item ID prefixed with BB.

| name            | type      | description               |
|-----------------|-----------|---------------------------|
| `id`            | `int`     | ID used to identify bike. |
| `bikeId`        | `string`  | Item ID of the bike       |
| `model`         | `string`  | Model of the bike         |
| `isRented`      | `boolean` | Status of rental          |
| `rentStartTime` | `string`  | Starting time of rental   |

### Rental request
To rent a bike, users must submit a rental request.

| name            | type     | description                                                             |
|-----------------|----------|-------------------------------------------------------------------------|
| `renterId`      | `string` | ID of the renter. It can be either Thai national ID or passport number. |
| `rentStartTime` | `string` | Starting time of rental.                                                | 

## API endpoints

### Get token

Return token from given username and password.

```http request
POST /token/
```

#### Request parameters
Must be passed in request body in JSON format.

| name       | type   | description                            |
|------------|--------|----------------------------------------|
| `username` | string | Username given upon staff registration |
| `password` | string | Password of the username               |

#### Response

The response is in JSON format containing either token or error message.

Status code 200

```json
{
  "token": "token-here"
}
```

Status code 404

```json
{
  "error": "Incorrect username or password"
}
```

### Get all bikes

Return all bikes available.

```http request
GET /bikes/
```

#### Response

The response is a JSON array of bike or error message.

See [bikes](#bike) for detail of schema.

Status code 200
```json
[
  {
    "id": 0,
    "bikeId": "BB000",
    "model": "Lorem Ipsum",
    "isRented": false,
    "rentStartTime": null
  }
]
```

Status code 503
```json
{
  "message": "Internal Server Error"
}
```

Status code 401
```json
{
  "message": "Unauthorized"
}
```

### Rent a bike

Send a rental request to rent a bike

```http request
POST /bikes/{id}
```

`id` is the bike ID of bike to rent.

#### Request

Users must send a [rental request](#rental-request) in the request body.

```json
{
  "renterId": "1111111111111",
  "rentStartTime": "12/12/2022 08:00:00"
}
```

#### Response

The response is a message indicating either success or failure.

Status code 200

```json
{
  "status": "success"
}
```

Status code 401

```json
{
  "status": "error",
  "message": "Unauthorized"
}
```

Status code 404

```json
{
  "status": "error",
  "message": "Bike not found"
}
```