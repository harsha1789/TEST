@echo off
D:
java -Dwebdriver.ie.driver=D:\\ZAF_V1.4\\ZAF\\Project_Jars\\IEDriverServer.exe -jar D:\\ZAF_V1.4\\ZAF\\Project_Jars\\selenium-server-standalone-3.5.2.jar -role node -hub http://localhost:4444/grid/register -port 5300

pause