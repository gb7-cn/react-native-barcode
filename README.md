# react-native-barcode

A smart barcode scanner component for React Native app.
The library uses [https://github.com/zxing/zxing][1] to decode the barcodes for android, and also supports ios.

## Preview

![react-native-barcode-preview-ios][2]

## Installation

```
npm install https://github.com/Gou-Bo/react-native-barcode.git --save
```

## IOS端集成：

1.将\node_modules\react-native-barcode\ios\RCTBarcode\RCTBarCode.xcodeproj 添加到Xcode的Libraries中

2.在Build Phases->Link Binary With Libraries 加入RCTBarCode.xcodeproj\Products\libRCTBarCode.a

3.查看Build Settings->Seach Paths->Header Search Paths是否有$(SRCROOT)/../../../react-native/React并设为recursive

4.将\node_modules\react-native-barcode\ios\raw 文件夹拖到到Xcode项目中（确保文件夹是蓝色的）

![react-native-barcode-install-ios][4]

5.在info.plist添加相机权限 Privacy - Camera Usage Description：

![react-native-barcode-install-ios][5]


## android端集成：

* 在`android/settings.gradle`文件中：

```
...
include ':react-native-barcode'
project(':react-native-barcode').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-barcode/android')
```

* 在`android/app/build.gradle`文件中：

```
...
dependencies {
    ...
    // 在这里添加
    compile project(':react-native-barcode')
}
```

*  在MainApplication.java文件中：

```
...
import com.reactnativecomponent.barcode.RCTCapturePackage;    //这是要添加的

//将原来的代码注释掉，换成这句
private ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    //  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
   ...........
    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
              new MainReactPackage(),
              //添加以下代码
              new RCTCapturePackage()
      );
    }
  };
  //添加以下代码
  public void setReactNativeHost(ReactNativeHost reactNativeHost) {
    mReactNativeHost = reactNativeHost;
  }
 
  @Override
   public ReactNativeHost getReactNativeHost() {
     return mReactNativeHost;
   }
...
```

* 在AndroidManifest.xml文件中添加相机权限:
```
...
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.VIBRATE"/>

<uses-feature android:name="android.hardware.camera"/>
<uses-feature android:name="android.hardware.camera.autofocus"/>
...
```


## 使用

```js


import React, {Component} from 'react';
import {Alert, Dimensions, StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import Barcode from 'react-native-barcode'

let {width: deviceWidth} = Dimensions.get('window');

type Props = {};
export default class App extends Component<Props> {

    //构造方法
    constructor(props) {
        super(props);
        this.state = {
            viewAppear: false,
        };
    }

    componentDidMount() {
        //启动定时器
        this.timer = setTimeout(
            () => this.setState({viewAppear: true}),
            250
        );
    }

    //组件销毁生命周期
    componentWillUnmount() {
        //清楚定时器
        this.timer && clearTimeout(this.timer);
    }

    _onBarCodeRead = (e) => {
        // console.log(`e.nativeEvent.data.type = ${e.nativeEvent.data.type}, e.nativeEvent.data.code = ${e.nativeEvent.data.code}`)
        this._stopScan();
        Alert.alert("二维码", e.nativeEvent.data.code, [
            {text: '确认', onPress: () => this._startScan()},
        ])
    };

    _startScan = (e) => {
        this._barCode.startScan()
    };

    _stopScan = (e) => {
        this._barCode.stopScan()
    }

    _openFlash = (e) => {
        this._barCode.openFlash()
    }

    _closeFlash = (e) => {
        this._barCode.closeFlash()
    }

    render() {
        return (
            <View style={{flex: 1}}>
                {this.state.viewAppear ?
                    <Barcode style={{flex: 1,}}
                             ref={component => this._barCode = component}
                             onBarCodeRead={this._onBarCodeRead}/>
                    : null
                }
                <TouchableOpacity style={styles.inputBtn}
                                  onPress={this._openFlash.bind(this)}>
                    <Text style={styles.inputBtnText}>打开闪光灯</Text>
                </TouchableOpacity>
                <TouchableOpacity style={[styles.inputBtn, {top: 150}]}
                                  onPress={this._closeFlash.bind(this)}>
                    <Text style={styles.inputBtnText}>关闭闪光灯</Text>
                </TouchableOpacity>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    inputBtn: {
        width: deviceWidth,
        position: "absolute",
        top: 100,
        justifyContent: 'center',
        alignItems: 'center'
    },
    inputBtnText: {
        color: '#fff',
        fontSize: 18,
        textDecorationLine: 'underline'
    }
});

```

## Props

Prop                   | Type   | Optional | Default   | Description
---------------------- | ------ | -------- | --------- | -----------
barCodeTypes           | array  | Yes      |           | determines the supported barcodeTypes
scannerRectWidth       | number | Yes      | 255       | determines the width of scannerRect
scannerRectHeight      | number | Yes      | 255       | determines the height of scannerRect
scannerRectTop         | number | Yes      | 0         | determines the top shift of scannerRect
scannerRectLeft        | number | Yes      | 0         | determines the left shift of scannerRect
scannerLineInterval    | number | Yes      | 3000      | determines the interval of scannerLine's movement
scannerRectCornerColor | string | Yes      | `#09BB0D` | determines the color of scannerRectCorner

[1]: https://github.com/zxing/zxing
[2]: http://cyqresig.github.io/img/react-native-smart-barcode-preview-ios-v1.0.0.gif
[4]: https://upload-images.jianshu.io/upload_images/7262870-829064e584b20295.png
[5]: https://upload-images.jianshu.io/upload_images/7262870-bc6223fdf4d461e8.jpg