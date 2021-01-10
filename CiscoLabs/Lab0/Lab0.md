# LAB 0
Building a simple network
====================
Select PCs \ Laptops as user devices

We will use 2960 switches

Connect devices of the same type with a cross cable
Connect the PC to the switches with a straight cable
Assign an IP address to each PC, for example will be used 192.168.1.0/24 (mask /24=255.255.255.0)
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab0/1.png)
Open any PC, go to the Desktop tab and open IP Configuration
In the window that opens, enter the IP address in the IP Address line, enter the mask in the Subnet Mask field (24 mask is automatically substituted) 
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab0/2.png)
We use the ping command to check the network, for this on any PC you need to go to the Desktop tab and open Command Prompt
Then enter ping (for example, with PC2 ping 192.168.1.10)
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab0/3.png)
For convenience, you can rename the switches on the diagram and in the switch itself
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab0/4.png)
Click on the line with the name of the switch on the diagram and change it to any other (Switch0 -> SW0)
Open the switch and go to the CLI tab
Press Enter
Go to the privileged mode, enter en (from the word enable)
Then go to the configuration mode with the command conf t (config terminal)
Then enter hostname% NAME% (hostname SW0)
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab0/5.png)
