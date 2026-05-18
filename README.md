# TrenzadosTM - Microservicios

---

## Descripción del proyecto

TrenzadosTM es un sistema basado en arquitectura de microservicios orientado a la gestión de un negocio dedicado a la venta de productos para caballos, como cabezadas, riendas y barrigueras, elaboradas principalmente con técnicas de trenzado en hilo.

El sistema permite gestionar de forma distribuida distintas áreas del negocio, incluyendo:

- Usuarios y direcciones  
- Inventario de productos  
- Gestión de pedidos  
- Procesamiento de pagos  
- Administración de datos geográficos  

---

## Arquitectura

El proyecto está construido bajo una arquitectura de microservicios, comunicados a través de una API Gateway.

---

## API Gateway

Puerto: **9090**  
Base path: **/api/v1/**  

Todos los microservicios se consumen a través de este punto de entrada.

---

## Microservicios disponibles

Base path común para todos:

**/api/v1/**

---

### Ubicación
- /region  
- /ciudad  
- /comuna  
- /direccion  

---

### Usuario
- /usuarios  

---

### Catálogo de productos e Inventario
- /categorias  
- /productos  
- /tipo-producto  
- /inventario  

---

### Pedidos y Pagos
- /pedido  
- /detallepedido
- /bancos  
- /pagos  

---

## Bases de datos (XAMPP)

Para ejecutar el sistema es necesario crear las siguientes bases de datos en MySQL (XAMPP):

- trenzadostmusuario  
- trenzadostmdireccion  
- trenzadostminventario  
- trenzadostmpedido  
- trenzadostmpago  

Cada microservicio está asociado a su propia base de datos, siguiendo una arquitectura desacoplada.

---

## Tecnologías utilizadas

- Java + Spring Boot (microservicios)  
- MySQL (XAMPP)  
- API Gateway  
- Postman  
- ChatGPT (asistencia en desarrollo)  
- GitHub Copilot  
- Git & GitHub 
- Visual Studio Code  
- Postman  
- dbdiagram.io (modelo de datos)  
- draw.io (diagramas de arquitectura)  

---

## Modelo del negocio

TrenzadosTM es un negocio enfocado en la venta de productos artesanales para caballos, elaborados mediante técnicas de trenzado en hilo.

El sistema busca digitalizar y centralizar la gestión de:

- Clientes y usuarios  
- Direcciones y ubicación  
- Inventario de productos  
- Procesos de pedido  
- Pagos y bancos  

---

## Cómo ejecutar el proyecto

### 1. Clonar repositorio
git clone <url-del-repositorio>

---

### 2. Levantar XAMPP
- Iniciar Apache  
- Iniciar MySQL  

---

### 3. Crear bases de datos

Crear las 5 bases de datos listadas anteriormente.

---

### 4. Ejecutar microservicios

Levantar cada servicio en su respectivo puerto (configuración Spring Boot).

---

### 5. Ejecutar API Gateway

Puerto: 9090  

Acceso central:

http://localhost:9090/api/v1/

---

## Pruebas con Postman

Se recomienda usar Postman para probar todos los endpoints.

Ejemplo base:

http://localhost:9090/api/v1/usuarios

---

