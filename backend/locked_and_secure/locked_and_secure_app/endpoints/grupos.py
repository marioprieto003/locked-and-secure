from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from locked_and_secure_app.models import Usuarios, Grupos, Contraseñas
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

def get_grupo(request, id):
    if request.method != 'GET':
        return JsonResponse({"error": "Método no soportado"}, status=405)
    
    try:
        token = request.headers['token']
    except KeyError:
        return JsonResponse({"error": "Faltán parámetros"}, status=400)
    
    try:
        grupo = Grupos.objects.get(id=id, usuario__token_sesion=token)
    except Grupos.DoesNotExist:
        return JsonResponse({"error": "No existe ese grupo"}, status=404)
    
    contraseñas = Contraseñas.objects.filter(id_grupo__id=id)

    lista_contraseñas = []
    for contraseña in contraseñas:
        lista_contraseñas.append(
            {
                "id": int(contraseña.id),
                "contraseña": contraseña.contraseña,
                "email": contraseña.email,
                "usuario": contraseña.usuario,
                "fecha": contraseña.fecha,
                "plataforma": contraseña.plataforma
            }
        )
    
    return JsonResponse(lista_contraseñas, safe=False, status=200)