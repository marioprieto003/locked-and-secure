from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from locked_and_secure_app.models import Usuarios
import json, bcrypt, secrets

@csrf_exempt
def login(request):
    if request.method != 'POST':
        return JsonResponse({"error": "Método no soportado"}, status=405)
    
    body = json.loads(request.body)

    try:
        email = body['email']
        contraseña = body['contraseña']
    except KeyError:
        return JsonResponse({"error": "Faltán parámetros"}, status=400)
    
    try:
        usuario = Usuarios.objects.get(email=email)
    except  Usuarios.DoesNotExist:
        return JsonResponse({"error": "Usuario no encontrado"}, status=404)
    
    if bcrypt.checkpw(contraseña.encode('utf8'), usuario.contraseña.encode('utf8')):
        token_sesion = secrets.token_hex(15)
        usuario.update(token_sesion=token_sesion)
        return JsonResponse({"tokenSesion": random_token}, status=200)
    else:
        return JsonResponse({"error": "Contraseña incorrecta"}, status=401)
    
    
    