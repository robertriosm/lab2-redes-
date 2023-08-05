package fletcher_java;

public class Main {
    public static void main(String[] args) {
        FletcherChecksum fletcherChecksum = new FletcherChecksum(8);
    
        // Tramas sin manipular
        String trama1 = "1010101010101010";
        String trama2 = "1100110011001100";
        String trama3 = "1111000011110000";
    
        // Tramas con un bit manipulado
        String trama4 = "1010101010101011";  // Cambiamos el último bit de trama1
        String trama5 = "1100110011001101";  // Cambiamos el último bit de trama2
        String trama6 = "1111000011110001";  // Cambiamos el último bit de trama3
    
        // Tramas con dos bits manipulados
        String trama7 = "1010101010101101";  // Cambiamos el último y el penúltimo bit de trama1
        String trama8 = "1100110011001111";  // Cambiamos el último y el penúltimo bit de trama2
        String trama9 = "1111000011110011";  // Cambiamos el último y el penúltimo bit de trama3
    
        // Trama modificada especialmente para que el algoritmo del lado del receptor no sea capaz de detectar el error.
        // Cambiamos dos bits en bloques diferentes de tal manera que el checksum resultante es el mismo.
        String trama10 = "0000000011111111";
    
        // Primero, ejecutaremos el lado del emisor.
        String emisorTrama1 = fletcherChecksum.emisor(trama1);
        String emisorTrama2 = fletcherChecksum.emisor(trama2);
        String emisorTrama3 = fletcherChecksum.emisor(trama3);
    
        // Luego, ejecutaremos el lado del receptor.
        System.out.println("Resultado trama 1: " + fletcherChecksum.receptor(emisorTrama1));
        System.out.println("Resultado trama 2: " + fletcherChecksum.receptor(emisorTrama2));
        System.out.println("Resultado trama 3: " + fletcherChecksum.receptor(emisorTrama3));
    
        // Ahora, alteramos un bit en las tramas pero utilizamos el mismo checksum.
        String mensaje4 = trama4 + emisorTrama1.substring(emisorTrama1.length() - 16);
        String mensaje5 = trama5 + emisorTrama2.substring(emisorTrama2.length() - 16);
        String mensaje6 = trama6 + emisorTrama3.substring(emisorTrama3.length() - 16);
    
        // Pasamos los mensajes alterados al receptor.
        System.out.println("Resultado trama 4: " + fletcherChecksum.receptor(mensaje4));
        System.out.println("Resultado trama 5: " + fletcherChecksum.receptor(mensaje5));
        System.out.println("Resultado trama 6: " + fletcherChecksum.receptor(mensaje6));
    
        // Alteramos dos bits en las tramas pero utilizamos el mismo checksum.
        String mensaje7 = trama7 + emisorTrama1.substring(emisorTrama1.length() - 16);
        String mensaje8 = trama8 + emisorTrama2.substring(emisorTrama2.length() - 16);
        String mensaje9 = trama9 + emisorTrama3.substring(emisorTrama3.length() - 16);
    
        // Pasamos los mensajes alterados al receptor.
        System.out.println("Resultado trama 7: " + fletcherChecksum.receptor(mensaje7));
        System.out.println("Resultado trama 8: " + fletcherChecksum.receptor(mensaje8));
        System.out.println("Resultado trama 9: " + fletcherChecksum.receptor(mensaje9));
    

        // Genera el mensaje con el checksum para una trama.
        String mensaje = fletcherChecksum.emisor(trama10);

        // Invierte los bits en la trama pero utiliza el mismo checksum.
        String mensajeAlterado = "1111111100000000" + mensaje.substring(mensaje.length() - 16);

        // Ahora el receptor no debería detectar el error.
        System.out.println("Resultado: " + fletcherChecksum.receptor(mensajeAlterado));
    }
    
}

