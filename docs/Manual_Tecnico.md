# Manual Técnico - Quetzal Space Defender

## 1. Información general

**Laboratorio de Introducción a la Programación y Computación 1**
**Práctica 2**

---

## 2. Descripción del sistema

Quetzal Space Defender es un juego tipo Side Scroller desarrollado en Java.

El jugador selecciona un piloto y una nave para iniciar una partida. Durante el juego aparecen enemigos y objetos especiales que se desplazan horizontalmente por la pantalla.

El sistema utiliza programación orientada a objetos, vectores, interfaz gráfica con Swing e hilos para controlar diferentes elementos de una partida.

---

## 3. Estructura del proyecto

El código fuente se encuentra organizado en diferentes paquetes según su función.

### 3.1. Paquete `hilos`

Contiene las clases encargadas de ejecutar procesos independientes durante una partida.

- `HiloDisparo`: genera proyectiles automáticamente según el tiempo de recarga de la nave.
- `HiloEnemigo`: controla el movimiento horizontal de cada enemigo.
- `HiloObjetoEspecial`: controla el movimiento horizontal de cada objeto especial.
- `HiloProyectil`: controla el movimiento de cada proyectil y verifica colisiones con enemigos.

### 3.2. Paquete `logica`

Contiene las clases encargadas de la lógica principal del programa.

- `GestorDatos`: administra pilotos, partidas, naves, Top de puntajes y persistencia.
- `GestorColisiones`: verifica las colisiones entre el jugador, enemigos y objetos especiales.

### 3.3. Paquete `modelo`

Contiene las clases que representan los datos principales del sistema.

- `Piloto`: almacena el nombre, nave seleccionada y cantidad de partidas.
- `Nave`: almacena tipo de nave, dificultad, velocidad de movimiento y tiempo entre disparos.
- `Partida`: almacena piloto, nave, puntaje y fecha de una partida.

### 3.4. Paquete `reportes`

- `GeneradorReporte`: genera la gráfica de mejores puntajes mediante JFreeChart y exporta el reporte HTML.

### 3.5. Paquete `vista`

Contiene las interfaces gráficas del sistema.

- `MenuPrincipal`
- `VentanaCrearPiloto`
- `VentanaSeleccionarPiloto`
- `VentanaJuego`
- `VentanaTopPuntajes`
- `GamePanel`

### 3.6. Clase principal

- `Main`: inicia la aplicación y muestra el menú principal.

---

## 4. Estructura de archivos

```text
src/
├── hilos/
│   ├── HiloDisparo.java
│   ├── HiloEnemigo.java
│   ├── HiloObjetoEspecial.java
│   └── HiloProyectil.java
│
├── logica/
│   ├── GestorColisiones.java
│   └── GestorDatos.java
│
├── modelo/
│   ├── Nave.java
│   ├── Partida.java
│   └── Piloto.java
│
├── reportes/
│   └── GeneradorReporte.java
│
├── vista/
│   ├── GamePanel.java
│   ├── MenuPrincipal.java
│   ├── VentanaCrearPiloto.java
│   ├── VentanaJuego.java
│   ├── VentanaSeleccionarPiloto.java
│   └── VentanaTopPuntajes.java
│
└── Main.java
```

Las librerías externas se encuentran en:

```text
lib/
├── jcommon-1.0.23.jar
└── jfreechart-1.0.19.jar
```

---

## 5. Librerías utilizadas

### 5.1. JFreeChart 1.0.19

Se utiliza para generar una gráfica de barras que representa los cinco mejores puntajes registrados.

### 5.2. JCommon 1.0.23

Es una dependencia requerida por la versión utilizada de JFreeChart.

Ambas librerías se encuentran dentro de la carpeta `lib` del proyecto para evitar depender de rutas externas del equipo donde fue desarrollado.

---

## 6. Manejo de datos con vectores

El sistema utiliza vectores para almacenar la información principal.

### 6.1. Pilotos

```java
private Piloto[] pilotos;
```

Capacidad máxima:

```text
50 pilotos
```

El número de elementos utilizados se controla mediante:

```java
private int totalPilotos;
```

### 6.2. Partidas

```java
private Partida[] partidas;
```

Capacidad máxima:

```text
200 partidas
```

El número de partidas almacenadas se controla mediante:

```java
private int totalPartidas;
```

### 6.3. Elementos de una partida

`GamePanel` utiliza vectores para almacenar:

- enemigos;
- objetos especiales;
- proyectiles.

---

## 7. Persistencia

El sistema posee persistencia mediante archivos de texto.

La información se conserva aunque el programa sea cerrado y ejecutado nuevamente.

### 7.1. Archivo `pilotos.txt`

Almacena los pilotos registrados.

Formato:

```text
nombre;tipoNave
```

Ejemplo:

```text
Keneth;Acorazado
```

Al iniciar el programa, el archivo es leído y los pilotos son cargados nuevamente en el vector correspondiente.

### 7.2. Archivo `partidas.txt`

Almacena el historial de partidas.

Formato:

```text
nombrePiloto;tipoNave;puntaje;fecha
```

Ejemplo:

```text
Keneth;Acorazado;540;2026-09-23
```

Al iniciar el programa, las partidas son cargadas nuevamente en el vector de historial.

La cantidad de partidas de cada piloto se reconstruye a partir de las partidas almacenadas.

---

## 8. Naves

Existen tres modelos de nave.

| Nave | Dificultad | Velocidad de movimiento | Tiempo entre disparos |
|---|---|---:|---:|
| Explorador | Fácil | 8 | 2000 ms |
| Caza Estelar | Normal | 5 | 1000 ms |
| Acorazado | Difícil | 2 | 300 ms |

La nave seleccionada determina la velocidad del jugador y la frecuencia automática de disparo.

---

## 9. Dificultad de los enemigos

Además de las características propias de cada nave, la dificultad modifica la velocidad y frecuencia de generación de enemigos.

| Dificultad | Velocidad de enemigos | Intervalo de generación |
|---|---:|---:|
| Fácil | 3 | 120 frames |
| Normal | 4 | 90 frames |
| Difícil | 5 | 60 frames |

De esta forma, una dificultad mayor incrementa la presión ejercida por los enemigos durante la partida.

---

## 10. Manejo de hilos

El proyecto utiliza varias clases que heredan de `Thread`.

### 10.1. `HiloDisparo`

Se encarga de generar automáticamente proyectiles según el tiempo de recarga de la nave seleccionada.

### 10.2. `HiloEnemigo`

Cada enemigo posee un hilo independiente.

Su posición horizontal se actualiza constantemente desde el borde derecho hacia el borde izquierdo.

### 10.3. `HiloObjetoEspecial`

Cada objeto especial posee su propio hilo para controlar su desplazamiento horizontal.

### 10.4. `HiloProyectil`

Cada proyectil utiliza un hilo independiente.

Durante su ejecución:

1. aumenta su posición horizontal;
2. verifica colisiones con enemigos;
3. elimina el enemigo en caso de impacto;
4. se elimina cuando deja de ser necesario.

### 10.5. Hilo principal del juego

`GamePanel` implementa `Runnable`.

Su ciclo principal se encarga de:

- actualizar la posición del jugador;
- generar enemigos;
- generar objetos especiales;
- verificar colisiones;
- solicitar el repintado de la interfaz.

---

## 11. Sincronización

Debido a que diferentes hilos pueden modificar los mismos vectores, se utilizan métodos `synchronized` para proteger operaciones importantes.

Entre ellas:

- agregar proyectiles;
- eliminar proyectiles;
- eliminar enemigos;
- eliminar objetos especiales;
- destruir todos los enemigos;
- iniciar y detener el juego.

También se utilizan variables `volatile` para determinadas banderas y posiciones compartidas entre hilos.

---

## 12. Colisiones

La clase `GestorColisiones` administra las principales colisiones durante la partida.

### 12.1. Jugador contra enemigo

Cuando el área del jugador intersecta el área de un enemigo:

1. se elimina el enemigo;
2. finaliza la partida;
3. se guarda el resultado.

### 12.2. Proyectil contra enemigo

Cuando un proyectil impacta un enemigo:

- el enemigo es eliminado;
- el proyectil se detiene;
- se suman 20 puntos.

---

## 13. Objetos especiales

Existen tres tipos de objetos especiales.

### 13.1. Snitch espacial

Identificador:

```text
tipo = 0
```

Efectos:

- suma 150 puntos;
- elimina todos los enemigos visibles.

### 13.2. Bludger

Identificador:

```text
tipo = 1
```

Efecto:

- ralentiza al jugador durante 2 segundos.

### 13.3. Quaffle

Identificador:

```text
tipo = 2
```

Efecto:

- suma 10 puntos.

---

## 14. Top de puntajes

El método `getTopPuntajes` realiza una copia del vector de partidas y ordena los resultados de mayor a menor.

Para realizar el ordenamiento se utiliza el algoritmo de burbuja.

Posteriormente se seleccionan únicamente los primeros cinco elementos para formar el Top 5.

---

## 15. Historial

El historial contiene todas las partidas almacenadas.

Para cada partida se registra:

- nombre del piloto;
- tipo de nave;
- puntaje;
- fecha.

La información se muestra mediante una tabla en `VentanaTopPuntajes`.

---

## 16. Generación de reportes

La clase `GeneradorReporte` genera dos archivos:

```text
reporte_grafica.png
reporte_quetzal.html
```

### 16.1. Gráfica

La gráfica se genera mediante JFreeChart.

Muestra los cinco mejores puntajes registrados.

La imagen es almacenada mediante `ImageIO`.

### 16.2. Reporte HTML

El HTML es generado mediante:

- `FileWriter`;
- `PrintWriter`.

Incluye:

- gráfica de desempeño;
- Top 5 de mejores puntajes;
- historial completo de partidas.

Después de generar el reporte, este se abre automáticamente en el navegador predeterminado.

Los archivos generados se encuentran incluidos en `.gitignore`, debido a que son productos creados durante la ejecución del programa.

---

## 17. Fin de partida

Cuando el jugador pierde:

1. se detienen los hilos del juego;
2. se crea un objeto `Partida`;
3. se almacena la partida;
4. se actualiza la cantidad de partidas del piloto;
5. se muestra el puntaje final;
6. se cierra la ventana de juego.

La interfaz gráfica de fin de partida se ejecuta mediante `SwingUtilities.invokeLater`.

---

## 18. Ejecución

Para ejecutar el programa:

1. Tener instalado Java en el equipo.
2. Descargar o clonar el repositorio.
3. Abrir una terminal en la carpeta raíz del proyecto.
4. Ejecutar el siguiente comando:

```bash
java -jar dist/Practica2.jar
```

5. Se mostrará el menú principal de Quetzal Space Defender.

El JAR fue generado incluyendo las dependencias necesarias para la ejecución del programa.



---

## 19. Clase principal

La aplicación inicia mediante:

```java
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MenuPrincipal menu=new MenuPrincipal();
                menu.setVisible(true);
            }
        });
    }
}
```

El uso de `SwingUtilities.invokeLater` permite iniciar la interfaz gráfica mediante el hilo de eventos de Swing.
