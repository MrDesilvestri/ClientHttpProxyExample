import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            String proxyHost = "localhost"; // Cambia esto si el proxy no se ejecuta en localhost
            int proxyPort = 8081; // Cambia esto si el proxy está en un puerto diferente

            // Configurar proxy
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

            // Crear conexión a través del proxy
            URL url = new URL("http://localhost:8080/boleta/1"); // Cambia la URL según tu endpoint
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);

            // Establecer método de solicitud y tiempo de espera
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Obtener la respuesta del proxy
            int responseCode = connection.getResponseCode();
            System.out.println("Código de respuesta del proxy: " + responseCode);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Respuesta del proxy:");
            System.out.println(response.toString());

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}