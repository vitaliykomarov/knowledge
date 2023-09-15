def temp_var(x, y):
    temp = x
    x = y
    y = temp
    print("\nAfter swapping:")
    print(f'First var = {x} \nSecond var = {y}')


def mult_assigment(x, y):
    x, y = y, x
    print("\nAfter swapping:")
    print(f'First var = {x} \nSecond var = {y}')


def math_op(x, y):
    x = x + y
    y = x - y
    x = x - y
    print("\nAfter swapping:")
    print(f'First var = {x} \nSecond var = {y}')
    print("\nHow did it work:\nx = x + y\ny = x - y\nx = x - y")


print("The program accepts only integers")
x = int(input("Enter first var: "))
y = int(input("Enter second var: "))

print("\nHere several methods to swap:")
print("\t1) Use a temporary variable")
print("\t2) Use multiple assignment (x,y = y,x)")
print("\t3) Use addition and subtraction")
choose = int(input("Choose the method: "))

print("\nBefore swapping:\nFirst var = {0}\nSecond var = {1}".format(x, y))

match choose:
    case 1:
        temp_var(x, y)
    case 2:
        mult_assigment(x, y)
    case 3:
        math_op(x, y)
