class Hamming:
    def __init__(self, n: int, m: int, code: str) -> None:
        self.r = n - m
        self.m = m
        self.n = n

        if not ((m + self.r + 1) <= 2**self.r):
            raise Exception("\n\nla secuencia no cumple la condicion para ser valida: (m + r + 1) <= 2 ** r\n\n")
        else:
            self.code = code


    def get_parities(self):
        self.r = 0
        self.m = len(self.code)

        while (2 ** self.r < self.m + self.r + 1):
            self.r += 1
        
        self.final_len = self.m + self.r
        return self.r


    def get_by_index(self, index: int, secuence: str):
        val = 0

        for i in range(1, self.final_len + 1):
            if i & index != 0:
                if secuence[i].startswith("p"):
                    continue
                val ^= int(secuence[i])

        return val   


    def set_parity_positions(self):
        result = [None for _ in range(self.final_len+1)]
        j = 0

        for i in range(1, self.final_len+1):
            if i == 2**j:
                result[i] = "p" + str(j+1)
                j += 1
            else:
                result[i] = self.code[i-j-1]
            
        for i in range(self.r):
            index = 2**i
            val = self.get_by_index(index=index, secuence=result)
            result[index] = f"({str(val)})"
        
        return result
    

    def encode(self, arr: list[str]):
        encoded = ""
        insides = ""
        for i in range(1, len(arr)):
            if arr[i] is not None:
                encoded += arr[i]
                if arr[i].endswith(")") and arr[i].startswith("("):
                    insides += arr[i][1:-1]

        return (encoded.replace("(", "").replace(")", ""), insides)


    def hamming(self):
        self.get_parities()
        hamming_arr = self.set_parity_positions()
        self.hamming_encoded, self.parities = self.encode(hamming_arr)
    

    def print_results(self):
        print(f"""Hamming:
            Secuencia ingresada: {self.code}
            Cantidad de bits de paridad: {self.get_parities()}
            Codificacion: {self.hamming_encoded}
            Bits de paridad: {self.parities}
        """)