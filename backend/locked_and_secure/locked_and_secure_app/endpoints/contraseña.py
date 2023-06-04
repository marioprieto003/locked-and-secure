from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from locked_and_secure_app.models import Usuarios, Contraseñas, Grupos
import json, secrets, base64


@csrf_exempt
def contraseña(request):
    if request.method == 'GET':
        # Creamos una contraseña con el paquete secrets
        # Añadimos un + al final para asgurarnos de que siempre hay un simbolo
        return JsonResponse({"contraseña": secrets.token_urlsafe(14)+'+'}, status=200)
    
    elif request.method == 'POST':
        body = json.loads(request.body)
        try:
            token = request.headers['token']
            email = body['email']
            usuario = body['usuario']
            contraseña = body['contraseña']
            plataforma = body['plataforma']
            grupo = body['grupo']
        except KeyError:
            return JsonResponse({"error": "Faltán parámetros"}, status=400)
        
        # Recuperamos el usuario con el tokjen de sesion
        usuario_bd = Usuarios.objects.get(token_sesion=token)   
        # Recuperamos el grupo a partir del id que llega en el body
        grupo_bd = Grupos.objects.get(id=grupo)
        
        # Creamos un objecto contraseña, introducimos los valores y lo guardamos en BBDD
        contraseña_bd = Contraseñas(id_usuario=usuario_bd, id_grupo=grupo_bd, contraseña=contraseña, email=email, usuario=usuario, plataforma=plataforma)
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
        

    
    
        





