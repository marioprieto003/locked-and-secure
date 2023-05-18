from django.contrib import admin
from .models import Usuarios, Contraseñas, Grupos

admin.site.register(Usuarios)
admin.site.register(Contraseñas)
admin.site.register(Grupos)