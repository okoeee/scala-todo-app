
# MySQL
## init
init:
	docker-compose up -d mysql

## stop
stop:
	docker-compose stop mysql

## start
start:
	docker-compose start mysql

## down with volume
down:
	docker-compose down -v

# SchemaSpy
up/schemaspy:
	java -jar schemaspy/schemaspy-6.2.4.jar -configFile schemaspy/schemaspy.properties -vizjs
