DOCKER_COMP_CMD=docker compose
DOCKER_CMD=docker
API_HOST=

.FORCE:

do_nothing: .FORCE
	echo "nothing to do with make w/o paramerters"

install:
	mkdir -p /helium-etl/hip94-run/
	mkdir /helium-etl/hip94-run/mongo_data
	mkdir /helium-etl/hip94-run/mongo_dump
	mkdir /helium-etl/hip94-run/cache-files
	mkdir /helium-etl/hip94-run/nginx
	mkdir /helium-etl/hip94-run/front
	cp ./config/default.conf /helium-etl/hip94-run/nginx/

back: .FORCE
	./gradlew build -x test && docker build -t disk91/hip94 .

front: .FORCE
	export API_HOST=$(API_HOST); \
	cd nuxt/hip94 && yarn install --ignore-engines && yarn generate && cp -R dist/* /helium-etl/hip94-run/front/

build: front back

start:
	$(DOCKER_COMP_CMD) up -d

stop:
	$(DOCKER_COMP_CMD) stop

