@echo off

cd "C:\Users\Jakobs BANGER PC\OneDrive\Studium\WI\BAM\00_Semester\01_SS_24\Business Integration\11_Aufgaben\SS24_BAM1_BusInt\bank1\src\main\java"
javac de\jakob_kroemer\bank1\Bank1Application.java
start cmd /k "java -cp . de.jakob_kroemer.bank1.Bank1Application"

cd "C:\Users\Jakobs BANGER PC\OneDrive\Studium\WI\BAM\00_Semester\01_SS_24\Business Integration\11_Aufgaben\SS24_BAM1_BusInt\creditbureau\src\main\java"
javac de\jakob_kroemer\creditbureau\CreditbureauApplication.java
start cmd /k "java -cp . de.jakob_kroemer.creditbureau.CreditbureauApplication"

cd "C:\Users\Jakobs BANGER PC\OneDrive\Studium\WI\BAM\00_Semester\01_SS_24\Business Integration\11_Aufgaben\SS24_BAM1_BusInt\loan-broker\src\main\java"
javac de\jakob_kroemer\LoanBrokerApplication.java
