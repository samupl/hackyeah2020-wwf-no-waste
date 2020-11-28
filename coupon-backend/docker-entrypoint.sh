#!/bin/sh

set -e

python manage.py migrate
exec uvicorn coupon_backend.asgi:application --host 0.0.0.0
