test-pipeline:
	act -P ubuntu-latest=catthehacker/ubuntu:act-latest --reuse --container-architecture linux/amd64 --secret-file .secrets

gen-code-java:
	buf generate --template java-proto-modules/buf.gen.yaml


# Service with version versions

users=users
users_version=$(users):local

gateway=gateway
gateway_version=$(gateway):0.0.1

workspace=workspace
workspace_version=$(workspace):0.0.1

access_manager=access-manager
access_manager_version=$(access_manager):0.0.1

web=web
web_version=$(web):0.0.1

# Docker hub username
APP_NAME=biswasakash/nexussphere

# Command
DOCKER_BUILD_CMD=docker build --platform linux/amd64,linux/arm64  -t $(APP_NAME)
DOCKER_TAG_CMD=docker tag $(APP_NAME)
DOCKER_PUSH_CMD=docker push $(APP_NAME)

# Build config
build-docker-proxy:
	$(DOCKER_BUILD_CMD)-docker-proxy:latest -f docker/proxy.Dockerfile .

build-users:
	$(DOCKER_BUILD_CMD)-$(users):local -f users/local.Dockerfile .

build-workspace:
	$(DOCKER_BUILD_CMD)-$(workspace):latest  -f docker/$(workspace).Dockerfile .

	$(DOCKER_TAG_CMD)-$(workspace):latest $(APP_NAME)-$(workspace_version)

build-gateway:

	$(DOCKER_BUILD_CMD)-$(gateway):latest  -f docker/$(gateway).Dockerfile .

	$(DOCKER_TAG_CMD)-$(gateway):latest $(APP_NAME)-$(gateway_version)


build-access-manager:

	$(DOCKER_BUILD_CMD)-$(access_manager):latest  -f docker/$(access_manager).Dockerfile .

	$(DOCKER_TAG_CMD)-$(access_manager):latest $(APP_NAME)-$(access_manager_version)

build-web:

	$(DOCKER_BUILD_CMD)-$(web):latest  -f docker/$(web).Dockerfile web

	$(DOCKER_TAG_CMD)-$(web):latest $(APP_NAME)-$(web_version)


build-all: build-users build-gateway build-workspace build-access-manager build-web 

# Container registry push config
push-docker-proxy:
	$(DOCKER_PUSH_CMD)-docker-proxy:latest

push-users:
	$(DOCKER_PUSH_CMD)-$(users_version)

push-gateway:
	$(DOCKER_PUSH_CMD)-$(gateway_version)

push-access-manager:
	$(DOCKER_PUSH_CMD)-$(access_manager_version)

push-workspace:
	$(DOCKER_PUSH_CMD)-$(workspace_version)

push-web:
	$(DOCKER_PUSH_CMD)-$(web_version)
	$(DOCKER_PUSH_CMD)-$(web):latest

push-all: push-users push-gateway push-workspace push-access-manager push-web

# Delete containers config.
delete-docker-proxy:
	docker rmi -f $(APP_NAME)-docker-proxy:latest

delete-users:
	docker rmi -f $(APP_NAME)-$(users_version)

delete-gateway:
	docker rmi -f $(APP_NAME)-$(gateway):latest $(APP_NAME)-$(gateway_version)

delete-workspace:
	docker rmi -f $(APP_NAME)-$(workspace):latest $(APP_NAME)-$(workspace_version)

delete-access-manager:
	docker rmi -f $(APP_NAME)-$(access_manager):latest $(APP_NAME)-$(access_manager_version)

delete-web:
	docker rmi -f $(APP_NAME)-$(web):latest $(APP_NAME)-$(web_version)

delete-all: delete-users delete-gateway delete-access-manager delete-workspace delete-web


