# Because we will forget... Makefile requires 'tab' before command line call.

# Usage:
# For rust work, just do `make test-rust` to force build and tests
# For code deployed into localstack, do `make test` to force build, deploy and test

# Installs locally, we'll use deploy for AWS. Note for cdk deploy, the use of hotswap-fallback (or hotswap) is bad
# in a production environment. It is only used here to speed up development.
install: up node_modules bootstrap deploy

bootstrap:
	. config/env-dev && cdklocal bootstrap

deploy:
	. config/env-dev && cdklocal deploy --require-approval never --all  --hotswap-fallback

# This makes sure the directory is there. It will only run if the directory is missing.
node_modules:
	npm clean-install

# Upgrade the dependencies
upgrade:
	npm update

# Redo the local install
reinstall: destroy-local install

destroy-local:
	. config/env-dev && cdklocal destroy --force --all

# Test suite, define all our tests here
test: install
	npm test

## Run the service and watch for changes
up:
	docker-compose up --detach

## Shut down the service and any associated volume
down:
	docker-compose down --volumes

clean: docker-nuke docker-clean-images
	rm -rf cdk.out node_modules

docker-clean-images:
	docker image prune --force --filter "until=24h"

docker-clean-volumes:
	docker volume prune --force

docker-nuke:
	docker system prune --force --all
