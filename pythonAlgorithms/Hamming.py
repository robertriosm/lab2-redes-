
import math

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

    colocar 2**r paridades

    code: la secuencia
    m: longitud inicial
    n: longitud final

    r es un exponente que cumple 2**r >= m + r + 1
    """

    # variables
    parities = {}
    data = {}
    count = 1
    r = n - m

    # ver bits de paridades
    for i in range(0, r):
        parities[f"p{count}"] = 2**i
        i += 1
        count += 1
    
    
    # revisar si se cumple la condicion
    if not (m + r + 1 <= math.pow(2,r)):
        print(r)
        return None
        
    
    # obtener bits de datos
    count = 1
    for i in range(1, n+1):
        data[f"d{count}"] = i
        count += 1

    # Create a set of unique values from dict1
    parities_pos_arr = set(parities.values())

    # posiciones del output
    data = {key: value for key, value in data.items() if value not in parities_pos_arr}

    # fully merged
    positions = data | parities

    # ver las posiciones de los bits de datos
    data_values = list(data.values())

    # calcular sus ecuas
    ecuas = {}
    for i in data_values:
        ecuas[i] = decompose_into_powers_of_2(i)
    
    p_parity_pos = {}
    for i in parities_pos_arr:
        p_parity_pos[i] = []
    
    # ahora ver la paridad de cada p'i
    for bit in parities_pos_arr:
        for val, sumandos in ecuas.items():
            if bit in sumandos:
                p_parity_pos[bit].append(val)


    # ahora relacionar las posiciones de data con los bits
    bit_data_pos = {}
    for i in data_values:
        bit_data_pos[i] = None


    return data



print(hamming(code="011101", n=11, m=7))