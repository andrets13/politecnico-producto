\# CRUD de Productos con Spring Boot y Firebase



API REST desarrollada con Spring Boot para gestionar productos mediante operaciones CRUD. Los datos se almacenan en Firebase Cloud Firestore.



\## Tecnologías



\* Java 21

\* Spring Boot 3.5.16

\* Maven Wrapper

\* Firebase Admin SDK

\* Cloud Firestore

\* Postman



\## Modelo Producto



```json

{

&#x20; "id": "ID\_GENERADO\_POR\_FIREBASE",

&#x20; "nombre": "Libro de matematicas",

&#x20; "descripcion": "Libro de matematicas grado 6",

&#x20; "precio": 55000

}

```



La colección utilizada en Firestore se llama:



```text

producto

```



\## Estructura principal



```text

src/main/java/com/ejemplo/productos

├── config

├── controller

├── exception

├── model

├── repository

├── service

└── ProductosApplication.java

```



\## Requisitos



\* Java JDK 21.

\* Conexión a Internet.

\* Archivo `firebase-productos.json`.

\* Puerto 8080 disponible.

\* Postman para probar la API.



No es necesario instalar Maven porque el proyecto incluye Maven Wrapper.



\## Descargar el proyecto



```powershell

git clone https://github.com/andrets13/politecnico-producto.git

cd ".\\politecnico-producto"

```



\## Configurar Firebase



El archivo de credenciales debe ubicarse en:



```text

credenciales/firebase-productos.json

```



La estructura debe quedar:



```text

politecnico-producto

├── credenciales

│   └── firebase-productos.json

├── src

├── pom.xml

└── README.md

```



Desde PowerShell, configure la variable de entorno:



```powershell

$env:GOOGLE\_APPLICATION\_CREDENTIALS = `

(Resolve-Path ".\\credenciales\\firebase-productos.json").Path

```



Verifique la ruta:



```powershell

$env:GOOGLE\_APPLICATION\_CREDENTIALS

```



\## Ejecutar la aplicación



```powershell

.\\mvnw.cmd clean spring-boot:run

```



La aplicación estará lista cuando aparezca:



```text

Tomcat started on port 8080

Started ProductosApplication

```



URL principal:



```text

http://localhost:8080/api/productos

```



\## Ejecutar desde Eclipse



1\. Seleccione `File > Import`.

2\. Seleccione `Maven > Existing Maven Projects`.

3\. Seleccione la carpeta del proyecto.

4\. Presione `Finish`.

5\. Clic derecho sobre el proyecto.

6\. Seleccione `Maven > Update Project`.

7\. Abra `Run > Run Configurations`.

8\. Seleccione `Spring Boot App`.

9\. Abra la pestaña `Environment`.

10\. Agregue:



```text

Name:

GOOGLE\_APPLICATION\_CREDENTIALS

```



```text

Value:

RUTA\_COMPLETA\\credenciales\\firebase-productos.json

```



11\. Presione `Apply > Run`.



\## Endpoints



| Método | Endpoint              | Operación           |

| ------ | --------------------- | ------------------- |

| GET    | `/api/productos`      | Listar productos    |

| GET    | `/api/productos/{id}` | Consultar por ID    |

| POST   | `/api/productos`      | Crear producto      |

| PUT    | `/api/productos/{id}` | Actualizar producto |

| DELETE | `/api/productos/{id}` | Eliminar producto   |



\## Pruebas en Postman



\### Listar



```http

GET http://localhost:8080/api/productos

```



Respuesta esperada:



```text

200 OK

```



\### Crear



```http

POST http://localhost:8080/api/productos

Content-Type: application/json

```



```json

{

&#x20; "nombre": "Mouse inalámbrico",

&#x20; "descripcion": "Mouse ergonómico con conexión Bluetooth",

&#x20; "precio": 85000

}

```



Respuesta esperada:



```text

201 Created

```



\### Consultar por ID



```http

GET http://localhost:8080/api/productos/ID\_DEL\_PRODUCTO

```



\### Actualizar



```http

PUT http://localhost:8080/api/productos/ID\_DEL\_PRODUCTO

Content-Type: application/json

```



```json

{

&#x20; "nombre": "Mouse actualizado",

&#x20; "descripcion": "Mouse recargable con conexión Bluetooth",

&#x20; "precio": 99000

}

```



Respuesta esperada:



```text

200 OK

```



\### Eliminar



```http

DELETE http://localhost:8080/api/productos/ID\_DEL\_PRODUCTO

```



Respuesta esperada:



```text

204 No Content

```



\## Validaciones



\* `nombre` es obligatorio.

\* `descripcion` es obligatoria.

\* `precio` es obligatorio y debe ser mayor que cero.



\## Problemas frecuentes



\### Credenciales no encontradas



Si aparece:



```text

Your default credentials were not found

```



Configure nuevamente:



```powershell

$env:GOOGLE\_APPLICATION\_CREDENTIALS = `

(Resolve-Path ".\\credenciales\\firebase-productos.json").Path

```



\### El GET devuelve `\[]`



Verifique:



\* Que la colección se llame `producto`.

\* Que existan documentos en Firestore.

\* Que la credencial corresponda al proyecto correcto.

\* Que los campos se llamen `nombre`, `descripcion` y `precio`.



\## Detener la aplicación



En PowerShell:



```text

Ctrl + C

```



\## Repositorio



```text

https://github.com/andrets13/politecnico-producto

```



\## Autor



```text

andrets13

```



