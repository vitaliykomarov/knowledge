# Lab 8
Configure inter-VLAN routing and configure ACL (Access Control List)
===============================================
For each PC, you must set the following access:

VLAN10  
Ping server only

VLAN20  
It is allowed to connect to the web server only and only by IP

VLAN30  
All is allowed

VLAN40  
Allowed dns, http and ping PC VLAN20

VLAN50  
Ping everything and full access to the server via dns, ftp, http

Ports  
FTP - 20/21  
DNS - 53  
HTTP – 80  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab8/1.png)   
  
##### Set network settings on all PCs
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab8/2.png)

Also set the network settings on the Internet server and configure the dns, ftp, http services  
Network settings are configured in the same way as on a PC  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab8/3.png)  

Go to the Services tab  
Make sure HTTP is enabled  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab8/4.png)  

On the left in the menu, select DNS  
On this tab, enable DNS by selecting ON  
Now, on the same tab, add an A record  
In the Name field, we will indicate, for example, isp.com, in the Address field, we will indicate the IP address of the server and click Add  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab8/5.png)  

Go to the FTP tab  
Check if the service is enabled  
Also, here you can add an account with full access  
To do this, for example, we indicate the name isp and the password isp, put a checkmark against each access and click Add  
##### Server configured  

#### Configure switches and routers  
First, set up the switches  
  
##### SW0:  
`en`  
`conf t`  
`vlan 10`  
`exit`  
`vlan 40`  
`exit`  
`int fa0/1`  
`switchport access vlan 10`  
`switchport mode access`  
`exit`  
`int f0/2`  
`switchport access vlan 40`  
`switchport mode access`  
`exit`  
`int g0/1`  
`switchport trunk allowed vlan 10,40`  
`switchport mode trunk`  
##### SW0 configured
  
##### SW1:  
`en`  
`conf t`  
`vlan 30`  
`exit`  
`vlan 20`  
`exit`  
`int f0/1`  
`switchport access vlan 20`  
`switchport mode access`  
`exit`  
`int f0/2`  
`switchport access vlan 30`  
`switchport mode access`  
`exit`  
`int g0/1`  
`switchport trunk allowed vlan 20,30`  
`switchport mode trunk`  
`exit`  
##### SW1 configured

##### Configuring Layer 3 Switch MS0:  
`en`  
`conf t`  
`vlan 50`  
`exit`  
`vlan 40`  
`exit`  
`vlan 10`  
`exit`  
`int f0/1`  
`switchport access vlan 50`  
`switchport mode access`  
`switchport nonegotiate`   
`exit`  
`int g0/1`  
`switchport trunk encapsulation dot1q`  
`switchport trunk allowed vlan 10,40,50`  
`switchport mode trunk`  
`exit`  
`int g0/2`  
`no sw`  
`no switchport`  
`ip address 192.168.1.2 255.255.255.0`  
`exit`  
  
##### Now set up the ACL  
ACL is configured using the `access-list% NUMBER%` command  
The following numbers can be used instead of `%NUMBER%`:  
`<1-99>     IP standard access list`  
`<100-199>  IP extended access list`  
We will set from the extended ACL, for convenience we will denote in accordance with VLAN  
(for example VLAN10 - 110, VLAN20 - 120, etc.)  
Then set the rule to `allow` or `deny`  
Next, the protocol is selected  
After that, you must specify for whom the rule applies  
`A.B.C.D - source address`  
`any - any host address`  
`host - is the only host source`  
For example, in the case of host, you must specify the IP address  
Further, depending on the requirements, you must indicate what the rule applies to  
`A.B.C.D - recipient address`  
`any - any destination host`  
`host - the only destination host`  
For example, the following command allows PC VLAN10 to ping the server  
`access-list 110 permit icmp host 192.168.10.10 host 10.0.0.10`  
`access-list 110 permit icmp host 192.168.10.10 any echo-reply`  
`echo-reply - allows the PC to respond to pings`  
Disable all other protocols, thereby leaving the VLAN10 PC to ping only with the server and respond to ping  
`access-list 110 deny ip host 192.168.10.10 any`  
Let's continue with ACL settings  
`access-list 140 permit icmp host 192.168.40.40 host 192.168.20.20`  
`access-list 140 permit tcp host 192.168.40.40 host 10.0.0.10 eq www`  
`#www - World Wide Web (HTTP, 80)`  
`access-list 140 permit udp host 192.168.40.40 host 10.0.0.10 eq domain`  
`#domain - Domain Name Service (DNS, 53)`  
`access-list 140 deny ip host 192.168.40.40 any`  
`access-list 150 permit ip host 192.168.50.50 any`  
##### ACL-configured  
In order for the rules to work, they must be applied to the interface, in this case to the virtual interfaces of the corresponding each VLAN
`int Vlan10`  
`ip address 192.168.10.1 255.255.255.0`  
`ip access-group 110 in`  
`exit`  
Using the `ip access-group 110` command, we apply the ACL to this interface and VLAN  
`int Vlan40`  
`ip address 192.168.40.1 255.255.255.0`  
`ip access-group 140 in`  
`exit`  
`int Vlan50`  
`ip address 192.168.50.1 255.255.255.0`  
`ip access-group 150 in`  
`exit`  
##### ACL assigned  
Configure routing protocol  
`ip routing`  
`router rip`  
`version 2`  
`network 192.168.1.0`  
`network 192.168.10.0`  
`network 192.168.40.0`  
`network 192.168.50.0`  
`exit`  
##### MS0-configured  

##### Let's start configuring routers  
##### R0:  
`en`  
`conf t`  
`int g0/0`  
`ip address 192.168.1.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 192.168.2.1 255.255.255.0`  
`no shut`  
`exit`  
`router rip`  
`version 2`  
`network 192.168.1.0`  
`network 192.168.2.0`  
`exit`  
##### R0-configured  

##### R1:  
`en`  
`conf t`  
`int g0/0`  
`ip address 192.168.2.2 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 192.168.3.1 255.255.255.0`  
`no shut`  
`exit`  
`router rip`  
`version 2`  
`network 192.168.2.0`  
`network 192.168.3.0`  
`exit`  
##### R1-configured  
  
##### ISP:  
`en`  
`conf t`  
`int g0/0`  
`ip address 10.10.10.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip address 10.0.0.1 255.255.255.0`  
`no shut`  
`exit`  
`ip route 192.168.20.0 255.255.255.0 GigabitEthernet0/0`  
`ip route 192.168.10.0 255.255.255.0 GigabitEthernet0/0`  
`ip route 192.168.30.0 255.255.255.0 GigabitEthernet0/0`  
`ip route 192.168.40.0 255.255.255.0 GigabitEthernet0/0`  
`ip route 192.168.50.0 255.255.255.0 GigabitEthernet0/0`  
`ip route 192.168.1.0 255.255.255.0 GigabitEthernet0/0`  
`ip route 192.168.2.0 255.255.255.0 GigabitEthernet0/0`  
`ip route 192.168.3.0 255.255.255.0 GigabitEthernet0/0`  
  
##### Let's configure the last EDGE router, you also need to configure the ACL on it  
##### EDGE:  
`en`  
`conf t`  
`int g0/0`  
`ip address 192.168.3.2 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1`  
`ip add`  
`ip address 10.10.10.10 255.255.255.0`  
`no shut`  
`exit`  
`access-list 130 permit ip host 192.168.30.30 any`  
`access-list 120 permit icmp host 192.168.20.20 any echo-reply`  
`access-list 120 permit tcp host 192.168.20.20 host 10.0.0.10 eq www`  
`access-list 120 deny ip host 192.168.20.20 any`  
`access-list 120 deny icmp host 192.168.20.20 any echo`  
`int g0/2.20`  
`encapsulation dot1Q 20`  
`ip address 192.168.20.1 255.255.255.0`  
`ip access-group 120 in`  
`exit`  
`int g0/2.30`  
`encapsulation dot1Q 30`  
`ip address 192.168.30.1 255.255.255.0`  
`ip access-group 130 in`  
`exit`  
`int g0/2`  
`no shut`  
`exit`  
`ip route 0.0.0.0 0.0.0.0 g0/1`  
`router rip`  
`version 2`  
`passive-interface g0/1`  
`default-information originate`  
`network 192.168.3.0`  
`network 192.168.20.0`  
`network 192.168.30.0`  
`exit`  
##### EDGE-configured    
  
##### Network configured, ACL configured  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab8/6.png)  
For example, let's check the ping and connection to the server from the PC VLAN20  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab8/7.png)  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab8/8.png)  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab8/9.png)  
#### ACLs are working correctly  
