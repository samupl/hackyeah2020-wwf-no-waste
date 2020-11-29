FROM node:15 as build

ARG VUE_APP_API_URL
ARG VUE_APP_COUPON_APP_API_URL

ENV VUE_APP_API_URL=$VUE_APP_API_URL
ENV VUE_APP_COUPON_APP_API_URL=$VUE_APP_COUPON_APP_API_URL

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
