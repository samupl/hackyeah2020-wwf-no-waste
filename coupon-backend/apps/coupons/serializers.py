from rest_framework import serializers

from apps.coupons.models import Coupon


class CouponSerializer(serializers.Serializer):
    id = serializers.UUIDField(read_only=True)
    required_opinions = serializers.IntegerField(read_only=True)
    description = serializers.CharField(read_only=True)
    coupon = serializers.CharField(read_only=True)
    expiry_date = serializers.DateTimeField(read_only=True)
    icon = serializers.ImageField(read_only=True)
