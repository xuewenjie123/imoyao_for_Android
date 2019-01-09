# imoyao_for_Android

gradle

Add it in your root build.gradle at the end of repositories:

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


maven

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
