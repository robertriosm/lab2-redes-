public class Hamming {
    private int r;
    private int m;
    private int n;
    private String code;
    private int final_len;
    public String hammingEncoded;
    public String parities;

    public Hamming(int n, int m, String code) {
        this.r = n - m;
        this.m = m;
        this.n = n;

        if ((m + this.r + 1) > Math.pow(2, this.r)) {
            throw new RuntimeException("\n\nla secuencia no cumple la condicion para ser valida: (m + r + 1) <= 2 ** r\n\n");
        } else {
            this.code = code;
        }
    }

    public int getParities() {
        this.r = 0;
        this.m = this.code.length();

        while (Math.pow(2, this.r) < this.m + this.r + 1) {
            this.r += 1;
        }

        this.final_len = this.m + this.r;
        return this.r;
    }

    public int getByIndex(int index, String[] sequence) {
        int val = 0;

        for (int i = 1; i <= this.final_len; i++) {
            if ((i & index) != 0) {
                if (sequence[i].startsWith("p")) {
                    continue;
                }
                val ^= Integer.parseInt(sequence[i]);
            }
        }

        return val;
    }

    public String[] setParityPositions() {
        String[] result = new String[this.final_len + 1];
        int j = 0;

        for (int i = 1; i <= this.final_len; i++) {
            if (i == Math.pow(2, j)) {
                result[i] = "p" + (j + 1);
                j += 1;
            } else {
                result[i] = Character.toString(this.code.charAt(i - j - 1));
            }
        }

        for (int i = 0; i < this.r; i++) {
            int index = (int) Math.pow(2, i);
            int val = this.getByIndex(index, result);
            result[index] = "(" + val + ")";
        }

        return result;
    }

    public String[] encode(String[] arr) {
        String encoded = "";
        String insides = "";
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != null) {
                encoded += arr[i];
                if (arr[i].endsWith(")") && arr[i].startsWith("(")) {
                    insides += arr[i].substring(1, arr[i].length() - 1);
                }
            }
        }

        return new String[] { encoded.replace("(", "").replace(")", ""), insides };
    }

    public void hamming() {
        getParities();
        String[] hammingArr = setParityPositions();
        String[] encodedResults = encode(hammingArr);
        this.hammingEncoded = encodedResults[0];
        this.parities = encodedResults[1];
    }

    public void printResults() {
        System.out.println("Hamming:\n" +
                "    Secuencia ingresada: " + this.code + "\n" +
                "    Cantidad de bits de paridad: " + getParities() + "\n" +
                "    Codificacion: " + this.hammingEncoded + "\n" +
                "    Bits de paridad: " + this.parities + "\n");
    }
}
