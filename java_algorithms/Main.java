package java_algorithms;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de FletcherChecksum con un tamaño de bloque de 8
        FletcherChecksum fletcher = new FletcherChecksum(8);

        // Trama de prueba sin errores
        String cadenaPrueba = "110101";
        
        System.out.println("\n--------- FLETCHER CHECKSUM ---------\n");

        // Emisor
        String mensaje = fletcher.emisor(cadenaPrueba);
        System.out.println("Mensaje del emisor: " + mensaje);

        // Receptor
        String resultado = fletcher.receptor(mensaje);
        System.out.println("Resultado del receptor: " + resultado);

        // Trama de prueba con errores
        String mensajeConErrores = "110101001101010011010101";

        // Receptor
        resultado = fletcher.receptor(mensajeConErrores);
        System.out.println("Resultado del receptor: " + resultado);
    }
}

