package fletcher_java;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Random;


public class Main {

    public static void enviarTrama(String trama, int blockSize, String host, int puerto) {
        String blockSizeBinary = String.format("%4s", Integer.toBinaryString(blockSize)).replace(' ', '0');
        trama = blockSizeBinary + trama; // Añadir el tamaño del bloque al principio de la trama
        try (Socket socket = new Socket(host, puerto);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            out.writeBytes(trama + "\n"); // Enviar la trama
            System.out.println("Trama enviada: " + trama);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generarTramaAleatoria(int tamano) {
        Random random = new Random();
        StringBuilder trama = new StringBuilder(tamano);
        for (int i = 0; i < tamano; i++) {
            trama.append(random.nextInt(2));
        }
        return trama.toString();
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
    
    public static void main(String[] args) throws InterruptedException {
        String hostReceptor = "localhost";
        int puertoReceptor = 65432;
        int numeroTramas = 1000; // Número de tramas a enviar por cada configuración
    
        // Valores para variar el tamaño de la trama
        int[] tamanosTrama = {8, 16, 32};
    
        // Valores para variar la probabilidad de error
        double[] probabilidadesError = {0.001, 0.01, 0.05};
    
        // Valores para variar el tamaño del bloque en Fletcher Checksum (redundancia/tasa de código)
        int[] tamanosBloque = {4, 8, 16};
    
        for (int tamanoTrama : tamanosTrama) {
            for (double probabilidadError : probabilidadesError) {
                for (int tamanoBloque : tamanosBloque) {
                    FletcherChecksum fletcherChecksum = new FletcherChecksum(tamanoBloque);
    
                    for (int i = 0; i < numeroTramas; i++) {
                        // Generar trama aleatoria
                        String trama = generarTramaAleatoria(tamanoTrama);
                        // Aplicar el algoritmo del emisor
                        String emisorTrama = fletcherChecksum.emisor(trama);
                        // Aplicar ruido a la trama
                        String tramaRuidosa = aplicarRuido(emisorTrama, probabilidadError);

                        // Enviar la trama ruidosa
                        enviarTrama(tramaRuidosa, tamanoBloque, hostReceptor, puertoReceptor);
                        
                        Thread.sleep(100);
                    }
                }
            }
        }
    }    
        
    
}

