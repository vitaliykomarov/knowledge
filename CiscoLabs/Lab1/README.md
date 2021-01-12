# LAB 1
Building a network with a router
===========================
Use the 2911 as a router (router)  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab1/1.png)  

Connect PC to switches, switches to router with straight cable  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab1/2.png)  

Allocation of multiple subnets  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab1/3.png)  

Each PC is assigned an IP address and default gateway, for example 192.168.1.1 for the 192.168.1.0/24 subnet.  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab1/4.png)
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab1/5.png)  

By default, all PCs connected to the switches are logged into Vlan1  
Let's configure Vlan on each switch with the corresponding subnet  
Go to privileged mode, enter `en` (from the word enable)  
Then go to configuration mode with the command `conf t` (config terminal)  
Type `interface Vlan1` to go to the Vlan configuration   
Assign an IP address and subnet mask using the `ip address 192.168.1.10 255.255.255.0` command.   
Exit Vlan configuration with `exit` command   
Configure the default gateway using the `ip default-gateway 192.168.1.1` command 
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab1/6.png)
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab1/7.png)  

Configure the router, for each port write our own subnet   
Go to privileged mode, enter `en` (from the word enable)   
Then go to configuration mode with the command `conf t` (config terminal)   
Enter the `interface GigabitEthernet0/0` to configure the GigabitEthernet0 / 0 port   
Assign the corresponding IP address and IP mask `ip address 192.168.1.1 255.255.255.0`  
To enable the port enter `no shutdown`  
This must be done for each port.   
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab1/8.png)
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab1/9.png)  

Check the network with the ping command from any computer, for example, with PC0 check PC5   
Ping passes   
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab1/10.png)  

Network configured



### Listing  
For each switch  
`en`  
`conf t`  
`hostname %NAME%`  
`interface Vlan1`  
`ip address 192.168.*.* 255.255.255.0`  
`exit`  
`ip default-gateway 192.168.1.1`  
  
For each port (GigabitEthernet0/0,1,2) of the router   
`en`  
`conf t`  
`hostname %NAME%`  
`interface GigabitEthernet0/*`  
`ip address 192.168.*.1 255.255.255.0`  
`no shutdown`  
`exit`  
