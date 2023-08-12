from math import floor, log, pow
from hamming import Hamming

class Detection:
    
    def __init__(self, code, errors) -> None:
        self.code = code
        self.code_with_errors = errors
    


    def decode(self, hamming:Hamming):
        self.cleaned_data = self.clean_bits()
        h = Hamming(11,7,self.cleaned_data)
        h.hamming()
        self.errors = self.comparison(hamming.parities,h.parities)
        self.corrected = self.fix_errors(hamming.parities) 
    


    def clean_bits(self):
        cleaned = ""
        self.parities = ""

        for i in range(len(self.code_with_errors)):
            if (i + 1) != pow(2, int( floor( log(i + 1) / log(2) ) ) ):
                cleaned += self.code_with_errors[i]
            else:
                self.parities += self.code_with_errors[i]

        return cleaned
    

    
    def comparison(self, last_one, with_errors):
        errors = ""

        for i in range(len(last_one)):
            if last_one[i] == with_errors[i]:
                errors += "0"
            else:
                errors += "1"
        
        return "".join(reversed(errors))



    def to_decimal(self):
        d = 0
        rev = self.errors[::-1]

        for i in range(len(rev)):
            if rev[i] == '1':
                d += 2**i

        return d
    


    def fix_errors(self, parities):
        count = 0
        compared = self.comparison(parities, self.parities)[::-1]
        result = ""
        index = -1

        for i in range(len(self.errors)):
            if self.errors[i] == "1":
                count += 1
        
        for i in range(len(compared)):
            if self.errors[i] == "1":
                count += 1
                index = i
        
        if count == 0:    
            result = self.code_with_errors

        elif count == 1:
            error_pos = 2**index
            val = self.code_with_errors[error_pos]
            result = self.code_with_errors[:self.code_with_errors-1] + ('1' if val == '0' else '0') + self.code_with_errors[error_pos:]

        else:
            p = self.to_decimal()
            val = self.code_with_errors[p - 1]
            result = self.code_with_errors[:p-1] + ("1" if val == "0" else "0") + self.code_with_errors[p:]
        
        return result
    


    def print_results(self):
        print(f"""Deteccion:
        Original: {self.code_with_errors}
        Corregida: {self.corrected}
        """)
    