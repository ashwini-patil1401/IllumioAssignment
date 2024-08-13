# IllumioAssignment
Created this project with IntelliJ and executed the same there with sample logs

Input: 
flowlog.txt
version vpc-id subnet-id instance-id interface-id srcaddr dstaddr srcport dstport protocol
3 vpc-abcdefab012345678 subnet-aaaaaaaa012345678 i-01234567890123456 eni-1235b8ca123456789 10.0.0.62 52.213.180.42 5001 43418 udp

lookup.csv
dstport, protocol, tag
23,tcp,sv_P1
31,udp,SV_P3

Place these files in this format in Practice/src/main/resources folder

Output:
The output files will be generated in Practice folder

portProtocolCount.txt
68 udp 1
43418 udp 2
5001 tcp 2
5001 udp 1
43416 tcp 2


tagCount.txt
sv_P2 1
sv_P1 2
sv_P4 2
Untagged 3
