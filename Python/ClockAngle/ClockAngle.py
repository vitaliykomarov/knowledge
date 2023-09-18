# -*- coding: utf-8 -*-

print("Program for calculating the angle between the hour and minute hands")

while (True):
    hour = int(input("Enter the hour (0-12): "))
    minutes = int(input("Enter minutes (0-60): "))
    if hour < 0 or hour > 12 or minutes < 0 or minutes > 60:
        print("Wrong input\nPlease try again")
    else:
        break

if hour == 12:
    hour = 0
if minutes == 60:
    hour += 1
    minutes = 0
    if hour > 12:
        hour = hour - 12

hour_angle = (360 / 12 / 60) * (hour * 60 + minutes)
minutes_angle = (360 / 60) * minutes
angle = abs(hour_angle - minutes_angle)
angle = min(360 - angle, angle)
print("Angle between the hour and minutes hand will be: ", str(angle)+"°") #Alt+0176 - degree sign
