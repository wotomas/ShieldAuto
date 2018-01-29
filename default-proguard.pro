#### AUTO-GENERATED PROGUARD RULE FOR stetho START ####
-keep class com.facebook.stetho.** { *; }
-dontwarn com.facebook.stetho.**
#### AUTO-GENERATED PROGUARD RULE FOR stetho END   ####


#### AUTO-GENERATED PROGUARD RULE FOR retrofit START ####
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
#### AUTO-GENERATED PROGUARD RULE FOR retrofit END   ####


#### AUTO-GENERATED PROGUARD RULE FOR rxjava START ####
# Rxjava-promises

-keep class com.darylteo.rx.** { *; }

-dontwarn com.darylteo.rx.**
#### AUTO-GENERATED PROGUARD RULE FOR rxjava END   ####


