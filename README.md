# PacktWebsiteUITesting
Repo with automated tests for Packt Website
1. Reasoning for your choice of automation software and reporting
   
   I have used Java programming language, Testng as testing framework and Maven as the software project management tool. The reason I have chosen these is because I have experience in working with these tools since my graduation and internship.
I have used surefire reporting method for reporting as it is compatible with Testng


2. Any installation instructions for your software and framework
   Install Maven:
   Download from: https://maven.apache.org/download.cgi
   Installation instruction: https://maven.apache.org/install.html
   Dependencies from Maven:
   Testng: https://mvnrepository.com/artifact/org.testng/testng/7.7.1
   Selenium: https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java/4.11.0
   slf4j-simple: https://mvnrepository.com/artifact/org.slf4j/slf4j-simple/2.0.9
   ReportNG: https://mvnrepository.com/artifact/org.uncommons/reportng/1.1.4
   Maven surefire report: https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-surefire-report-plugin/3.2.2
   These dependencies are added in pom.xml file.

4. Instructions and commands to run your code
   Github Repository Link:  https://github.com/Vaishnavi444/PacktWebsiteUITesting
   Commands to run:
   C:\<Path to workspace>\Selenium\PacktSiteAutomation>mvn test
   
6. Any other information you would like us to know, the more documentation the better
   After running the test, Report can be viewed here
   C:\<Path to workspace>\Selenium\PacktSiteAutomation\target\surefire-reports\emailable-report.html
   Alternative report file:
   C:\<Path to workspace>\Selenium\PacktSiteAutomation\target\surefire-reports\Surefire suite\Surefire test.html
