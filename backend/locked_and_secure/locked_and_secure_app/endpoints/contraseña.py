from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from locked_and_secure_app.models import Usuarios, Contraseñas, Grupos
import json, bcrypt, secrets, datetime, base64


def contraseña(request):
    if request.method == 'GET':
        return JsonResponse({"contraseña": secrets.token_urlsafe(14)+'+'}, status=200)
    
    elif request.method == 'POST':
        body = json.loads(request.body)
        try:
            token = request.headers['token']
            email = request.body['email']
            usuario = request.body['usuario']
            contraseña = request.body['contraseña']
            grupo = request.body['grupo']
        except KeyError:
            return JsonResponse({"error": "Faltán parámetros"}, status=400)
        
        usuario_bd = Usuarios.objects.get(token_sesion=token)
        grupo_bd = Grupos.objects.get(nombre=grupo)
        
        contraseña_bd = Contraseñas(id_usuario=usuario_bd, id_grupo=grupo_bd, contraseña=encrypt(usuario_bd.clave, contraseña), email=email, usuario=usuario)
        contraseña_bd.save()
        return JsonResponse({}, status=200)
        
    else:
        return JsonResponse({"error": "Método no soportado"}, status=405)   
        

    
    
        





