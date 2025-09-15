# Culturarte - Dockerized MySQL setup and project changes

## What I changed
- `src/main/java/culturarte/casosuso/DatabaseConnection.java`: now reads DB configuration from environment variables:
  - `DB_HOST` (default: localhost)
  - `DB_PORT` (default: 3306)
  - `DB_NAME` (default: culturarte)
  - `DB_USER` (default: tecnologo)
  - `DB_PASSWORD` (default: tecnologo)
  - To run the DatabaseInitializer from the app, set `DB_INIT=true` (default: not executed).

- `src/main/java/culturarte/casosuso/JPAUtil.java`: now overrides persistence properties with the same environment variables so JPA connects to the Docker DB.

- `src/main/resources/META-INF/persistence.xml`: fixed a corrupted JDBC URL and left sensible defaults.

- Added `docker-compose.yml` which creates a MySQL 8.0 container and exposes port 3306.
  - Root password: `rootpassword`
  - Database: `culturarte`
  - User: `tecnologo`
  - Password: `tecnologo`
  - The SQL file `docker/mysql-init/culturarte.sql` is copied from the provided SQL and will be executed on first container startup.

## How to run
1. From the project root (where `docker-compose.yml` is located) run:
   ```
   docker compose up -d
   ```
   This will create a MySQL container and initialize the database using `docker/mysql-init/culturarte.sql`.

2. Start your Java application from NetBeans or command line. Ensure the following environment variables (if different from defaults) are set for the app:
   - DB_HOST (default localhost)
   - DB_PORT (default 3306)
   - DB_NAME (default culturarte)
   - DB_USER (default tecnologo)
   - DB_PASSWORD (default tecnologo)

   If running from NetBeans, set these as VM/environment variables in the Run configuration.

3. If you want the application itself to attempt DB initialization (not necessary because the SQL is loaded by Docker), set `DB_INIT=true` in the app environment.

## Notes & suggestions
- Keep `db_data` volume persistent to preserve data.
- If the MySQL container already has data, the SQL in `docker/mysql-init` will NOT be executed (it runs only on empty DB).
- If you prefer MariaDB, change the image accordingly and adjust the JDBC dialect if necessary.




### Avoiding "address already in use" when starting the DB

The default `docker-compose.yml` included **does not** expose the DB port on the host, so it won't conflict
with any local MySQL or other service already listening on 3306. The container is still reachable from your app
if the app runs on the same Docker network (e.g., if the app is another container or if you connect using the
docker host `localhost` when `ports` is used).

If you want to expose the DB port on the host (for local tools to connect), use the optional override file:

```
# Expose host port 33320 mapped to container 3306:
DB_HOST_PORT=33320 docker compose -f docker-compose.yml -f docker-compose.expose.yml up -d

# Or to use default 3306 (risky if host already has MySQL):
DB_HOST_PORT=3306 docker compose -f docker-compose.yml -f docker-compose.expose.yml up -d
```

If you prefer the old single-file behavior, you can also run:
```
docker compose -f docker-compose.yml -f docker-compose.expose.yml up -d
```
and it will use 3306 by default unless you set `DB_HOST_PORT`.

Notes:
- The optional `docker-compose.expose.yml` file maps `${DB_HOST_PORT:-3306}:3306`. Setting `DB_HOST_PORT` lets you avoid conflicts.
- If you don't expose the port, your application can connect to the database using host `db` (container name) and port `3306`
  when the application runs in another container on the same Docker network. If your Java app runs on the host (NetBeans),
  you need to expose the port to connect from the host.


### Cambio de puerto por defecto
Para evitar conflictos con MySQL local, el proyecto ahora mapea por defecto el puerto **33320** del host al **3306** del contenedor MySQL.
- Puerto host por defecto: **33320**
- Puerto interno del contenedor: **3306**

Levantar la DB y exponer puerto (por defecto 33320):
```
docker compose up -d
# o, si usa docker-compose binario:
sudo docker-compose up -d
```

Si quieres usar otro puerto en el host, puedes ejecutar:
```
DB_HOST_PORT=33307 docker compose -f docker-compose.yml -f docker-compose.expose.yml up -d
```

Conexión desde NetBeans / host
- En tu aplicación (NetBeans) usa estos valores por defecto OR asegúrate de definir las variables de entorno:
  - DB_HOST=localhost
  - DB_PORT=33320
  - DB_NAME=culturarte
  - DB_USER=tecnologo
  - DB_PASSWORD=tecnologo



Nota: se eliminó la línea `container_name: culturarte_db` del docker-compose.yml
para evitar conflictos cuando existan contenedores previos con el mismo nombre.
Docker Compose ahora generará un nombre único basado en el proyecto.
