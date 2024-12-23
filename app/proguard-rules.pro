# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep JUnit-related classes
-dontwarn org.junit.**
-dontwarn org.apiguardian.**
-dontwarn junit.**

# Keep Test classes
-keep class * extends junit.framework.TestCase
-keep class * extends androidx.test.ext.junit.runners.AndroidJUnit4

# Keep all test classes
-keep class * {
    @org.junit.Test *;
}

# JNA-related rules
-dontwarn com.sun.jna.**
-keep class com.sun.jna.** { *; }

# Windows-specific JNA rules
-dontwarn com.sun.jna.platform.win32.**
-keep class com.sun.jna.platform.win32.** { *; }

# ByteBuddy rules
-dontwarn net.bytebuddy.**
-keep class net.bytebuddy.** { *; }

# Java Instrumentation rules
-dontwarn java.lang.instrument.**
-keep class java.lang.instrument.** { *; }

# FindBugs annotations
-dontwarn edu.umd.cs.findbugs.annotations.**
-keep class edu.umd.cs.findbugs.annotations.** { *; }

# SLF4J logging
-dontwarn org.slf4j.**
-keep class org.slf4j.** { *; }

# MockK rules
-dontwarn io.mockk.**
-keep class io.mockk.** { *; }

# Keep any classes using specific annotations
-keep @edu.umd.cs.findbugs.annotations.SuppressFBWarnings class * { *; }
-keep class * {
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings *;
}