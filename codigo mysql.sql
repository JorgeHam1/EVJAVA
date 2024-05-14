create database hospitalham

use HospitalHam;

create table sexo (
id int auto_increment not null primary key,
sexo varchar(50)
);

insert into sexo (sexo) values ('masculino');
insert into sexo (sexo) values ('femenino ');

select * from sexo;

create table ListaCitas(
ID int auto_increment not null primary key,
Nombres varchar(50),
Apellidos varchar(50),
sexo int,
Edad int,
cita date,
Nombre_Doctor varchar(50),
Apellido_Doctor varchar(50),
Especialidad varchar(50),
FOREIGN KEY (sexo) references sexo(id) ON DELETE CASCADE ON UPDATE CASCADE
);

insert into ListaCitas (nombres,apellidos,sexo,edad,Nombre_Doctor,Apellido_Doctor,Especialidad,cita) values ("Pamela","Luna",2,22,"Ana","Barraza","Cardiologia","2024/08/31");
delete from ListaCitas where ListaCitas.id=7;
select * from ListaCitas;

SHOW VARIABLES LIKE 'max_connections';
show status like 'trheads_connected';


select ListaCitas.ID,ListaCitas.nombres,ListaCitas.apellidos,sexo.sexo,ListaCitas.edad, ListaCitas.Nombre_Doctor, ListaCitas.Apellido_Doctor,ListaCitas.Especialidad, ListaCitas.cita
 FROM ListaCitas JOIN sexo ON ListaCitas.sexo = sexo.id;

UPDATE ListaCitas SET ListaCitas.nombres=?,ListaCitas.apellido =?,ListaCitas.sexo=?, ListaCitas.edad=?, ListaCitas.Nombre_Doctor=?, ListaCitas.Apellido_Doctor=?, ListaCitas.Especialidad=?, ListaCitas.cita=?
Where ListaCitas.id=?

DELETE FROM ListaCitas WHERE ListaCitas.id=2;