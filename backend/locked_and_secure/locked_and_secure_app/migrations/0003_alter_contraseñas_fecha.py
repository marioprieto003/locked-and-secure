# Generated by Django 4.1.7 on 2023-06-01 07:21

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('locked_and_secure_app', '0002_grupos_usuario_usuarios_clave_and_more'),
    ]

    operations = [
        migrations.AlterField(
            model_name='contraseñas',
            name='fecha',
            field=models.DateField(default=datetime.datetime(2023, 6, 1, 7, 21, 55, 73442, tzinfo=datetime.timezone.utc)),
        ),
    ]
