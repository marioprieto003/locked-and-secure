from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from locked_and_secure_app.models import Usuarios, Contraseñas, Grupos
from locked_and_secure_app.endpoints.funciones import *
import json, secrets


@csrf_exempt
def contraseña(request):
    if request.method == 'GET':
        return JsonResponse({"contraseña": secrets.token_urlsafe(14)+'+'}, status=200)
    
    elif request.method == 'POST':
        body = json.loads(request.body)
        try:
            token = request.headers['token']
            clave = request.headers['clave'] # comprobacion de la clave y del token de sesion, necesarias ambas o solo una?
            email = request.body['email']
            usuario = request.body['usuario']
            contraseña = request.body['contraseña']
            grupo = request.body['grupo']
        except KeyError:
            return JsonResponse({"error": "Faltán parámetros"}, status=400)
        
        usuario_bd = Usuarios.objects.get(token_sesion=token)

        if clave != usuario_bd.clave:
            return JsonResponse({"error": "Clave no válida"}, status=401)
        
        grupo_bd = Grupos.objects.get(nombre=grupo)
        
        contraseña_bd = Contraseñas(id_usuario=usuario_bd, id_grupo=grupo_bd, contraseña=encrypt_1(clave, contraseña), email=email, usuario=usuario)
        contraseña_bd.save()
        return JsonResponse({}, status=200)
    
    elif request.method == 'UPDATE':
        body = json.loads(request.body)
        try:
            token = request.headers['token']
            id_contraseña = request.body['idContraseña']
            email = request.body['email']
            usuario = request.body['usuario']
            contraseña = request.body['contraseña']
            grupo = request.body['grupo']
        except KeyError:
            return JsonResponse({"error": "Faltán parámetros"}, status=400)
        
        try:
            contraseña = Contraseñas.objects.get(id=id_contraseña)
        except Contraseñas.DoesNotExist:
            return JsonResponse({"error": "La contraseña no existe"}, status=404)
        

    else:
        return JsonResponse({"error": "Método no soportado"}, status=405)   
        

    
    
        





