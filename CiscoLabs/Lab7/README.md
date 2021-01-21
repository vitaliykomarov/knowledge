# Lab 7
Configure routing on routers with several VLANs and configure a Layer 3 switch
=====================================
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab7/1.png)  
First, set the network settings on all PCs  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab7/2.png)  
  
#### Configuring Layer 2 Switches  
  
##### Switch0:  
Use the `vlan %NUMBER%` command to create VLANs on this switch  
Switch the Fa0 / 1-2 ports to access mode using the `switchport mode access` command  
Assign the corresponding VLAN to each port with the `switchport access vlan %NUM%` command  
Configure ports g0/1-2  
Assign a corresponding VLAN to each port, put it into access mode and enable the transmission of a specific VLAN command `switchport access vlan %NUM%`  
First switch configured  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab7/3.png)  
  
##### Switch1:  
Configuring in the same way, but with some changes  
`vlan 30`  
`exit`  
`vlan 40`  
`exit`  
`int f0/1`  
`switch`  
`switchport access vlan 30`  
`switchport mode access`  
`exit`  
`int f0/2`  
`switchport mode access`  
`switchport access vlan 40`
`exit`  
`int g0/1`  
`switchport mode trunk`  
`switchport trunk allowed vlan 10,20,30,40,50,60`  
`exit`  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab7/4.png)  
  
##### Switch2:  
`vlan 50`  
`exit`  
`vlan 60`  
`exit`  
`int f0/1`  
`switchport access vlan 50`  
`switchport mode access`  
`exit`  
`int f0/2`  
`switchport mode access`  
`switchport access vlan 60`  
`exit`  
`int g0/1`
`switchport mode trunk`  
`switchport trunk allowed vlan 50,60`  
`exit`  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab7/5.png)  
  
#### Now let's start configuring routers  
##### Router1  
Since this router is directly connected to each subnet, each port must be configured accordingly  
`int g0/0`  
`ip address 192.168.2.20 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 192.168.10.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/2`  
`ip address 192.168.20.1 255.255.255.0`  
`no shut`  
`exit`  
Configure the dynamic routing protocol RIP  
`router rip`  
`version 2`  
`network 192.168.2.0`  
`network 192.168.10.0`  
`network 192.168.20.0`  
`exit`  
##### Router1 configured  

##### Router0  
Configure ports g0 / 0-1 as usual  
`int g0/0`  
`ip address 192.168.1.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 192.168.2.1 255.255.255.0`  
`no shut`  
`exit`  
Since two VLANs are connected via port g0/2, it will be necessary to configure the subinterface  
To do this, enter `int g0/2.30` for VLAN30  
But before setting the IP address, you must enter the `encapsulation dot1Q 30` command - which is used to tag traffic, to transmit information about VLAN membership  
`int g0/2.30`  
`encapsulation dot1Q 30`  
`ip address 192.168.30.1 255.255.255.0`  
`exit`  
`int g0/2.40`  
`encapsulation dot1Q 40`  
`ip address 192.168.40.1 255.255.255.0`  
`exit`  
To enable port g0/2, you must enter `no shutdown` after entering the port configuration mode with the int g0/2 command  
Also configure dynamic routing protocol RIP  
`router rip`  
`version 2`  
`network 192.168.1.0`  
`network 192.168.2.0`  
`network 192.168.30.0`  
`network 192.168.40.0`  
##### Router0 configuration done  
  
##### Configuring a Layer 3 Multilayer Switch 0-3560  
Create VLANs  
`en`  
`conf t`  
`vlan 50`  
`exit`  
`vlan 60`  
`exit`  
Fa0/1 port setting  
`int fa0/1`  
`switchport access vlan 50`  
`switchport mode access`  
`switchport nonegotiate` - use this command to disable DTP frames, this mode is used to prevent conflicts with other non-cisco equipment  
`exit`  
You cannot configure sub-interfaces on this switch, 
but it is possible to configure virtual interfaces of the 3rd level for each VLAN for communication between networks  
`interface vlan50`  
`ip address 192.168.50.1 255.255.255.0`  
`exit`  
`interface vlan60`  
`ip address 192.168.60.1 255.255.255.0`  
`exit`  
Let's configure port g0/1  
`int g0/1`  
`switchport trunk encapsulation dot1q`  
`switchport mode trunk`  
`switchport trunk allowed vlan 50,60`  
`exit`  
The g0/2 setting is slightly different as by default the switch port ports operate at Layer 2,
and it needs to be switched to level 3, after which the IP address can be configured  
Use the `no switchport` command to switch the port operation mode  
`int g0/2`  
`no switchport`  
`ip address 192.168.1.10 255.255.255.0`  
`exit`  
Now let's configure the dynamic routing protocol RIP  
But to configure routing functions, you must first enter `ip routing`  
`ip routing`  
`router rip`  
`version 2`  
`network 192.168.1.0`  
`network 192.168.50.0`  
`network 192.168.60.0`  
`exit`  
##### Layer 3 switch configured  
  
Check the network, the ping passes  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab7/6.png)  
#### Network configured  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab7/7.png)  
