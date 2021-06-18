# Server 

Dieses `docker-compose.yml` started die gesamte Anwendung inkl. der zugehörigen Datenbank
und des Keycloaks und ist dafür gedacht, die Anwendung auf der szut-Infrastruktur zu starten.

```sh 
$ docker compose up -d
```

Nach dem Starten ist es notwendig, einen Benutzer für den `customer-management-service`
im Keycloak anzulegen:

1. Login auf `http://localhost:8080/auth/` mit `admin` und `admin`.
2. Benutzer `larmic` anlegen mit Passwort `test` und Rolle `user` und deaktiveren von `temporary password`