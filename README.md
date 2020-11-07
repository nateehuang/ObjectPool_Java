Computing in Finance HomeWork SCHEDULER
------------------------------

I. Basic Info
------------------------------
Author: Nate Huang
Email: nhuang@nyu.edu
Date: 12/5/2019
Version: 1.0
package edu.nyu.compfin19.homework3


II. File List
------------------------------
(Interfaces)
ObjectPool.java			PointerableObject.java
Scheduler.java			SchedulerTask.java

(Major Classes in the src directory)
ObjectPoolImpl.java 	SchedulerContainer.java
SchedulerImpl.java            	   

(Tests in the junit directory)
MockitoTest.java        SchedulerContainerTest.java

III. Design
-----------------------------
ObjectPoolImpl is a recycle system to reuse the memory and avoid calling too much garbage collector

SchedulerContainer has four fields: 
	_previous connects the container with a previous container
	_next connects the container with a next container
	_time records the time to run the event
	_runnable records the code to run
Note: the container is designed to connect in increasing order in the _time field
For the methods in SchedulerContainer, please see the documentation in java

SchedulerImpl has three fields:
	_objectPool is a recycle system implemented in ObjectPoolImpl class
	_head records the event with the smallest time
	_last records the event with the largest time
For the methods in SchedulerContainer, please see the documentation in java

SchedulerContainerTest is a junit test on SchedulerContainer

MockitoTest is a test on ObjectPoolImpl and SchedulerImpl based on Mockito framework

IV. How to run
-----------------------------
Create package edu.nyu.compfin19.homework3 in the src directory
Copy all the interfaces and major classes into the edu.nyu.compfin19.homework3 package

Create package edu.nyu.compfin19.homework3 in the junit directory
Copy the Tests Classes to the package edu.nyu.compfin19.homework3 
