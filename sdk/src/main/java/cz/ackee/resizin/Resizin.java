package cz.ackee.resizin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Resizin.com server instance with variable app id.
 * Provides UrlGenerator for building image url with various modificators
 * like width, height, crop and gravity
 */
public class Resizin {

    public static final String TAG = Resizin.class.getName();
    public static final String IMAGE_SERVER_URL = "https://img.resizin.com/";
    private final String appId;

    /**
     * @param appId id of your app at the Resizin server
     */
    public Resizin(String appId) {
        this.appId = appId;
    }

    /**
     * @return builder class for creating url
     */
    public Resizin.UrlGenerator url() {
        return new Resizin.UrlGenerator(this.appId, IMAGE_SERVER_URL);
    }

    /**
     * Parse given url and parse it to instance
     *
     * @param url URL of existing image
     * @return builder class for creating url
     */
    public Resizin.UrlGenerator withUrl(String url) {
        return new Resizin.UrlGenerator(this.appId, IMAGE_SERVER_URL).parseUrl(url);
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
        int quality;
        String extension;
        int upscale = -1;
        int borderTop = -1;
        int borderLeft = -1;
        int borderRight = -1;
        int borderBottom = -1;
        String background;
        String imageId;

        UrlGenerator(String appId, String serverUrl) {
            this.width = Integer.MIN_VALUE;
            this.height = Integer.MIN_VALUE;
            this.grayscale = false;
            this.crop = Crop.FIT;
            this.gravity = Gravity.CENTER;
            this.appId = appId;
            this.serverUrl = serverUrl;
            this.quality = -1;
            this.extension = "";
            imageId = null;
        }

        /**
         * Specify width of image
         *
         * @param width of image
         * @return UrlGenerator instance
         */
        public Resizin.UrlGenerator width(int width) {
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
        public Resizin.UrlGenerator height(int height) {
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
        public Resizin.UrlGenerator grayscale(boolean grayscale) {
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
        public Resizin.UrlGenerator crop(Crop crop) {
            this.cropSet = true;
            this.crop = crop;
            return this;
        }

        /**
         * Specify gravity of cropped image. See {@link Gravity} for
         * available options
         *
         * @param gravity of cropped image
         * @return UrlGenerator instance
         */
        public Resizin.UrlGenerator gravity(Gravity gravity) {
            this.gravitySet = true;
            this.gravity = gravity;
            return this;
        }

        /**
         * Specify quality of image in percent
         *
         * @param quality percentual quality of image, values 0 - 100
         * @return UrlGenerator instance
         */
        public Resizin.UrlGenerator quality(int quality) {
            this.quality = quality;
            return this;
        }

        /**
         * Specify extension of image
         *
         * @param extension extension eg. jpg
         * @return UrlGenerator instance
         */
        public Resizin.UrlGenerator extension(String extension) {
            this.extension = extension;
            return this;
        }

        /**
         * Indicator if image should be upscaled if size is larger than original dimensions
         *
         * @param upscale boolean indicator
         * @return UrlGenerator instance
         */
        public Resizin.UrlGenerator upscale(boolean upscale) {
            this.upscale = upscale ? 1 : 0;
            return this;
        }

        /**
         * Specify additional border of the image
         *
         * @param border width of the border (must be greater or equal to 0)
         * @return UrlGenerator instance
         */
        public Resizin.UrlGenerator border(int border) {
            this.borderTop = this.borderLeft = this.borderRight = this.borderBottom = border;
            return this;
        }

        /**
         * Specify additional individual borders of the image
         *
         * @param borderTop    width of the top border (must be greater or equal to 0)
         * @param borderLeft   width of the top border (must be greater or equal to 0)
         * @param borderRight  width of the top border (must be greater or equal to 0)
         * @param borderBottom width of the top border (must be greater or equal to 0)
         * @return UrlGenerator instance
         */
        public Resizin.UrlGenerator border(int borderTop, int borderLeft, int borderRight, int borderBottom) {
            this.borderTop = borderTop;
            this.borderLeft = borderLeft;
            this.borderRight = borderRight;
            this.borderBottom = borderBottom;
            return this;
        }

        /**
         * Specify background of the image when cropping to fit or using borders
         *
         * @param background color of the background (i.e. "00bcd4" or "#00bcd4")
         * @return UrlGenerator instance
         */
        public Resizin.UrlGenerator background(String background) {
            if (background != null) {
                if (background.matches("#?[0-9a-fA-F]{6}")) {
                    this.background = background.replace("#", "");
                } else {
                    throw new IllegalArgumentException("Wrong format of color, use format 'RRGGBB' or '#RRGGBB' (i.e. '#00bcd4')");
                }
            } else {
                this.background = null;
            }
            return this;
        }

        public String getImageId() {
            return imageId;
        }

        /**
         * Generate url with previously parsed imageId
         *
         * @return built url as String
         */
        public String generate() {
            return generate(imageId);
        }

        /**
         * Generates url with specified parameters
         *
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
                transformations.add(String.format(Locale.US, "w_%d", this.width));
            }

            if (this.heightSet) {
                transformations.add(String.format(Locale.US, "h_%d", this.height));
            }

            if (this.cropSet) {
                transformations.add(String.format(Locale.US, "c_%s", this.crop));
            }

            if (this.gravitySet) {
                transformations.add(String.format(Locale.US, "g_%s", this.gravity));
            }

            if (this.grayscale) {
                transformations.add("f_greyscale");
            }
            if (quality >= 0) {
                transformations.add(String.format(Locale.US, "q_%d", this.quality));
            }
            if (upscale >= 0) {
                transformations.add(String.format(Locale.US, "u_%d", this.upscale));
            }
            if (borderTop >= 0 && borderLeft >= 0 && borderRight >= 0 && borderBottom >= 0) {
                transformations.add(String.format(Locale.US, "b_%d_%d_%d_%d", borderTop, borderLeft, borderRight, borderBottom));
            }
            if (background != null) {
                transformations.add(String.format(Locale.US, "bg_%s", background));
            }

            builder.append(this.join("-", transformations));
            builder.append("/");
            builder.append(imageId);
            if (this.extension != null && this.extension.length() > 0) {
                builder.append(".").append(extension);
            }
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

        private Resizin.UrlGenerator parseUrl(String url) {
            try {
                String[] parts = url.split("/");
                String imageId = parts[parts.length - 1];
                if (!parts[4].equals("image")) {
                    throw new Exception();
                }
                if (imageId.contains(".")) {
                    this.imageId = imageId.split("\\.")[0];
                    this.extension = imageId.split("\\.")[1];
                } else {
                    this.imageId = imageId;
                }

                String modificatorsText = parts[parts.length - 2];
                String[] modificators = modificatorsText.split("-");
                for (String modificator : modificators) {
                    if (modificator.startsWith("w")) {
                        this.width = Integer.parseInt(modificator.substring(2));
                        widthSet = true;
                    }
                    if (modificator.startsWith("h")) {
                        this.height = Integer.parseInt(modificator.substring(2));
                        heightSet = true;
                    }
                    if (modificator.startsWith("c")) {
                        cropSet = true;
                        crop = Crop.fromValue(modificator.substring(2));
                    }

                    if (modificator.startsWith("g")) {
                        gravitySet = true;
                        gravity = Gravity.fromValue(modificator.substring(2));
                    }

                    if (modificator.startsWith("f")) {
                        grayscale = true;
                    }

                    if (modificator.startsWith("q")) {
                        quality = Integer.parseInt(modificator.substring(2));
                    }

                    if (modificator.startsWith("u")) {
                        this.upscale = Integer.parseInt(modificator.substring(2));
                    }

                    if (modificator.startsWith("b_")) {
                        String numbersString = modificator.substring(2);
                        String[] numbers = numbersString.split("_");
                        borderTop = Integer.parseInt(numbers[0]);
                        borderLeft = Integer.parseInt(numbers[1]);
                        borderRight = Integer.parseInt(numbers[2]);
                        borderBottom = Integer.parseInt(numbers[3]);
                    }
                    if (modificator.startsWith("bg")) {
                        this.background = modificator.substring(3);
                    }
                }
                return this;
            } catch (Exception e) {
                throw new InvalidUrlException("Url " + url + " is in incorrect format", e);
            }
        }
    }
}
