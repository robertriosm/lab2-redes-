import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Cliente {
    public static void main(String[] args) {
        String direccionServidor = "localhost"; // Dirección IP del servidor
        int puerto = 1234; // Puerto en el que el servidor está escuchando

        try (Socket socket = new Socket(direccionServidor, puerto)) {
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

            for (int i = 1; i <= 1000; i++) {
                Hamming h = new Hamming(12, 8, generarTramaAleatoria(8));
                h.hamming();
                Random random = new Random();
                double p = random.nextDouble();
                String ruido = aplicarRuido(h.hammingEncoded, p); // ruido que quieres enviar
                salida.writeUTF(ruido + "," + h.hammingEncoded + "," + String.valueOf(p));
                System.out.println(h.hammingEncoded + " enviado!");
            }

            salida.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con el servidor.");
        }
    }

    public static String aplicarRuido(String trama, double probabilidadError) {
        Random random = new Random();
        StringBuilder tramaRuidosa = new StringBuilder(trama.length());
        for (int i = 0; i < trama.length(); i++) {
            if (random.nextDouble() < probabilidadError) {
                tramaRuidosa.append(trama.charAt(i) == '0' ? '1' : '0'); // Invierte el bit
            } else {
                tramaRuidosa.append(trama.charAt(i)); // Mantiene el bit original
            }
        }
        return tramaRuidosa.toString();
    }

    public static String generarTramaAleatoria(int tamano) {
        Random random = new Random();
        StringBuilder trama = new StringBuilder(tamano);
        for (int i = 0; i < tamano; i++) {
            trama.append(random.nextInt(2));
        }
        return trama.toString();
    }
}
