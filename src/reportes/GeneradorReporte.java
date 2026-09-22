package reportes;

import logica.GestorDatos;
import modelo.Partida;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GeneradorReporte {

    public static boolean exportar(GestorDatos gestorDatos) {
        try {
            String rutaImagen="reporte_grafica.png";
            generarGrafica(gestorDatos, rutaImagen);
            generarHTML(gestorDatos, rutaImagen);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Se genera la grafica de barras con JFreeChart y se guarda como PNG
    private static void generarGrafica(GestorDatos gestorDatos, String rutaImagen) throws Exception {
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();

        Partida[] top=gestorDatos.getTopPuntajes(5);
        for (int i=0; i<top.length; i++) {
            Partida p=top[i];
            String etiqueta=p.getNombrePiloto()+" ("+p.getTipoNave()+")";
            dataset.addValue(p.getPuntaje(), "Puntaje", etiqueta);
        }

        JFreeChart grafica= ChartFactory.createBarChart(
                "Top 5 Mejores Puntajes",
                "Piloto",
                "Puntaje",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        // Se convierte la grafica a imagen y se guarda en disco con ImageIO
        BufferedImage imagen=grafica.createBufferedImage(700, 400);
        ImageIO.write(imagen,"PNG",new File(rutaImagen));
    }

    // Se genera el archivo HTML con los datos del juego usando PrintWriter
    private static void generarHTML(GestorDatos gestorDatos, String rutaImagen) throws Exception {

        PrintWriter writer = new PrintWriter(new FileWriter("reporte_quetzal.html"));

        writer.println("<!DOCTYPE html>");
        writer.println("<html lang='es'>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'>");
        writer.println("<title>Reporte Quetzal Space Defender</title>");
        writer.println("<style>");
        writer.println(" body { font-family: Arial, sans-serif; background: #0a0a1e; color: white; padding: 30px; }");
        writer.println(" h1 { color: #00c8ff; text-align: center; }");
        writer.println(" h2 { color: #ffd700; margin-top: 30px; }");
        writer.println(" table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
        writer.println(" th { background: #003366; padding: 10px; text-align: left; }");
        writer.println(" td { padding: 8px 10px; border-bottom: 1px solid #333; }");
        writer.println(" tr:nth-child(even) { background: #111133; }");
        writer.println(" .grafica { text-align: center; margin-top: 30px; }");
        writer.println(" img { border: 2px solid #00c8ff; border-radius: 8px; max-width: 100%; }");
        writer.println("</style>");
        writer.println("</head>");
        writer.println("<body>");

        writer.println("<h1>Quetzal Space Defender - Reporte de Partidas</h1>");

        // Se incluye la imagen de la grafica generada por JFreeChart
        writer.println("<div class='grafica'>");
        writer.println("<h2>Grafica de desempeño</h2>");
        writer.println("<img src='" + rutaImagen + "' alt='Grafica de puntajes'/>");
        writer.println("</div>");

        // Se escribe la tabla del top 5
        writer.println("<h2>Top 5 Mejores Puntajes</h2>");
        writer.println("<table>");
        writer.println("<tr><th>#</th><th>Piloto</th><th>Nave</th><th>Puntaje</th><th>Fecha</th></tr>");

        Partida[] top = gestorDatos.getTopPuntajes(5);
        for (int i = 0; i < top.length; i++) {
            Partida p = top[i];
            writer.println("<tr>");
            writer.println("<td>" + (i + 1) + "</td>");
            writer.println("<td>" + p.getNombrePiloto() + "</td>");
            writer.println("<td>" + p.getTipoNave() + "</td>");
            writer.println("<td>" + p.getPuntaje() + "</td>");
            writer.println("<td>" + p.getFecha() + "</td>");
            writer.println("</tr>");
        }
        writer.println("</table>");

        // Se escribe la tabla del historial completo
        writer.println("<h2>Historial Completo de Partidas</h2>");
        writer.println("<table>");
        writer.println("<tr><th>Piloto</th><th>Nave</th><th>Puntaje</th><th>Fecha</th></tr>");

        Partida[] historial = gestorDatos.getPartidas();
        for (int i = 0; i < historial.length; i++) {
            Partida p = historial[i];
            writer.println("<tr>");
            writer.println("<td>" + p.getNombrePiloto() + "</td>");
            writer.println("<td>" + p.getTipoNave() + "</td>");
            writer.println("<td>" + p.getPuntaje() + "</td>");
            writer.println("<td>" + p.getFecha() + "</td>");
            writer.println("</tr>");
        }
        writer.println("</table>");

        writer.println("<p style='text-align:center; color:#555; margin-top:40px;'>");
        writer.println("Generado por Quetzal Space Defender - Lab IPC1");
        writer.println("</p>");
        writer.println("</body>");
        writer.println("</html>");

        writer.close();
    }
}
