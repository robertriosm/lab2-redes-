package java_algorithms;

import java.util.ArrayList;

public class FletcherChecksum {
    private int blockSize;

    // Constructor de la clase
    public FletcherChecksum(int blockSize) {
        this.blockSize = blockSize;
    }

    // Método para agregar ceros al final de los datos hasta que su longitud sea múltiplo del tamaño del bloque
    public String paddingZeros(String data) {
        while (data.length() % blockSize != 0) {
            data += "0";
        }
        return data;
    }

    // Método para dividir los datos en bloques del tamaño especificado
    public ArrayList<String> splitIntoBlocks(String data) {
        ArrayList<String> blocks = new ArrayList<>();
        for (int i = 0; i < data.length(); i += blockSize) {
            blocks.add(data.substring(i, Math.min(i + blockSize, data.length())));
        }
        return blocks;
    }

    // Método para convertir una cadena de dígitos binarios en una lista de bytes
    public ArrayList<Integer> binaryStringToBytes(String data) {
        ArrayList<String> blocks = splitIntoBlocks(data);
        ArrayList<Integer> bytes = new ArrayList<>();
        for (String block : blocks) {
            bytes.add(Integer.parseInt(block, 2));
        }
        return bytes;
    }

    // Método para implementar la parte del emisor del algoritmo de checksum de Fletcher
    public String emisor(String data) {
        data = paddingZeros(data);
        ArrayList<Integer> bytesData = binaryStringToBytes(data);

        int S1 = 0;
        int S2 = 0;

        for (Integer byteData : bytesData) {
            S1 = (S1 + byteData) % 255;
            S2 = (S2 + S1) % 255;
        }

        int checksum = (S2 << 8) | S1;
        return data + String.format("%16s", Integer.toBinaryString(checksum)).replace(' ', '0');
    }

    // Método para implementar la parte del receptor del algoritmo de checksum de Fletcher
    public String receptor(String message) {
        String data = message.substring(0, message.length() - 16);
        int receivedChecksum = Integer.parseInt(message.substring(message.length() - 16), 2);

        ArrayList<Integer> bytesData = binaryStringToBytes(data);

        int S1 = 0;
        int S2 = 0;

        ArrayList<String> errorLocation = new ArrayList<>();

        for (int i = 0; i < bytesData.size(); i++) {
            int S1_temp = (S1 + bytesData.get(i)) % 255;
            int S2_temp = (S2 + S1_temp) % 255;

            int calculatedChecksum = (S2_temp << 8) | S1_temp;

            if (calculatedChecksum != receivedChecksum) {
                errorLocation.add(String.format("(%d, %s)", i, String.format("%8s", Integer.toBinaryString(bytesData.get(i))).replace(' ', '0')));
            }

            S1 = S1_temp;
            S2 = S2_temp;
        }

        int calculatedChecksum = (S2 << 8) | S1;

        if (calculatedChecksum == receivedChecksum) {
            return "No se detectaron errores: " + data;
        } else {
            return "Se detectaron errores en las posiciones " + errorLocation + ": la trama se descarta";
        }
    }
}
