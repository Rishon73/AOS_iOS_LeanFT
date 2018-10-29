
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
* To modify the Application Models jar files, clone and change this project https://github.com/Rishon73/aos-ios-models, next run  Maven install in the Maven Lifecycle - this will install the Applocation Model jars in your local maven repo

