package com.mf;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.hp.lft.sdk.*;
import com.hp.lft.sdk.mobile.*;
import com.mf.utils.*;

import unittesting.*;

public class LeanFtTest extends UnitTestClassBase {

    private boolean noProblem;
    private Device device;
    private static appModel appModel;
    private MobileLabUtils utils = new MobileLabUtils();
    private String userName = "Shahar";
    private String userPassword  = "460d4691b2f164b933e1476fa1";

    @BeforeClass
    public void beforeClass() throws Exception {
    }

    @AfterClass
    public void afterClass() throws Exception {
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        Logging.logMessage("Enter setUp() method ", Logging.LOG_LEVEL.INFO );
        //utils.setAppIdentifier("com.Advantage.iShopping");
        utils.setAppIdentifier("com.mf.iShopping");
        utils.setAppVersion("1.1.5");
        utils.setPackaged(true);
        utils.setInstallApp(true);
        utils.setUninstallApp(false);
        utils.setHighlight(true);

        String appVersion = System.getProperty("appVersion");
        if (appVersion != null) utils.setAppVersion(appVersion);

        String appIdentifier = System.getProperty("appIdentifier");
        if (appIdentifier != null) utils.setAppIdentifier(appIdentifier);

        String appModelClass = System.getProperty("appModelClass");
        if (appModelClass == null) appModelClass = "AOS_iOS_115";

        noProblem = true;

        try {
            DeviceDescription deviceDescription = new DeviceDescription();

            deviceDescription.setOsType("IOS");
            deviceDescription.setOsVersion(">=11.3.0");
            deviceDescription.setName("iPhone 8");

            //utils.lockDevice(deviceDescription, MobileLabUtils.LabType.MC);
            utils.lockDeviceById("ed2ff5276810f2265b87cb2d58acc7b9246aa5c4", MobileLabUtils.LabType.MC);

            device = utils.getDevice();
            if (device != null) {
                appModel = new appModel(device);
                utils.setApp();

                Logging.logMessage ("Allocated device: \"" + device.getName() + "\" (" + device.getId() + "), Model :"
                        + device.getModel() + ", OS: " + device.getOSType() + " version: " + device.getOSVersion()
                        + ", manufacturer: " + device.getManufacturer() + ". App in use: \"" + utils.getApp().getName()
                        + "\" v" + utils.getApp().getVersion(),Logging. LOG_LEVEL.INFO);

                if (utils.isInstallApp()) {
                    Logging.logMessage ("Installing app: " + utils.getApp().getName(), Logging.LOG_LEVEL.INFO);
                    utils.getApp().install();
                } else {
                    Logging.logMessage ("Restarting app: " + utils.getApp().getName(), Logging.LOG_LEVEL.INFO);
                    utils.getApp().restart();
                }
            } else {
                Logging.logMessage ("Device couldn't be allocated, exiting script", Logging.LOG_LEVEL.ERROR);
                noProblem = false;
            }
        } catch (Exception ex) {
            Logging.logMessage ("Exception in setup(): " + ex.getMessage(), Logging.LOG_LEVEL.ERROR);
            noProblem = false;
        }
    }

    @AfterMethod
    public void afterMethod() throws Exception {
    }

    @Test //(threadPoolSize = 10, invocationCount = 2)
    public void test() throws GeneralLeanFtException, InterruptedException {
        if (!noProblem) {
            Assert.fail();
            return;
        }

        try {
            if (utils.isInstallApp() && appModel.HomeApplication().AllowButton().exists(5)) {
                if (utils.isHighlight())
                    appModel.HomeApplication().AllowButton().highlight();
                appModel.HomeApplication().AllowButton().tap();
            }

            Logging.logMessage ("Tap 'Open Menu'", Logging.LOG_LEVEL.INFO);
            openMenu();

            Logging.logMessage ("Check if the user signed in", Logging.LOG_LEVEL.INFO);
            if (appModel.AdvantageShoppingApplication().SIGNOUTLabel().exists(5)) {
                signOut();
                utils.windowSync(2000);

                Logging.logMessage ("Tap 'Open Menu (after sign-out)'", Logging.LOG_LEVEL.INFO);
                openMenu();
                utils.windowSync(2000);
            }

            signIn();

            Logging.logMessage ("Select 'laptop' category", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().LAPTOPSLabel().highlight();
            appModel.AdvantageShoppingApplication().LAPTOPSLabel().tap();

            Logging.logMessage ("Select a laptop", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().SelectedLaptop().highlight();
            appModel.AdvantageShoppingApplication().SelectedItem().tap();

            Logging.logMessage ("Tap 'Add to Cart' button", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().ADDTOCARTButton().highlight();
            appModel.AdvantageShoppingApplication().ADDTOCARTButton().tap();
            utils.windowSync(1500);

            /*Logging.logMessage ("Tap the back button", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().BackButton().highlight();
            appModel.AdvantageShoppingApplication().BackButton().tap();

            Logging.logMessage ("Tap 'Open Menu'", Logging.LOG_LEVEL.INFO);
            openMenu();

            Logging.logMessage ("Tap 'Open Cart'", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().OpenCart().highlight();
            appModel.AdvantageShoppingApplication().OpenCart().tap();*/

            Logging.logMessage("Navigate to cart", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().cartIconButton().highlight();
            appModel.AdvantageShoppingApplication().cartIconButton().tap();

            Logging.logMessage ("Tap the checkout button", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().CHECKOUTButton().highlight();
            appModel.AdvantageShoppingApplication().CHECKOUTButton().tap();

            Logging.logMessage ("Tap the pay now button", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().PAYNOWButton().highlight();
            appModel.AdvantageShoppingApplication().PAYNOWButton().tap();

            Logging.logMessage ("Tap OK", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().OkButton().highlight();
            appModel.AdvantageShoppingApplication().OkButton().tap();

            openMenu();
            signOut();

            Logging.logMessage ("********** Test completed successfully **********", Logging.LOG_LEVEL.INFO);

        } catch (ReplayObjectNotFoundException ronfex) {
            Logging.logMessage ("error code: " + ronfex.getErrorCode() + " - " + ronfex.getMessage(), Logging.LOG_LEVEL.ERROR);
            Assert.fail();
        }
    }

    private void signOut() throws GeneralLeanFtException {
        if (utils.isHighlight())
            appModel.AdvantageShoppingApplication().SIGNOUTLabel().highlight();
        appModel.AdvantageShoppingApplication().SIGNOUTLabel().tap();

        if (utils.isHighlight())
            appModel.AdvantageShoppingApplication().SignOutYesButton().highlight();
        appModel.AdvantageShoppingApplication().SignOutYesButton().tap();
    }

    private void signIn() throws GeneralLeanFtException {
        Logging.logMessage ("Tap login label", Logging.LOG_LEVEL.INFO);
        if (utils.isHighlight())
            appModel.AdvantageShoppingApplication().LOGINLabel().highlight();
        appModel.AdvantageShoppingApplication().LOGINLabel().tap();

        Logging.logMessage ("Type name", Logging.LOG_LEVEL.INFO);
        if (utils.isHighlight())
            appModel.AdvantageShoppingApplication().UserNameField().highlight();
        appModel.AdvantageShoppingApplication().UserNameField().setText(userName);

        Logging.logMessage ("Type password", Logging.LOG_LEVEL.INFO);
        if (utils.isHighlight())
            appModel.AdvantageShoppingApplication().PasswordField().highlight();
        appModel.AdvantageShoppingApplication().PasswordField().setSecure(userPassword);

        Logging.logMessage ("Tap login button", Logging.LOG_LEVEL.INFO);
        if (utils.isHighlight())
            appModel.AdvantageShoppingApplication().LOGINButton().highlight();
        appModel.AdvantageShoppingApplication().LOGINButton().tap();
    }

    private void openMenu() throws GeneralLeanFtException{
        Logging.logMessage("Open menu", Logging.LOG_LEVEL.INFO);
        if (utils.isHighlight())
            appModel.AdvantageShoppingApplication().MenuButton().highlight();
        appModel.AdvantageShoppingApplication().MenuButton().tap();
    }
}
