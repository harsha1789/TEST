@echo off
D:
java -Dwebdriver.chrome.driver=D:\ZAF\Project_Jars\chromedriver.exe -jar D:\ZAF\Project_Jars\selenium-server-standalone-3.5.2.jar -role node -hub http://localhost:4444/grid/register -port 5304

pause