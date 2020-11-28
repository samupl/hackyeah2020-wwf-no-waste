FROM python:3.9

RUN mkdir /app
WORKDIR /app
ADD requirements.txt /app
RUN pip install -r requirements.txt

ADD . /app
# ENTRYPOINT ["uwsgi", "--http", "0.0.0.0:8000", "--module", "coupon_backend.wsgi", "--master", "--processes", "4", "--threads", "2"]
# ENTRYPOINT ["python", "manage.py", "runserver", "0.0.0.0:8000"]
ENTRYPOINT ["uvicorn", "coupon_backend.asgi:application", "--host", "0.0.0.0"]
