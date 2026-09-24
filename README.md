# Quetzal Space Defender - Práctica 2

## Información

**Curso:** Introducción a la Programación y Computación 1  
**Práctica:** Práctica 2  
**Proyecto:** Quetzal Space Defender - Side Scroller  
**Lenguaje:** Java  

---

## Descripción

Quetzal Space Defender es un juego tipo Side Scroller desarrollado en Java.

El jugador puede crear pilotos, seleccionar diferentes modelos de nave y participar en partidas donde debe evitar enemigos, destruirlos mediante disparos automáticos y recoger objetos especiales para aumentar su puntaje.

El proyecto utiliza:

- programación orientada a objetos;
- vectores;
- interfaz gráfica con Swing;
- hilos;
- persistencia mediante archivos;
- JFreeChart para generación de gráficas;
- exportación de reportes HTML.

---

## Funcionalidades principales

- Creación y validación de pilotos.
- Selección de nave.
- Tres niveles de dificultad.
- Movimiento vertical del jugador.
- Disparos automáticos.
- Enemigos controlados mediante hilos.
- Proyectiles controlados mediante hilos.
- Objetos especiales.
- Sistema de puntajes.
- Top 5 de mejores puntajes.
- Historial completo de partidas.
- Persistencia de pilotos y partidas.
- Generación de gráfica con JFreeChart.
- Exportación de reporte HTML.
- Apertura automática del reporte en el navegador.

---

## Naves disponibles

| Nave | Dificultad | Velocidad | Tiempo entre disparos |
|---|---|---:|---:|
| Explorador | Fácil | 8 | 2000 ms |
| Caza Estelar | Normal | 5 | 1000 ms |
| Acorazado | Difícil | 2 | 300 ms |

La dificultad también modifica la velocidad y frecuencia de generación de enemigos.

---

## Controles

| Acción | Control |
|---|---|
| Subir | `W` o `Flecha Arriba` |
| Bajar | `S` o `Flecha Abajo` |
| Disparar | Automático |

---

## Objetos especiales

### Snitch Espacial

- suma 150 puntos;
- destruye todos los enemigos visibles.

### Bludger

- ralentiza al jugador durante 2 segundos.

### Quaffle

- suma 10 puntos.

---

## Estructura del repositorio

```text
.
├── dist/
│   └── Practica2.jar
│
├── docs/
│   ├── diagramas/
│   │   └── Diagrama_Flujo.png
│   ├── imagenes/
│   │   ├── menu_principal.png
│   │   ├── crear_piloto.png
│   │   ├── seleccionar_piloto.png
│   │   ├── fin_partida.png
│   │   ├── top_historial.png
│   │   └── reporte_exportado.png
│   ├── Diagrama_Flujo.md
│   ├── Manual_Tecnico.md
│   └── Manual_Usuario.md
│
├── lib/
│   ├── jcommon-1.0.23.jar
│   └── jfreechart-1.0.19.jar
│
├── src/
│   ├── META-INF/
│   ├── hilos/
│   ├── logica/
│   ├── modelo/
│   ├── reportes/
│   ├── vista/
│   └── Main.java
│
├── pilotos.txt
├── partidas.txt
└── README.md
```

---

## Ejecución

### JAR ejecutable

El repositorio incluye:

```text
dist/Practica2.jar
```

Para ejecutarlo:

```bash
java -jar dist/Practica2.jar
```

Es necesario tener Java instalado.

---

## Persistencia

El programa utiliza:

```text
pilotos.txt
partidas.txt
```

Estos archivos permiten conservar:

- pilotos registrados;
- historial de partidas;
- información necesaria para reconstruir el Top de puntajes.

Los datos continúan disponibles después de cerrar y volver a ejecutar el programa.

---

## Reportes

Desde la ventana de Top de Puntajes se puede seleccionar:

```text
Exportar Reporte
```

El sistema genera:

```text
reporte_quetzal.html
reporte_grafica.png
```

El reporte contiene:

- gráfica de los mejores puntajes;
- Top 5;
- historial completo de partidas.

El HTML se abre automáticamente en el navegador predeterminado.

---

## Documentación

### Manual Técnico

[Ver Manual Técnico](docs/Manual_Tecnico.md)

### Manual de Usuario

[Ver Manual de Usuario](docs/Manual_Usuario.md)

### Diagrama de Flujo

[Ver Diagrama de Flujo](docs/Diagrama_Flujo.md)
