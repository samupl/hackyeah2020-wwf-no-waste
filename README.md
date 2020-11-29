# hackyeah2020-wwf-no-waste

## Requirements

* Docker
* Docker Compose

## Build

```
docker-compose build
```

## Run

```
# First run database in the background
docker-compose up -d db

# Then run the backend to initialize the db
docker-compose up backend

# Stop the above by pressing CTRL-C
# Stop the DB, just in case
docker-compose down

# Start everything up
docker-compose up
```

## Addresses

* Admin UI: http://127.0.0.1:8000
* Backend: http://127.0.0.1:8080
