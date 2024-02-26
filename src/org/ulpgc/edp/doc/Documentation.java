package org.ulpgc.edp.doc;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class used to open the documentation website automatically.
 *
 * @author Javier
 */
public class Documentation {

    public static void open(boolean permission) {
        if (!permission) return;

        // URL que deseas abrir en el navegador
        String url = "https://javier-castilla.github.io/Java-own-Python-dictionary-implementation-DOCUMENTATION/org/ulpgc/edp/model/package-summary.html";

        // Intentar abrir la URL en el navegador
        try {
            // Crear un objeto URI a partir de la cadena de URL
            URI uri = new URI(url);

            // Verificar si Desktop API es soportado en el sistema
            if (Desktop.isDesktopSupported()) {
                // Obtener la instancia de Desktop
                Desktop desktop = Desktop.getDesktop();
                // Abrir la URL en el navegador predeterminado
                desktop.browse(uri);
            } else {
                System.out.println(
                        "La funcionalidad de apertura de navegador no" +
                                "está soportada en este sistema."
                );
            }
        } catch (IOException | URISyntaxException e) {
            System.out.println("Ocurrió un error al abrir la URL: " + e.getMessage());
        }
    }
}

