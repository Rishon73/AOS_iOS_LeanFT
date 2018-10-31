
## AOS_iOS_UFTPro

LeanFT Java script with TestNG framework.

For parallel executions use the @Test annotaion:

@Test (threadPoolSize = 3, invocationCount = 1) invocationCount dictates the number of devices to run the script.

---------------

* **LeanFT 14.50**

* **MC 2.80**

* **AOS for iOS v1.1.4 and 1.1.5**

---------------

It uses Advantage Online Shopping iOS app to purchase an item.

Note that you may need to change the user name and password. The scrip uses the setSecure method to encrypt the password. Use the tool from the LeanFT > Tools menu in your IDE or from the Windows Start menu.

Important Notes:
---------------
* Please refer to the https://github.com/Rishon73/MCUtils README.md for MCUtils.jar installation instruction
* This project includes 2 Application Models to match the 2 versions of AOS app (1.1.4 and 1.1.5). To install the jar files to your Maven repository and use the correct one, follow these steps:
  * Open a Terminal and execute these commands:
    * mvn install:install-file -Dfile="<PATH_TO_PROJECT>/jarFiles/iOSAppModel-1.1.4.jar" -DpomFile="<PATH_TO_PROJECT>/jarFiles/iOSAppModel-1.1.4-pom.xml" 
    * mvn install:install-file -Dfile="<PATH_TO_PROJECT>/jarFiles/iOSAppModel-1.1.5.jar" -DpomFile="<PATH_TO_PROJECT>/jarFiles/iOSAppModel-1.1.5-pom.xml" 
  * In the IDE, inside the Maven Projects view, you should see 3 profiles: AppModel114, AppModel1141 and AppModel115. Selecting one of them will automatically set the version app you're testing and the correlating Application Model to match that version
  
  
To modify the Application Models jar files, clone this project https://github.com/Rishon73/aos-ios-models, do the needed modifications and run  **Maven install** in the Maven Lifecycle - this will install the Applocation Model jars in your local maven repo

