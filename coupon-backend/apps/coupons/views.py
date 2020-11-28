from django.utils import timezone
from rest_framework.response import Response
from rest_framework.reverse import reverse
from rest_framework.viewsets import ViewSet

from apps.coupons.models import Coupon
from apps.coupons.serializers import CouponSerializer
from apps.db_integration.models import Review


class UserCouponsViewSet(ViewSet):

    def list(self, request):
        example_url = reverse(
            'user-coupons-detail',
            kwargs={'pk': 'example'},
            request=request
        )
        return Response(
            data={
                'reason': f'You need to pass username in the url. E.g. {example_url}'
            }
        )

    def retrieve(self, request, pk):
        review_count = Review.objects.filter(author=pk).values('product').distinct().count()
        coupons_qs = Coupon.objects.filter(
            required_opinions__lte=review_count
        ).order_by(
            '-required_opinions'
        )

        now = timezone.now()

        coupons_working = []
        coupons_expired = []
        for coupon in coupons_qs:
            if now > coupon.expiry_date:
                coupons_expired.append(coupon)
            else:
                coupons_working.append(coupon)
        coupons = coupons_working + coupons_expired

        serializer = CouponSerializer(
            instance=coupons, many=True, context={'request': request}
        )
        return Response(data=serializer.data)
