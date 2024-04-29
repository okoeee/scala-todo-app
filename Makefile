
# init
init:
	docker-compose up -d

# stop
stop:
	docker-compose stop

# start
start:
	docker-compose start

# down with volume
down:
	docker-compose down -v
