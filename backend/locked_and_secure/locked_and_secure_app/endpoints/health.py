from django.http import JsonResponse


def health(requests):
    return JsonResponse({"health": "ok"}, safe=False)