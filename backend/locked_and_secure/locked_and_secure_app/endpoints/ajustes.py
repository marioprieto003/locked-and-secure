from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from locked_and_secure_app.models import Usuarios
from cryptography.fernet import Fernet
import json, bcrypt, secrets

def cuenta(request, apartado):
    if request.method != 'GET':
        return JsonResponse({"error": "Método no soportado"}, status=405)
    
    if apartado == 'cuenta':
        try:
            token = request.headers['token']
        except KeyError:
            return JsonResponse({"error": "Faltán parámetros"}, status=400)
        
        try:
            usuario = Usuarios.objects.get(token_sesion=token)
        except Usuarios.DoesNotExist:
            return JsonResponse({"error": "Usuario no encontrado"}, status=404)
        
        return JsonResponse({
            "nombre": usuario.nombre,
            "apellidos": usuario.apellido1 + " " + usuario.apellido2
        }, status=200)