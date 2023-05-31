from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from locked_and_secure_app.models import Usuarios, Grupos
import json, bcrypt, secrets

@csrf_exempt
def crear_grupo(request):
    if request.method != 'POST':
        return JsonResponse({"error": "Método no soportado"}, status=405)
    
    body = json.loads(request.body)
    try:
        token = request.headers['token']
        nombre = body['nombre']
    except KeyError:
        return JsonResponse({"error": "Faltán parámetros"}, status=400)
    
    # Obtenemos el usuario con el token de sesión
    usuario = Usuarios.objects.get(token_sesion=token)
    
    # Comprobación de que el usuario no tenga un grupo con el mismo nombre
    try:
        grupo = Grupos.objects.get(nombre=nombre)
    except Grupos.DoesNotExist:
        # Creamos un objeto grupo con el nombre y el usuario asociado
        grupo = Grupos(nombre=nombre, usuario=usuario)
        grupo.save()
        return JsonResponse({}, status=200)
    
    return JsonResponse({"error": "Grupo ya existente"}, status=409)