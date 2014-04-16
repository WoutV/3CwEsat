#Create simulator
set ns [new Simulator]

$ns color 0 Blue
$ns color 1 Red

#trace file
set tf [open oef.out.tr w]
$ns trace-all $tf

#nam tracefile
set nf [open oef.out.nam w]
$ns namtrace-all $nf

proc finish {} {
	#finalize trace files
	global ns nf tf
	$ns flush-trace
	close $tf
	close $nf
	
	exec nam oef.out.nam &
	exit 0
}

# create nodes
set n0 [$ns node]
set n1 [$ns node]

#and links
$ns duplex-link $n0 $n1 10Mb 10ms DropTail

$ns at 10.0 "finish"

$ns run
