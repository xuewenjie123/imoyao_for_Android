# 配网 

基于 Esptouch 开发，支持 Smart Config 、 Airkiss 配网

Esptouch v0.3.4.6 only support Espressif's Smart Config v2.4

权限

	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	<uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE" />
	<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
	<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
	

gradle集成方式

Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
  
Step 2. Add the dependency

	dependencies {
    		implementation 'com.github.xuyazhong:imoyao_for_Android:1.0.0'
	}


maven集成方式
Step 1
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://www.jitpack.io</url>
		</repository>
	</repositories>
Step 2. Add the dependency

	<dependency>
	    <groupId>com.github.xuyazhong</groupId>
	    <artifactId>imoyao_for_Android</artifactId>
	    <version>1.0.0</version>
	</dependency>
