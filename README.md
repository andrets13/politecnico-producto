# CRUD de Productos con GraphQL y gRPC

Este repositorio contiene los dos backends solicitados en la actividad académica. Ambos administran la entidad `Producto`, implementan CRUD completo, utilizan Spring Boot, Maven, Spring Data JPA/Hibernate como ORM y SQL Server como base de datos.

## Proyectos incluidos

| Proyecto | Tecnología de comunicación | Puerto | Tabla |
|---|---|---:|---|
| Proyecto principal | GraphQL sobre HTTP | 8081 | `productos` |
| `productos-grpc` | gRPC sobre HTTP/2 | 9090 | `productos` |

El proyecto principal conserva además la API REST del trabajo anterior. Esto permite comparar REST con los dos enfoques nuevos, pero la entrega requerida se demuestra mediante GraphQL y gRPC.

## Tecnologías

- Java 17 o superior
- Spring Boot 3.5
- Spring for GraphQL
- gRPC Java y Protocol Buffers
- Spring Data JPA e Hibernate
- SQL Server
- Maven Wrapper
- Validación Jakarta y manejo de errores

## Modelo Producto

| Campo | Tipo Java | Definición en SQL Server |
|---|---|---|
| `id` | `Long` | `bigint identity`, llave primaria |
| `nombre` | `String` | `varchar(100)`, obligatorio |
| `descripcion` | `String` | `varchar(500)`, obligatorio |
| `precio` | `BigDecimal` | `decimal(18,2)`, positivo |

Hibernate crea y actualiza la tabla automáticamente porque ambos proyectos usan `spring.jpa.hibernate.ddl-auto=update`. Las dos aplicaciones comparten la tabla `productos` de la base `poli`; así, un dato creado con GraphQL también puede consultarse mediante gRPC. No se escriben consultas SQL manuales para el CRUD.

## 1. Preparar SQL Server

Ejecutar una sola vez con un usuario administrador:

```sql
USE master;
GO

CREATE DATABASE poli;
GO

CREATE LOGIN poli WITH PASSWORD = '1234', CHECK_POLICY = OFF;
GO

USE poli;
GO

CREATE USER poli FOR LOGIN poli;
GO

ALTER ROLE db_owner ADD MEMBER poli;
GO
```

Las credenciales pueden cambiarse sin modificar el código:

```powershell
$env:DB_URL="jdbc:sqlserver://127.0.0.1:1433;databaseName=poli;encrypt=true;trustServerCertificate=true"
$env:DB_USERNAME="poli"
$env:DB_PASSWORD="1234"
```

## 2. Aplicación GraphQL

Desde la raíz del repositorio, ejecutar en Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Abrir GraphiQL en `http://localhost:8081/graphiql` o enviar solicitudes `POST` a `http://localhost:8081/graphql`.

### Crear

```graphql
mutation {
  crearProducto(input: {
    nombre: "Monitor"
    descripcion: "Monitor IPS de 24 pulgadas"
    precio: 850000.00
  }) {
    id
    nombre
    descripcion
    precio
  }
}
```

### Listar y consultar por ID

```graphql
query {
  productos {
    id
    nombre
    precio
  }
}
```

```graphql
query {
  producto(id: 1) {
    id
    nombre
    descripcion
    precio
  }
}
```

GraphQL permite solicitar únicamente los campos necesarios. Por ejemplo, la consulta de listado anterior no devuelve `descripcion`.

### Actualizar

```graphql
mutation {
  actualizarProducto(
    id: 1
    input: {
      nombre: "Monitor gamer"
      descripcion: "Monitor IPS de 24 pulgadas y 144 Hz"
      precio: 990000.00
    }
  ) {
    id
    nombre
    descripcion
    precio
  }
}
```

### Eliminar

```graphql
mutation {
  eliminarProducto(id: 1)
}
```

El contrato declarativo se encuentra en `src/main/resources/graphql/schema.graphqls`.

## 3. Aplicación gRPC

En otra terminal, ingresar al segundo proyecto:

```powershell
cd productos-grpc
..\mvnw.cmd spring-boot:run
```

El servidor usa el puerto `9090`. La reflexión gRPC está habilitada, por lo cual Postman puede descubrir el servicio sin importar manualmente el archivo `.proto`.

En Postman, crear una solicitud gRPC con la dirección `localhost:9090` y seleccionar `productos.ProductoService`.

| Operación CRUD | Método gRPC | Mensaje de ejemplo |
|---|---|---|
| Crear | `Crear` | `{"nombre":"Teclado","descripcion":"Teclado mecánico","precio":"250000.00"}` |
| Listar | `Listar` | `{}` |
| Consultar | `Obtener` | `{"id":1}` |
| Actualizar | `Actualizar` | `{"id":1,"producto":{"nombre":"Teclado RGB","descripcion":"Teclado mecánico RGB","precio":"300000.00"}}` |
| Eliminar | `Eliminar` | `{"id":1}` |

El contrato se encuentra en `productos-grpc/src/main/proto/producto.proto`. El precio se transporta como texto para conservar la precisión decimal y evitar los errores de redondeo propios de `float` y `double`.

También puede probarse con `grpcurl`:

```powershell
grpcurl -plaintext localhost:9090 list
grpcurl -plaintext -d '{"nombre":"Mouse","descripcion":"Mouse inalámbrico","precio":"120000.00"}' localhost:9090 productos.ProductoService/Crear
grpcurl -plaintext -d '{}' localhost:9090 productos.ProductoService/Listar
```

## Manejo de errores

- GraphQL devuelve errores con códigos como `NOT_FOUND` y `BAD_REQUEST`.
- gRPC usa estados estándar: `NOT_FOUND`, `INVALID_ARGUMENT`, `UNAVAILABLE` e `INTERNAL`.
- La validación rechaza nombres o descripciones vacíos y precios nulos, no numéricos o menores o iguales a cero.
- Los errores de acceso a SQL Server no exponen información sensible del servidor.

## Ejecutar pruebas y compilar

Proyecto GraphQL:

```powershell
.\mvnw.cmd clean test
```

Proyecto gRPC:

```powershell
cd productos-grpc
..\mvnw.cmd clean test
```

Las pruebas utilizan la conexión configurada a SQL Server. Antes de ejecutarlas, SQL Server debe estar iniciado y la base `poli` debe estar disponible.

## Estructura relevante

```text
productos/
├── src/main/java/com/ejemplo/productos/
│   ├── controller/              API REST previa
│   ├── graphql/                 resolvers GraphQL y manejo de errores
│   ├── model/                   entidad JPA Producto
│   ├── repository/              repositorio Spring Data JPA
│   └── service/                 reglas del CRUD
├── src/main/resources/graphql/  contrato GraphQL
└── productos-grpc/
    └── src/main/
        ├── java/.../config/      ciclo de vida del servidor gRPC
        ├── java/.../model/       entidad JPA Producto
        ├── java/.../repository/  repositorio JPA
        ├── java/.../service/     reglas del CRUD
        ├── java/.../transport/   implementación de métodos gRPC
        └── proto/                contrato Protocol Buffers
```

## Comparación de enfoques

| Criterio | REST | GraphQL | gRPC |
|---|---|---|---|
| Contrato | Rutas HTTP y OpenAPI | Esquema tipado | Archivo `.proto` |
| Formato | Generalmente JSON | JSON con campos seleccionados | Protocol Buffers binario |
| Ventaja principal | Simplicidad y compatibilidad | Evita traer información innecesaria | Alto rendimiento y comunicación punto a punto |
| Prueba recomendada | Swagger/Postman | GraphiQL/Postman | Postman gRPC/grpcurl |
| Caso habitual | API pública o web | Clientes con consultas variables | Comunicación entre servicios internos |

## Cumplimiento de la actividad

- Dos aplicaciones backend independientes: GraphQL y gRPC.
- Entidad `Producto` con `id`, `nombre`, `descripcion` y `precio`.
- Operaciones crear, leer, actualizar y eliminar en ambas aplicaciones.
- ORM Hibernate mediante Spring Data JPA.
- SQL Server como base de datos relacional.
- Creación automática de tablas.
- Manejo de validaciones y errores en ambos protocolos.
- Código organizado por responsabilidades y listo para publicarse en GitHub.
