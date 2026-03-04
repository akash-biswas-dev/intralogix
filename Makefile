
# Docker hub username
USERNAME=biswasakash
APP_NAME=nexussphere

# Command
CMD=docker build -t

# Service with version versions

USERS=users
USERS_VERSION=${USERS}:0.0.1


GATEWAY=gateway
GATEWAY_VERSION=${GATEWAY}:0.0.1

CLIENT=client
CLIENT_VERSION=${CLIENT}:0.0.1


build-users:
	$(CMD) "$(USERNAME)/$(APP_NAME)-$(USERS_VERSION)" -f dockerfile/$(USERS).Dockerfile server

build-gateway:
	$(CMD) "$(USERNAME)/$(APP_NAME)-$(GATEWAY_VERSION)" -f dockerfile/$(GATEWAY).Dockerfile server

build-client:
	$(CMD) "$(USERNAME)/$(APP_NAME)-$(CLIENT_VERSION)" -f dockerfile/$(CLIENT).Dockerfile client

build-all: build-users build-gateway build-client


