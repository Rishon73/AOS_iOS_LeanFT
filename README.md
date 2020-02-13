# uftdev-aos-mobile-ios

LeanFT Java project with TestNG framework. Using the following:
* **LeanFT 14.50**

* **MC 2.80**

* **AOS for iOS v1.1.4 and 1.1.5**

---------------
## Notes:
It uses Advantage Online Shopping iOS app to purchase an item.

Note that you may need to change the user name and password. The scrip uses the setSecure method to encrypt the password. Use the tool from the LeanFT > Tools menu in your IDE or from the Windows Start menu.


## Dependencies:

### MobileUtilities
MobileUtilities is a framework with basic mobile lab funtions. It works with Mobile Center Lab and StormRunner Functional Mobile Lab
* Please refer to the https://github.com/Rishon73/mobileutilities for additional information. Installation instructions are documented in README.md file
* This project includes the jar and pom files to install to your local Maven repository:
  * Open a Terminal and execute this command:
    * _mvn install:install-file -Dfile="<PATH_TO_PROJECT>/jarFiles/MobileUtilities-1.3-14.50.0.jar" -DpomFile="<PATH_TO_PROJECT>/jarFiles/MobileUtilities-1.3-14.50.0-pom.xml"_

### Application Models

* This project includes 2 Application Models to match the 2 versions of AOS app (1.1.4 and 1.1.5). To install the jar files to your Maven repository and use the correct one, follow these steps:
  * Open a Terminal and execute these commands:
    * _mvn install:install-file -Dfile="<PATH_TO_PROJECT>/jarFiles/iOSAppModel-1.1.4.jar" -DpomFile="<PATH_TO_PROJECT>/jarFiles/iOSAppModel-1.1.4-pom.xml"_
    * _mvn install:install-file -Dfile="<PATH_TO_PROJECT>/jarFiles/iOSAppModel-1.1.5.jar" -DpomFile="<PATH_TO_PROJECT>/jarFiles/iOSAppModel-1.1.5-pom.xml"_
  * In the IDE, inside the Maven Projects view, you should see 3 profiles: AppModel114, AppModel1141 and AppModel115. Selecting one of them will automatically set the version app you're testing and the correlating Application Model to match that version
  
  
To modify the Application Models jar files, clone this project https://github.com/Rishon73/aos-ios-models, do the needed modifications and run  **Maven install** in the Maven Lifecycle - this will install the Applocation Model jars in your local maven repo



##### Prior to UFT renaming in 2019 was called Mobile AOS Test-iOS
