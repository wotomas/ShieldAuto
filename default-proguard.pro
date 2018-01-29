#### AUTO-GENERATED PROGUARD RULE FOR stetho START ####
-keep class com.facebook.stetho.** { *; }
-dontwarn com.facebook.stetho.**
#### AUTO-GENERATED PROGUARD RULE FOR stetho END   ####


#### AUTO-GENERATED PROGUARD RULE FOR gson START ####
## GSON 2.2.4 specific rules ##

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keepattributes EnclosingMethod

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
#### AUTO-GENERATED PROGUARD RULE FOR gson END   ####


#### AUTO-GENERATED PROGUARD RULE FOR retrofit START ####
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8

-dontwarn okio.**
-dontwarn javax.annotation.**
#### AUTO-GENERATED PROGUARD RULE FOR retrofit END   ####


#### AUTO-GENERATED PROGUARD RULE FOR rxjava START ####
# Rxjava-promises

-keep class com.darylteo.rx.** { *; }

-dontwarn com.darylteo.rx.**
#### AUTO-GENERATED PROGUARD RULE FOR rxjava END   ####


#### AUTO-GENERATED PROGUARD RULE FOR bottom-bar START ####
-dontwarn com.roughike.bottombar.**
#### AUTO-GENERATED PROGUARD RULE FOR bottom-bar END   ####


#### AUTO-GENERATED PROGUARD RULE FOR picasso START ####
## Square Picasso specific rules ##
## https://square.github.io/picasso/ ##
-dontwarn com.squareup.okhttp.**

# Checks for OkHttp versions on the classpath to determine Downloader to use.
-dontnote com.squareup.picasso.Utils
# Downloader used only when OkHttp 2.x is present on the classpath.
-dontwarn com.squareup.picasso.OkHttpDownloader
# Downloader used only when OkHttp 3.x is present on the classpath.
-dontwarn com.squareup.picasso.OkHttp3Downloader
#### AUTO-GENERATED PROGUARD RULE FOR picasso END   ####


