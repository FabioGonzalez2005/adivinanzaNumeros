package servidoradivinanza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServidorAdivinanza {

    public static void main(String[] args) {
        int port = 12343;
        int numeroParaAdivinar = new Random().nextInt(100);

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Servidor iniciado en el puerto " + port + ".");
            
            while (true) {
                try (Socket client = server.accept();
                     BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     PrintWriter output = new PrintWriter(client.getOutputStream(), true)) {

                    System.out.println("Cliente conectado: " + client.getInetAddress());
                    
                    System.out.println("Numero a adivinar: " + numeroParaAdivinar);
                    
                    int propuestaCliente;
                    boolean continuar = true;
                    
                    while (continuar) {
                        String mensaje = input.readLine();
                        propuestaCliente = Integer.parseInt(mensaje);
                        System.out.println(propuestaCliente);
                        if (propuestaCliente < numeroParaAdivinar) {
                            output.println("El número es mayor.");
                            
                        } else if (propuestaCliente > numeroParaAdivinar) {
                            output.println("El número es menor.");
                        } else {
                            output.println("¡Has acertado! El número era " + numeroParaAdivinar);
                            continuar = false;
                        }
                    }
                } catch (IOException e) {
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
