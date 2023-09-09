DOCKER_COMP_CMD=docker compose
DOCKER_CMD=docker

.FORCE:

do_nothing: .FORCE
	echo "nothing to do with make w/o paramerters"

install:
	mkdir -p /helium-etl/hip94-run/
	mkdir /helium-etl/hip94-run/mongo_data
	mkdir /helium-etl/hip94-run/mongo_dump
	mkdir /helium-etl/hip94-run/cache-files

back: .FORCE
	./gradlew build -x test && docker build -t disk91/hip94 .

build: back

start:
	$(DOCKER_COMP_CMD) up -d

stop:
	$(DOCKER_COMP_CMD) stop

