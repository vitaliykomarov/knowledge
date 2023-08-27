import requests
import json

city = input('Enter the name of the city: ')

url = ('https://api.openweathermap.org/data/2.5/weather?q='+city+'&units=metric&lang=ru&appid=79d1ca96933b0328e1c7e3e7a26cb347')

weather_data = requests.get(url).json()
weather_data_structure = json.dumps(weather_data, indent=2)

temperature = round(weather_data['main']['temp'])
temperature_feels = round(weather_data['main']['feels_like'])
wind_speed = round(weather_data['wind']['speed'])

#print(weather_data_structure)
print('The temperature in', city, 'is', str(temperature), 'degrees Celsius')
print('Feels like', str(temperature_feels), 'degrees')
print('Wind speed', str(wind_speed),'m/s')