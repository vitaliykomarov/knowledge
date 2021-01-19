# Lab 3
Building a network with division into VLANs
==========================
### Task:  
Check ping in different VLANs  
192.168.1.0/24  
VLAN 40 - native  
VLAN 99 - black hole  
  
192.168.1.10  
VLAN 10 - SYSTEM  
  
192.168.1.20  
VLAN 20 - STUDENT  
  
192.168.1.30  
VLAN 30 - PROFESSOR  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab3/1.png)  

Configure PCs  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab3/2.png)  

Let's configure VLANs and a trunk on Switch0 for transmitting traffic of several VLAN-s  
In configuration mode, create all the necessary VLANs using the command `vlan %NUM%` and so on, 
when executing the command, you will go to the VLAN settings, in it you can specify the name using the command `name %NAME%`  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab3/3.png)  

Next, proceed to configuring the FastEthernet0/1 interface command `interface fa0/1`  
Then assign Vlan with the command `switchport access vlan 20` and put the port in access mode with the command `switchport mode access`  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab3/4.png)   

Configure the remaining FastEthernet0/2 and FastEthernet0/3 ports with the corresponding VLANs  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab3/5.png)   

Configuring a trunk on the GigabitEthernet0/1 interface  
Switch the interface to trunk mode with the command `switchport mode trunk`  
Let's configure VLAN 40 as native Vlan, the traffic of this Vlan is transmitted untagged, for this enter `switchport trunk native vlan 40`  
By default, all VLANs are allowed in the trunk, 
but they can be limited by specifying only some VLANs that are transmitted through a specific trunk using the command `switchport trunk allowed vlan 10,20,30,99`  
### Switch0 configured  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab3/6.png)   

Configure Switch1 in the same way  
On switches Switch2 and Switch3, two GigabitEthernet interfaces must be configured  
### VLANs configured  

Try to ping the network, for example, from PC5, which is in Vlan 30, ping PC1, since these are different Vlan, then ping will not work   
Now check PC2, ping passes  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab3/7.png)   
### Network configured  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab3/8.png)     
