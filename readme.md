# customer-management-service

Dieses Projekt dient als Unterstützung für den [Workshop](https://github.com/FA-Team-SZUT/workshop-neusta-2021-06-21_und_22)
am 21.06. bzw. 22.06.2021.

Der Workshop setzt sich aus 7 Kapiteln zusammen. Jeder Teil wird zunächst einzeln 
vorgestellt und soll dann hier im Rahmen umgesetzt werden.

## Technische Voraussetzungen

* Java 11
* Maven 3.x 
* GIT
* Docker + Docker Compose

## Durchführung

1. Jedes Kapitel wird zunächst in dem Workshop einzeln vorgestellt
2. Jede:r Teilnehmer:in (oder eine Gruppe) entwickelt im eigenen Branch eine Lösung für das jeweilige Kapitel
3. Ein:e Teilnehmer:in (oder eine Gruppe) stellt eine Lösung vor
4. Ggf. wird die Musterlösung vorgestellt
5. Das nächste Kapitel wird vorgestellt

## Aufgabe

![Aufgabe](assets/goal.png)

Erstelle die Anwendung `customer-management-service`. Ein Customer enthält dabei die 
Attribute `id`, `name`, `company`, `createDate` und `lastUpdateDate`.

Es muss möglich sein, über REST einen Customer anzulegen und zu löschen. Die Attribute
`name` und `company` sollen änderbar sein. Die Datumswerte werden automatisch bestimmt.

Die REST-Schnittstelle soll über einen [Keycloak](https://www.keycloak.org/) abgesichert sein.
Dieser stellt die Rolle `user` und einen Nutzer `szut` bereit.

Die Daten liegen persitent in einer [PostgreSQL](https://www.postgresql.org/).

Alle Komponenten (service, Keycloak und PostgreSQL) können über ein 
[Docker Compose](https://docs.docker.com/compose/) gestartet und gestoppt werden.

## Musterlösungen

Die Musterlösungen stehen jeweils als eigener Branch (`muster-loesung-kapitel-x`) bereit.

