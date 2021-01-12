# LAB 2
Building a network with subnets
===========================
Let's build a network 192.168.0.0/24 and divide it into subnets  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab2/1.png)
For each subnet we get the following characteristics  
192.168.1.128/25 (255.255.255.128)  
Network: 192.168.1.128  
First available address: 192.168.1.129  
Last available address: 192.168.1.254  
Broadcast address: 192.168.1.255  
Number of hosts: 126  

192.168.2.192/26 (255.2553.255.192)  
Network: 192.168.2.192  
First available address: 192.168.2.193  
Last available address: 192.168.2.254  
Broadcast address: 192.168.2.255  
Number of hosts: 62  

192.168.3.240/28 (255.255.255.240)  
Network: 192.168.3.240  
First available address: 192.168.3.241  
Last available address: 192.168.3.254  
Broadcast address: 192.168.3.255  
Number of hosts: 14  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab2/2.png)

From each subnet, the first available address is assigned to the router, the second, the third to the PC, and the last possible address to the switches  
Set the settings for each PC according to the subnet  
Example:  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab2/3.png)    
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab2/4.png)    

Let's configure each GI port on the router  
Example for gi0/0 port:  
`en`  
`conf t`  
`hostname R0`  
`interface GigabitEthernet0/0`  
`ip address 192.168.1.129 255.255.255.128`  
`no shutdown`  
`exit`  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab2/5.png)  

Configure the default gateway and Vlan1 (assigning an IP address to the switch) on each switch  
Example for switch SW0:  
`en`  
`conf t`  
`hostname SW0`  
`ip default-gateway 192.168.1.129`  
`interface Vlan1`  
`ip address 192.168.1.254 255.255.255.128`  
`exit`  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab2/6.png)  

Check the network with the ping command from any computer  
Ping passes  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab2/7.png)  
Network configured  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab2/8.png)  
