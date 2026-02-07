# 🔨 VoltStream - Backend para Plataforma de Subastas en Tiempo Real

Backend REST API para una plataforma de subastas en tiempo real desarrollado con Spring Boot, siguiendo principios de **Arquitectura Hexagonal (Puertos y Adaptadores)** y **Domain-Driven Design (DDD)**.

## 🚀 Características

- ✅ Crear subastas con precio inicial y duración
- ✅ Listar todas las subastas activas
- ✅ Realizar pujas en subastas activas
- ✅ Validación de reglas de negocio (pujas mayores al precio actual, subastas cerradas)
- ✅ Arquitectura limpia y desacoplada
- ✅ Base de datos H2 en memoria para desarrollo

## 🏗️ Arquitectura

El proyecto sigue **Arquitectura Hexagonal** con separación clara de capas:

```
src/main/java/com/voltstream/
├── config/                          # Configuración de Spring
│   └── SecurityConfig.java
├── modules/
│   └── auctions/
│       ├── application/             # Casos de uso (lógica de aplicación)
│       │   ├── CreateAuctionUseCase.java
│       │   └── PlaceBidUseCase.java
│       ├── domain/                  # Núcleo del negocio (sin dependencias)
│       │   ├── model/
│       │   │   ├── Auction.java
│       │   │   └── repository/
│       │   │       └── AuctionRepository.java (Puerto)
│       │   └── exception/
│       │       ├── AuctionClosedException.java
│       │       ├── AuctionNotFoundException.java
│       │       └── InvalidBidAmountException.java
│       └── infrastructure/          # Adaptadores (REST, BD, etc.)
│           ├── AuctionController.java
│           ├── rest/dto/
│           │   ├── CreateAuctionRequest.java
│           │   └── CreateAuctionResponse.java
│           └── persistence/jpa/
│               ├── AuctionEntity.java
│               ├── JpaAuctionRepositoryAdapter.java
│               └── SpringDataAuctionRepository.java
```

### Capas:

- **Domain**: Lógica de negocio pura, sin dependencias externas
- **Application**: Casos de uso que orquestan la lógica de dominio
- **Infrastructure**: Implementaciones concretas (REST API, JPA, etc.)

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 4.0.2**
- **Spring Data JPA**
- **Spring Security**
- **H2 Database** (en memoria)
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

### Crear una subasta

```http
POST /api/auctions
Content-Type: application/json

{
  "title": "iPhone 15 Pro",
  "startPrice": 500.00,
  "durationHours": 24
}
```

**Respuesta:**
```json
{
  "auctionId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### Listar todas las subastas

```http
GET /api/auctions
```

**Respuesta:**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "title": "iPhone 15 Pro",
    "currentPrice": 500.00,
    "endTime": "2024-02-08T15:30:00",
    "active": true
  }
]
```

### Realizar una puja

```http
POST /api/auctions/{auctionId}/bids?amount=550.00
```

**Respuesta:**
```
200 OK
```

## 🧪 Ejemplos de uso con cURL

```bash
# Crear subasta
curl -X POST http://localhost:8080/api/auctions \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Laptop Gaming",
    "startPrice": 800.00,
    "durationHours": 48
  }'

# Listar subastas
curl http://localhost:8080/api/auctions

# Realizar puja
curl -X POST "http://localhost:8080/api/auctions/{auctionId}/bids?amount=850.00"
```

## 🗄️ Base de Datos

El proyecto usa **H2 Database** en memoria para desarrollo. La consola H2 está habilitada:

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:voltstreamdb`
- **Usuario**: `sa`
- **Contraseña**: `789`

## 🔒 Seguridad

La configuración de seguridad está deshabilitada para facilitar el desarrollo y testing:
- CSRF deshabilitado
- Todos los endpoints son públicos

⚠️ **Nota**: Esta configuración es solo para desarrollo/demo. En producción se debe implementar autenticación y autorización adecuadas.

## 📝 Reglas de Negocio

1. **Pujas válidas**: Deben ser mayores al precio actual
2. **Subastas cerradas**: No se pueden realizar pujas en subastas finalizadas
3. **Duración**: Las subastas tienen una fecha de finalización
4. **Precio inicial**: Toda subasta comienza con un precio base

## 🎯 Principios Aplicados

- **Arquitectura Hexagonal**: Separación clara entre dominio e infraestructura
- **DDD**: Modelado rico del dominio con reglas de negocio encapsuladas
- **SOLID**: Código mantenible y extensible
- **Clean Code**: Código legible y autodocumentado

## 🚧 Próximas Mejoras

- [ ] WebSocket para actualizaciones en tiempo real
- [ ] Sistema de autenticación JWT
- [ ] Historial de pujas
- [ ] Notificaciones a usuarios
- [ ] Tests unitarios e integración
- [ ] Documentación con Swagger/OpenAPI

## 📄 Licencia

Este es un proyecto de demostración para portafolio.

## 👤 Autor

Desarrollado como proyecto de demostración de arquitectura backend.
