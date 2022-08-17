# Hypo Fin Banking API

This is a fake banking system API.

# The API

### Identities

The identities API is used to authenticate and identify a user.

#### Create

To authenticate a user:

```
clientId=c0002
---
POST /identities
{
  "login": "fred",
  "password": "p@$$word"
}
```

RESPONSE

```
200 OK
{
  "uid": "u12345",
  "token": "t1234"
}
```

#### Delete

To end a user's session:

```
clientId=c0002
---
DELETE /identities/98p87iuryhgli487$$y
```

RESPONSE

```
204 NO CONTENT
```

### Accounts

The accounts API is used to retrieve information about the current user's accounts.

#### List

```
clientId=c0002
token=t1234
---
GET /accounts
```

RESPONSE

```
200 OK
[
  {
    "id": "a0001",
    "desc": "Fake Checking",
    "t": "CHK",
    "bal": 1867.37
  },
  {
    "id": "a0003",
    "desc": "Fake Savings",
    "t": "SAV",
    "bal": 17099.33
  }
]
```

#### Get

```
clientId=c0002
token=t1234
---
GET /accounts/a0001
```

RESPONSE

```
200 OK

{
  "id": "a0001",
  "desc": "Fake Checking",
  "t": "CHK",
  "bal": 739.77
}
```
