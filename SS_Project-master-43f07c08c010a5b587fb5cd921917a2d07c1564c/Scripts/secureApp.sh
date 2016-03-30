#!/bin/sh

#set -x
while [ 1 ]
do
	echo `netstat -tn 2>/dev/null | grep :8080 | awk '{print $5}' | cut -d: -f1 | sort | uniq -c | sort -nr | head -n1` | tee t.txt

	number=`cat  t.txt| awk '{print $1}'`
	ipaddr=`cat  t.txt| awk '{print $2}'`
	echo $number
	echo $ipaddr
	if [ "$ipaddr" = "127.0.0.1" ]; then
	 	echo "C"
		sleep 1
		continue
	fi
	if [ $number -gt 5 ]; then
	    	echo "Blocked: $ipaddr"
	    	#iptables -A INPUT -s $ipaddr -j DROP
		#iptables -A OUTPUT -d $ipaddr -j DROP
	fi
	sleep 1
done
