# IllumioAssignmenthttps://github.com/ashwini-patil1401/IllumioAssignment/blob/main/README.md
Created this project with IntelliJ and executed the same there with sample logs

Input: 
flowlog.txt
version accno eni src dst srcport dstport protocol packets bytes start end action logstatus
2 123456789010 eni-1235b8ca123456789 172.31.16.139 172.31.16.21 20641 43418 6 20 4249 1418530010 1418530070 ACCEPT OK

lookup.csv
dstport, protocol, tag
68,17,sv_P2

Place these files in this format in Practice/src/main/resources folder

 OUTPUT:

 PORT PROTOCOL COUNT:

 68 17 1
 22 17 1
 43418 6 1
 31 6 1
 443 17 1
 43416 6 2

 TAG COUNT:

 sv_P2 2
 SV_P3 1
 sv_P1 1
 Untagged 3

