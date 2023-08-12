public class Detection {
    private String code;
    private String codeWithErrors;
    private String cleanedData;
    private String errors;
    private String parities;
    private String corrected;

    public Detection(String code, String errors) {
        this.code = code;
        this.codeWithErrors = errors;
    }

    public void decode(Hamming hamming) {
        this.cleanedData = this.cleanBits();
        Hamming h = new Hamming(11, 7, this.cleanedData);
        h.hamming();
        this.errors = this.comparison(hamming.parities, h.parities);
        this.corrected = this.fixErrors(hamming.parities);
    }

    public String cleanBits() {
        String cleaned = "";
        this.parities = "";

        for (int i = 0; i < this.codeWithErrors.length(); i++) {
            if ((i + 1) != Math.pow(2, (int) Math.floor(Math.log(i + 1) / Math.log(2)))){
                cleaned += this.codeWithErrors.charAt(i);
            } else {
                this.parities += this.codeWithErrors.charAt(i);
            }
        }

        return cleaned;
    }

    public String comparison(String lastOne, String withErrors) {
        String errors = "";

        for (int i = 0; i < lastOne.length(); i++) {
            if (lastOne.charAt(i) == withErrors.charAt(i)) {
                errors += "0";
            } else {
                errors += "1";
            }
        }

        return new StringBuilder(errors).reverse().toString();
    }

    public int toDecimal() {
        int d = 0;
        String rev = new StringBuilder(this.errors).reverse().toString();

        for (int i = 0; i < rev.length(); i++) {
            if (rev.charAt(i) == '1') {
                d += (int) Math.pow(2, i);
            }
        }

        return d;
    }

    public String fixErrors(String parities) {
        int count = 0;
        String compared = this.comparison(parities, this.parities);
        StringBuilder result = new StringBuilder();
        int index = -1;

        for (int i = 0; i < this.errors.length(); i++) {
            if (this.errors.charAt(i) == '1') {
                count++;
            }
        }

        for (int i = 0; i < compared.length(); i++) {
            if (this.errors.charAt(i) == '1') {
                count++;
                index = i;
            }
        }

        if (count == 0) {
            result.append(this.codeWithErrors);
        } else if (count == 1) {
            int errorPos = (int) Math.pow(2, index);
            char val = this.codeWithErrors.charAt(errorPos);
            char y = val == '0' ? '1' : '0';
            result.append(this.codeWithErrors, 0, errorPos - 1).append(y).append(this.codeWithErrors.substring(errorPos));
        } else {
            int p = this.toDecimal();
            char val = this.codeWithErrors.charAt(p - 1);
            char y = val == '0' ? '1' : '0';
            result.append(this.codeWithErrors, 0, p - 1).append(y).append(this.codeWithErrors.substring(p));
        }

        return result.toString();
    }

    public void printResults() {
        System.out.println("Deteccion:\n" +
                "Original: " + this.codeWithErrors + "\n" +
                "Corregida: " + this.corrected + "\n");
    }
}
