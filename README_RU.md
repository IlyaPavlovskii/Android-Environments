# Android Environments build gradle plugin

Плагин предназначен для устранения копирования деклараций переменных 
окружения для различных типов. Он помогает разработчику избавиться от
рутиной работы делкарации билд конфигураций и позволяет это сделать 
автоматически.

### Как установить
TBD
 
### Как использовать

Все что необходимо знать о конфигурации плагина. В `build.gradle` файле
вашего Android проекта, необходимо объявить расширение `environments` и 
подключить настроить компоненты из которых будут читаться Ваши 
конфигурационные файлы и как будет происходить настройка компонентов.

[build.gradle]
```groovy
environments { 
    // Корневая папка с директориями окружения
    // Значение по умолчанию - "config" 
    configPath = "config"  
    // Наименование файла переменных окружения
    // Значение по умолчанию - "config.properties"
    configFile = "config.properties"
    // Использовать переменные окружения для различных build type 
    // версий(debug/release)
    // Значение по умолчанию - false
    useBuildTypes = true 
    // Использовать переменные окружения для различных флейворов
    // Значение по умолчанию - false
    useProductFlavors = true
}
``` 

Далее, Вам необходимо расположить конфигурационные файлы по типу сборок 
и флейворов по нужным директориям.

![Папка с конфигурациями!](img/config.png "Папка с конфигурациями")

На данный момент плагин поддерживает только `.properties` расширение. 
Пример конфигурационного файла.
```properties
key.build.type.value="debug value"
key-int=123
key-LOng=78L
KEY_float=32.1f
key.some.boolean=true
```
Плагин сам поймет тип данных указанных в конфигурационном файле и 
поддержит корректный формат при кодогенерации.

Пример сгенерированного BuildConfig файла.
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
