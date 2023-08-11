"""
Nombre del archivo: main.py
Autores: Pedro Pablo Arriola Jimenez y Roberto Rios
Carnet: 20188 y 20979
Fecha de creación: 01/08/2023
Descripción: Archivo main donde se ejecuta todo el codigo implementado
"""

from FletcherChecksum import FletcherChecksum
import socket
from time import sleep
import matplotlib.pyplot as plt
import csv


import csv

def iniciar_servidor(puerto):
    with open('resultados.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(["Trama", "Tamaño de Trama", "Tamaño de Bloque", "Error"]) # Encabezado

        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.bind(('localhost', puerto))
            s.listen()
            print(f"Esperando conexión en el puerto {puerto}...")
            while True:
                conn, addr = s.accept()
                with conn:
                    print('Conexión aceptada:', addr)
                    trama_recibida = conn.recv(1024).decode().strip()
                    print("Trama recibida:", trama_recibida)

                    # Extraer el tamaño del bloque (por ejemplo, los primeros 4 bits)
                    block_size_bits = 4
                    block_size = int(trama_recibida[:block_size_bits], 2)

                    # Crear una nueva instancia de FletcherChecksum con el tamaño de bloque correcto
                    fletcher_checksum = FletcherChecksum(block_size)
                    
                    # Procesar el resto de la trama
                    resultado = fletcher_checksum.receptor(trama_recibida[block_size_bits:])

                    # Escribir el resultado en el CSV
                    writer.writerow([resultado['trama'], resultado['tamaño_trama'], resultado['tamaño_bloque'], resultado['error']])

                    sleep(0.1)


puerto_receptor = 65432
iniciar_servidor(puerto_receptor)
