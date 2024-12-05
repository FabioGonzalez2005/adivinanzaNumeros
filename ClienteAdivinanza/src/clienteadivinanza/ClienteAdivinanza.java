package clienteadivinanza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ClienteAdivinanza {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12343;
        int maximo = 100;
        int minimo = 0;
        int propuestaCliente;

        try (Socket socket = new Socket(host, port);
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Conectado al servidor " + host + " en el puerto " + port + ".");
            
            boolean continuar = true;
            while (continuar) {
                propuestaCliente = minimo + new Random().nextInt(maximo - minimo + 1);
                System.out.println("Enviando número: " + propuestaCliente);
                output.println(propuestaCliente);

                String respuesta = input.readLine();
                
                System.out.println("Respuesta del servidor: " + respuesta);
                if (respuesta.contains("¡Has acertado!")) {
                    continuar = false;
                } else if (respuesta.contains ("menor")) {
                    maximo = propuestaCliente;
                } else if (respuesta.contains ("mayor")) {
                    minimo = propuestaCliente;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
