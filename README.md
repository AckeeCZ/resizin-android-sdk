# Resizin.com Android SDK
Library providing URL generator for Ackee's resizin.com image server
## Dependencies
```groovy
compile 'cz.ackee.resizin:resizin-android-sdk:x.x.x'
```
You can find latest version name in lib.properties

## Usages
### Initialization

```java
String appId = ...
Resizin resizin = new Resizin(appId);
```

### Url generator
Url generator has multiple options you can set. All of them are optional.
Values and meaning of Gravity and Crop options are available at https://github.com/AckeeCZ/resizin-android-sdk#examples
```java
public void loadImage(String imageId, ImageView img) {
    String url = resizin.urlGenerator()
        .width(img.getWidth())
        .height(img.getHeight())
        .gravity(Gravity.CENTER)
        .crop(Crop.SE)
        .grayscale(true)
        .generate(imageId);
    Picasso.with(context).load(url).into(img)
}
```


### Parsing of urls
If you have full url, for example from server, and you want to parse it again to Resizin.UrlGenerator instance, you can do

```java
public void parseUrl(String url) {
     resizin.urlGenerator()
            .withUrl(url)
            .background("#ffffff")
            .generate();
}
```

