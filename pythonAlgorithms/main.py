from hamming import *
from deteccion import *

try:
    print("\n\nPrueba de codificacion:\n\n")
    hamming = Hamming(11, 7, "110101")
    hamming.encode_sec()
except Exception as e:
    print(f"\n\nSe detecto un error: {e}\n\n")
    
try:
    print("\n\nPrueba de decodificacion:\n\n")
    hammingDecoding = Deteccion(hamming.encoded_code, "10011110000")
    hammingDecoding.decode(hamming)
    
except Exception as e:
    print(f"\n\n{e}\n\n")
