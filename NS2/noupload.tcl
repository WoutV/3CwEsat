#Create simulator
set ns [new Simulator]

$ns color 0 Blue
$ns color 1 Red
$ns color 2 Green

#trace file
set tf [open out.tr w]
$ns trace-all $tf

#nam tracefile
set nf [open out.nam w]
$ns namtrace-all $nf

proc finish {} {
	#finalize trace files
	global ns nf tf
	$ns flush-trace
	close $tf
	close $nf
	
	exec nam out.nam &
	exit 0
}

# create nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]
set n6 [$ns node]
set n7 [$ns node]

#and links
$ns duplex-link $n0 $n2 10Mb 0.2ms DropTail
$ns duplex-link $n1 $n2 10Mb 0.2ms DropTail
$ns duplex-link $n2 $n3 10Mb 0.2ms DropTail
$ns simplex-link $n3 $n4 256kb 0.2ms DropTail
$ns duplex-link $n4 $n3 4Mb 0.2ms DropTail
$ns duplex-link $n4 $n5 100Mb 0.3ms DropTail
$ns duplex-link $n5 $n6 100Mb 0.3ms DropTail
$ns duplex-link $n5 $n7 100Mb 0.3ms DropTail

#ftp connectie download 6 naar 1
set tcp [new Agent/TCP]
$ns attach-agent $n6 $tcp
set sink [new Agent/TCPSink]
$ns attach-agent $n1 $sink
$ns connect $tcp $sink
$tcp set fid_ 1
$tcp set window_ 80

set ftp [new Application/FTP]
$ftp attach-agent $tcp

#UDP upload van 0 naar 7
#set udp0 [new Agent/UDP]
#$ns attach-agent $n0 $udp0
#set cbr0 [new Application/Traffic/CBR]
#$cbr0 attach-agent $udp0
#$cbr0 set fid_ 2
#$udp0 set packetSize_ 1500	

#set null0 [new Agent/Null]
#$ns attach-agent $n7 $null0
#$ns connect $udp0 $null0


#start en stop
$ns at 0.1 "$ftp start"
#$ns at 3.0 "$cbr0 start"
#$ns at 6.0 "$cbr0 stop"
$ns at 9.9 "$ftp stop"
$ns at 10.0 "finish"

$ns run



