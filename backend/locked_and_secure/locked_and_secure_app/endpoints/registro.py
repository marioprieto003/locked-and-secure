from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from locked_and_secure_app.models import Usuarios
import json, bcrypt

@csrf_exempt
def registro(request):
    if request.method != "POST":
        return JsonResponse({'error': 'Método no soportado'}, status=405)

    body_json = json.loads(request.body)

    try:
        email = body_json['email']
        contraseña = body_json['contraseña']
        nombre = body_json['nombre']
        apellido1 = body_json['apellido1']
        apellido2 = body_json['apellido2']
    except KeyError:
        return JsonResponse({"error": "Faltán parámetros"}, status=400)

    try:
        alreadyregister = Usuarios.objects.get(email=email)
        return JsonResponse({"error": "Email already registered"}, status=409)
    except Usuarios.DoesNotExist:
        salted_and_hashed_pass = bcrypt.hashpw(contraseña.encode('utf8'), bcrypt.gensalt()).decode('utf8')
        usuario = Usuarios(email=email,
                             contraseña=salted_and_hashed_pass,
                             nombre=nombre,
                             apellido1=apellido1,
                             apellido2=apellido2,
                             token_sesion=None,
                             token_recuperacion=None
                            )
        usuario.save()
        return JsonResponse({"mensaje": "Usuario creado"}, status=201)

