FROM nginx:latest

WORKDIR /etc/nginx/conf.d

RUN rm -r default.conf

COPY proxy.conf default.conf

