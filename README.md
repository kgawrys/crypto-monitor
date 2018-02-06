# Crypto monitor

This projects periodically polls an Coin market cap API and saves it's data in Postgres. 
Based on this data, application will present cryptocurrencies that increased their rank greatly in a given time period. 

Coin market cap: https://coinmarketcap.com/all/views/all/

The project also serves as a playground for learning kubernetes and google cloud engine deployment and react as a frontend.

## Technologies used

- Akka-http
- Akka-actors
- Akka-quartz-scheduler
- Slick

## TODO
- react frontend
- kubernetes GCP deployment

## Prerequisites

## How to run
`sbt run`