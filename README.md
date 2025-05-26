# Módulo administrativo: gestión de usuarios - Backend
## Descripción
Este módulo centraliza las funciones administrativas del sistema ECI Bienestar Total, una plataforma diseñada para centralizar y optimizar la gestión de los servicios de bienestar universitario de la Universidad Escuela Colombiana de Ingeniería Julio Garavito. En específico, este módulo, permite al Administrador del sistema gestionar de forma integral los perfiles y roles de usuario y la configuración de horarios asociados al uso de espacios, servicios y recursos de bienestar universitario.

## Tecnologías utilizadas
- **Spring Boot**: Framework principal para el desarrollo del backend en Java.
- **Maven**: Herramienta de gestión y construcción de proyectos.
- **PostgreSQL**: Base de datos relacional utilizada para entidades estructuradas y relaciones de los usuarios.
- **JWT (JSON Web Tokens)**: Implementación de autenticación y autorización segura basada en tokens.

## Desarrollo 
Este módulo se implementa como un microservicio independiente dentro del ecosistema ECI Bienestar Total, siguiendo una estructura interna basada en el patrón MVC mediante Spring Boot. Esto permite una separación clara entre controladores, servicios, modelos y persistencia.

JWT se utiliza para manejar la autenticación y controlar accesos según roles en cada endpoint.

## Endpoints principales

### User Controller  
Operaciones relacionadas con la gestión de usuarios (estudiantes, administradores y otros perfiles):

| Método | Ruta             | Descripción                                             |
|--------|------------------|---------------------------------------------------------|
| PUT    | `/user`          | Actualizar usuario                                      |
| PUT    | `/user/schedule` | Actualizar datos de administrador relacionados con horarios |
| PUT    | `/user/password` | Cambiar contraseña                                      |
| POST   | `/user/query`    | Consultar usuarios con filtros personalizados          |
| DELETE | `/user/{id}`     | Eliminar usuario por ID                                 |

### Authentication Controller  
Controlador encargado del registro y autenticación de usuarios:

| Método | Ruta                      | Descripción                    |
|--------|---------------------------|-------------------------------|
| POST   | `/authentication/student` | Registrar nuevo estudiante     |
| POST   | `/authentication/login`   | Iniciar sesión (login)         |
| POST   | `/authentication/admin`   | Registrar nuevo administrador  |

## Pruebas unitarias
Se implementaron pruebas unitarias para garantizar la calidad y estabilidad del sistema, enfocadas en:
- `config`: Validación del filtro y configuración de seguridad.
- `controller`: Pruebas de endpoints con `@WebMvcTest`.
- `service`: Lógica de negocio probada con `Mockito`.
- `model`: Validación de comportamiento básico.

## Análisis de cobertura
Se integró la herramienta JaCoCo (Java Code Coverage Library) y Mockito para cobertura de preubas.
Se buscó mantener una cobertura mínima del 80% en servicios y controladores.
![](https://github.com/T-800-Squad/Chronos/blob/main/images/jacoco_chronos.png)

## Diagramas del sistema

### Diagrama de clases
![](https://github.com/T-800-Squad/Chronos/blob/main/images/diag_clases.png)
### Diagrama de componentes
![](https://github.com/T-800-Squad/Chronos/blob/main/images/diag_comp.png)
### Diagrama de arquitectura
![](https://github.com/T-800-Squad/Chronos/blob/main/images/diag_arq.png)

## Público objetivo
Este backend está orientado principalmente a los Administradores del sistema, quienes gestionan y configuran:
- La base de usuarios
- La asignación de permisos
- Los horarios generales del sistema
- Las políticas de acceso y operación

## Integración con ECI Bienestar Total
Este módulo se conecta con otros componentes del sistema ECI Bienestar Total, proporcionando autenticación, control de usuarios, y asignación de roles que habilitan o restringen el acceso a funcionalidades como:
- Reservas de espacios de recreación
- Préstamos de elementos deportivos
- Actividades extracurriculares
- Servicios de salud
- Gimnasio

## Realizado por el equipo Chronos de CVDS Company G3
- Lina Sánchez
- Miguel Vanegas
- Yojhan Toro
- Ivan Cubillos 
