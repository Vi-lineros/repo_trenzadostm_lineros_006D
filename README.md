# TrenzadosTM — Sistema de Microservicios

Plataforma distribuida basada en la arquitectura de microservicios orientada a la gestión integral de **TrenzadosTM**, un negocio dedicado a la digitalización, control de inventario y procesamiento de ventas de productos artesanales para caballos (cabezadas, riendas, barrigueras, etc.) elaborados mediante técnicas de trenzado en hilo.

---

## Arquitectura del Sistema

El sistema se rige bajo un patrón descentralizado donde cada módulo de negocio posee su propia autonomía funcional y persistencia de datos (arquitectura base de datos por microservicio). Toda comunicación externa es centralizada por un componente central de enrutamiento (**API Gateway**).

### Componente Central (API Gateway)
* **Puerto de Entrada:** `9090`
* **Base Path Global:** `/api/v1/`
* **Acceso Centralizado:** `http://localhost:9090/api/v1/`

Todos los microservicios se consumen a través de este punto de entrada único.

---

## 1. Bibliotecas Utilizadas

El desarrollo del ecosistema se construyó utilizando el framework **Spring Boot** en lenguaje Java, incorporando las siguientes dependencias principales en sus archivos `pom.xml`:

* **`org.springframework.boot:spring-boot-starter-web`** — Servidor web embebido (Tomcat) y soporte para controladores RESTful.
* **`org.springframework.boot:spring-boot-starter-data-jpa`** — Integración de JPA e Hibernate para la persistencia de datos en MySQL.
* **`org.springdoc:springdoc-openapi-starter-webmvc-ui`** — Generación automática de documentación técnica bajo el estándar OpenAPI 3 (Swagger UI).
* **`com.mysql:mysql-connector-j`** — Driver JDBC para la conexión con el motor de bases de datos MySQL.
* **`org.projectlombok:lombok`** — Reducción de código boilerplate (Getters, Setters, Constructores) mediante anotaciones.
* **`org.junit.jupiter:junit-jupiter` & `org.mockito:mockito-core`** — Frameworks para el desarrollo y aislamiento de pruebas unitarias (Mocks).

---

## 2. Requisitos y Configuración en Visual Studio Code

Para ejecutar este proyecto es necesario preparar el entorno de desarrollo con los siguientes elementos:

### Prerrequisitos del Sistema
1. **Java Development Kit (JDK) 21:** Instalado y configurado en el sistema operativo.
2. **XAMPP (MySQL):** Panel de control local para el motor de bases de datos.

### Preparación de la Base de Datos (XAMPP)
Antes de iniciar los servicios en el IDE, abra el Panel de Control de XAMPP, inicie los servicios de **Apache** y **MySQL**, e ingrese a *phpMyAdmin* para crear los siguientes esquemas de bases de datos vacíos:
* `trenzadostmdireccion`
* `trenzadostmenvio`
* `trenzadostminventario`
* `trenzadostmpago`
* `trenzadostmpedido`
* `trenzadostmreportes`
* `trenzadostmsucursal`
* `trenzadostmusuario`
* `trenzadostmusuarios`

---

### Extensiones Obligatorias en VS Code
Para disponer de las herramientas visuales de Spring Boot y Testing, busque e instale las siguientes extensiones desde el mercado de VS Code (`Ctrl+Shift+X`):

1. **Extension Pack for Java** (Microsoft): Instala el soporte de lenguaje para Java, depurador, gestor de proyectos Maven y el ejecutor de pruebas.
2. **Spring Boot Extension Pack** (VMware): Incluye las herramientas esenciales de desarrollo para Spring y, lo más importante, el **Spring Boot Dashboard**.
3. **Lombok Annotations Support for VS Code** (Gabriel潮流): Es indispensable para que VS Code reconozca los métodos generados automáticamente por Lombok (como los Getters y Setters) y no marque errores visuales en el código.

---

### Cómo Ejecutar el Proyecto en VS Code

1. **Abrir el proyecto:** * En VS Code, vaya a `Archivo` > `Abrir carpeta...` y seleccione la carpeta raíz del repositorio clonado.
   * Al abrirse, el gestor de Java de VS Code detectará automáticamente los archivos `pom.xml` y comenzará a estructurar el espacio de trabajo (verá una barra de progreso en la esquina inferior derecha).

2. **Uso del Spring Boot Dashboard:**
   * En la barra lateral izquierda de VS Code, aparecerá un nuevo ícono con la silueta de una hoja/símbolo de Spring llamado **Spring Boot Dashboard**. Dé clic sobre él.
   * Se desplegará un panel interactivo con la lista completa de todos los microservicios del proyecto (`trenzadostmdireccion`, `trenzadostmsucursal`, `trenzadostmpedido`, etc.).
   * Para iniciar cada microservicio, simplemente pase el cursor sobre el nombre de uno de ellos y haga clic en el botón de **Play (Run)** o en el botón de **Bicho (Debug)**.
   * Ejecute todos los microservicios necesarios, asegurándose de levantar también el componente del **API Gateway** en su puerto central.

3. **Ejecución de Pruebas Unitarias (Panel de Testing):**
   * En la barra lateral izquierda, haga clic en el ícono del **Tubo de ensayo (Testing)**.
   * El IDE cargará de forma gráfica el árbol completo de pruebas del proyecto, organizadas por módulos, paquetes y clases bajo la ruta `src/test/java`.
   * Para ejecutar los tests, puede dar clic en el botón de **Play** general en la parte superior del panel para correr todas las pruebas del sistema, o hacer clic en el botón de **Play** individual que aparece al lado de cada método o clase de test.
   * Una vez finalizado el proceso, el panel mostrará de forma visual un check verde (`✓`) confirmando que las aserciones pasaron correctamente.

---

## 3. Ejemplos de Rutas para Ejecución de API REST

Todos los puertos locales asignados por defecto se encuentran mapeados a continuación. Las pruebas de consumo deben realizarse utilizando una herramienta gráfica como **Postman**, apuntando al **API Gateway (Puerto 9090)**.

### Mapeo de Puertos Locales
todos los PATH tienen como predeterminado de inicio `/api/v1/**` despues de poner el puerto
* **`8080`** — Seguimiento (`/seguimiento`)
* **`8081`** — Dirección / Ubicación (`/region`, `/ciudad`, `/comuna`, `/direccion`)
* **`8082`** — Sucursales (`/sucursales`)
* **`8083`** — Usuario / Repartidor (`/usuarios`, `/repartidores`)
* **`8084`** — Catálogo de Productos e Inventario (`/categorias`, `/productos`, `/tipo-producto`, `/inventario`)
* **`8085`** — Pedidos (`/pedido`, `/detallepedido`)
* **`8086`** — Pagos y Bancos (`/bancos`, `/pagos`)
* **`8087`** — Envíos (`/envios`)
* **`8088`** — Reportes (`/reportes/ventas`, `/reportes/inventario`, `/reportes/envios`)
* **`8089`** — Autenticacion (`/api/auth/login`)

### Ejemplos Prácticos en Postman

#### Crear una Comuna (POST)
* **Ruta:** `POST http://localhost:9090/api/v1/comuna`
* **Cuerpo de la Petición (JSON):**
  ```json
  {
    "comuna": "pudahuel"
  }

* **Respuesta esperada:**
  ```json
  {
  "idComuna": 1,
  "nombreComuna": "pudahuel"
  }

#### Crear una Dirección Completa (POST)
* **Ruta:** `POST http://localhost:9090/api/v1/direccion`
* **Cuerpo de la Petición (JSON):**
  ```json
  {
    "ciudad": { "idCiudad": 1 },
    "comuna": { "idComuna": 1 },
    "region": { "idRegion": 1 },
    "direccion": "Pasaje monti 8712"
  }
* **Respuesta esperada:**
  ```json
  {
    "idDireccion": 1,
    "ciudad": { "idCiudad": 1, "nombreCiudad": null },
    "comuna": { "idComuna": 1, "nombreComuna": null },
    "region": { "idRegion": 1, "nombreRegion": null },
    "direccion": "Pasaje monti 1095"
  }
#### Listar Direcciones (GET)
* **Ruta:** `GET http://localhost:9090/api/v1/direccion`
* **Respuesta esperada:**
  ```json

  [
    {
      "idDireccion": 1,
      "ciudad": { "idCiudad": 1, "nombreCiudad": "osorno" },
      "comuna": { "idComuna": 1, "nombreComuna": "pudahuel" },
      "region": { "idRegion": 1, "nombreRegion": "metropolitana" },
      "direccion": "Pasaje monti 1095"
    }
  ]
* **Nota: Para aquellos endpoints protegidos del módulo de Usuarios, recuerde iniciar sesión en /api/auth/login, copiar el JWT generado y añadirlo visualmente en la pestaña Authorization de Postman bajo el tipo Bearer Token.

---

## 4. Ejemplos de Ruta para Ejecución de Swagger (OpenAPI)

Cada microservicio expone de forma interactiva e independiente su interfaz de Swagger UI. Esto permite revisar los contratos y probar endpoints desde el propio navegador web sin configurar herramientas externas.

### Formato Base de URL de Documentación
* **Formato Interfaz Gráfica:** `http://localhost:<PUERTO_MICROSERVICIO>/swagger-ui/index.html`
* **Formato JSON Estructurado:** `http://localhost:<PUERTO_MICROSERVICIO>/v3/api-docs`

### Enlaces de Acceso Directo por Componente

* ** Microservicio Dirección (Puerto 8081):**
  * Interfaz: `http://localhost:8081/swagger-ui/index.html`
  * JSON: `http://localhost:8081/v3/api-docs`

* ** Microservicio Sucursal (Puerto 8082):**
  * Interfaz: `http://localhost:8082/swagger-ui/index.html`
  * JSON: `http://localhost:8082/v3/api-docs`

* ** Microservicio Usuario / Autenticación (Puerto 8083):**
  * Interfaz: `http://localhost:8083/swagger-ui/index.html`
  * JSON: `http://localhost:8083/v3/api-docs`

* ** Microservicio Inventario y Catálogo (Puerto 8084):**
  * Interfaz: `http://localhost:8084/swagger-ui/index.html`

* ** Microservicio Pedidos (Puerto 8085):**
  * Interfaz: `http://localhost:8085/swagger-ui/index.html`

* ** Microservicio Pagos (Puerto 8086):**
  * Interfaz: `http://localhost:8086/swagger-ui/index.html`

---

## Tecnologías Complementarias Utilizadas
A continuación se detallan las herramientas, lenguajes y frameworks tecnológicos implementados en el diseño y construcción de este ecosistema:

### Núcleo de Desarrollo & Backend
* **Java JDK 21:** Lenguaje de programación principal utilizado para el desarrollo de la lógica del sistema.
* **Spring Boot:** Framework base para agilizar el desarrollo de los microservicios independientes.
* **Spring Cloud Gateway:** Componente encargado del enrutamiento dinámico y la creación del gateway central del sistema.
* **Spring Data JPA:** Abstracción de persistencia que utiliza Hibernate para la gestión de accesos a la base de datos.
* **Spring Security / JWT:** Framework de seguridad encargado de la autenticación de usuarios y protección de endpoints mediante tokens de acceso.
* **Swagger / OpenAPI 3:** Herramienta utilizada para la generación automática de la documentación interactiva de las APIs.
* **Maven:** Gestor de construcción (builds) y automatización de dependencias del proyecto.

### Base de Datos & Almacenamiento
* **XAMPP / MySQL:** Entorno de servidor local y motor de base de datos relacional para el almacenamiento de datos de cada microservicio.

### Herramientas de Diseño & Productividad
* **Git & GitHub:** Herramientas para el control de versiones distribuidas y alojamiento del repositorio en la nube.
* **Visual Studio Code:** Entorno de desarrollo integrado (IDE) principal utilizado en la programación del código fuente.
* **Postman:** Suite gráfica utilizada para las pruebas de endpoints y simulación de peticiones HTTP en los microservicios.
* **dbdiagram.io:** Herramientas web para el diseño del modelo lógico de datos (Modelamiento MER).
* **draw.io:** Software empleado para la maquetación y diseño de los diagramas de la arquitectura de microservicios.

### Asistencia de Inteligencia Artificial (IA)
* **GitHub Copilot:** Herramienta de IA integrada al IDE para el análisis, optimización y asistencia en la co-creación de código.
* **ChatGPT:** Asistente conversacional de IA enfocado en la planeación estructural y resolución de problemas durante el desarrollo de los microservicios.
