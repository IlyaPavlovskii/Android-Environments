# Android Environments build gradle plugin
[![](https://jitpack.io/v/IlyaPavlovskii/Android-Environments.svg)](https://jitpack.io/#IlyaPavlovskii/Android-Environments)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Android Environments plugin helps us to avoid manual declaration of build 
config fields in each of the build types or product flavors. It's no longer required
to write `buildConfigField` for every single field.

### How to install
Add next fields in your root `build.gradle` file:
```groovy
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath 'com.github.IlyaPavlovskii.Android-Environments:by.bulba.android.environments.gradle.plugin:0.9.0'
    }
}
```

Then setup your application `build.gradle` file:
```groovy
apply plugin: 'by.bulba.android.environments'

android {
    environments {
        useBuildTypes = true
        useProductFlavors = true
    }
}
```

### How to use

Just add `environments` extension in your project `build.gradle` file 
and set it up. This extension provides you a way to define your configuration file
location and build config field rules.
 
[build.gradle]
```groovy
environments { 
    // Environments root directory.
    // Default value - "config" 
    configPath = "config"  
    // Configuration file name.
    // Default value - "config.properties"
    configFile = "config.properties"
    // Set to true when you need to use custom environments on each 
    // build types(debug or release).
    // Default value - false
    useBuildTypes = true 
    // Set to true when you need to use custom environments on each
    // product flavor.
    // Default value - false
    useProductFlavors = true
}
``` 

Then you'll be able to locate all of your configuration files by build types and 
product flavors folders.

![Configuration directory!](img/config.png "Configuration directory")

Plugin supports `.properties` file extension only.
```properties
key.build.type.value="debug value"
key-int=123
key-LOng=78L
KEY_float=32.1f
key.some.boolean=true
```
Plugin is intellegent enough to automatically convert your environment fields to the known types
(String, Integer, Long, Float or Boolean).

```java
public final class BuildConfig {
  //...
  // Fields from the variant
  public static final String KEY_FLAVOR_VALUE = "free_value";
  // Fields from build type: debug
  public static final String KEY_BUILD_TYPE_VALUE = "debug value";
  public static final Float KEY_FLOAT = 32.1f;
  public static final Integer KEY_INT = 123;
  public static final Long KEY_LONG = 78L;
  public static final Boolean KEY_SOME_BOOLEAN = true;
  public static final String TEST_VALUE = "MY_TEST_VALUE";
}
```

## License

    Copyright (C) 2019 Ilya Pavlovskii

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

