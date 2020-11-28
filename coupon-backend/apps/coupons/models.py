import os
import uuid

from django.db import models


def get_file_path(instance, filename):
    ext = filename.split('.')[-1]
    return f"{uuid.uuid4()}.{ext}"


class Coupon(models.Model):
    id = models.UUIDField(
        "ID", primary_key=True, default=uuid.uuid4, editable=False
    )
    required_opinions = models.IntegerField(default=0)

    description = models.TextField()
    coupon = models.CharField(max_length=255)
    expiry_date = models.DateTimeField()
    icon = models.ImageField(upload_to=get_file_path)
