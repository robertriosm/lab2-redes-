
class Hamming:
    def __init__(self, n: int, m: int, code: str):
        r = n - m

        if not ((m + r + 1) <= 2**r):
            raise Exception("\n\nla secuencia no cumple: (m + r + 1) <= 2 ** r\n\n")
        else:
            self.code = code



    def get_parity(self, index: int, lcode: list[str]):
        val = 0

        for i in range(1, self.final_bits + 1):
            if i & index != 0:
                if lcode[i].startswith("P"):
                    continue

                bit = int(lcode[i])
                val ^= bit

        return val
    


    def parities_rel_bits(self):
        r = 0
        m = len(self.code)
        
        while 2**r < m + r + 1:
            r += 1
        
        self.final_bits = m + r
        self.parityBitCount = r

        return r



    def set_parity_pos(self):
        j = 0
        code_arr = [None] * (self.final_bits + 1)
        
        for i in range(1, self.final_bits + 1):
            if i == 2**j:
                code_arr[i] = "P" + str(j + 1)
                j += 1
            else:
                code_arr[i] = self.code[i - j - 1]

        for j in range(self.parityBitCount):
            index = 2**j
            code_arr[index] = "(" + str(self.get_parity(index, code_arr)) + ")"

        return code_arr



    def bits_to_code(self, code_arr_l: list):
        code = ""
        bitsInsideParentheses = ""

        for i in range(1, len(code_arr_l)):
            if code_arr_l[i] is not None:
                code += code_arr_l[i]
                if code_arr_l[i].startswith("(") and code_arr_l[i].endswith(")"):
                    bitsInsideParentheses += code_arr_l[i][1:-1]

        return (code.replace(")", "").replace("(", ""), bitsInsideParentheses)
    


    def encode_sec(self):
        print("Secuencia ingresada: ", self.code)
        print("Bits de paridad: ", self.parities_rel_bits())
        hammingCodeArray = self.set_parity_pos()
        self.encoded_code, self.parityBits = self.bits_to_code(hammingCodeArray)
        print("Codigo: ", self.encoded_code)
        print("Bits de paridad: ", self.parityBits)
