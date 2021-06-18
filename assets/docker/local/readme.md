# Lokale Entwicklung

Dieses `docker-compose.yml` started die notwendige Datenbank um lokal auf 
dem eigenen Rechner entwickeln und testen zu können. 

## Vorbereitung Keycloak

`realm`, `clients` und `roles` sind bereits in der [realm-szut.json](keycloak_imports/realm-szut.json) enthalten. 
Die User müssen manuell angelegt werden.

```sh 
$ docker compose up -d
```

Hinweis: Beim ersten Starten dauert es ein wenig bis die URL aufrufbar ist.

1. Login auf `http://localhost:8080/auth/` mit `admin` und `admin`.
2. Benutzer `larmic` anlegen mit Passwort `test` und Rolle `user` und deaktiveren von `temporary password`

## Keycloak testen

```sh 
$ curl -X POST '<KEYCLOAK_SERVER_URL>/auth/realms/<REALM_NAME>/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=<CLIENT>' \
 --data-urlencode 'username=<USER>' \
 --data-urlencode 'password=<PASSWORD>'
```

```sh 
$ curl -X POST 'http://localhost:8080/auth/realms/szut/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=customer-management-service' \
 --data-urlencode 'username=larmic' \
 --data-urlencode 'password=test'
``` 
