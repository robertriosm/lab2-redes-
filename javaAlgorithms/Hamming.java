package javaAlgorithms;

import java.lang.Math;


public class Hamming {
    private String code;
    private int final_bits;
    private int parityBitCount;
    public String encoded_code;
    public String parityBits;

    public Hamming(int n, int m, String code) {
        int r = n - m;

        if (!((m + r + 1) <= Math.pow(2, r))) {
            throw new IllegalArgumentException("\n\nla secuencia no cumple: (m + r + 1) <= 2 ** r\n\n");
        } else {
            this.code = code;
        }
    }

    public int get_parity(int index, String[] lcode) {
        int val = 0;

        for (int i = 1; i <= this.final_bits; i++) {
            if ((i & index) != 0) {
                if (lcode[i].startsWith("P")) {
                    continue;
                }

                int bit = Integer.parseInt(lcode[i]);
                val ^= bit;
            }
        }

        return val;
    }

    public int parities_rel_bits() {
        int r = 0;
        int m = this.code.length();

        while (Math.pow(2, r) < m + r + 1) {
            r += 1;
        }

        this.final_bits = m + r;
        this.parityBitCount = r;

        return r;
    }

    public String[] set_parity_pos() {
        int j = 0;
        String[] code_arr = new String[this.final_bits + 1];

        for (int i = 1; i <= this.final_bits; i++) {
            if (i == Math.pow(2, j)) {
                code_arr[i] = "P" + Integer.toString(j + 1);
                j += 1;
            } else {
                code_arr[i] = Character.toString(this.code.charAt(i - j - 1));
            }
        }

        for (j = 0; j < this.parityBitCount; j++) {
            int index = (int) Math.pow(2, j);
            code_arr[index] = "(" + Integer.toString(this.get_parity(index, code_arr)) + ")";
        }

        return code_arr;
    }

    public String[] bits_to_code(String[] code_arr_l) {
        StringBuilder code = new StringBuilder();
        StringBuilder bitsInsideParentheses = new StringBuilder();

        for (int i = 1; i < code_arr_l.length; i++) {
            if (code_arr_l[i] != null) {
                code.append(code_arr_l[i]);
                if (code_arr_l[i].startsWith("(") && code_arr_l[i].endsWith(")")) {
                    bitsInsideParentheses.append(code_arr_l[i].substring(1, code_arr_l[i].length() - 1));
                }
            }
        }

        return new String[]{code.toString().replace(")", "").replace("(", ""), bitsInsideParentheses.toString()};
    }

    public void encode_sec() {
        String[] hammingCodeArray = this.set_parity_pos();
        String[] bitsToCode = this.bits_to_code(hammingCodeArray);
        this.encoded_code = bitsToCode[0];
        this.parityBits = bitsToCode[1];
        
        System.out.println("Codigo: " + this.encoded_code);
        System.out.println("Bits de paridad: " + this.parityBits);

        System.out.println("Secuencia ingresada: " + this.code);
        System.out.println("Bits de paridad: " + this.parities_rel_bits());
        
    }
}
