"""
Nombre del archivo: FletcherChecksum.py
Autor: Pedro Pablo Arriola Jimenez
Carnet: 20188
Fecha de creación: 01/08/2023
Descripción: Implementación del algoritmo de checksum de Fletcher en Python
"""

class FletcherChecksum(object):
    """
    Implementación del algoritmo de checksum de Fletcher.
    """
    
    def __init__(self, block_size):
        """
        Constructor de la clase.
        
        :param block_size: Tamaño del bloque para el algoritmo.
        """
        self.block_size = block_size
    
    def padding_zeros(self, data):
        """
        Agrega ceros al final de los datos hasta que la longitud sea múltiplo del tamaño del bloque.
        
        :param data: Datos a los que se agregarán ceros.
        :return: Datos con ceros añadidos.
        """
        while len(data) % self.block_size != 0:
            data += '0'
        return data

    def split_into_blocks(self, data):
        """
        Divide los datos en bloques del tamaño especificado.
        
        :param data: Datos que se dividirán en bloques.
        :return: Lista de bloques de datos.
        """
        return [data[i:i+self.block_size] for i in range(0, len(data), self.block_size)]

    def binary_string_to_bytes(self, data):
        """
        Convierte una cadena de dígitos binarios en una lista de bytes.
        
        :param data: Cadena de dígitos binarios.
        :return: Lista de bytes.
        """
        return [int(b, 2) for b in self.split_into_blocks(data)]

    def emisor(self, data):
        """
        Implementa la parte del emisor del algoritmo de checksum de Fletcher.
        Toma una cadena de dígitos binarios y devuelve la cadena original concatenada con el checksum calculado.
        
        :param data: Cadena de dígitos binarios.
        :return: Cadena original concatenada con el checksum calculado.
        """
        data = self.padding_zeros(data)
        bytes_data = self.binary_string_to_bytes(data)
    
        S1 = 0
        S2 = 0

        for byte in bytes_data:
            S1 = (S1 + byte) % 255
            S2 = (S2 + S1) % 255

        checksum = (S2 << 8) | S1
        return data + format(checksum, 'b').zfill(16)

    def receptor(self, message):
        """
        Implementa la parte del receptor del algoritmo de checksum de Fletcher.
        Toma un mensaje que consta de una cadena de dígitos binarios y un checksum,
        verifica si el checksum es correcto y, en caso de error, muestra la ubicación del error.
        
        :param message: Mensaje que consta de una cadena de dígitos binarios y un checksum.
        :return: Cadena original si no se detectan errores, o ubicación del error si se detectan errores.
        """
        data = message[:-16]
        received_checksum = int(message[-16:], 2)

        bytes_data = self.binary_string_to_bytes(data)

        S1 = 0
        S2 = 0

        error_location = []

        for i, byte in enumerate(bytes_data):
            S1_temp = (S1 + byte) % 255
            S2_temp = (S2 + S1_temp) % 255

            calculated_checksum = (S2_temp << 8) | S1_temp

            if calculated_checksum != received_checksum:
                error_location.append((i, format(byte, 'b').zfill(8)))
            
            S1 = S1_temp
            S2 = S2_temp

        calculated_checksum = (S2 << 8) | S1

        if calculated_checksum == received_checksum:
            return "No se detectaron errores: " + data
        else:
            return f"Se detectaron errores en las posiciones {error_location}: la trama se descarta"
