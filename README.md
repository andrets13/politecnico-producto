\# CRUD de Productos con Spring Boot y Firebase



Aplicación backend que implementa una API REST para administrar productos mediante operaciones de creación, consulta, actualización y eliminación.



Los datos se almacenan en Firebase Cloud Firestore.



\## Repositorio



```text

https://github.com/andrets13/politecnico-producto

```



\## Tecnologías utilizadas



\* Java 21

\* Spring Boot 3.5.16

\* Maven Wrapper

\* Firebase Admin SDK

\* Firebase Cloud Firestore

\* Jakarta Validation

\* API REST

\* Postman

\* Eclipse IDE



\## Funcionalidades



La API permite:



\* Crear productos.

\* Consultar todos los productos.

\* Consultar un producto por su identificador.

\* Actualizar productos.

\* Eliminar productos.

\* Validar los datos recibidos.

\* Almacenar la información en Firebase Cloud Firestore.



\## Modelo Producto



Cada producto contiene los siguientes campos:



| Campo         | Tipo   | Descripción                                |

| ------------- | ------ | ------------------------------------------ |

| `id`          | String | Identificador único generado por Firestore |

| `nombre`      | String | Nombre del producto                        |

| `descripcion` | String | Descripción del producto                   |

| `precio`      | Double | Precio del producto                        |



Ejemplo:



```json

{

&#x20; "id": "OyvwFRQRZO95TzJRm9Jm",

&#x20; "nombre": "Libro de matematicas",

&#x20; "descripcion": "Libro de matematicas grado 6",

&#x20; "precio": 55000.0

}

```



\## Estructura del proyecto



```text

productos

├── credenciales

│   ├── README.md

│   └── firebase-productos.json

├── src

│   ├── main

│   │   ├── java

│   │   │   └── com.ejemplo.productos

│   │   │       ├── config

│   │   │       ├── controller

│   │   │       ├── exception

│   │   │       ├── model

│   │   │       ├── repository

│   │   │       ├── service

│   │   │       └── ProductosApplication.java

│   │   └── resources

│   │       └── application.properties

│   └── test

├── .gitignore

├── mvnw

├── mvnw.cmd

├── pom.xml

└── README.md

```



\## Organización de paquetes



| Paquete                 | Responsabilidad                           |

| ----------------------- | ----------------------------------------- |

| `config`                | Configuración de la conexión con Firebase |

| `controller`            | Definición de los endpoints REST          |

| `exception`             | Manejo de excepciones                     |

| `model`                 | Definición del modelo Producto            |

| `repository`            | Operaciones con Firebase Firestore        |

| `service`               | Lógica de negocio                         |

| `com.ejemplo.productos` | Clase principal de Spring Boot            |



\## Requisitos previos



Antes de ejecutar el proyecto se necesita:



\* Java JDK 21.

\* Conexión a Internet.

\* Archivo `firebase-productos.json`.

\* Puerto 8080 disponible.

\* Eclipse IDE o PowerShell.

\* Postman para probar los endpoints.



No es necesario instalar Maven porque el proyecto incluye Maven Wrapper.



\## Descargar el proyecto



Desde PowerShell:



```powershell

git clone https://github.com/andrets13/politecnico-producto.git

cd ".\\politecnico-producto"

```



\## Configurar las credenciales de Firebase



El archivo de credenciales se entrega de manera separada por seguridad.



El archivo debe llamarse:



```text

firebase-productos.json

```



Debe guardarse en:



```text

politecnico-producto\\credenciales\\firebase-productos.json

```



La estructura debe quedar:



```text

politecnico-producto

├── credenciales

│   └── firebase-productos.json

├── src

└── pom.xml

```



Si el archivo está en la carpeta Descargas, se puede copiar con:



```powershell

Copy-Item `

"C:\\Users\\NOMBRE\_USUARIO\\Downloads\\firebase-productos.json" `

".\\credenciales\\firebase-productos.json"

```



Se debe reemplazar `NOMBRE\_USUARIO` por el usuario correspondiente de Windows.



\## Verificar Java



Ejecute:



```powershell

java -version

```



Debe mostrar Java 21 o una versión compatible.



Ejemplo:



```text

java version "21"

```



\## Ejecutar desde PowerShell



Ubíquese en la carpeta principal:



```powershell

cd "RUTA\\politecnico-producto"

```



Configure la variable de entorno:



```powershell

$env:GOOGLE\_APPLICATION\_CREDENTIALS = `

(Resolve-Path ".\\credenciales\\firebase-productos.json").Path

```



Verifique la ruta:



```powershell

$env:GOOGLE\_APPLICATION\_CREDENTIALS

```



Ejecute la aplicación:



```powershell

.\\mvnw.cmd clean spring-boot:run

```



Cuando la aplicación inicie correctamente aparecerán mensajes similares a:



```text

Tomcat started on port 8080

Started ProductosApplication

```



La API estará disponible en:



```text

http://localhost:8080/api/productos

```



\## Ejecutar desde Eclipse



\### Importar el proyecto



1\. Abrir Eclipse.

2\. Seleccionar `File`.

3\. Seleccionar `Import`.

4\. Seleccionar `Maven`.

5\. Seleccionar `Existing Maven Projects`.

6\. Presionar `Next`.

7\. Seleccionar la carpeta `politecnico-producto`.

8\. Verificar que Eclipse detecte el archivo `pom.xml`.

9\. Presionar `Finish`.

10\. Esperar mientras Maven descarga las dependencias.



\### Actualizar Maven



1\. Clic derecho sobre el proyecto.

2\. Seleccionar `Maven`.

3\. Seleccionar `Update Project`.

4\. Marcar `Force Update of Snapshots/Releases`.

5\. Presionar `OK`.



\### Configurar la credencial en Eclipse



1\. Seleccionar `Run`.

2\. Seleccionar `Run Configurations`.

3\. Abrir `Spring Boot App`.

4\. Seleccionar el proyecto.

5\. Entrar a `Environment`.

6\. Presionar `Add`.

7\. Agregar:



```text

Name:

GOOGLE\_APPLICATION\_CREDENTIALS

```



```text

Value:

RUTA\_COMPLETA\\politecnico-producto\\credenciales\\firebase-productos.json

```



Ejemplo:



```text

C:\\Users\\docente\\Downloads\\politecnico-producto\\credenciales\\firebase-productos.json

```



8\. Presionar `Apply`.

9\. Presionar `Run`.



La variable debe llamarse exactamente:



```text

GOOGLE\_APPLICATION\_CREDENTIALS

```



\## Base de datos



La aplicación utiliza la base de datos predeterminada de Cloud Firestore:



```text

(default)

```



La colección utilizada se llama:



```text

producto

```



Cada documento contiene:



```text

nombre

descripcion

precio

```



El identificador del documento se utiliza como `id` del producto.



\## Endpoints disponibles



| Método | Endpoint              | Operación           | Respuesta        |

| ------ | --------------------- | ------------------- | ---------------- |

| GET    | `/api/productos`      | Listar productos    | `200 OK`         |

| GET    | `/api/productos/{id}` | Consultar por ID    | `200 OK`         |

| POST   | `/api/productos`      | Crear producto      | `201 Created`    |

| PUT    | `/api/productos/{id}` | Actualizar producto | `200 OK`         |

| DELETE | `/api/productos/{id}` | Eliminar producto   | `204 No Content` |



\## Probar con Postman



La aplicación debe estar ejecutándose antes de realizar las pruebas.



\### Listar productos



```http

GET http://localhost:8080/api/productos

```



Respuesta esperada:



```text

200 OK

```



Ejemplo:



```json

\[

&#x20; {

&#x20;   "id": "OyvwFRQRZO95TzJRm9Jm",

&#x20;   "nombre": "Libro de matematicas",

&#x20;   "descripcion": "Libro de matematicas grado 6",

&#x20;   "precio": 55000.0

&#x20; }

]

```



\### Consultar un producto por ID



```http

GET http://localhost:8080/api/productos/ID\_DEL\_PRODUCTO

```



Se debe reemplazar `ID\_DEL\_PRODUCTO` por un identificador existente.



Respuesta esperada:



```text

200 OK

```



\### Crear un producto



```http

POST http://localhost:8080/api/productos

```



En Postman seleccione:



```text

Body > raw > JSON

```



Envíe:



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



Ejemplo de respuesta:



```json

{

&#x20; "id": "ID\_GENERADO\_POR\_FIREBASE",

&#x20; "nombre": "Mouse inalámbrico",

&#x20; "descripcion": "Mouse ergonómico con conexión Bluetooth",

&#x20; "precio": 85000.0

}

```



\### Actualizar un producto



```http

PUT http://localhost:8080/api/productos/ID\_DEL\_PRODUCTO

```



Envíe todos los campos del producto:



```json

{

&#x20; "nombre": "Mouse inalámbrico actualizado",

&#x20; "descripcion": "Mouse ergonómico recargable con conexión Bluetooth",

&#x20; "precio": 99000

}

```



Respuesta esperada:



```text

200 OK

```



\### Eliminar un producto



```http

DELETE http://localhost:8080/api/productos/ID\_DEL\_PRODUCTO

```



No se debe enviar contenido en el cuerpo de la petición.



Respuesta esperada:



```text

204 No Content

```



Es normal que el cuerpo de la respuesta aparezca vacío.



\## Validaciones



Los campos deben cumplir las siguientes condiciones:



\* `nombre` es obligatorio.

\* `descripcion` es obligatoria.

\* `precio` es obligatorio.

\* `precio` debe ser mayor que cero.



Ejemplo de una petición inválida:



```json

{

&#x20; "nombre": "",

&#x20; "descripcion": "",

&#x20; "precio": 0

}

```



La API debe responder:



```text

400 Bad Request

```



\## Ejecutar las pruebas del proyecto



Desde PowerShell:



```powershell

.\\mvnw.cmd test

```



\## Detener la aplicación



En PowerShell:



```text

Ctrl + C

```



En Eclipse se debe presionar el botón rojo `Terminate` de la consola.



\## Solución de problemas



\### Credenciales no encontradas



Mensaje:



```text

Your default credentials were not found

```



Solución:



1\. Confirmar que exista:



```text

credenciales\\firebase-productos.json

```



2\. Configurar nuevamente:



```powershell

$env:GOOGLE\_APPLICATION\_CREDENTIALS = `

(Resolve-Path ".\\credenciales\\firebase-productos.json").Path

```



3\. Reiniciar la aplicación.



\### La consulta devuelve una lista vacía



Respuesta:



```json

\[]

```



Verifique:



\* Que la credencial pertenezca al proyecto correcto.

\* Que la colección se llame `producto`.

\* Que existan documentos en la colección.

\* Que la aplicación utilice la base `(default)`.



\### Puerto 8080 ocupado



Cambie temporalmente el puerto:



```powershell

.\\mvnw.cmd spring-boot:run `

"-Dspring-boot.run.arguments=--server.port=8081"

```



La URL será:



```text

http://localhost:8081/api/productos

```



\### Advertencia de Commons Logging



Puede aparecer:



```text

Standard Commons Logging discovery in action with spring-jcl

```



Es una advertencia de dependencias y no impide que la aplicación funcione.



\## Autor



Proyecto desarrollado por:



```text

aatinoco@poligran.edu.co

```



Como parte de la actividad de arquitectura de aplicaciones web relacionada con la implementación de servicios RESTful y operaciones CRUD.



