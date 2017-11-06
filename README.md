# Image Server Android Library
Library providing Url generator for Ackee Image Server
## Dependencies
```groovy
compile 'cz.ackee:imageserver:1.2.4'
```

## Usages
### Initialization

```java
String appId = ...
ImageServer imageServer = new ImageServer(appId);
```
or with custom image server url
```java
String appId = Constants.IMG_SERVER_APP_ID;
String url = Constants.IMG_SERVER_URL;
ImageServer imageServer = new ImageServer(appId, url);
```

### Url generator
Url generator has multiple options you can set. All of them are optional.
Values and meaning of Gravity and Crop options are available at https://gitlab.ack.ee/Ackee/image-server#examples
```java
public void loadImage(String imageId, ImageView img) {
    String url = imageServer.urlGenerator()
        .width(img.getWidth())
        .height(img.getHeight())
        .gravity(Gravity.CENTER)
        .crop(Crop.SE)
        .grayscale(true)
        .generate(imageId);
    Picasso.with(context).load(url).into(img)
}
```

