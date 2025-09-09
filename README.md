# API_SPRING_BOOT
# API Sistema de Venta con Control de Inventario

Este sistema permite gestionar ventas, productos, proveedores, categorías, órdenes de compra y ajustes de inventario.

Base URL:

---

## 🔑 Autenticación
Todas las peticiones requieren un `api_key`.

| Parámetro | Tipo     | Descripción                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Tu API key   |

---

## 📦 Venta Controlador

#### Obtener venta por ID
```http
GET /api/ventas/{id}
