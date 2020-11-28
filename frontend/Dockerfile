FROM node:15 as build


RUN mkdir /app
WORKDIR /app

ADD package.json /app
ADD yarn.lock /app
RUN yarn

ADD . /app
RUN yarn build


FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html
ADD docker/cfg/nginx/default.conf /etc/nginx/conf.d/
