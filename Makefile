
# Service with version versions

users=users
users_version=$(users):0.0.1

gateway=gateway
gateway_version=$(gateway):0.0.1

client=client
client_version=$(client):0.0.1

# Docker hub username
APP_NAME=biswasakash/nexussphere
# Command

DOCKER_BUILD_CMD=docker build --platform linux/amd64 -t $(APP_NAME)
DOCKER_TAG_CMD=docker tag $(APP_NAME)
DOCKER_PUSH_CMD=docker push $(APP_NAME)



build-users:

	$(DOCKER_BUILD_CMD)-$(users):latest  -f dockerfile/$(users).Dockerfile server

	$(DOCKER_TAG_CMD)-$(users):latest $(APP_NAME)-$(users_version)

build-gateway:

	$(DOCKER_BUILD_CMD)-$(gateway):latest  -f dockerfile/$(gateway).Dockerfile server

	$(DOCKER_TAG_CMD)-$(gateway):latest $(APP_NAME)-$(gateway_version)

build-client:

	$(DOCKER_BUILD_CMD)-$(client):latest  -f dockerfile/$(client).Dockerfile client

	$(DOCKER_TAG_CMD)-$(client):latest $(APP_NAME)-$(client_version)


build-all: build-users build-gateway build-client


push-users:
	$(DOCKER_PUSH_CMD)-$(users_version)
	$(DOCKER_PUSH_CMD)-$(users):latest

push-gateway:
	$(DOCKER_PUSH_CMD)-$(gateway_version)
	$(DOCKER_PUSH_CMD)-$(gateway):latest

push-client:
	$(DOCKER_PUSH_CMD)-$(client_version)
	$(DOCKER_PUSH_CMD)-$(client):latest

push-all: push-users push-gateway push-client


delete-users:
	docker rmi -f $(APP_NAME)-$(users):latest $(APP_NAME)-$(users_version)
delete-gateway:
	docker rmi -f $(APP_NAME)-$(gateway):latest $(APP_NAME)-$(gateway_version)
delete-client:
	docker rmi -f $(APP_NAME)-$(client):latest $(APP_NAME)-$(client_version)

delete-all: delete-users delete-gateway delete-client

