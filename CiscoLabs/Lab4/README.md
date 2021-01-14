# LAB 1  
Setting up a branch and office network with routing through ISP routers
=========================
### Tasks:  
1) Ping towards the branch through  
Main provider (ISP1)  

2) To the routes of the branch and office by default  
Provider statics  

3) Configure backup ISP (backup routes ISP2 and off1)  
Static response to 2  

![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab4/1.png)  

#### Configure IP and PC-Internet  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab4/2.png)  

#### Let's configure Router0  
For the GigabitEthernet0/0 port, set the `ip address 192.168.2.1 255.255.255.0`  
Port GigabitEthernet0/1 `ip address 192.168.1.1 255.255.255.0`  
Immediately configure the standard route with the `ip route command 0.0.0.0 0.0.0.0 192.168.2.2` - this command means forwarding any traffic via IP address 192.168.2.2  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab4/3.png)  

#### Now let's configure the Branch Router  
Set an appropriate address for each port  
g0/2 192.168.2.2/24  
g0/0 10.0.2.2/24  
g0/1 10.10.1.2/24  
Configure static routes to the provider using commands  
`ip route 0.0.0.0 0.0.0.0 10.0.2.1 100`  
`ip route 0.0.0.0 0.0.0.0 10.10.1.1 200`  
The numbers at the end of lines 100 and 200 mean the cost of the path, the lower the number, the more preferable the route.  
In the direction of the 192.168.1.0 network, write the route command  
`ip route 192.168.1.0 255.255.255.0 192.168.2.1`  
This will allow you to send packets to the desired network as the routers are on different subnets and don't transfer back to ISP routers  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab4/4.png) 

#### Set up office router  
Set an appropriate address for each port  
g0/2 172.16.0.1/24  
g0/0 10.0.1.2/24  
g0/1 10.10.2.2/24  
Configure static routes to the provider using commands  
`ip route 0.0.0.0 0.0.0.0 10.0.1.1 100`  
`ip route 0.0.0.0 0.0.0.0 10.10.2.1 200`  
There is no need to add a route to network 172.16.0.0 as this router is already on this subnet and knows about it  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab4/5.png) 

#### Let's start configuring ISP1 and ISP2  
On ISP1 router, set the following parameters for each port  
g0/0 192.168.10.1/24  
g0/1 10.0.1.1/24  
g0/2 10.0.2.1/24  
Now you need to add routes to each subnet through the corresponding router  
`ip route 192.168.1.0 255.255.255.0 10.0.2.2`  
`ip route 172.16.0.0 255.255.255.0 10.0.1.2`  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab4/6.png)  

Configure ISP2 in the same way  
g0/0 192.168.10.2/24  
g0/1 10.10.2.1/24  
g0/2 10.10.1.1/24  
`ip route 192.168.1.0 255.255.255.0 10.10.1.2`  
`ip route 172.16.0.0 255.255.255.0 10.10.2.2`  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab4/7.png) 
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab4/8.png) 
Network configured, check ping from PC2 to PC0
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab4/9.png) 
Make sure the ping goes through ISP1, you can switch to simulate mode and retry the ping so you can track packages
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab4/10.png) 
Now turn off ISP1 by unplugging the cables and recheck the ping through simulation.
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab4/11.png) 
### Routing configured



#### Listing  
##### Router0  
`en`  
`conf t`  
`interface g0/0`  
`ip address 192.168.2.1 255.255.255.0`  
`no shutdown`  
`exit`  
`interface g0/1`  
`ip address 192.168.1.1 255.255.255.0`  
`no shutdown`  
`exit`  
`ip route 0.0.0.0 0.0.0.0 192.168.2.2`  
  
##### Filial  
`en`  
`conf t`  
`interface g0/0`  
`ip address 10.0.2.2 255.255.255.0`  
`no shutdown`  
`exit`  
`interface g0/1`  
`ip address 10.10.1.2 255.255.255.0`  
`no shutdown`  
`exit`  
`interface g0/2`  
`ip address 192.168.2.2 255.255.255.0`  
`no shutdown`  
`exit`  
`ip route 0.0.0.0 0.0.0.0 10.0.2.1 100`  
`ip route 0.0.0.0 0.0.0.0 10.10.1.1 200`  
`ip route 192.168.1.0 255.255.255.0 192.168.2.1`  
  
##### Office
`en`  
`conf t`  
`interface g0/0`  
`ip address 10.0.1.2 255.255.255.0`  
`no shutdown`  
`exit`  
`interface g0/1`  
`ip address 10.10.2.2 255.255.255.0`  
`no shutdown`  
`exit`  
`interface g0/2`  
`ip address 172.16.0.1 255.255.255.0`  
`no shutdown`  
`exit`  
`ip route 0.0.0.0 0.0.0.0 10.0.1.1 100`  
`ip route 0.0.0.0 0.0.0.0 10.10.2.1 200`  
  
##### ISP1  
`en`  
`conf t`  
`interface g0/0`  
`ip address 192.168.10.1 255.255.255.0`  
`no shutdown`  
`exit`  
`interface g0/1`  
`ip address 10.0.1.1 255.255.255.0`  
`no shutdown`  
`exit`  
`interface g0/2`  
`ip address 10.0.2.1 255.255.255.0`  
`no shutdown`  
`exit`  
`ip route 192.168.1.0 255.255.255.0 10.0.2.2`  
`ip route 172.16.0.0 255.255.255.0 10.0.1.2`  
  
##### ISP2  
`en`  
`conf t`  
`interface g0/0`  
`ip address 192.168.10.2 255.255.255.0`  
`no shutdown`  
`exit`  
`interface g0/1`  
`ip address 10.10.2.1 255.255.255.0`  
`no shutdown`  
`exit`  
`interface g0/2`  
`ip address 10.10.1.1 255.255.255.0`  
`no shutdown`  
`exit`  
`ip route 192.168.1.0 255.255.255.0 10.10.1.2`  
`ip route 172.16.0.0 255.255.255.0 10.10.2.2`  
