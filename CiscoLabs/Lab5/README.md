# LAB 5  
Configure dynamic routing using RIP and OSPF
=========================================
### Tasks:  
1) RIP  
2) OSPF  
Static from EDGE towards ISP  
ISP static routing to all networks  

![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/1.png)  

Example of PC network settings  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/2.png) 

First, configure RIP and check the network (since the router chooses the priority OSPF protocol for routing)  

Let's start by setting up R1  
Configure each port by specifying the corresponding IP address and mask  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/3.png)  
Moving on to setting up RIP  
To do this, in the configuration mode, enter the router command rip  
Put it into operation mode 2 with the `version 2` command and declare the network with the `network 172.16.0.0` command  
(since the router is connected to 3 subnets starting at 172.16.*.*, you can declare them with one command)  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/4.png)   
  
Next move on to setting up R2  
Similarly to R1, configure the ports and proceed to configuring RIP  
Also declare `network 172.16.0.0`, and `network 192.168.2.0` for the network behind the router  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/5.png)   

Similarly, configure and declare routes on R3  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/6.png)  

Setting up EDGE  
First, configure the ports by setting the appropriate IP address and mask  
Now set static routes to route traffic towards the Internet  
`ip route 10.10.10.0 255.255.255.0 GigabitEthernet0/0` (specify the port through which static routing will go)  
`ip route 0.0.0.0 0.0.0.0 GigabitEthernet0/0`  
Go to the RIP settings  
Declare `network 172.16.0.0`  
Use the `passive-interface GigabitEthernet0/0` command to disable sending route updates to the Internet  
And with the command `default-information originate` declare static routes that RIP will advertise to the network  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/7.png)  

Set up the latest ISP router  
Configure the ports in the same way, but the routing will be static  
`ip route 172.16.0.0 255.255.255.0 GigabitEthernet0/1`  
`ip route 172.16.1.0 255.255.255.0 GigabitEthernet0/1`  
`ip route 172.16.2.0 255.255.255.0 GigabitEthernet0/1`  
`ip route 172.16.3.0 255.255.255.0 GigabitEthernet0/1`  
`ip route 192.168.1.0 255.255.255.0 GigabitEthernet0/1`  
`ip route 192.168.2.0 255.255.255.0 GigabitEthernet0/1`  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/8.png)  

#### RIP-routed network configured  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/9.png)  
Check the network with ping command from PC1 to PC2  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/10.png)  
Traffic passes  
  
Using the show ip route command, you can see the received routes  
The letter R means that the route was received using the RIP protocol  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/11.png)  
  
Now configure the OSPF protocol  
Let's start with R1  
To do this, enter the `router ospf 1` (1-ospf id) command in the configuration mode  
Set the ID with the `router-id 1.1.1.1` command (if the ID is not set, then the router will choose it by default, depending on the configured network)  
Declare subnets  
`network 172.16.1.0 0.0.0.255 area 0`  
`network 172.16.2.0 0.0.0.255 area 0`  
`network 172.16.3.0 0.0.0.255 area 0`  
(0.0.0.255 - OSPF uses inverse mask when configuring. This mask shows which part (how many bits) of the IP address can change.  
area 0 - (area id) identifier of the area in which the router interface will operate. 
An interface will fall into this zone provided that its IP address matches the network using the network and wildcard. 
For small networks this parameter can be specified equal to 0, but for large networks it is necessary to observe the hierarchical zone design in OSPF. 
All OSPF updates that are sent between different zones must go through zone 0.)  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/12.png)  

Configure R2 and R3 in the same way  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/13.png)  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/14.png)  

No need to configure ISP router, configure EDGE  
The configuration will be similar, but you must also declare the static routes with the command `default-information originate`  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/15.png)  

The OSPF configuration is complete, on any router enter `show ip route`  
As you can see in the screenshot, the routes have been updated with O instead of R, which means that the route was received via OSPF  
(OSPF is the priority protocol, so all routes are changed to OSPF)  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/16.png)  

Check network with ping command from PC1 to PC0 and PC2  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/17.png)  
Ping passes  
#### Routing is configured, everything works  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab5/18.png)  
