DOCKER_COMP_CMD=docker compose
DOCKER_CMD=docker

.FORCE:

back: .FORCE
	./gradlew build -x test && docker build -t disk91/hip94 .

build: back

start:
	$(DOCKER_COMP_CMD) up -d

stop:
	$(DOCKER_COMP_CMD) stop

