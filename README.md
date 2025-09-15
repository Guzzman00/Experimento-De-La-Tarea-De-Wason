Claro, aquí tienes una propuesta de `README.md` que combina, corrige y moderniza toda la información que me diste, resultando en una guía clara y precisa para tu proyecto.

-----

# Experimento de la Tarea de Selección de Wason 🧠

Este proyecto es una implementación interactiva de la Tarea de Selección de Wason, un famoso puzzle de psicología del razonamiento. La aplicación está desarrollada en Scala.js con la librería Laminar y demuestra dos versiones del problema:

1.  La versión clásica y abstracta con cartas (números y letras).
2.  La versión social y concreta sobre la edad para consumir alcohol.

## Requisitos ⚙️

Para compilar y ejecutar este proyecto, necesitarás el siguiente software instalado:

  * **Git**: Para clonar el repositorio.
  * **SBT (Simple Build Tool)**: Versión 1.8.x o superior. Se recomienda instalarlo a través de un gestor de paquetes como [SDKMAN\!](https://sdkman.io/install) o [Scoop](https://scoop.sh/) para facilitar la gestión de versiones.
      * **Para Windows:** Puedes usar el instalador `.msi` como [sbt-1.10.1.msi](https://github.com/sbt/sbt/releases/download/v1.10.1/sbt-1.10.1.msi).
      * **Para NixOS:** Puedes acceder a sbt a través de un shell con `nix-shell -p sbt`.
  * **Deno**: Un entorno de ejecución para JavaScript y TypeScript. Se usará para servir la aplicación localmente.
      * **Para NixOS:** Se puede usar con `nix-shell -p deno`.
      * **Para otros sistemas:** Sigue las instrucciones en [deno.land](https://deno.land/manual/getting_started/installation).
  * **IntelliJ IDEA**: Con el plugin de Scala, es el entorno de desarrollo recomendado para trabajar con el código.

-----

## Instalación 📂

1.  Abre una terminal y clona el repositorio en tu máquina local:
    ```bash
    git clone git@github.com:Guzzman00/Experimento-De-La-Tarea-De-Wason.git
    ```
2.  Navega al directorio del proyecto:
    ```bash
    cd Experimento-De-La-Tarea-De-Wason
    ```
3.  Abre la carpeta del proyecto con IntelliJ IDEA.

-----

## Ejecución 🚀

Para compilar el código Scala.js y ejecutar la aplicación web, sigue estos dos pasos:

### Paso 1: Compilar el Proyecto

El proyecto tiene un comando personalizado, `build`, que se encarga de compilar el código Scala a JavaScript y preparar todos los archivos necesarios en una carpeta `dist/`.

1.  Dentro de IntelliJ IDEA, abre la **pestaña `sbt`** y espera a que cargue el proyecto.
2.  En la **consola de `sbt`** (sbt shell), ejecuta el siguiente comando:
    ```sbt
    build
    ```
    Al terminar, verás un mensaje de éxito y la carpeta `dist/` se habrá creado en la raíz del proyecto, conteniendo los archivos `main.js` e `index.html`.

### Paso 2: Iniciar el Servidor Local

Ahora, usaremos Deno para servir los archivos generados en el paso anterior.

1.  Dentro de IntelliJ IDEA, abre una **pestaña de Terminal** del sistema.
2.  Si estás en **NixOS**, inicia un shell que tenga Deno disponible:
    ```bash
    nix-shell -p deno
    ```
3.  Una vez en el shell con Deno, inicia el servidor web apuntando a la carpeta `dist/`:
    ```bash
    deno run -A --watch https://deno.land/std/http/file_server.ts dist/
    ```
    El servidor se iniciará y te mostrará la dirección para acceder a la aplicación.

### Paso 3: Realizar el Experimento

1.  Abre tu navegador web y ve a la dirección **http://localhost:8000** (o el puerto que indique Deno).
2.  ¡Listo\! Ya puedes interactuar con el experimento de la Tarea de Wason.
