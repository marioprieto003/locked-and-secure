"""locked_and_secure URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from locked_and_secure_app.endpoints import health, login, registro, inicio, grupos, contraseña, ajustes, auth

urlpatterns = [
    path('admin/', admin.site.urls),
    path('health', health.health),
    path('login', login.login),
    path('registro', registro.registro),
    path('contraseña', contraseña.contraseña),
    path('inicio', inicio.all),
    path('grupo', grupos.crear_grupo),
    path('grupo/<int:id>', grupos.get_grupo),
    path('inicio', inicio.all),
    path('ajustes/<str:apartado>', ajustes.cuenta),
    path('auth', auth.auth)
]
