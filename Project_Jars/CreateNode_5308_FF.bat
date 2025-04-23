@echo off
D:
java -Dwebdriver.gecko.driver=D:\\ZAF_V1.4\\ZAF\Project_Jars\\geckodriver.exe -jar D:\\ZAF_V1.4\\ZAF\\Project_Jars\\selenium-server-standalone-3.5.2.jar -role node -hub http://localhost:4444/grid/register -port 5308

pause