# :lock: *Locked&Secure* :lock:

<p align="center">
  <img src="https://raw.githubusercontent.com/marioprieto003/locked-and-secure/main/imagenes/lockedsecure-logo.png" alt="Captura de pantalla">
</p>
<p align="left">
  <img src="https://img.shields.io/badge/STATUS-EN%20DESAROLLO-green"> 
  <img src="https://img.shields.io/github/stars/marioprieto003/locked-and-secure">
 </p>


## Índice
- [Descripción](#descripción)
- [Estructura](#estructura)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instalación](#instalación)

## Descripción
Locked&Secure es una app pensada para aquellas personas preocupadas por la seguridad de sus contraseñas que quieren tener un lugar seguro para ellas. </br>
Ofrece una interfaz que agrupa contraseñas por grupos. Dentro de esos grupos se pueden crear contraseñas especificando el email, usuario y plataforma asociada a la contraseña. A parte de un generador de QR para que compartir las contraseñas sea mucho más rápido. </br>
También cuenta con un sistema de generado de contraseñas seguras que cumplen los requisitos para ello.

## Estructura
~~~
├── app/                # Código fuente de la aplicación Android
├── backend/            # Código fuente del backend Django
├── imagenes/           # Archivos de imágenes
└── README.md           # Archivo Readme del repositorio
~~~

## Tecnologías utilizadas
- :snake: Django: Es un framework web gratuito y de código abierto basado en Python que sigue el patrón MVT (Modelo, Vista, Template).
- :iphone: Android Studio: Se usa para desarrollar una interfaz de usuario para dispositivos móviles.


## Instalación
1. Clonar o descargar el [repositorio](https://github.com/marioprieto003/locked-and-secure) </br>`git clone https://github.com/tu-nombre-usuario/locked-and-secure.git`
2. Abrir la carpeta *app* en Android Studio y lanzar la app
3. En el terminal, situarse en la carpeta *backend/*
4. Instalar las dependencias necesarias </br> `pip install -r requirements.txt`
5. moverse a la carpeta *backend/locked_and_secure*
6. Ejecutar `python manage.py runserver`
7. Listo! Ya puedes probar Locked&Secure

Para probarlo en un móvil habría que:
1. Tener el móvil y el ordenador conectados a la misma red
2. Con `ipconfig` conocer la ip interna del ordenador
3. En la clase Rest, cambiar la variable `MOVIL_IP` por `http://ipOrdenador:8000`
4. Asignar a `BASE_URL` la variable `MOVIL_IP`
5. Al iniciar el servidor usar `python manage.py runserver 0.0.0.0:8000`
6. Listo! Ya puedes probar Locked&Secure en un móvil

## Endpoints

| Operación            | Descripción                                                                         | Método y URL                | Cabeceras | Cuerpo petición                                                                                                                                          | Respuesta                                                                           | Cuerpo de respuesta(2xx)                                                                                                                                                                                                                                |
| -------------------- | ----------------------------------------------------------------------------------- | --------------------------- | --------- | -------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| auth                 | Endpoint para autenticar el usuario según si token de sesión                        | GET /auth                   | token     | \-                                                                                                                                                       | 200 OK<br>405 Not supported<br>400 Bad request<br>401 Unauthorized                  | {“mensaje”:”ok”}                                                                                                                                                                                                                                        |
| login                | Inicia una sesión y recibe un token de sesión                                       | POST /login                 | \-        | {<br>"email": xx@gmail.com,<br>"contraseña": asd<br>}                                                                                                    | 200 OK<br>405 Not supported<br>400 Bad request<br>401 Unauthorized<br>404 Not found | {<br>"token": "asd78vb7asdn"<br>}                                                                                                                                                                                                                       |
| registro             | Resgistra un usuario en la base de datos                                            | POST /register              | \-        | {<br>"email": xx@gmail.com,<br>"contraseña": asd,<br>"nombre": "andres",<br>"apellido1": "primero",<br>"apellido2": "segundo"<br>}                       | 201 Created<br>400 Bad request<br>405 Not supported<br>409 Conflict                 | {<br>"mensaje": "Usuario creado"<br>}                                                                                                                                                                                                                   |
| inicio               | Envía todos los grupos y contraseñas del usuario                                    | GET /inicio                 | token     | \-                                                                                                                                                       | 200 OK<br>400 Bad request<br>404 Not found<br>405 Not supported                     | [<br>{<br>"id":1,<br>"grupo": "General",<br>"tamaño": 1,<br>"contraseñas": [<br>{<br>"id": 1,<br>"contraseña": qdqwd,<br>"email": cxas@das.com,<br>"usuario": andres,<br>"plataforma": "Instagram",<br>"fecha": 23/07/2023 15:05:05<br>}<br>]<br>}<br>] |
| crear grupo          | Crea un grupo relacionado con el usuario logueado                                   | POST /grupo                 | token     | {<br>"nombre": "RRSS"<br>}                                                                                                                               | 201 Created<br>400 Bad request<br>405 Not supported<br>409 Conflict                 | {“mensaje”:”ok”}                                                                                                                                                                                                                                        |
| get grupo            | Recupera un grupo según el id                                                       | GET /grupo/<int:idGrupo>    | token     | \-                                                                                                                                                       | 200 OK<br>400 Bad request<br>404 Not found<br>405 Not supported                     | {<br>"id":1,<br>"grupo": "General"<br>}                                                                                                                                                                                                                 |
| get contraseña       | Devuelve una contraseña generada aleatoriamente                                     | GET /contraseña             | \-        | \-                                                                                                                                                       | 200 OK<br>405 Not supported                                                         | {<br>"contraseña": "js7fuyGjkd_kld?"<br>}                                                                                                                                                                                                               |
| crear contraseña     | Crea una contraseña relacionada con el grupo en el se crea y el usuario que la crea | POST /contraseña            | token     | {<br>"id": 1,<br>"contraseña": qdqwd,<br>"email": cxas@das.com,<br>"usuario": andres,<br>"plataforma": "Instagram",<br>"fecha": 23/07/2023 15:05:05<br>} | 201 Created<br>400 Bad request<br>405 Not supported<br>409 Conflict                 | {“mensaje”:”ok”}                                                                                                                                                                                                                                        |
| modificar contraseña | Modifica una contraseña ya creada                                                   | PUT /contraseña             | token     | {<br>"id": 1,<br>"contraseña": qdqwd,<br>"email": cxas@das.com,<br>"usuario": andres,<br>"plataforma": "Twitter",<br>"fecha": 23/07/2023 17:05:05<br>}   | 200 OK<br>400 Bad request<br>404 Not found<br>405 Not supported                     | {“mensaje”:”ok”}                                                                                                                                                                                                                                        |
| ajustes              | Consigue los datos personales y las configuraciones guardadas                       | GET /ajustes/<str:apartado> | token     | \-                                                                                                                                                       | 200 OK<br>400 Bad request<br>404 Not found<br>405 Not supported                     | {<br>"nombre": "Andres",<br>"apellidos": "Primero Segundo"<br>}                                                                                                                                                                                         |
