import math
from hamming import Hamming


class Deteccion:
    def __init__(self, data: str, to_check: str):

        self.code = data
        self.check_code = to_check
        print("Trama recibida            ->", self.code)
        print("Trama con errores         ->", self.check_code)



    def check_pars(self, par_l, par_r):
        errores = ""

        for i in range(len(par_l)):
            if par_l[i] != par_r[i]:
                errores += "1"
            else:
                errores += "0"
                
        corrected = errores[::-1]  
        
        return corrected
    


    def remove_extras(self):
        cleaned = ""
        self.parities = ""

        for i in range(len(self.check_code)):
            if not ((i + 1) != 2**int(math.floor(math.log(i + 1) / math.log(2)))):
                self.parities += self.check_code[i]

            else:
                cleaned += self.check_code[i]

        return cleaned


    def binary_to_decimal(self):
        decimal = 0
        correct = self.bit_err[::-1]

        for i in range(len(correct)):
            if correct[i] == '1':
                decimal += 2**i

        return decimal



    def detect(self, original):
        count = 0
        correct = ""
        index_err = -1
        comp = self.check_pars(original, self.parities)[::-1]

        for i in range(len(self.bit_err)):
            if self.bit_err[i] == '1':
                count += 1

        for i in range(len(comp)):
            if comp[i] == '1':
                count += 1
                index_err = i

        if count == 0:
            print("\nLa secuencia no tiene errores\n")
            correct = self.check_code

        elif count == 1:
            err_pos = 2**index_err
            
            bitValue = self.check_code[err_pos - 1]
            correct = self.check_code[:err_pos - 1] + ('1' if bitValue == '0' else '0') + self.check_code[err_pos:]
            print("\nHubo un error, en la posición: ", err_pos, ".\n")
        
        else:
            bit_pos = self.binary_to_decimal()

            bitValue = self.check_code[bit_pos - 1]
            correct = self.check_code[:bit_pos - 1] + ('1' if bitValue == '0' else '0') + self.check_code[bit_pos:]
            print("\nHubo un error, en la posicion: ", bit_pos, ".\n")


        return correct



    def decode(self, hamming):
        self.codeWithoutParityBits = self.remove_extras()
        h2 = Hamming(11, 7, self.codeWithoutParityBits)
        h2.encode_sec()
        self.bit_err = self.check_pars(hamming.parityBits, h2.parityBits)
        self.correctData = self.detect(hamming.parityBits)
        print("Secuencia original:", self.code)
        print("Secuencia con deteccion y correccion de errores:", self.correctData)
