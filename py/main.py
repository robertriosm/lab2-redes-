"""
Nombre del archivo: main.py
Autores: Pedro Pablo Arriola Jimenez y Roberto Rios
Carnet: 20188 y 20979
Fecha de creación: 01/08/2023
Descripción: Archivo main donde se ejecuta todo el codigo implementado
"""

from FletcherChecksum import FletcherChecksum


'''
PRUEBA PARA ALGORITMO DE DETECCION DE ERRORES

'''
# Crear una instancia de la clase FletcherChecksum con un tamaño de bloque de 8
fletcher = FletcherChecksum(8)

# Trama de prueba sin errores
cadena_prueba = "1010101010101011"

print("\n--------- FLETCHER CHECKSUM ---------\n")

# Emisor
mensaje = fletcher.emisor(cadena_prueba)
print("Mensaje del emisor:", mensaje)

# Receptor
resultado = fletcher.receptor(mensaje)
print("Resultado del receptor:", resultado)

# Trama de prueba con errores
mensaje_con_errores = "110101001101010011010101"

# Receptor
resultado = fletcher.receptor(mensaje_con_errores)
print("Resultado del receptor:", resultado)