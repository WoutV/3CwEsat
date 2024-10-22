#create a simulator
set ns [new Simulator]

#trace files: nam and tr
set tf [open out.tr w]
$ns trace-all $tf
set nf [open out.nam w]
$ns namtrace-all $nf

$ns color 0 Blue
$ns color 1 Red

#finish procedure
proc finish {} {
	global ns nf tf
	$ns flush-trace
	close $tf
	close $nf
	#exec xgraph WinFile.dat &
	#xexec xgraph WinFile2.dat &
	exec nam out.nam &
	exit 0
}

#defining the topology
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]
$ns duplex-link $n0 $n1 10Mb 10ms DropTail
$ns duplex-link $n0 $n2 10Mb 10ms DropTail
$ns duplex-link $n0 $n4 10Mb 10ms DropTail
$ns duplex-link $n1 $n3 10Mb 10ms DropTail
$ns duplex-link $n1 $n5 10Mb 10ms DropTail
$ns queue-limit $n0 $n1 20

set numberOfBursts 3
set filesPerBurst 40

#set up the tcp connection
set tcp1 [new Agent/TCP/Reno]
$ns attach-agent $n3 $tcp1
set sink1 [new Agent/TCPSink]
$ns attach-agent $n2 $sink1
$ns connect $tcp1 $sink1
$tcp1 set fid_ 1
$tcp1 set windowd_ 80

#setting up the ftp over tcp connection
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1


#making the generators for filesize and sending times
set rep 1
set rng1 [new RNG]
set rng2 [new RNG]
for {set i 0} {$i < $rep} {incr i} {
	$rng1 next-substream;
	$rng2 next-substream;
}

#Random inter-arrival times of TCP transfer at each source i
set RV [new RandomVariable/Exponential]
$RV set avg_ 0.05
$RV use-rng $rng1

#Random size of files to transmit
set RVSize [new RandomVariable/Pareto]
$RVSize set avg_ 15000
$RVSize set shape_ 1.5
$RVSize use-rng $rng2

#defining the transfers 
for {set i 1} {$i<=$numberOfBursts} { incr i} {
	set t [expr $i * 5]
	for {set j 1} {$j <= $filesPerBurst} { incr j} {
		set tcp($i,$j) [new Agent/TCP/Reno]
		$ns attach-agent $n5 $tcp($i,$j)
		set sink($i,$j) [new Agent/TCPSink]
		$ns attach-agent $n4 $sink($i,$j)
		$ns connect $tcp($i,$j) $sink($i,$j)
		$tcp($i,$j) set fid_ 0
		$tcp($i,$j) set window_ 80

		set ftp($i,$j) [new Application/FTP]
		$ftp($i,$j) attach-agent $tcp($i,$j)
		
		#setting the time and size of the transfers
		set t [expr $t + [$RV value]]
		set Conct($i,$j) $t
		set Size($i,$j) [expr [$RVSize value]]
		$ns at $Conct($i,$j) "$ftp($i,$j) send $Size($i,$j)"				
}}

set winfile [open WinFile.dat w]
set winfile2 [open WinFile2.dat w]

#procedure for plotting window size
proc plotWindow {tcpSource file} {
	global ns
	set time 0.1
	set now [$ns now]
	set cwnd [$tcpSource set cwnd_]
	puts $file "$now $cwnd"
	$ns at [expr $now+$time] "plotWindow $tcpSource $file"
}
$ns at 0.1 "plotWindow $tcp1 $winfile"

proc plotThreshold {tcpSource file} {
	global ns
	set time 0.1
	set now [$ns now]
	set ssthresh [$tcpSource set ssthresh_]
	puts $file "$now $ssthresh"
	$ns at [expr $now + $time] "plotThreshold $tcpSource $file"
}
$ns at 0.1 "plotThreshold $tcp1 $winfile2"

#schedule events
$ns at 0.0 "$ftp1 start"
$ns at 20.0 "$ftp1 stop"
$ns at 20.0 "finish"

$ns run		
