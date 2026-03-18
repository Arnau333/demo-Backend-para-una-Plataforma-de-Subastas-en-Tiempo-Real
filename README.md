# 🔨 VoltStream - Backend para Plataforma de Subastas en Tiempo Real

Backend REST API para una plataforma de subastas en tiempo real desarrollado con Spring Boot, siguiendo principios de **Arquitectura Hexagonal (Puertos y Adaptadores)** y **Domain-Driven Design (DDD)**.

## 🚀 Características

- ✅ Crear subastas con precio inicial y duración
- ✅ Listar todas las subastas activas
- ✅ Realizar pujas en subastas activas
- ✅ **Notificaciones en TIEMPO REAL** vía WebSockets (STOMP)
- ✅ **Seguridad con JWT** (Autenticación Stateless mediante Tokens)
- ✅ Validación de reglas de negocio (pujas mayores al precio actual, subastas cerradas)
- ✅ Arquitectura limpia y desacoplada
- ✅ Base de datos H2 en memoria para desarrollo

## 🏗️ Arquitectura

El proyecto sigue **Arquitectura Hexagonal** con separación clara de capas:

```
src/main/java/com/voltstream/
├── config/                          # Configuración Core (Seguridad, JWT, WebSocket)
├── modules/
│   ├── auctions/                    # Módulo principal de Subastas
│   │   ├── application/             # Casos de uso
│   │   ├── domain/                  # Núcleo del negocio (Modelos, Excepciones, Puertos)
│   │   └── infrastructure/          # Adaptadores (REST, JPA, Mensajería)
│   ├── users/                       # Gestión de usuarios y Auth (Nuevo)
│   └── websocket/                   # Infraestructura de tiempo real (Nuevo)
```

### Capas:

- **Domain**: Lógica de negocio pura, sin dependencias externas. Contiene las entidades y las interfaces de los repositorios (puertos).
- **Application**: Casos de uso que orquestan el flujo de datos.
- **Infrastructure**: Implementaciones concretas (REST API, JPA, Adaptadores de Repositorio, Configuración de WebSockets).

## 🛠️ Tecnologías

- **Java 17 / 21**
- **Spring Boot 4.0.2**
- **Spring Data JPA**
- **Spring Security** (Con filtros JWT personalizados)
- **Spring WebSocket** (STOMP + SockJS)
- **JJWT** (Para la gestión de JSON Web Tokens)
- **H2 Database** (En memoria)
- **Lombok**
- **Maven**

## 📋 Requisitos

- Java 17 o superior
- Maven 3.6+

## ⚙️ Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd "demo Backend para una Plataforma de Subastas en Tiempo Real"
```

### 2. Compilar el proyecto
```bash
./mvnw clean install
```

### 3. Ejecutar la aplicación
```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📡 API Endpoints

### 🔐 Autenticación
- `POST /api/auth/register`: Registro de nuevos usuarios.
- `POST /api/auth/login`: Retorna un `{"token": "..."}` válido para 5 horas.

### 🔨 Subastas (Requiere Header `Authorization: Bearer <token>`)

#### Crear una subasta
- `POST /api/auctions`
```json
{
  "title": "iPhone 15 Pro",
  "startPrice": 500.00,
  "durationHours": 24
}
```

#### Listar todas y pujar
- `GET /api/auctions`: Lista todas las subastas activas.
- `POST /api/auctions/{auctionId}/bids?amount=550.00`: Realiza una puja.

## 📡 WebSockets (Notificaciones Push)

El sistema notifica automáticamente cambios sin necesidad de refrescar el navegador:
- **Conexión**: `ws://localhost:8080/ws`
- **Topic General**: `/topic/auctions` (Nuevas subastas)
- **Topic por Subasta**: `/topic/auctions/{id}` (Actualización de precios al recibir pujas)

## 🔒 Seguridad

La API utiliza un esquema de seguridad **Stateless**:
- **JWT**: Cada petición protegida requiere el token en el header `Authorization`.
- **Rutas Públicas**: Solo `/api/auth/**` (auth) y `/ws/**` (websockets) son accesibles sin token.

## 📝 Reglas de Negocio

1. **Pujas válidas**: Deben ser siempre superiores al precio actual.
2. **Subastas cerradas**: No se permiten pujas si el tiempo ha expirado.
3. **Autenticación**: Solo usuarios autenticados pueden operar en el sistema de subastas.

## 🎯 Principios Aplicados

- **Arquitectura Hexagonal**: Máximo desacoplamiento.
- **DDD (Domain-Driven Design)**: Lógica centralizada en el dominio.
- **SOLID & Clean Code**: Código mantenible y fácil de leer.

## 🚧 Próximas Mejoras
- [ ] Historial de pujas detallado.
- [ ] Notificaciones por Email o Push externas.
- [ ] Documentación interactiva con Swagger.

## 👤 Autor
Desarrollado como proyecto de demostración de arquitectura backend profesional.
