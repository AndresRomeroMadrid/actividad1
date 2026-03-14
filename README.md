# Actividad 1 - Demo Spring Boot con Docker

Este proyecto es una aplicación Spring Boot que utiliza una base de datos MySQL, configurada para ser desplegada fácilmente mediante Docker y Docker Compose.

## Requisitos Previos

- Docker Desktop instalado.
- Docker Compose (incluido en Docker Desktop).

## Descripción de los Archivos Docker

### Dockerfile
El archivo `Dockerfile` utiliza un proceso de **multi-stage build** para optimizar el tamaño de la imagen final:
1.  **Etapa de Construcción (Build):** Utiliza una imagen de Maven con Java 17 para compilar el código fuente y generar el archivo `.jar`, omitiendo la ejecución de tests para acelerar el proceso.
2.  **Etapa de Ejecución (Run):** Utiliza una imagen ligera de JRE (Java Runtime Environment) para ejecutar la aplicación, exponiendo el puerto 8080.

### Docker Compose
El archivo `docker-compose.yml` orquestra:

2.  **app (Spring Boot):**
    - Construye la imagen utilizando el `Dockerfile` local.
    - Se conecta automáticamente al contenedor de base de datos.
    - Mapea el puerto de la aplicación basándose en variables de entorno.

## Variables de Entorno

La aplicación utiliza las siguientes variables de entorno que pueden configurarse en un archivo `.env`:

| Variable | Descripción | Valor por Defecto |
| :--- | :--- | :--- |
| `PORT` | Puerto externo para acceder a la aplicación | `8080` |

## Comandos para Despliegue

Sigue estos pasos para levantar el entorno completo:

1.  **Crear el archivo `.env`** (si no existe):
    Asegúrate de tener un archivo `.env` en la raíz con al menos:
    ```env
    PORT=8080
    ```

2.  **Construir y levantar los contenedores:**
    ```bash
    docker-compose up --build
    ```

3.  **Levantar en segundo plano (opcional):**
    ```bash
    docker-compose up -d
    ```

4.  **Detener los servicios:**
    ```bash
    docker-compose down
    ```

5.  **Ver los logs de la aplicación:**
    ```bash
    docker-compose logs -f app
    ```

## Acceso a la Aplicación
Una vez levantado, puedes acceder a la API en: `http://localhost:8080` (o el puerto que hayas definido en el `.env`).
