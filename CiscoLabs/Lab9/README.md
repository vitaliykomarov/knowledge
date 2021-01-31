# Lab 9
Configure a network with multiple Vlan and set ACL
=======================================
#### ACL parameters:  
Admin - Ping to all, WWW, DNS, FTP  
Sale - DNS, WWW  
Account1-2 - FTP  
Boss - WWW, DNS, FTP  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab9/1.png)  
  
##### Set network settings on PCs and servers  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab9/2.png)  
In the DNS server field, you must specify 192.168.100.100  
  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab9/3.png)  
  
##### Set up a WWW server  
We check that the value "ON" is set on the HTTP tab, and "OFF" on FTP  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab9/4.png)  
  
##### Set up DNS, FTP server  
Disable HTTP, on the DNS tab, create a WWW server record and enable the service, FTP is enabled by default  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab9/5.png)  
  
#### Configure the 1st floor  
##### SW1:  
`en`  
`conf t`  
`vlan 20`  
`exit`  
`vlan 30`  
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
`switchport mode trunk`  
`switchport trunk allowed vlan 20,30`  
`exit`  
##### SW1 – configured  
  
Let's configure the 3rd level switch  
##### Floor1:  
`en`  
`conf t`  
`vlan 10`  
`exit`  
`vlan 20`  
`exit`  
`vlan 30`  
`exit`  
`vlan 60`  
`exit`  
`int f0/1`  
`switchport access vlan 10`  
`switchport mode access`  
`switchport nonegotiate`  
`exit`  
`ip routing`  
`int g0/1`  
`switchport trunk encapsulation dot1q`  
`switchport mode trunk`  
`switchport trunk allowed vlan 10,20,30`  
`exit`  
`int g0/2`  
`no switchport`  
`ip address 192.168.60.10 255.255.255.0`  
`exit`  
`router rip`  
`version 2`  
`network 192.168.10.0`  
`network 192.168.20.0`  
`network 192.168.30.0`  
`network 192.168.60.0`  
`no auto-summary`  
`exit`  
`access-list 110 permit icmp host 192.168.10.10 any`  
`access-list 110 permit udp host 192.168.10.10 host 192.168.100.100 eq domain`  
`access-list 110 permit tcp host 192.168.10.10 any eq www`  
`access-list 110 permit tcp host 192.168.10.10 host 192.168.100.100 eq ftp`  
`access-list 120 permit tcp host 192.168.20.20 host 192.168.100.100 eq ftp`  
`access-list 120 permit icmp host 192.168.20.20 any echo-reply`  
`access-list 120 deny icmp host 192.168.20.20 any echo`  
`access-list 120 deny udp host 192.168.20.20 host 192.168.100.100 eq domain`  
`access-list 120 deny tcp host 192.168.20.20 any eq www`  
`access-list 130 permit icmp host 192.168.30.30 any echo-reply`  
`access-list 130 permit udp host 192.168.30.30 host 192.168.100.100 eq domain`  
`access-list 130 permit tcp host 192.168.30.30 any eq www`  
`access-list 130 deny tcp host 192.168.30.30 host 192.168.100.100 eq ftp`  
`access-list 130 deny icmp host 192.168.30.30 any echo`  
`int Vlan10`  
`ip address 192.168.10.1 255.255.255.0`  
`ip access-group 110 in`  
`exit`  
`int Vlan20`  
`ip address 192.168.20.1 255.255.255.0`  
`ip access-group 120 in`  
`exit`  
`int Vlan30`  
`ip address 192.168.30.1 255.255.255.0`  
`ip access-group 130 in`  
`exit`  
##### Floor1 – configured  
  
#### Configure the equipment on the 2nd floor  
##### SW2:  
`en`  
`conf t`  
`vlan 40`  
`exit`  
`vlan 50`  
`exit`  
`int f0/1`  
`switchport access vlan 40`  
`switchport mode access`  
`exit`  
`int f0/2`  
`switchport access vlan 50`  
`switchport mode access`  
`exit`  
`int g0/1`  
`switchport trunk allowed vlan 40,50`  
`switchport mode trunk`  
`no shut`  
`exit`  
##### SW2 – configured  

##### Floor2:  
`en`  
`conf t`  
`int g0/1.60`  
`encapsulation dot1Q 60`  
`ip address 192.168.60.20 255.255.255.0`  
`exit`  
`int g0/1`  
`no shut`  
`exit`  
`access-list 140 permit tcp host 192.168.40.40 host 192.168.100.100 eq ftp`  
`access-list 140 permit icmp host 192.168.40.40 any echo-reply`  
`access-list 140 deny icmp host 192.168.40.40 any echo`  
`access-list 140 deny udp host 192.168.40.40 host 192.168.100.100 eq domain`  
`access-list 140 deny tcp host 192.168.40.40 any eq www`  
`access-list 150 permit icmp host 192.168.50.50 any echo-reply`  
`access-list 150 permit tcp host 192.168.50.50 host 192.168.100.100 eq ftp`  
`access-list 150 permit udp host 192.168.50.50 host 192.168.100.100 eq domain`  
`access-list 150 permit tcp host 192.168.50.50 any eq www`  
`access-list 150 deny icmp host 192.168.50.50 any echo`  
`int g0/0.40`  
`encapsulation dot1Q 40`  
`ip address 192.168.40.1 255.255.255.0`  
`ip access-group 140 in`  
`exit`  
`int g0/0.50`  
`encapsulation dot1Q 50`  
`ip address 192.168.50.1 255.255.255.0`  
`ip access-group 150 in`  
`exit`  
`int g0/0`  
`no shut`  
`exit`  
`router rip`  
`version 2`  
`network 192.168.40.0`  
`network 192.168.50.0`  
`network 192.168.60.0`  
`no auto-summary`  
`exit`  
##### Floor2 – configured  
  
##### Set up Main:  
`en`  
`conf t`  
`vlan 60`  
`exit`  
`vlan 70`  
`exit`  
`int f0/1`  
`switchport access vlan 60`  
`switchport trunk allowed vlan 60`  
`switchport mode trunk`  
`exit`  
`int f0/2`  
`switchport access vlan 70`  
`switchport mode trunk`  
`switchport trunk allowed vlan 70`  
`exit`  
`int f0/3`  
`switchport mode trunk`  
`switchport access vlan 60`  
`switchport trunk allowed vlan 60,70`  
`exit`  
`int g0/1`  
`switchport access vlan 60`  
`switchport trunk allowed vlan 60`  
`switchport mode access`  
`exit`  
##### Main – configured
  
##### Edge:  
`en`  
`conf t`  
`int g0/0`  
`ip add`  
`ip address 192.168.100.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1.60`  
`encapsulation dot1Q 60`  
`ip address 192.168.60.1 255.255.255.0`  
`exit`  
`int g0/1.70`  
`encapsulation dot1Q 70`  
`ip address 10.10.70.70 255.255.255.0`  
`exit`  
`int g0/1`  
`no shut`  
`exit`  
`ip route 0.0.0.0 0.0.0.0 g0/1.70`  
`router rip`  
`ver 2`  
`passive-interface g0/1.70`  
`network 192.168.60.0`  
`network 192.168.100.0`  
`default-information originate`  
`no auto-summary`  
`exit`  
##### Edge – configured  
  
##### Configure the latest router  
##### ISP:  
`en`  
`conf t`  
`int g0/0`  
`ip address 10.10.10.1 255.255.255.0`  
`no shut`  
`exit`  
`int g0/1.70`  
`encapsulation dot1Q 70`  
`ip address 10.10.70.1 255.255.255.0`  
`exit`  
`int g0/1`  
`no shut`  
`ip route 192.168.10.0 255.255.255.0 GigabitEthernet0/1.70`  
`ip route 192.168.20.0 255.255.255.0 GigabitEthernet0/1.70`  
`ip route 192.168.30.0 255.255.255.0 GigabitEthernet0/1.70`  
`ip route 192.168.40.0 255.255.255.0 GigabitEthernet0/1.70`  
`ip route 192.168.50.0 255.255.255.0 GigabitEthernet0/1.70`  
`ip route 192.168.60.0 255.255.255.0 GigabitEthernet0/1.70`   
`ip route 192.168.100.0 255.255.255.0 GigabitEthernet0/1.70`  
##### ISP – configured  

#### Network configured, ACL configured  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab9/6.png)  

For example, a ping from the Admin passes, from Account2 is prohibited, as well as a visit to the site, on the PC Sale the site opens  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab9/7.png)  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab9/8.png)  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab9/9.png)  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab9/10.png)  
![Image](https://github.com/vitaliykomarov/knowledge/blob/main/CiscoLabs/Lab9/11.png)  
