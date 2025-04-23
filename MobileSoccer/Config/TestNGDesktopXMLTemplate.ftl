<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >
<suite name="Parallel test suite" parallel="tests" thread-count="10"
	verbose="10">
	<test name="Regression Suite for Device 1">
		<parameter name="Browser" value="Chrome"/>
		<parameter name="username" value="player102" />
		<parameter name="OSplatform" value="Windows" />
		<parameter name="gameName" value="${gameName}"/>
		<classes>
			<class name="Driver.DesktopDriver" />
		</classes>
	</test>
		

 </suite>