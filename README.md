[ ![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.ackeecz/resizin-sdk/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.ackeecz/resizin-sdk)

# Resizin.com Android SDK
Library providing URL generator for Ackee's resizin.com image server
## Dependencies
```groovy
compile 'io.github.ackeecz:resizin-sdk:x.x.x'
```
You can find latest version name in lib.properties or in the badge above

## Usages
### Initialization

```kotlin
val appId: String = ...
val resizin = Resizin(appId);
```

### Url generator
Url generator has multiple options you can set. All of them are optional.
Values and meaning of Gravity and Crop options are available at https://github.com/AckeeCZ/resizin-android-sdk#examples
```kotlin
fun loadImage(imageId: String, img: ImageView) {
    val url = resizin.url()
        .width(img.getWidth())
        .height(img.getHeight())
        .gravity(Gravity.CENTER)
        .crop(Crop.SE)
        .grayscale(true)
        .generate(imageId)
    Picasso.with(context).load(url).into(img)
}
```


### Parsing of urls
If you have full url, for example from server, and you want to parse it again to Resizin.UrlGenerator instance, you can do

```kotlin
fun parseUrl(url: String) {
     resizin.url()
            .withUrl(url)
            .background("#ffffff")
            .generate()
}
```

## License
Copyright 2021 Ackee, s.r.o.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.