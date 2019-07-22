# Android Environments build gradle plugin
[![](https://jitpack.io/v/IlyaPavlovskii/Android-Environments.svg)](https://jitpack.io/#IlyaPavlovskii/Android-Environments)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![CI](https://travis-ci.com/IlyaPavlovskii/Android-Environments.svg?branch=master)](https://travis-ci.com/IlyaPavlovskii/Android-Environments.svg?branch=master)

[![codecov.io](https://codecov.io/github/IlyaPavlovskii/Android-Environments/coverage.svg?branch=master)](https://codecov.io/github/IlyaPavlovskii/Android-Environments?branch=master)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=IlyaPavlovskii_Android-Environments&metric=alert_status)](https://sonarcloud.io/dashboard?id=IlyaPavlovskii_Android-Environments)

[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-Android%20Environments%20plugin-green.svg?style=flat )]( https://android-arsenal.com/details/1/7733 )

[![Patreon](https://img.shields.io/endpoint.svg?url=https%3A%2F%2Fshieldsio-patreon.herokuapp.com%2Fipavlovskii&style=for-the-badge)](https://patreon.com/ipavlovskii)

Плагин предназначен для устранения копирования деклараций переменных 
окружения для различных типов. Он помогает разработчику избавиться от
рутиной работы делкарации билд конфигураций и позволяет это сделать 
автоматически.

## Оглавление
- [Как установить](#Как-установить)
- [Как использовать](#Как-использовать)
- [Сотрудничество](#Сотрудничество)
- [Лицензия](#Лицензия)

### Как установить
Добавьте следующие строки в Ваш корневой `build.gradle` файл:
```groovy
plugins {
  id "android.environments" version "1.0.3"
}
```
Легаси плагин:
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "by.bulba.android.environments:android.environments:1.0.3"
  }
}

apply plugin: "android.environments"
```

Добавьте плагин и его расширение в `build.gradle` файл вашего приложения:
```groovy
apply plugin: 'by.bulba.android.environments'

android {
    environments {
        useBuildTypes = true
        useProductFlavors = true
    }
}
```
 
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
    // Использовать переменные окружения для различных build type 
    // версий(debug/release)
    // Значение по умолчанию - false
    useBuildTypes = true 
    // Использовать переменные окружения для различных флейворов
    // Значение по умолчанию - false
    useProductFlavors = true
    // Формат конфигурационного файла
    // Один на выбор [json,yml,properties - по умолчанию]
    format = "properties"
}
``` 

Далее, Вам необходимо расположить конфигурационные файлы по типу сборок 
и флейворов по нужным директориям. Плагин соберет все файлы с расширением
которое указано в конфигурационном расширении - `format`.

![Папка с конфигурациями!](img/config.png "Папка с конфигурациями")

Плагин поддерживает 3 формата конфигурационных файлов:
 `.json`,`.yml` или `.properties`(используется по умолчанию). 
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

### Сотрудничество

Если Вы нашли какую-либо проблему, либо хотите предложить что-то новое,
либо взять в работу одну из существующих задач, пожалуйста, ознакомтесь 
со следующими правилами участия в проекте.

1. Убедитесь в том, что Ваша задача не была создана [здесь](https://github.com/IlyaPavlovskii/Android-Environments/issues).
2. Примите участие в обсуждении.
3. Создайте новую задачу.

Перед внесением изменений:
1. Возьмите задачу, перенесите ее в статус "В процессе" и назначьте ее исполнителя.
2. Создайте новую ветку по следующим правилам:
```
$тип-задачи/ae-$(номер-задачи)/$номер-итерации
пример:
enhancement/ae-10/0
```
3. После того как работа над задачей закончена, создайте коммит по следующим правилам:
```
ae-$(номер-задачи): $короткое-описание-результата
```
4. Залейте ветку с изменениями.
5. Убедитесь в том, что ваши изменения не сломали рабочую функциональность. 
Сборка CI(travis-ci) должна быть зеленой. 
6. Если синтаксический анализатор кода/тестов(sonarqube, codecov) предупреждает о
созданных проблемах либо недостаточном качестве кода - исправьте это либо опишите 
причины в задаче на github.
7. Создайте pull-request и, по желанию, призовите хотя бы одного участника проекта.

### Лицензия

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

