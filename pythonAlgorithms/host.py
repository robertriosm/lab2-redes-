import socket
import csv
import struct
from detection import Detection

direccion_servidor = 'localhost'
puerto = 1234

def leer_mensaje(conn):
    longitud_bytes = conn.recv(2)
    longitud = struct.unpack('>H', longitud_bytes)[0]
    mensaje_bytes = conn.recv(longitud)
    return mensaje_bytes.decode('utf-8')

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((direccion_servidor, puerto))
    s.listen()
    print("Esperando conexión...")
    conn, addr = s.accept()
    with conn:
        print("Conexión establecida desde", addr)
        
        with open('mensajes.csv', 'w', newline='') as csvfile:
            writer = csv.writer(csvfile)
            writer.writerow(['Trama', 'con ruido', 'Intensidad']) # Encabezado del CSV

            for i in range(1000):
                mensaje_recibido = leer_mensaje(conn)
                ruido, enc, p = mensaje_recibido.split(",")
                print("Mensaje recibido:", mensaje_recibido)
                trama = Detection(enc,ruido)
                writer.writerow([enc,ruido,p]) # Escribe el mensaje en el CSV

        print("Todos los mensajes han sido guardados en mensajes.csv")
