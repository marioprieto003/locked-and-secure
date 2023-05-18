from django.http import JsonResponse
from locked_and_secure_app.models import Usuarios

def auth(request):
    if response.method != 'GET':
        return JsonResponse({"error": "Método no soportado"}, status=405)
    
    try:
        token_sesion = request.headers['tokenSesion']  
    except KeyError:
        return JsonResponse({"error": "Faltán cabeceras"}, status=400)
    
    try:
        usuario = Usuarios.objects.get(token_sesion=token_sesion)
    except Usuarios.DoesNotExist:
        return JsonResponse({"error": "Token no válido"}, status=401)
    
    return JsonResponse({"mensaje": "ok"}, status=200)