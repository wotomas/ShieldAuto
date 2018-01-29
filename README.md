[<img src="media/autoshield.png" width="400" />]() [<img src="media/name.png" width="200" />]()

# ShieldAuto
[![CI status](https://img.shields.io/badge/start%20with-why%3F-brightgreen.svg?style=flat)](https://github.com/wotomas/ShieldAuto) ![Download](https://api.bintray.com/packages/wotomas/maven/ShieldAuto/images/download.svg)

We were tried of fetching proguard config files for every libraries that were needed to be included in release APK. By using [ShieldAuto](https://github.com/wotomas/ShieldAuto/) developers don't have to worry about checking proguard config file to check if setup is correct for each imported libraries.

ShieldAuto is a Java based Gradle plugin to manage proguard config for release build. 

This is still a premature version and any helps are welcome. Test cases, refactoring, optimization, bug fixes and adding any libraries are welcome. Current State diagram is as following: 

# State Diagram
[<img src="media/state_diagram" width="800" />]()

# How to use?
```gradle
// modify project level build.gradle
buildscript {
    repositories {
        ... 
        maven {
            url  "https://dl.bintray.com/wotomas/maven"
        }
    }
    dependencies {
        ...
        classpath 'info.kimjihyok:ShieldAuto:0.0.4'
    }
}
```

```gradle
// add ShieldAuto plugin below application plugin 'com.android.application' plugin
apply plugin: 'info.kimjihyok.ShieldAuto'

android {
    ...  
    buildTypes {
        release {
            // apply sheidlAuto.auto() to proguardFiles configuration
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro', shieldAuto.auto()
        }
    }
```

# Supported Libraries
* [BottomBar](https://github.com/roughike/BottomBar/)
* [Butterknife](http://jakewharton.github.io/butterknife/)
* [Calligraphy](https://github.com/chrisjenx/Calligraphy/)
* [Crashlytics](http://try.crashlytics.com/sdk-android/)
* [Flurry](https://github.com/flurry/flurry-android-sdk/)
* [Fresco](https://github.com/facebook/fresco/)
* [Glide](https://github.com/bumptech/glide/)
* [Gson](https://code.google.com/p/google-gson/)
* [Square OkHttp](http://square.github.io/okhttp/)
* [Square Picasso](https://github.com/square/picasso)
* [Square Retrofit](http://square.github.io/retrofit/)
* [RxJava](https://github.com/ReactiveX/RxJava/wiki/The-RxJava-Android-Module)
* [Stetho](https://github.com/facebook/stetho/)

Request for additional libraries could be submitted through issues. Pull requests are always welcome.

# License
Copyright (C) 2018 Kim Jihyok

See the file copyright/LICENSE.txt.

# Development and Contribution
We are open for any contributions. Please use the issue tickets for communication before sending pull requests.

# Thanks to
<div>Icons made by <a href="https://www.flaticon.com/authors/maxim-basinski" title="Maxim Basinski">Maxim Basinski</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>