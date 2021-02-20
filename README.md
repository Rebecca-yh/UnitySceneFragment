## 运行demo

Android Studio打开 AndroidProject运行即可



## unity导入Android说明

### 新建项目

首先新建一个unity项目和一个Android项目，对应本demo的UnityScene和AndroidProject

### Unity Scene

#### 将unity配置为Android

file --> buildingSettings

选择Android，点击switch platform。

#### 构建项目

勾选export project，building，新建UnityScene文件夹保存，导出后文件结构大致如下



![截屏2021-02-15 下午6.11.52](https://tva1.sinaimg.cn/large/008eGmZEgy1gnogbst7x4j30co0g8jsi.jpg)

#### 引入到Android项目中

以下文件放到Android 项目中对应位置（我仅导出arm-v7）

![截屏2021-02-15 下午6.35.25](https://tva1.sinaimg.cn/large/008eGmZEgy1gnogbrzc22j30fw0lmq4s.jpg)

unity-classes.jar, armeabi-v7a放到Android project的app/libs目录下；assets文件夹放到app/src/main文件夹下，与java同级。Android Studio文件结构如下

![截屏2021-02-15 下午6.40.50](https://tva1.sinaimg.cn/large/008eGmZEgy1gnogbscq1sj30hu0mgwfy.jpg)

gradle中添加（与buildTypes同级）

```
sourceSets {
    main {
        //unity3D
        jniLibs.srcDirs = ['libs', 'libs-sdk']
    }
}
```

defaultConfig中添加

```
        ndk {
            abiFilters "armeabi-v7a"
        }
```

在local.properties中配置ndk（替换你自己的ndk）

```
ndk.dir=.../Android/sdk/ndk/xxxx
```

Android架构中应当看到

![截屏2021-02-15 下午7.17.41](https://tva1.sinaimg.cn/large/008eGmZEgy1gnogbrf9r3j30d2096mxv.jpg)

### Android Project

#### 创建Fragment

创建UnityScene.java

```java
package com.example.androidproject.unity;

import com.unity3d.player.UnityPlayer;

public class UnityScene {
    public static UnityPlayer mUnityPlayer;
    public UnityScene(){

    }

}

```



创建一个空的Fragment，添加如下代码

```java
 private View playerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        playerView = UnityScene.mUnityPlayer.getView();
        //具体参数 跟自己公司Unity开发人员协商
        //第一个参数是unity那边的挂载脚本名字
        //第二个参数是 unity提供的方法名
        //第三个参数是 自己要给unity传的值
//        UnityScene.mUnityPlayer.UnitySendMessage("Main Camera","Id","1");

        return playerView;

    }
```





#### 改写MainActivity

在MainAcivity的onCreate中添加

```java
UnityScene.mUnityPlayer = new UnityPlayer(getApplicationContext());
getWindow().setFormat(PixelFormat.RGBX_8888);

if (savedInstanceState == null) {
    getSupportFragmentManager().beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragment_container_view, UnityFragment.class, null)
            .commit();
}
```

MainAcivity重写其他方法

```java
@Override
protected void onDestroy() {
    UnityScene.mUnityPlayer.quit();
    super.onDestroy();
}
@Override
protected void onPause() {
    super.onPause();
    UnityScene.mUnityPlayer.pause();
}


@Override
protected void onResume() {
    super.onResume();
    UnityScene.mUnityPlayer.resume();
}


@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    UnityScene.mUnityPlayer.configurationChanged(newConfig);
}


@Override
public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    UnityScene.mUnityPlayer.windowFocusChanged(hasFocus);
}


@Override
public boolean dispatchKeyEvent(KeyEvent event) {
    if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
        return UnityScene.mUnityPlayer.injectEvent(event);
    return super.dispatchKeyEvent(event);
}


@Override
public boolean onKeyUp(int keyCode, KeyEvent event) {
    return UnityScene.mUnityPlayer.injectEvent(event);
}


@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    return UnityScene.mUnityPlayer.injectEvent(event);
}
@Override
public boolean onTouchEvent(MotionEvent event) {
    return UnityScene.mUnityPlayer.injectEvent(event);
}


public boolean onGenericMotionEvent(MotionEvent event) {
    return UnityScene.mUnityPlayer.injectEvent(event);
}
```



gradle中增加

```
    implementation "androidx.fragment:fragment:1.2.1"
```



activity_main.xml如下

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.fragment.app.FragmentContainerView
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/fragment_container_view"
        android:layout_width="match_parent"
        android:layout_height="500dp"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```



string.xml中添加

```xml
<string name="game_view_content_description">Game View</string>
```

## 完成

运行项目即可看到unity场景嵌套在fragment中

![Screenshot_20210215-192853](https://tva1.sinaimg.cn/large/008eGmZEgy1gnogbq75dij30u01t00v0.jpg)

