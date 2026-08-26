25/08/2026

# CRUD de Productos con Spring Boot y SQL Server

API REST para administrar productos mediante operaciones CRUD. La persistencia se realiza en SQL Server utilizando Spring Data JPA e Hibernate como ORM.

## Tecnologías

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- SQL Server
- Maven
- Eclipse IDE

## Funcionalidades

- Crear productos
- Listar productos
- Consultar un producto por ID
- Actualizar productos
- Eliminar productos
- Validar nombre, descripción y precio
- Generar automáticamente la tabla mediante Hibernate

## Requisitos

- Java 21
- SQL Server disponible en el puerto `1433`
- Maven o Maven Wrapper
- Eclipse IDE o un editor compatible con Maven

## Base de datos

Ingresar a SQL Server con un usuario administrador y ejecutar:

```sql
USE master;
GO

CREATE DATABASE poli;
GO

CREATE LOGIN poli
WITH PASSWORD = '1234',
CHECK_POLICY = OFF;
GO

USE poli;
GO

CREATE USER poli FOR LOGIN poli;
GO

ALTER ROLE db_owner ADD MEMBER poli;
GO
```

No es necesario crear manualmente la tabla `productos`. Hibernate la genera automáticamente al iniciar la aplicación.

## Configuración

La conexión está definida en:

```text
src/main/resources/application.properties
```

```properties
spring.application.name=productos
server.port=8081

spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=poli;encrypt=true;trustServerCertificate=true
spring.datasource.username=poli
spring.datasource.password=1234
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
```

## Descargar y ejecutar

```powershell
git clone https://github.com/andrets13/politecnico-producto.git
cd politecnico-producto
.\mvnw.cmd spring-boot:run
```

También puede importarse en Eclipse como un proyecto Maven y ejecutar:

```text
ProductosApplication.java
→ Run As
→ Spring Boot App
```

La aplicación estará disponible en:

```text
http://localhost:8081
```

## Endpoints

| Operación | Método | URL |
|---|---|---|
| Listar | GET | `/api/productos` |
| Consultar | GET | `/api/productos/{id}` |
| Crear | POST | `/api/productos` |
| Actualizar | PUT | `/api/productos/{id}` |
| Eliminar | DELETE | `/api/productos/{id}` |

## Ejemplo para crear un producto

```http
POST http://localhost:8081/api/productos
Content-Type: application/json
```

```json
{
  "nombre": "Libro de matemáticas",
  "descripcion": "Libro de matemáticas grado 6",
  "precio": 55000.00
}
```

El campo `id` es generado automáticamente por SQL Server.

## Modelo generado por Hibernate

La entidad `Producto` genera una tabla con la siguiente estructura:

| Campo | Tipo aproximado en SQL Server |
|---|---|
| `id` | `bigint identity` |
| `nombre` | `nvarchar(100)` |
| `descripcion` | `nvarchar(500)` |
| `precio` | `decimal(18,2)` |