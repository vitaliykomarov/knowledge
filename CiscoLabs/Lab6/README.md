# Lab 6
Configure subnets with different routing protocols
========================================
### Tasks:  
OSPF  
RIP  
Static  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab6/1.png)  
  
Configure PC, set IP address, mask and gateway  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab6/2.png)  
  
#### RIP setup  
##### R1:  
`en`  
`conf t`  
`int g0/0`  
`ip address 192.168.1.2 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 192.168.5.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/2`  
`ip address 192.168.2.1 255.255.255.0`  
`no shut`  
`exit`  
`router rip`  
`version 2`  
`network 192.168.1.0`  
`network 192.168.2.0`  
`network 192.168.5.0`  

##### R2:
`int g0/0`  
`ip address 192.168.3.2 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 192.168.5.2 255.255.255.0`  
`no shut`  
`exit`  
`int g0/2`  
`ip address 192.168.4.1 255.255.255.0`  
`no shut`  
`exit`  
`router rip`  
`version 2`  
`network 192.168.3.0`  
`network 192.168.4.0`  
`network 192.168.5.0`  

##### Filial1:
`int g0/0`  
`ip address 11.11.11.11 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 192.168.1.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/2`  
`ip address 192.168.3.1 255.255.255.0`  
`no shut`  
`exit`  
`ip route 0.0.0.0 0.0.0.0 11.11.11.1`  
`router rip`  
`version 2`  
`passive-interface g0/0`  
`network 192.168.1.0`  
`network 192.168.3.0`  
`default-information originate`  
`exit`  
  
#### RIP configured  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab6/3.png)  
   
#### OSPF setup  
##### R3:  
`int g0/0`  
`ip address 172.16.1.2 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 172.16.5.1 255.255.255.0`  
`no shut`  
`int g0/2`  
`ip address 172.16.2.1 255.255.255.0`  
`no shut`  
`exit`  
`router ospf 1`  
`router-id 2.2.2.2`  
`network 172.16.1.0 0.0.0.255 area 0`  
`network 172.16.2.0 0.0.0.255 area 0`  
`network 172.16.5.0 0.0.0.255 area 0`  
`exit`  
  
##### R4:  
`int g0/0`  
`ip address 172.16.3.2 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 172.16.5.1 255.255.255.0`  
`no shut`  
`int g0/2`  
`ip address 172.16.4.1 255.255.255.0`  
`no shut`  
`exit`  
`router ospf 1`  
`router-id 3.3.3.3`  
`network 172.16.3.0 0.0.0.255 area 0`  
`network 172.16.4.0 0.0.0.255 area 0`  
`network 172.16.5.0 0.0.0.255 area 0`  
`exit`  

##### Filial2:  
`int g0/0`  
`ip address 10.10.10.10 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 172.16.1.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/2`  
`ip address 172.16.3.1 255.255.255.0`  
`no shut`  
`exit`  
`ip route 0.0.0.0 0.0.0.0 10.10.10.1`  
`router ospf 1`  
`rout`  
`router-id 1.1.1.1`  
`network 10.10.10.0 0.0.0.255 area 0`  
`network 172.16.1.0 0.0.0.255 area 0`  
`network 172.16.3.0 0.0.0.255 area 0`  
`default-information originate`  

#### OSPF configured  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab6/4.png)   
  
#### Configure static routing  
  
##### ISP1:  
`int g0/0`  
`ip address 12.12.12.12 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 100.100.1.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/2`  
`ip address 100.100.3.1 255.255.255.0`  
`no shut`  
`exit`  
`ip route 0.0.0.0 0.0.0.0 g0/0`  
`ip route 100.100.2.0 255.255.255.0 100.100.1.2`  
`ip route 100.100.4.0 255.255.255.0 100.100.3.2`  
`ip route 100.100.5.0 255.255.255.0 100.100.1.2`  
`ip route 100.100.5.0 255.255.255.0 100.100.3.2`  

##### ISP2:
`int g0/0`  
`ip address 100.100.1.2 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 100.100.5.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/2`  
`ip address 100.100.2.1 255.255.255.0`  
`no shut`  
`exit`  
`ip route 100.100.3.0 255.255.255.0 100.100.1.1`  
`ip route 100.100.3.0 255.255.255.0 100.100.5.2`  
`ip route 100.100.4.0 255.255.255.0 100.100.5.2`  
`ip route 0.0.0.0 0.0.0.0 g0/0`  
  
##### ISP3:  
`int g0/0`  
`ip address 100.100.3.2 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 100.100.5.2 255.255.255.0`  
`no shut`  
`exit`  
`int g0/2`  
`ip address 100.100.4.1 255.255.255.0`  
`no shut`  
`exit`  
`ip route 0.0.0.0 0.0.0.0 g0/0`  
`ip route 100.100.1.0 255.255.255.0 100.100.3.1`  
`ip route 100.100.1.0 255.255.255.0 100.100.5.1`  
`ip route 100.100.2.0 255.255.255.0 100.100.5.1`  
  
##### EDGE:  
`int g0/0`  
`ip address 10.10.10.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 11.11.11.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/2`  
`ip address 12.12.12.1 255.255.255.0`  
`no shut`  
`exit`  
`ip route 100.100.0.0 255.255.0.0 12.12.12.12`  
`ip route 172.16.0.0 255.255.0.0 10.10.10.10`  
`ip route 192.168.0.0 255.255.0.0 11.11.11.11`  
  
#### Static routing configured  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab6/5.png)   
  
#### Network configured  
Ping passes  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab6/6.png)   
