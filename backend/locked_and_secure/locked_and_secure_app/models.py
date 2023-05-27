from django.db import models

class Usuarios(models.Model):
    # id auto generado
    email = models.EmailField()
    contraseña = models.TextField()
    nombre = models.TextField()
    apellido1 = models.TextField()
    apellido2 = models.TextField()
    token_sesion = models.TextField(null=True, default=None)
    token_recuperacion = models.TextField(null=True, default=None)
    
    
            
class Contraseñas(models.Model):
    # id auto generado
    id_usuario = models.ForeignKey('Usuarios', on_delete=models.CASCADE)
    id_grupo = models.ForeignKey('Grupos', on_delete=models.CASCADE)
    contraseña = models.TextField()
    email = models.EmailField(null=True)
    usuario = models.TextField(null=True)
    fecha = models.DateField()
        
class Grupos(models.Model):
    # id auto generado
    usuario = models.ForeignKey('Usuarios', on_delete=models.CASCADE, default=None)
    nombre = models.TextField()
    imagen = models.TextField()

    
        
