\# CRUD de Productos con Spring Boot y Firebase



!\[Java](https://img.shields.io/badge/Java-21-orange)

!\[Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.16-brightgreen)

!\[Firebase](https://img.shields.io/badge/Firebase-Firestore-yellow)

!\[API REST](https://img.shields.io/badge/API-REST-blue)



API REST para gestionar productos mediante operaciones de creación, consulta, actualización y eliminación. La aplicación fue desarrollada con Spring Boot y utiliza Firebase Cloud Firestore como base de datos NoSQL.



\## Tecnologías



\* Java 21

\* Spring Boot 3.5.16

\* Maven Wrapper

\* Firebase Admin SDK

\* Cloud Firestore

\* Jakarta Validation

\* Postman

\* Eclipse IDE



\## Estructura del proyecto



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



| Paquete      | Responsabilidad           |

| ------------ | ------------------------- |

| `config`     | Configuración de Firebase |

| `controller` | Endpoints de la API REST  |

| `exception`  | Manejo de errores         |

| `model`      | Modelo Producto           |

| `repository` | Acceso a Firestore        |

| `service`    | Lógica de negocio         |



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



\## Requisitos



\* Java JDK 21.

\* Git.

\* Conexión a Internet.

\* Archivo `firebase-productos.json`.

\* Puerto 8080 disponible.

\* Postman para probar la API.



No es necesario instalar Maven porque el proyecto incluye Maven Wrapper.



\## Descargar el proyecto



Desde PowerShell:



```powershell

git clone https://github.com/andrets13/politecnico-producto.git

cd ".\\politecnico-producto"

```



\## Configurar la credencial de Firebase



El archivo `firebase-productos.json` se entrega por separado.



Cree la carpeta:



```powershell

New-Item -ItemType Directory -Force "C:\\credenciales"

```



Guarde el archivo recibido en:



```text

C:\\credenciales\\firebase-productos.json

```



La estructura debe quedar:



```text

C:\\credenciales

└── firebase-productos.json

```



Configure la variable de entorno:



```powershell

$env:GOOGLE\_APPLICATION\_CREDENTIALS = "C:\\credenciales\\firebase-productos.json"

```



Verifique la configuración:



```powershell

$env:GOOGLE\_APPLICATION\_CREDENTIALS

```



\## Ejecutar desde PowerShell



Ubíquese en el proyecto:



```powershell

cd "RUTA\\politecnico-producto"

```



Configure la credencial si todavía no lo ha hecho:



```powershell

$env:GOOGLE\_APPLICATION\_CREDENTIALS = "C:\\credenciales\\firebase-productos.json"

```



Ejecute la aplicación:



```powershell

.\\mvnw.cmd clean spring-boot:run

```



La aplicación estará disponible cuando aparezca:



```text

Tomcat started on port 8080

Started ProductosApplication

```



URL principal:



```text

http://localhost:8080/api/productos

```



\## Ejecutar desde Eclipse



\### Importar el proyecto



1\. Abra Eclipse.

2\. Seleccione `File > Import`.

3\. Seleccione `Maven > Existing Maven Projects`.

4\. Seleccione la carpeta `politecnico-producto`.

5\. Presione `Finish`.

6\. Espere la descarga de las dependencias.

7\. Clic derecho sobre el proyecto.

8\. Seleccione `Maven > Update Project`.

9\. Marque `Force Update of Snapshots/Releases`.

10\. Presione `OK`.



\### Configurar la credencial



1\. Seleccione `Run > Run Configurations`.

2\. Abra `Spring Boot App`.

3\. Seleccione el proyecto.

4\. Abra la pestaña `Environment`.

5\. Presione `Add`.

6\. Configure:



```text

Name:

GOOGLE\_APPLICATION\_CREDENTIALS

```



```text

Value:

C:\\credenciales\\firebase-productos.json

```



7\. Presione `Apply`.

8\. Presione `Run`.



También puede ejecutar la clase:



```text

com.ejemplo.productos.ProductosApplication

```



mediante:



```text

Run As > Spring Boot App

```



\## Endpoints



| Método | Endpoint              | Operación           | Código esperado  |

| ------ | --------------------- | ------------------- | ---------------- |

| GET    | `/api/productos`      | Listar productos    | `200 OK`         |

| GET    | `/api/productos/{id}` | Consultar por ID    | `200 OK`         |

| POST   | `/api/productos`      | Crear producto      | `201 Created`    |

| PUT    | `/api/productos/{id}` | Actualizar producto | `200 OK`         |

| DELETE | `/api/productos/{id}` | Eliminar producto   | `204 No Content` |



\## Pruebas con Postman



\### Listar productos



```http

GET http://localhost:8080/api/productos

```



\### Consultar por ID



```http

GET http://localhost:8080/api/productos/ID\_DEL\_PRODUCTO

```



\### Crear producto



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



\### Actualizar producto



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



\### Eliminar producto



```http

DELETE http://localhost:8080/api/productos/ID\_DEL\_PRODUCTO

```



La respuesta esperada es:



```text

204 No Content

```



Es normal que la respuesta no tenga contenido.



\## Validaciones



\* `nombre` es obligatorio.

\* `descripcion` es obligatoria.

\* `precio` es obligatorio.

\* `precio` debe ser mayor que cero.



Ejemplo inválido:



```json

{

&#x20; "nombre": "",

&#x20; "descripcion": "",

&#x20; "precio": 0

}

```



Respuesta esperada:



```text

400 Bad Request

```



\## Problemas frecuentes



\### Credenciales no encontradas



Si aparece:



```text

Your default credentials were not found

```



Ejecute:



```powershell

$env:GOOGLE\_APPLICATION\_CREDENTIALS = "C:\\credenciales\\firebase-productos.json"

```



Compruebe también que el archivo exista:



```powershell

Test-Path "C:\\credenciales\\firebase-productos.json"

```



El resultado debe ser:



```text

True

```



\### El GET devuelve una lista vacía



Si la respuesta es:



```json

\[]

```



Verifique:



\* Que la credencial pertenezca al proyecto correcto.

\* Que la colección se llame exactamente `producto`.

\* Que existan documentos en Firestore.

\* Que los campos se llamen `nombre`, `descripcion` y `precio`.



\### Puerto 8080 ocupado



Ejecute temporalmente en otro puerto:



```powershell

.\\mvnw.cmd spring-boot:run `

"-Dspring-boot.run.arguments=--server.port=8081"

```



Nueva URL:



```text

http://localhost:8081/api/productos

```



\## Detener la aplicación



En PowerShell:



```text

Ctrl + C

```



En Eclipse presione el botón rojo `Terminate`.



\## Repositorio



```text

https://github.com/andrets13/politecnico-producto

```



\## Autor



```text

andrets13

```



Proyecto desarrollado como actividad académica para implementar servicios RESTful y operaciones CRUD utilizando un framework backend y una base de datos en la nube.



