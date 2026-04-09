# Sistema De Transporte

## Configurar la base de datos Access

El proyecto usa el archivo local `sistema_transporte.accdb`.

Paso obligatorio: actualizar la ruta en [src/main/java/hn/uth/sistemadetransporte/util/Conexion.java](src/main/java/hn/uth/sistemadetransporte/util/Conexion.java).

Busca esta línea:

```java
private static final String ARCHIVO_ACCESS = "C:/Users/andym/Documents/GitHub/SistemaDeTransporte/sistema_transporte.accdb";
```

Y cámbiala por la ruta real de tu PC, por ejemplo:

```java
private static final String ARCHIVO_ACCESS = "D:/proyectos/SistemaDeTransporte/sistema_transporte.accdb";
```

Importante:

- No abras la base en Microsoft Access mientras la app corre.
- Si necesitas la tabla GPS, ejecuta [src/main/resources/sql/gps_registros.sql](src/main/resources/sql/gps_registros.sql).

## Componentes PrimeFaces usados

### Navegación y estructura

- ```p:button``` :Navegación entre páginas.
- ```p:dialog``` :Formularios modales para crear/editar.
- ```p:outputPanel``` :Agrupa bloques que se actualizan de forma parcial.

### Formularios

- ```p:outputLabel``` :Etiquetas de formulario.
- ```p:inputText``` :Entrada de texto.
- ```p:inputNumber``` :Entrada numérica.
- ```p:selectOneMenu``` :Selección en combo.
- ```p:calendar``` :Selección de fecha o fecha/hora.
- ```p:spinner``` :Enteros en rango.
- ```p:inputMask``` :Entrada con máscara.
- ```p:inputTextarea``` :Texto multilinea.
- ```p:password``` :Contraseña en login.

### Acciones y feedback

- ```p:commandButton``` :Ejecuta acciones de guardar, editar, eliminar y login.
- ```p:growl``` :Muestra mensajes de éxito/error.
- ```p:confirmDialog``` :Confirmación global para eliminar/inactivar.
- ```p:confirm``` :Vincula confirmación a botones de acción.

### Datos y visualización

- ```p:dataTable``` :Muestra tablas CRUD con paginación.
- ```p:column``` :Define columnas dentro de p:dataTable.
- ```p:donutChart``` :Gráfico dona.
- ```p:barChart``` :Gráfico de barra.

## 5. Uso de AJAX (PrimeFaces)

Se usa `p:ajax` en [src/main/webapp/combustible.xhtml](src/main/webapp/combustible.xhtml).

Casos actuales:

1. En galones (`blur`): recalcula total.
2. En precio por galón (`blur`): recalcula total.

Ejemplo:

```xml
<p:ajax event="blur" listener="#{combustibleBean.calcularTotal()}" update="txtTotal" />
```
