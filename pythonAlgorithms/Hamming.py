
class Hamming:
    
    
    def __init__(self, n: int, m: int, code: str):
        self.r = n - m
        self.m = m
        self.n = n

        if not ((m + self.r + 1) <= 2**self.r):
            raise Exception("\n\nla secuencia no cumple la condicion para ser valida: (m + r + 1) <= 2 ** r\n\n")
        else:
            self.code = code

    
    def encode(self):
        pass
    
    
    def get_parities(self):
        parities = []

        for i in self.decompose_into_powers_of_2(self.m):
            print(i)

        pass

    def decompose_into_powers_of_2(n):
        powers = []
        power_of_2 = 1
        
        while n > 0:
            if n % 2 == 1:
                powers.append(power_of_2)
            n //= 2
            power_of_2 *= 2
        
        return powers
    
    def hamming(code: str, n: int, m: int) -> str:
        """
        data bits: 
        parity bits:

        colocar 2**r

        code: la secuencia
        n: longitud inicial
        m: longitud final
        r: un exponente que cumple 2**r >= m + r + 1
        """

        output = ""





        return output

    
h = Hamming(n=11,m=7,code="0110000")
print(h.m)
print(h.n)
print(h.code)
# h.get_parities()