# CSIS 3810 Operating Systems
## Design Programming Project Assignment 3

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A communication function in an operating system must decide on the destination for the
retrieval of an object in a communication at any given time. The decision involves picking
one of the possible destinations based on the best bandwidth availability. Thus, each
decision thread will attempt to look up a destination in the Bandwidth Cache that contains
up to ten destination entries that each are composed of a tuple of address, and bandwidth
value.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;There are 10 decision threads in the system all competing for access to the Bandwidth
Cache each will query the cache with one of 100 possible destination addresses (for the
purpose of this assignment each thread will randomly choose a number between 1 and
100 on each query).

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The decision thread will query the Bandwidth Cache for the current destination; if the
destination is in the cache the thread will read the bandwidth and return to their decision
process. If the destination is not in the Bandwidth Cache the thread will perform a
destination bandwidth query that consists of the thread selecting a random value between
1 and 10 and simulating the time it took to “ping” the destination. When the thread has
calculated the current bandwidth, it must update the Bandwidth Cache. If the destination is
in the Bandwidth Cache it must be replaced, if there is no room in the Bandwidth Cache
then an entry must be evicted and replaced with current destination bandwidth calculation
information. After the Bandwidth Cache has been updated by the current thread the
destination is then used by that decision thread to continue with its processing.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Provide a design and Java solution with threads and semaphore(s) to coordinate the
decision threads and their interaction with the Bandwidth Cache. The decision threads will
periodically have a communication decision to make, thus you will create a driver that will
periodically prompt decision threads to begin a destination decision process. 