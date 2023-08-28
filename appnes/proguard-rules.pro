# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/hzy/software/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepclasseswithmembers class site.ken.framework.gamedata.dao.entity.GameEntity{*;}
-keepclasseswithmembers class site.ken.framework.ui.gamegallery.ZipRomFile{*;}
-keepclassmembers class * extends site.ken.framework.base.JniEmulator{public ** getInstance();}

-keep class site.ken.framework.ui** { *; }
-keep class site.ken.framework** { *; }
-keep class site.ken.framework.gamedata** { *; }
-keepclassmembers class site.ken.framework.ui** { *; }
-keepclassmembers class site.ken.framework.gamedata** { *; }
-keepclassmembers class site.ken.framework** { *; }

-dontwarn site.ken.framework.**

