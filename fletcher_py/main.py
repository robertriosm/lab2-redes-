"""
Nombre del archivo: main.py
Autores: Pedro Pablo Arriola Jimenez y Roberto Rios
Carnet: 20188 y 20979
Fecha de creación: 01/08/2023
Descripción: Archivo main donde se ejecuta todo el codigo implementado
"""

from FletcherChecksum import FletcherChecksum

# Primero, crearemos una instancia del objeto FletcherChecksum con un tamaño de bloque de 8 (un byte).
fletcher_checksum = FletcherChecksum(8)

# A continuación, generaremos las tramas para las pruebas.

# Tramas sin manipular
trama1 = "1010101010101010"
trama2 = "1100110011001100"
trama3 = "1111000011110000"

# Tramas con un bit manipulado
trama4 = "1010101010101011"  # Cambiamos el último bit de trama1
trama5 = "1100110011001101"  # Cambiamos el último bit de trama2
trama6 = "1111000011110001"  # Cambiamos el último bit de trama3

# Tramas con dos bits manipulados
trama7 = "1010101010101101"  # Cambiamos el último y el penúltimo bit de trama1
trama8 = "1100110011001111"  # Cambiamos el último y el penúltimo bit de trama2
trama9 = "1111000011110011"  # Cambiamos el último y el penúltimo bit de trama3

# Trama modificada especialmente para que el algoritmo del lado del receptor no sea capaz de detectar el error.
trama10 = "0000000011111111"  

# Primero, generamos los mensajes para cada trama con el emisor.
mensaje1 = fletcher_checksum.emisor(trama1)
mensaje2 = fletcher_checksum.emisor(trama2)
mensaje3 = fletcher_checksum.emisor(trama3)

# Luego, pasamos los mensajes al receptor sin alterarlos.
# No se deben detectar errores.
print("Resultado trama 1:", fletcher_checksum.receptor(mensaje1))
print("Resultado trama 2:", fletcher_checksum.receptor(mensaje2))
print("Resultado trama 3:", fletcher_checksum.receptor(mensaje3))

# Ahora, alteramos un bit en las tramas pero utilizamos el mismo checksum.
mensaje4 = trama4 + mensaje1[-16:]
mensaje5 = trama5 + mensaje2[-16:]
mensaje6 = trama6 + mensaje3[-16:]

# Pasamos los mensajes alterados al receptor.
# Se deben detectar errores.
print("Resultado trama 4:", fletcher_checksum.receptor(mensaje4))
print("Resultado trama 5:", fletcher_checksum.receptor(mensaje5))
print("Resultado trama 6:", fletcher_checksum.receptor(mensaje6))

# Alteramos dos bits en las tramas pero utilizamos el mismo checksum.
mensaje7 = trama7 + mensaje1[-16:]
mensaje8 = trama8 + mensaje2[-16:]
mensaje9 = trama9 + mensaje3[-16:]

# Pasamos los mensajes alterados al receptor.
# Se deben detectar errores.
print("Resultado trama 7:", fletcher_checksum.receptor(mensaje7))
print("Resultado trama 8:", fletcher_checksum.receptor(mensaje8))
print("Resultado trama 9:", fletcher_checksum.receptor(mensaje9))


# Genera el mensaje con el checksum para una trama.
mensaje = fletcher_checksum.emisor(trama10)

# Invierte los bits en la trama pero utiliza el mismo checksum.
mensaje_alterado = "1111111100000000" + mensaje[-16:]

# Pasamos el mensaje alterado al receptor.
# No se debe detectar ningún error.
print("Resultado trama 10:", fletcher_checksum.receptor(mensaje_alterado))
