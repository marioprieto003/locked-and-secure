from django.apps import AppConfig


class LockedAndSecureAppConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'locked_and_secure_app'
