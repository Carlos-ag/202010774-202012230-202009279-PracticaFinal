# Stockify - Práctica final
# Proyecto realizado por:
# Carlos Martín de Argila Lorente, Carlos Marí Noguera, Miguel Ángel Fernández Villar

## Introducción:

Este es el repositorio se ha realizada en el cuadro de PROGRAMACIÓN DE APLICACIONES TELEMÁTICAS.
Se ha realizado tanto el frontend como el backend de una aplicación completa para el seguimiento de acciones y mercados financieros y de una cartera. La aplicacion personaliza el conteido para cada usuario mostrandole la información de su cartera una vez que ha iniciado sesión.

## Overview de la aplicación
La aplicación cuenta con varias secciones:
-1. Presentación: disponemos de una pagina web de presención que permite conocer al usuario de primera mano las funcionalidades de nuestra aplicación así como los planes de suscripción que tiene a su alcance.
-2. Login/Registro: aquí es donde el usuario puede crearse una cuenta o inciar sesión
-3. Aplicación: La aplicación en si, se puede divir en 4 secciones. Aunque la hemos programado para optimizar la UX de tal manera que solo se actualiza el Main, porque como el resto de secciones siempre son iguales no tienen porque cambiar. Las 4 secciones son:
- Header: esta sección se compone de un logo (que te lleva a la página de inicio) y de una search bar autocompletable (cuidado API limit 5 busquedas por minuto) que permite al usuario buscar acciones y para obtener su precio y un gráfico de su cotización. 
- Sidebar: Está sección permite al usuario navegar entre las distintas pantallas de la aplicación. Dispone de varios botones:
1. Inicio: Te da la bienvenida a la aplicación con uan bonita vista de las cotizaciones de los principales indices de Estados Unidos
2. Noticias: Incluye noticias de hoy relevantes a los mercados
3. Resumen: incluye unos gráficos generados en Python con plotly (que en un futuro se podría hacer otro endpoint para ir actualizandolos con los datos reales del usuario)
4. Cartera: En esta pestaña el usuario puede exportar/importar una cartera de acciones desde un CSV, y añadir/modificar/borrar movimientos de la cartera de acciones. Se muestran en una tabla todos los movimientos del usuario
5. Dividendos: Esta página es parecida a la de resumen, solo que en esta se hace un resumen de los dividendos que ha cobrado el usuario. Al igual que resumen, en un futuro se podrían actualizar los plotly con los datos reales del usuario.
6. Social: Esta pestaña permite al usuario relacionarse con otros usuarios de la aplicacion viendo el número de movimientos que han hecho. Puede buscar por usuario, y ver los resultados tanto en forma de tabla como de cards. 
7. Mi perfil: permite al usuario contactar con el soporte técnico con una interfaz similar a la de Whatsapp y también el usuario aquí puede cambiar información relativa a su cuenta. 
8. Cerrar sesión: se cierra sesión de la cuenta existente.
- Main: en el main se muestra el contenido seleccionado en la sidebar o el gráfico de la acción buscada en la search bar.
- Footer: en el footer siempre está disponible un mensaje por si el usuario quiere contactar con soporte técnico.

## Funcionalidades a destacar:
La aplicación cuenta con avanzadas funcionalidades como:
- autentificación (se verifica que un usuario no autentificado no pueda acceder a contenido restringido a usuarios que han iniciado sesión). Si intentan acceder sin iniciar sesión salta una alerta y se les redirecciona automaticamente a la página del login.
- Cookies
- views, 
- queries complejas con joins de varias tablas, 
- logs, 
- tests, 
- llamadas a APIs(tanto del backend como APIs externas), 
- actualización solamente del contenido de la web que cambiante (no toda la web), uso de 
- SCSS
- Bootstrap
- Transaccionales (principalmente para asegurar que cuando se importa una cartera a través de un CSV se cargue entera la cartera)
- Gestión de errores
- Actuator para verificar que el backend está activo

## Bases de datos creadas: TODO cambiar imagen y texto
Para representar las bases de datos creadas hemos creado el diagrama que se puede ver a continuación:
 ![Diagrama SQL](assets/diagrama_sql.png)

 Aquí están las tablas con las descripciones solicitadas:

### Tabla: SUBSCRIPTION_PLANS

| Columna | Tipo de Datos | Descripción |
| --- | --- | --- |
| ID | INT, NOT NULL, AUTO_INCREMENT | Identificador único de cada plan de suscripción. |
| NAME | VARCHAR(255), NOT NULL | Nombre del plan de suscripción. |
| PRICE | DOUBLE, NOT NULL | Precio del plan de suscripción. |

### Tabla: USERS

| Columna | Tipo de Datos | Descripción |
| --- | --- | --- |
| ID | INT, NOT NULL, AUTO_INCREMENT | Identificador único de cada usuario. |
| NAME | VARCHAR(255), NOT NULL | Nombre del usuario. |
| EMAIL | VARCHAR(255), NOT NULL | Correo electrónico del usuario. |
| PHONE | VARCHAR(255), NOT NULL | Teléfono del usuario. |
| PASSWORD | VARCHAR(255), NOT NULL | Contraseña del usuario. |
| SUBSCRIPTION_PLAN | INT, NOT NULL | Identificador del plan de suscripción al que está suscrito el usuario. Clave foránea que hace referencia a la tabla SUBSCRIPTION_PLANS. |

### Tabla: SIGNED_MESSAGES

| Columna | Tipo de Datos | Descripción |
| --- | --- | --- |
| ID | INT, NOT NULL, AUTO_INCREMENT | Identificador único de cada mensaje firmado. |
| USER_ID | INT, NOT NULL | Identificador del usuario que envía el mensaje. Clave foránea que hace referencia a la tabla USERS. |
| MESSAGE | VARCHAR(255), NOT NULL | Contenido del mensaje. |
| TIMESTAMP | TIMESTAMP, NOT NULL | Fecha y hora en la que se envió el mensaje. |
| CONVERSATION_ID | INT, NOT NULL | Identificador de la conversación a la que pertenece el mensaje. |

### Tabla: UNSIGNED_MESSAGES

| Columna | Tipo de Datos | Descripción |
| --- | --- | --- |
| ID | INT, NOT NULL, AUTO_INCREMENT | Identificador único de cada mensaje sin firmar. |
| NAME | VARCHAR(255), NOT NULL | Nombre del remitente del mensaje. |
| EMAIL | VARCHAR(255), NOT NULL | Correo electrónico del remitente del mensaje. |
| MESSAGE | VARCHAR(255), NOT NULL | Contenido del mensaje. |
| TIMESTAMP | TIMESTAMP, NOT NULL | Fecha y hora en la que se envió el mensaje. |

### Tabla: PORTFOLIO_MOVEMENTS

| Columna | Tipo de Datos | Descripción |
| --- | --- | --- |
| ID | INT, NOT NULL, AUTO_INCREMENT | Identificador único de cada movimiento del portafolio. |
| USER_ID | INT, NOT NULL | Identificador del usuario asociado al movimiento del portafolio. Clave foránea que hace referencia a la tabla USERS. |
| TICKER | VARCHAR(255), NOT NULL | Símbolo de cotización (ticker) del instrumento financiero. |
| QUANTITY | INT, NOT NULL | Cantidad de instrumentos involucrados en el movimiento. |
| PRICE | DOUBLE, NOT NULL | Precio del instrumento financiero en el momento del movimiento. |
| DATE | DATE, NOT NULL | Fecha del movimiento del portafolio. |

### Vista: SOCIAL

| Columna | Descripción |
| --- | --- |
| USER_NAME | Nombre del usuario. |
| USER_EMAIL | Correo electrónico del usuario. |
| SUBSCRIPTION_PLAN | Nombre del plan de suscripción al que está suscrito el usuario. |
| PORTFOL

IO_MOVEMENTS | Cantidad de movimientos del portafolio asociados al usuario. |
 

## Endpoints de la API: todo cambiar

A continuación se presenta la documentación de los endpoints de las APIs proporcionadas:

### PortfolioController

1. **GET /portfolio/{userID}**

   Obtiene el portafolio del usuario especificado por el ID de usuario.

   - Parámetros de ruta:
     - `userID`: ID del usuario (Integer)

   - Respuestas:
     - `200 OK`: Si se encuentra el portafolio del usuario.
     - `400 BAD_REQUEST`: Si ocurre un error al obtener el portafolio.
     - `500 INTERNAL_SERVER_ERROR`: Si ocurre un error inesperado.

2. **POST /movement**

   Añade un nuevo movimiento al portafolio.

   - Parámetros de cuerpo (JSON):
     - `payload`: Objeto PortfolioMovement

   - Respuestas:
     - `200 OK`: Si se añade correctamente el movimiento.
     - `400 BAD_REQUEST`: Si ocurre un error al añadir el movimiento.
     - `500 INTERNAL_SERVER_ERROR`: Si ocurre un error inesperado.

3. **POST /upload**

   Sube y procesa un archivo CSV con movimientos del portafolio.

   - Parámetros de formulario:
     - `file`: Archivo CSV (MultipartFile)

   - Respuestas:
     - `200 OK`: Si se sube y procesa correctamente el archivo.
     - `500 INTERNAL_SERVER_ERROR`: Si falla la subida o el procesamiento del archivo.

4. **GET /download/{userID}**

   Descarga el archivo CSV con los movimientos del portafolio del usuario especificado por el ID de usuario.

   - Parámetros de ruta:
     - `userID`: ID del usuario (Integer)

   - Respuestas:
     - `200 OK`: Si se descarga correctamente el archivo.
     - `404 NOT_FOUND`: Si no se encuentra el archivo.
     - `500 INTERNAL_SERVER_ERROR`: Si falla la descarga del archivo.

5. **POST /movement/update**

   Actualiza un movimiento existente en el portafolio.

   - Parámetros de cuerpo (JSON):
     - `payload`: Objeto PortfolioMovement

   - Respuestas:
     - `200 OK`: Si se actualiza correctamente el movimiento.
     - `400 BAD_REQUEST`: Si ocurre un error al actualizar el movimiento.
     - `500 INTERNAL_SERVER_ERROR`: Si ocurre un error inesperado.

6. **GET /movement/{movementID}**

   Obtiene un movimiento específico del portafolio.

   - Parámetros de ruta:
     - `movementID`: ID del movimiento (Integer)

   - Respuestas:
     - `200 OK`: Si se encuentra el movimiento.
     - `400 BAD_REQUEST`: Si ocurre un error al obtener el movimiento.
     - `500 INTERNAL_SERVER_ERROR`: Si ocurre un error inesperado.

7. **DELETE /movement/{movementID}**

   Elimina un movimiento específico del portafolio.

   - Parámetros de ruta:
     - `movementID`: ID del movimiento (Integer)

   - Respuestas:
     - `200 OK`: Si se elimina correctamente el movimiento.
     - `500 INTERNAL_SERVER_ERROR`: Si ocurre un error inesperado.


### SignedMessageController

1. **POST /signedMessages**

   Añade un mensaje firmado.

   - Parámetros de cuerpo (JSON):
     - `signedMessageDTO`: Objeto SignedMessageDTO

   - Respuestas:
     - `200 OK`: Si se añade correctamente el mensaje firmado.
     - `400 BAD_REQUEST`: Si ocurre un error al añadir el mensaje firmado.
     - `500 INTERNAL_SERVER_ERROR`: Si ocurre un error inesperado.

2. **GET /lastConversationId**

   Obtiene el último ID de conversación.

   - Respuestas:
     - `200 OK`: Si se obtiene correctamente el último ID de conversación.

3. **GET /signedMessages/{conversationId}**

   Obtiene los mensajes firmados de una conversación específica.

   - Parámetros de ruta:
     - `conversationId`: ID de la conversación (Integer)

   - Respuestas:
     - `200 OK`: Si se obtienen correctamente los mensajes firmados.
     - `400 BAD_REQUEST`: Si ocurre un error al obtener los mensajes firmados.
     - `500 INTERNAL_SERVER_ERROR`: Si ocurre un error inesperado.

4. **GET /signedMessages/latest/{userId}**

   Obtiene los últimos mensajes firmados del usuario especificado por el ID de usuario.

   - Parámetros de ruta:
     - `userId`: ID del usuario (Integer)

   - Respuestas:
     - `200 OK`: Si se obtienen correctamente los últimos mensajes firmados.
     - `400 BAD_REQUEST`: Si ocurre un error al obtener los últimos mensajes firmados.
     - `500 INTERNAL_SERVER_ERROR`: Si ocurre un error inesperado.

### SuscriptionPlanController

1. **GET /suscriptionPlans/{suscriptionPlanID}**

   Obtiene información del plan de suscripción especificado por el ID de suscripción.

   - Parámetros de ruta:
     - `suscriptionPlanID`: ID del plan de suscripción (Integer)

   - Respuestas:
     - `200 OK`: Si se obtiene correctamente la información del plan de suscripción.

2. **GET /subscriptionPlans**

   Obtiene todos los planes de suscripción.

   - Respuestas:
     - `200 OK`: Si se obtienen correctamente los planes de suscripción.

### UnsignedMessageController

1. **POST /contact**

   Añade un mensaje de contacto.

   - Parámetros de cuerpo (JSON):
     - `message`: Mapa con información del mensaje de contacto

   - Respuestas:
     - `200 OK`: Si se añade correctamente el mensaje de contacto.
     - `400 BAD_REQUEST`: Si ocurre un error al añadir el mensaje de contacto.
     - `500 INTERNAL_SERVER_ERROR`: Si ocurre un error inesperado.

### UserController

1. **GET /users/{userID}**

   Obtiene información del usuario especificado por el ID de usuario.

   - Parámetros de ruta:
     - `userID`: ID del usuario (Integer)

   - Respuestas:
     - `200 OK`: Si se obtiene correctamente la información del usuario.

2. **POST /users**

   Añade un nuevo usuario.

   - Parámetros de cuerpo (JSON):
     - `user`: Objeto User

   - Respuestas:
     - `200 OK`: Si se añade correctamente el usuario.
     - `400 BAD_REQUEST`: Si ocurre un error al añadir el usuario.
     - `500 INTERNAL_SERVER_ERROR`: Si ocurre un error inesperado.


## Tests unitarios:

- SignedMessageControllerTest:
    - addSignedMessage_shouldReturnOk_whenValidMessage: Comprueba que se devuelve una respuesta con estado OK (200) cuando se añade un mensaje válido.
    - addSignedMessage_shouldReturnBadRequest_whenInvalidMessage: Comprueba que se devuelve una respuesta con estado Bad Request (400) cuando se intenta añadir un mensaje inválido.
    - addSignedMessage_shouldReturnInternalServerError_whenUnexpectedError: Comprueba que se devuelve una respuesta con estado Internal Server Error (500) cuando ocurre un error inesperado al añadir un mensaje.
    - getLastConversationId_shouldReturnOk: Comprueba que se devuelve una respuesta con estado OK (200) y un entero cuando se pide el último ID de conversación.
    - getSignedMessages_shouldReturnOk_whenValidConversationId: Comprueba que se devuelve una respuesta con estado OK (200) y una lista de mensajes cuando se pide una lista de mensajes para una ID de conversación válida.
    - getLatestMessagesByUserId_shouldReturnOk_whenValidUserId: Comprueba que se devuelve una respuesta con estado OK (200) y una lista de los últimos mensajes para un ID de usuario válido.
    - getSignedMessages_shouldReturnBadRequest_whenInvalidConversationId: Comprueba que se devuelve una respuesta con estado Bad Request (400) cuando se intenta obtener una lista de mensajes con un ID de conversación inválido.
    - getSignedMessages_shouldReturnInternalServerError_whenUnexpectedError: Comprueba que se devuelve una respuesta con estado Internal Server Error (500) cuando ocurre un error inesperado al obtener una lista de mensajes.
    - addSignedMessage_shouldReturnBadRequest_whenRequiredFieldMissing: Comprueba que se devuelve una respuesta con estado Bad Request (400) cuando falta un campo requerido en el mensaje.

- SuscriptionPlanControllerTest:
    - getSuscriptionPlanInfo_shouldReturnSuscriptionPlanInfo: Comprueba que se devuelve una respuesta con estado OK (200) y los detalles del plan de suscripción correspondiente a un ID de plan válido.
    - getAllSuscriptionPlans_shouldReturnAllSuscriptionPlans: Comprueba que se devuelve una respuesta con estado OK (200) y una lista con todos los planes de suscripción disponibles.
    - getSuscriptionPlanInfo_shouldReturnBadRequest_whenInvalidId: Comprueba que se devuelve una respuesta con estado Bad Request (400) cuando se intenta obtener información de un plan de suscripción con un ID inválido.
    - getSuscriptionPlanInfo_shouldReturnInternalServerError_whenUnexpectedError: Comprueba que se devuelve una respuesta con estado Internal Server Error (500) cuando ocurre un error inesperado al obtener información de un plan de suscripción.

- UnsignedMessageControllerTest:
    - addContactMessage_shouldAddMessage: Comprueba que se devuelve una respuesta con estado OK (200) cuando se añade un mensaje de contacto.
    - addContactMessage_shouldReturnBadRequest_whenInvalidPayload: Comprueba que se devuelve una respuesta con estado Bad Request (400) cuando se intenta añadir un mensaje de contacto con datos inválidos.
    - addContactMessage_shouldReturnInternalServerError_whenUnexpectedError: Comprueba que se devuelve una respuesta con estado Internal Server Error (500) cuando ocurre un error inesperado al añadir un mensaje de contacto.

- UserControllerTest:
    - getUserInfo_shouldReturnUserInfo(): se prueba que se pueda obtener la información de un usuario existente a través de su ID. Se espera que el endpoint retorne un código de estado HTTP 200 y la información del usuario en formato JSON.

    - addUser_shouldAddUser(): se prueba que se pueda agregar un nuevo usuario a través del endpoint correspondiente. Se espera que el endpoint retorne un código de estado HTTP 200 y la información del usuario agregado en formato JSON.

    - addUser_shouldReturnBadRequest_whenInvalidData(): se prueba que el endpoint de agregar usuario retorne un código de estado HTTP 400 cuando se proporciona información inválida para el nuevo usuario.

    - addUser_shouldReturnInternalServerError_whenUnexpectedError(): se prueba que el endpoint de agregar usuario retorne un código de estado HTTP 500 cuando se produce un error inesperado al intentar agregar el nuevo usuario.




### MUY IMPORTANTE: USO:
Para usarlo hay que ejecutar el backend en el puerto 8080 (por defecto) para que funcione con el front. Como verá tengo el front y el backend en dos carpetas diferentes. Para ejecutar el backend hay que ponerse en la carpeta stockify-api. La web se puede acceder desde [aquí](https://carlos-ag.github.io/202010774-GITT-PAT-practica-5/Stockify/html/)


### IMPORTANTE:
Para ejecutar la API hace falta ponerse desde la carpeta stockify-api.
El frontend y el backend están separados (Stockify y stockify-api respectivamente)



## IMPORTANTE:
- API Noticias (100 noticias por día) (en principio *5 ya que he puesto 5 api keys diferentes)
- API Autocompletado (5 por minuto) compartidas con la API de cotización de acciones


### Introducción:
Está página web está siendo realizada en el cuadro de PROGRAMACIÓN DE APLICACIONES TELEMÁTICAS. 

La utilidad de la página es el de facilitar información al usuario sobre su cartera de acciones y permitirle en unos minutos tener un resumen de la evolución de esta en los últimos días/meses/años. 

La página web funciona tanto en ordenadores como en dispositivos móviles y además es responsive, es decir, se adapta al tamaño de la pantalla del dispositivo en el que se visualiza. Verá que los colores del header también cambian para que quede bonita en cualquier dispositivo.

Las técnologías usadas por el momento son HTML, CSS, Javascript, Python y Plotly, Java, Spring Boot, H2.

Para familiarizarnos con los frameworks, hemos incluido Bootstrap en la página de login.

También se ha usado SASS para facilitar el desarrollo de los archivos CSS. Para ello se ha usado la extensión de VSCode llamada Live Sass Compiler, que compila los archivos SASS a CSS en tiempo real. Se ha configurado esta extensión para que guarde los archivos en la carpeta CSS (y no en la carpeta SASS).

### Instalación:
Necesita un editor de texto como puede ser VSCode y un navegador para visualizar la página web.
Si lo desea para facilitar el desarrollo de la página web puede la extensión de VSCode llamada Live Server que sirve para ver en directo los cambios realizados en el HTML sin necesidad de recargar la página web. También recomendamos el uso de Live Sass Compiler para facilitar el desarrollo de los archivos CSS ya que compila los archivos SASS a CSS en tiempo real.
