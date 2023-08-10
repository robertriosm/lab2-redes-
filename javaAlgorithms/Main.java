
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("\n\nPrueba de codificacion:\n\n");
            Hamming hamming = new Hamming(11, 7, "110101");
            hamming.encode_sec();
        } catch (Exception e) {
            System.out.println("\n\nSe detecto un error: " + e + "\n\n");
        }

        try {
            System.out.println("\n\nPrueba de decodificacion:\n\n");
            Hamming hamming = new Hamming(11, 7, "110101");
            hamming.encode_sec();
            Deteccion hammingDecoding = new Deteccion(hamming.encoded_code, "10011110000");
            hammingDecoding.decode(hamming);
        } catch (Exception e) {
            System.out.println("\n\n" + e + "\n\n");
        }
    }
}
