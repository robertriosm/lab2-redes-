def decompose_into_powers_of_2(n):
    powers = []
    power_of_2 = 1
    
    while n > 0:
        if n % 2 == 1:
            powers.append(power_of_2)
        n //= 2
        power_of_2 *= 2
    
    return powers

numbers = [3, 5, 10, 20, 31, 100]

for num in numbers:
    powers = decompose_into_powers_of_2(num)
    print(powers)
    # print(f"{num} =", " + ".join(map(str, powers)))
