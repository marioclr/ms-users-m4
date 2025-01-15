insert into profilem4 (description,profile_code,id) values ('MOVIMIENTOS DE PERSONAL', 'MOVPERS', 1)
insert into permissionm4 (descripcion,nombre,profile_id,id) values ('Registros de movimientos para dar de alta a los empleados','ALTAS_EMPLEADOS',1,1)
insert into permissionm4 (descripcion,nombre,profile_id,id) values ('Registros de movimientos para dar de baja a los empleados','BAJAS_EMPLEADOS',1,2)
insert into userm4 (activo,num_empleado,password,perfil_id,user_name,id) values (TRUE,'357819','1234',1,'mclr',1)