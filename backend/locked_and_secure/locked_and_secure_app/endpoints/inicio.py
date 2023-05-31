from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from locked_and_secure_app.models import Usuarios, Grupos, Contraseñas
import json, bcrypt, secrets

def all(request):
    if request.method != 'GET':
        return JsonResponse({"error": "Método no soportado"}, status=405)
       
    try:
        token = request.headers['token']
    except KeyError:
        return JsonResponse({"error": "Faltán parámetros"}, status=400)
    

    
    try:
        usuario = Usuarios.objects.get(token_sesion=token)
    except  Usuarios.DoesNotExist:
        return JsonResponse({"error": "Usuario no encontrado"}, status=404)
    
    # Obtenemos los grupos que ha creado el usuario
    grupos = Grupos.objects.filter(usuario=usuario)
    
    lista_contraseñas_completa = []
    for grupo in grupos:
        # Obtenemos las contraseñas que el usuario ha creado en cada grupo
        contraseñas = Contraseñas.objects.filter(id_grupo=grupo)
        
        lista_contraseñas_grupo = []
        for contraseña in contraseñas:
             lista_contraseñas_grupo.append(
                 {
                    "id": int(contraseña.id),
                    "contraseña": contraseña.contraseña,
                    "email": contraseña.email,
                    "usuario": contraseña.usuario,
                    "fecha": contraseña.fecha
                 }
             )
             
        lista_contraseñas_completa.append({
            "id": int(grupo.id),
            "grupo": grupo.nombre,
            "tamaño": len(lista_contraseñas_grupo),
            "contraseñas": lista_contraseñas_grupo
            }
        )
  
    return JsonResponse(lista_contraseñas_completa, safe=False, status=200)