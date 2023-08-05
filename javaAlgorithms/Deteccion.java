package javaAlgorithms;

import java.lang.Math;

public class Deteccion {
    private String code;
    private String check_code;
    private String bit_err;    
    private String parities;
    private String codeWithoutParityBits;
    private String correctData;


    public Deteccion(String data, String to_check) {
        this.code = data;
        this.check_code = to_check;
        System.out.println("Secuencia a corregir: " + this.check_code);
        System.out.println("Secuencia recibida: " + this.code);
    }

    public String check_pars(String par_l, String par_r) {
        StringBuilder errores = new StringBuilder();

        for (int i = 0; i < par_l.length(); i++) {
            if (par_l.charAt(i) != par_r.charAt(i)) {
                errores.append("1");
            } else {
                errores.append("0");
            }
        }

        String corrected = errores.reverse().toString();

        return corrected;
    }

    public String remove_extras() {
        StringBuilder cleaned = new StringBuilder();
        this.parities = "";

        for (int i = 0; i < this.check_code.length(); i++) {
            if (!((i + 1) != Math.pow(2, Math.floor(Math.log(i + 1) / Math.log(2))))) {
                this.parities += this.check_code.charAt(i);
            } else {
                cleaned.append(this.check_code.charAt(i));
            }
        }

        return cleaned.toString();
    }

    public int binary_to_decimal() {
        int decimal = 0;
        String correct = new StringBuilder(this.bit_err).reverse().toString();

        for (int i = 0; i < correct.length(); i++) {
            if (correct.charAt(i) == '1') {
                decimal += Math.pow(2, i);
            }
        }

        return decimal;
    }

    public String detect(String original) {
        int count = 0;
        String correct = "";
        int index_err = -1;
        String comp = new StringBuilder(this.check_pars(original, this.parities)).reverse().toString();

        for (int i = 0; i < this.bit_err.length(); i++) {
            if (this.bit_err.charAt(i) == '1') {
                count += 1;
            }
        }

        for (int i = 0; i < comp.length(); i++) {
            if (comp.charAt(i) == '1') {
                count += 1;
                index_err = i;
            }
        }

        if (count == 0) {
            System.out.println("\nLa secuencia no tiene errores\n");
            correct = this.check_code;
        } else if (count == 1) {
            int err_pos = (int) Math.pow(2, index_err);

            char bitValue = this.check_code.charAt(err_pos - 1);
            correct = this.check_code.substring(0, err_pos - 1) + ((bitValue == '0') ? '1' : '0') + this.check_code.substring(err_pos);
            System.out.println("\nHubo un error, en la posición: " + err_pos + ".\n");
        } else {
            int bit_pos = this.binary_to_decimal();

            char bitValue = this.check_code.charAt(bit_pos - 1);
            correct = this.check_code.substring(0, bit_pos - 1) + ((bitValue == '0') ? '1' : '0') + this.check_code.substring(bit_pos);
            System.out.println("\nHubo un error, en la posición: " + bit_pos + ".\n");
        }

        return correct;
    }

    public void decode(Hamming hamming) {
        this.codeWithoutParityBits = this.remove_extras();
        Hamming h2 = new Hamming(11, 7, this.codeWithoutParityBits);
        h2.encode_sec();
        this.bit_err = this.check_pars(hamming.parityBits, h2.parityBits);
        this.correctData = this.detect(hamming.parityBits);
        System.out.println("Trama original  -> " + this.code);
        System.out.println("Trama corregida -> " + this.correctData);
    }
}
