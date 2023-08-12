public class Main {
    public static void main(String[] args) {
        try {
            Hamming h = new Hamming(12, 8, "01100001");
            h.hamming();
            h.printResults();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        try {
            Detection detector = new Detection("01100001", "111111010011");
            detector.decode(new Hamming(12, 8, "01100001")); // Re-creating the Hamming object here; you can also use the previous object if needed
            detector.printResults();
        } catch (Exception e) {
            System.out.println("\nSe ha detectado mas de un error en la trama!");
        }
    }
}

