# Generated by Django 4.1.7 on 2023-06-01 07:21

import datetime
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('locked_and_secure_app', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='grupos',
            name='usuario',
            field=models.ForeignKey(default=None, on_delete=django.db.models.deletion.CASCADE, to='locked_and_secure_app.usuarios'),
        ),
        migrations.AddField(
            model_name='usuarios',
            name='clave',
            field=models.TextField(null=True),
        ),
        migrations.AlterField(
            model_name='contraseñas',
            name='fecha',
            field=models.DateField(default=datetime.datetime(2023, 6, 1, 7, 21, 20, 90883, tzinfo=datetime.timezone.utc)),
        ),
        migrations.AlterField(
            model_name='contraseñas',
            name='id_grupo',
            field=models.ForeignKey(default=None, on_delete=django.db.models.deletion.CASCADE, to='locked_and_secure_app.grupos'),
        ),
        migrations.AlterField(
            model_name='contraseñas',
            name='id_usuario',
            field=models.ForeignKey(default=None, on_delete=django.db.models.deletion.CASCADE, to='locked_and_secure_app.usuarios'),
        ),
        migrations.AlterField(
            model_name='grupos',
            name='imagen',
            field=models.TextField(default=None, null=True),
        ),
    ]