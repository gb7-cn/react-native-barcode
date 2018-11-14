package com.reactnativecomponent.barcode;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RCTCapturePackage implements ReactPackage {
    RCTCaptureModule mModuleInstance;
    RCTCaptureManager captureManager;

    public RCTCapturePackage() {
        captureManager = new RCTCaptureManager();
    }


    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        mModuleInstance = new RCTCaptureModule(reactApplicationContext, captureManager);
        return Arrays.<NativeModule>asList(
                mModuleInstance
        );
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
        return Arrays.<ViewManager>asList(captureManager);
    }

}
