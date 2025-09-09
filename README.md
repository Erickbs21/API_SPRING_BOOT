# API_SPRING_BOOT
# 📌 API Sistema de Venta con Control de Inventario

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

| Método  | Endpoint                        | Descripción                        |
|---------|---------------------------------|------------------------------------|
| GET     | `/api/ventas/{id}`              | Obtener venta por ID               |
| PUT     | `/api/ventas/{id}`              | Actualizar venta                   |
| DELETE  | `/api/ventas/{id}`              | Eliminar venta                     |
| GET     | `/api/ventas`                   | Listar todas las ventas            |
| POST    | `/api/ventas`                   | Crear venta                        |
| GET     | `/api/ventas/total-dia`         | Total de ventas del día            |
| GET     | `/api/ventas/numero/{numero}`   | Buscar venta por número            |
| GET     | `/api/ventas/fecha`             | Buscar ventas por fecha (todas)    |
| GET     | `/api/ventas/fecha/{fecha}`     | Buscar ventas por fecha específica |
| GET     | `/api/ventas/contar-dia`        | Contar ventas del día              |

---

## 🏢 Proveedor Controlador

| Método  | Endpoint                          | Descripción                 |
|---------|-----------------------------------|-----------------------------|
| GET     | `/api/proveedores/{id}`           | Obtener proveedor por ID    |
| PUT     | `/api/proveedores/{id}`           | Actualizar proveedor        |
| DELETE  | `/api/proveedores/{id}`           | Eliminar proveedor          |
| GET     | `/api/proveedores`                | Listar proveedores          |
| POST    | `/api/proveedores`                | Crear proveedor             |
| GET     | `/api/proveedores/buscar`         | Buscar proveedor            |

---

## 📦 Producto Controlador

| Método  | Endpoint                                   | Descripción                           |
|---------|--------------------------------------------|---------------------------------------|
| GET     | `/api/productos/{id}`                      | Obtener producto por ID               |
| PUT     | `/api/productos/{id}`                      | Actualizar producto                   |
| DELETE  | `/api/productos/{id}`                      | Eliminar producto                     |
| PUT     | `/api/productos/{id}/stock`                | Actualizar stock de producto          |
| GET     | `/api/productos`                           | Listar productos                      |
| POST    | `/api/productos`                           | Crear producto                        |
| GET     | `/api/productos/stock-bajo`                | Productos con stock bajo              |
| GET     | `/api/productos/codigo-barras/{codigo}`    | Buscar producto por código de barras  |
| GET     | `/api/productos/categoria/{categoriaId}`   | Buscar productos por categoría        |
| GET     | `/api/productos/buscar`                    | Buscar productos                      |

---

## 📑 Orden Compra Controlador

| Método  | Endpoint                                        | Descripción                     |
|---------|-------------------------------------------------|---------------------------------|
| GET     | `/api/ordenes-compra/{id}`                      | Obtener orden por ID            |
| PUT     | `/api/ordenes-compra/{id}`                      | Actualizar orden                |
| DELETE  | `/api/ordenes-compra/{id}`                      | Eliminar orden                  |
| PUT     | `/api/ordenes-compra/{id}/recibir`              | Recibir orden                   |
| GET     | `/api/ordenes-compra`                           | Listar órdenes de compra        |
| POST    | `/api/ordenes-compra`                           | Crear orden de compra           |
| GET     | `/api/ordenes-compra/proveedor/{proveedorId}`   | Buscar órdenes por proveedor    |
| GET     | `/api/ordenes-compra/numero/{numeroOrden}`      | Buscar orden por número         |

---

## 🏷️ Categoría Controlador

| Método  | Endpoint                       | Descripción              |
|---------|--------------------------------|--------------------------|
| GET     | `/api/categorias/{id}`         | Obtener categoría por ID |
| PUT     | `/api/categorias/{id}`         | Actualizar categoría     |
| DELETE  | `/api/categorias/{id}`         | Eliminar categoría       |
| GET     | `/api/categorias`              | Listar categorías        |
| POST    | `/api/categorias`              | Crear categoría          |
| GET     | `/api/categorias/buscar`       | Buscar categorías        |

---

## 🔄 Ajuste Inventario Controlador

| Método  | Endpoint                                          | Descripción                        |
|---------|---------------------------------------------------|------------------------------------|
| GET     | `/api/ajustes-inventario`                         | Listar ajustes de inventario       |
| POST    | `/api/ajustes-inventario`                         | Crear ajuste de inventario         |
| GET     | `/api/ajustes-inventario/{id}`                    | Obtener ajuste por ID              |
| GET     | `/api/ajustes-inventario/producto/{productoId}`   | Ajustes por producto               |
| GET     | `/api/ajustes-inventario/historial/producto/{id}` | Historial de ajustes por producto  |

---
