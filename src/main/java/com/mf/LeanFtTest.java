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
    private static AppModelAOS_iOS appModel;
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
        utils.setInstallApp(false);
        utils.setUninstallApp(false);
        utils.setHighlight(true);
        utils.setAppIdentifier("com.Advantage.iShopping");
        utils.setAppVersion("1.1.4");
        utils.setPackaged(true);

        noProblem = true;

        try {
            DeviceDescription deviceDescription = new DeviceDescription();

            deviceDescription.setOsType("IOS");
            deviceDescription.setOsVersion(">=9.0.0");

            utils.lockDevice(deviceDescription, MobileLabUtils.LabType.MC);
            //utils.lockDeviceById("8a05bbf719c5a6840177ad62b88674ee53893590");

            device = utils.getDevice();
            if (device != null) {
                appModel = new AppModelAOS_iOS(device);
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
                appModel.AdvantageShoppingApplication().SelectedLaptop4().highlight();
            appModel.AdvantageShoppingApplication().SelectedLaptop4().tap();

            Logging.logMessage ("Tap 'Add to Cart' button", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().ADDTOCARTButton().highlight();
            appModel.AdvantageShoppingApplication().ADDTOCARTButton().tap();
            utils.windowSync(1500);

            Logging.logMessage ("Tap the back button", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().BackButton().highlight();
            appModel.AdvantageShoppingApplication().BackButton().tap();

            Logging.logMessage ("Tap 'Open Menu'", Logging.LOG_LEVEL.INFO);
            openMenu();

            Logging.logMessage ("Tap 'Open Cart'", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().OpenCart().highlight();
            appModel.AdvantageShoppingApplication().OpenCart().tap();

            Logging.logMessage ("Tap the checkout button", Logging.LOG_LEVEL.INFO);
            if (utils.isHighlight())
                appModel.AdvantageShoppingApplication().CHECKOUTPAYButton().highlight();
            appModel.AdvantageShoppingApplication().CHECKOUTPAYButton().tap();

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
            appModel.AdvantageShoppingApplication().YesButton().highlight();
        appModel.AdvantageShoppingApplication().YesButton().tap();
    }

    private void signIn() throws GeneralLeanFtException {
        Logging.logMessage ("Tap login label", Logging.LOG_LEVEL.INFO);
        if (utils.isHighlight())
            appModel.AdvantageShoppingApplication().LOGINLabel().highlight();
        appModel.AdvantageShoppingApplication().LOGINLabel().tap();

        Logging.logMessage ("Type name", Logging.LOG_LEVEL.INFO);
        if (utils.isHighlight())
            appModel.AdvantageShoppingApplication().USERNAMEEditField().highlight();
        appModel.AdvantageShoppingApplication().USERNAMEEditField().setText(userName);

        Logging.logMessage ("Type password", Logging.LOG_LEVEL.INFO);
        if (utils.isHighlight())
            appModel.AdvantageShoppingApplication().PASSWORDEditField().highlight();
        appModel.AdvantageShoppingApplication().PASSWORDEditField().setSecure(userPassword);

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
