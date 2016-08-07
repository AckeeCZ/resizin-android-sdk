package cz.ackee.imageserver;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Image server instance with variable app id and server url.
 * Provides UrlGenerator for building image url with various modificators
 * like width, height, crop and gravity
 */
public class ImageServer {
    public static final String TAG = ImageServer.class.getName();
    public static final String IMAGE_SERVER_URL = "https://img.ack.ee/";
    private final String appId;

    private String serverUrl;

    public ImageServer(String appid, String serverUrl) {
        this.appId = appid;
        this.serverUrl = serverUrl;
    }

    public ImageServer(String appId) {
        this(appId, IMAGE_SERVER_URL);
    }

    /**
     * @return builder class for creating url
     */
    public ImageServer.UrlGenerator url() {
        return new ImageServer.UrlGenerator(this.appId, this.serverUrl);
    }

    /**
     * Generator of url to image server
     */
    public static class UrlGenerator {
        final String serverUrl;
        int width;
        int height;
        boolean grayscale;
        Crop crop;
        Gravity gravity;
        boolean widthSet;
        boolean heightSet;
        boolean cropSet;
        String appId;
        boolean gravitySet;

        private UrlGenerator(String appId, String serverUrl) {
            this.width = Integer.MIN_VALUE;
            this.height = Integer.MIN_VALUE;
            this.grayscale = false;
            this.crop = Crop.FIT;
            this.gravity = Gravity.CENTER;
            this.appId = appId;
            this.serverUrl = serverUrl;
        }

        /**
         * Specify width of image
         *
         * @param width of image
         * @return UrlGenerator instance
         */
        public ImageServer.UrlGenerator width(int width) {
            this.widthSet = true;
            this.width = width;
            return this;
        }

        /**
         * Specify height of image
         *
         * @param height of image
         * @return UrlGenerator instance
         */
        public ImageServer.UrlGenerator height(int height) {
            this.heightSet = true;
            this.height = height;
            return this;
        }

        /**
         * Specify if grayscale filter should be applied to image
         *
         * @param grayscale switch for grayscale filtrer
         * @return UrlGenerator instance
         */
        public ImageServer.UrlGenerator grayscale(boolean grayscale) {
            this.grayscale = grayscale;
            return this;
        }

        /**
         * Specify crop of image. see {@link Crop} for available
         * options
         *
         * @param crop of image
         * @return UrlGenerator instance
         */
        public ImageServer.UrlGenerator crop(Crop crop) {
            this.cropSet = true;
            this.crop = crop;
            return this;
        }

        /**
         * Specify gravity of cropped image. See {@link Gravity} for
         * available options
         * @param gravity of cropped image
         * @return UrlGenerator instance
         */
        public ImageServer.UrlGenerator gravity(Gravity gravity) {
            this.gravitySet = true;
            this.gravity = gravity;
            return this;
        }

        /**
         * Generates url with specified parameters
         * @param imageId id of image on image server
         * @return built url as String
         */
        public String generate(String imageId) {
            StringBuilder builder = new StringBuilder(this.serverUrl);
            if (!this.serverUrl.endsWith("/")) {
                builder.append("/");
            }

            builder.append(this.appId).append("/");
            builder.append("image").append("/");
            List<String> transformations = new ArrayList<>();
            if (this.widthSet) {
                transformations.add(String.format(Locale.getDefault(), "w_%d", this.width));
            }

            if (this.heightSet) {
                transformations.add(String.format(Locale.getDefault(), "h_%d", this.height));
            }

            if (this.cropSet) {
                transformations.add(String.format(Locale.getDefault(), "c_%s", this.crop));
            }

            if (this.gravitySet) {
                transformations.add(String.format(Locale.getDefault(), "g_%s", this.gravity));
            }

            if (this.grayscale) {
                transformations.add("f_greyscale");
            }

            builder.append(this.join("-", transformations));
            builder.append("/");
            builder.append(imageId);
            return builder.toString();
        }

        private String join(String delimiter, List<String> transformations) {
            StringBuilder builder = new StringBuilder();
            for (String operation : transformations) {
                if (builder.length() > 0) {
                    builder.append(delimiter);
                }
                builder.append(operation);
            }

            return builder.toString();
        }
    }
}
