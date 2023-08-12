from hamming import Hamming
from detection import Detection

try:
    h = Hamming(12, 8, "01100001")
    h.hamming()
    h.print_results()
except ValueError as e:
    print(e)

try:
    detector = Detection(h.code, "111111010011")
    detector.decode(h)
    detector.print_results()
except Exception as e:
    print("\nSe ha detectado mas de un error en la trama!")
    