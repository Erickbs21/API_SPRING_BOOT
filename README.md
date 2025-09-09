# API_SPRING_BOOT
# API Sistema de Venta con Control de Inventario

Este sistema permite gestionar ventas, productos, proveedores, categorías, órdenes de compra y ajustes de inventario.

Base URL:

---

##  Autenticación
Todas las peticiones requieren un `api_key`.

| Parámetro | Tipo     | Descripción                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. API key   |

---

## 📦 Venta Controlador
#### Obtener venta por ID
```http
#### Obtener venta por ID
GET /api/ventas/{id}

PUT /api/ventas/{id}

DELETE /api/ventas/{id}

DELETE /api/ventas/{id}

GET /api/ventas

POST /api/ventas

GET /api/ventas/total-dia

GET /api/ventas/numero/{numeroVenta}

GET /api/ventas/fecha

GET /api/ventas/fecha/{fecha}

GET /api/ventas/contar-dia
```
