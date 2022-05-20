package io.github.ackeecz.resizin.sdk

import androidx.annotation.VisibleForTesting
import java.util.Locale

/**
 * Resizin.com server instance with variable app id.
 * Provides UrlGenerator for building image url with various modificators
 * like width, height, crop and gravity
 *
 * @param appId id of your app at the Resizin server
 */
class Resizin(private val appId: String) {

    companion object {

        private const val IMAGE_SERVER_URL = "https://img.resizin.com/"
    }

    /**
     * Generate url with previously parsed imageId
     *
     * @return built url as String
     */
    fun url(): UrlGenerator {
        return UrlGenerator(this.appId, IMAGE_SERVER_URL)
    }

    /**
     * Parse given url and parse it to instance
     *
     * @param url URL of existing image
     * @return builder class for creating url
     */
    fun withUrl(url: String): UrlGenerator {
        return UrlGenerator(this.appId, IMAGE_SERVER_URL).parseUrl(url)
    }

    /**
     * Generator of url to image server
     */
    class UrlGenerator internal constructor(private var appId: String, private val serverUrl: String) {

        private var width: Int = 0
        private var height: Int = 0
        private var grayscale: Boolean = false
        private var crop: Crop
        private var useOriginalImage: Boolean = false
        private var gravity: Gravity
        private var widthSet: Boolean = false
        private var heightSet: Boolean = false
        private var cropSet: Boolean = false
        private var gravitySet: Boolean = false
        private var quality: Int = 0
        private var extension: String? = null
        private var upscale = -1
        private var borderTop = -1
        private var borderLeft = -1
        private var borderRight = -1
        private var borderBottom = -1
        var background: String? = null
            @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE) get
            private set
        var imageId: String? = null
            internal set

        init {
            width = Integer.MIN_VALUE
            height = Integer.MIN_VALUE
            grayscale = false
            crop = Crop.FIT
            gravity = Gravity.CENTER
            quality = -1
            extension = ""
            imageId = null
        }

        /**
         * Specify width of image
         *
         * @param width of image
         * @return UrlGenerator instance
         */
        fun width(width: Int): UrlGenerator {
            this.widthSet = true
            this.width = width
            return this
        }

        /**
         * Specify height of image
         *
         * @param height of image
         * @return UrlGenerator instance
         */
        fun height(height: Int): UrlGenerator {
            this.heightSet = true
            this.height = height
            return this
        }

        /**
         * Specify if grayscale filter should be applied to image
         *
         * @param grayscale switch for grayscale filtrer
         * @return UrlGenerator instance
         */
        fun grayscale(grayscale: Boolean): UrlGenerator {
            this.grayscale = grayscale
            return this
        }

        /**
         * Specify crop of image. see [Crop] for available
         * options
         *
         * @param crop of image
         * @return UrlGenerator instance
         */
        fun crop(crop: Crop): UrlGenerator {
            this.cropSet = true
            this.crop = crop
            return this
        }

        /**
         * Specify gravity of cropped image. See [Gravity] for
         * available options
         *
         * @param gravity of cropped image
         * @return UrlGenerator instance
         */
        fun gravity(gravity: Gravity): UrlGenerator {
            this.gravitySet = true
            this.gravity = gravity
            return this
        }

        /**
         * Specify quality of image in percent
         *
         * @param quality percentual quality of image, values 0 - 100
         * @return UrlGenerator instance
         */
        fun quality(quality: Int): UrlGenerator {
            this.quality = quality
            return this
        }

        /**
         * Specify extension of image
         *
         * @param extension extension eg. jpg
         * @return UrlGenerator instance
         */
        fun extension(extension: String): UrlGenerator {
            this.extension = extension
            return this
        }

        /**
         * Indicator if image should be upscaled if size is larger than original dimensions
         *
         * @param upscale boolean indicator
         * @return UrlGenerator instance
         */
        fun upscale(upscale: Boolean): UrlGenerator {
            this.upscale = if (upscale) 1 else 0
            return this
        }

        /**
         * Specify additional border of the image
         *
         * @param border width of the border (must be greater or equal to 0)
         * @return UrlGenerator instance
         */
        fun border(border: Int): UrlGenerator {
            this.borderBottom = border
            this.borderRight = this.borderBottom
            this.borderLeft = this.borderRight
            this.borderTop = this.borderLeft
            return this
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
        fun border(borderTop: Int, borderLeft: Int, borderRight: Int, borderBottom: Int): UrlGenerator {
            this.borderTop = borderTop
            this.borderLeft = borderLeft
            this.borderRight = borderRight
            this.borderBottom = borderBottom
            return this
        }

        /**
         * Specify background of the image when cropping to fit or using borders
         *
         * @param background color of the background (i.e. "00bcd4" or "#00bcd4")
         * @return UrlGenerator instance
         */
        fun background(background: String?): UrlGenerator {
            if (background != null) {
                if (background.matches("#?[0-9a-fA-F]{6}".toRegex())) {
                    this.background = background.replace("#", "")
                } else {
                    throw IllegalArgumentException("Wrong format of color, use format 'RRGGBB' or '#RRGGBB' (i.e. '#00bcd4')")
                }
            } else {
                this.background = null
            }
            return this
        }

        /**
         * Specify if should be loaded original image without any processing on the image server
         *
         * @param useOriginalImage load original image
         * @return UrlGenerator instance
         */
        fun useOriginalImage(useOriginalImage: Boolean): UrlGenerator {
            this.useOriginalImage = useOriginalImage
            return this
        }

        /**
         * Generates url with specified parameters
         *
         * @param imageId id of image on image server
         * @return built url as String
         */
        @JvmOverloads
        fun generate(imageId: String? = this.imageId): String {
            return buildString {
                append(serverUrl)
                if (!serverUrl.endsWith("/")) {
                    append("/")
                }
                append(appId).append("/")
                val transformations = createTransformations()
                if (useOriginalImage && transformations.isNotEmpty())
                    throw IllegalStateException("You cannot specify transformations and use original image")
                if (useOriginalImage) {
                    append("original").append("/")
                } else {
                    append("image").append("/")
                    append(transformations.filter { it.isNotEmpty() }.joinToString("-"))
                    if (transformations.isNotEmpty()) {
                        append("/")
                    }
                }
                append(imageId)
                if (!extension.isNullOrEmpty()) {
                    append(".").append(extension)
                }
            }
        }

        private fun createTransformations(): List<String> {
            val transformations = mutableListOf<String>()

            if (this.widthSet) {
                transformations.add(String.format(Locale.US, "w_%d", this.width))
            }

            if (this.heightSet) {
                transformations.add(String.format(Locale.US, "h_%d", this.height))
            }

            if (this.cropSet) {
                transformations.add(String.format(Locale.US, "c_%s", this.crop))
            }

            if (this.gravitySet) {
                transformations.add(String.format(Locale.US, "g_%s", this.gravity))
            }

            if (this.grayscale) {
                transformations.add("f_greyscale")
            }

            if (quality >= 0) {
                transformations.add(String.format(Locale.US, "q_%d", this.quality))
            }

            if (upscale >= 0) {
                transformations.add(String.format(Locale.US, "u_%d", this.upscale))
            }

            if (borderTop >= 0 && borderLeft >= 0 && borderRight >= 0 && borderBottom >= 0) {
                transformations.add(String.format(Locale.US, "b_%d_%d_%d_%d", borderTop, borderLeft, borderRight, borderBottom))
            }

            if (background != null) {
                transformations.add(String.format(Locale.US, "bg_%s", background))
            }

            return transformations
        }

        internal fun parseUrl(url: String): UrlGenerator {
            try {
                val parts = url.split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                val imageId = parts[parts.size - 1]
                if (parts[4] != "image" && parts[4] != "original") {
                    throw Exception()
                }
                if (imageId.contains(".")) {
                    this.imageId = imageId.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0]
                    this.extension = imageId.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[1]
                } else {
                    this.imageId = imageId
                }

                if (parts[4] == "original") {
                    this.useOriginalImage = true
                } else {
                    val modificatorsText = parts[parts.size - 2]
                    val modificators = modificatorsText.split("-".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                    for (modificator in modificators) {
                        if (modificator.startsWith("w")) {
                            this.width = Integer.parseInt(modificator.substring(2))
                            widthSet = true
                        }
                        if (modificator.startsWith("h")) {
                            this.height = Integer.parseInt(modificator.substring(2))
                            heightSet = true
                        }
                        if (modificator.startsWith("c")) {
                            cropSet = true
                            crop = Crop.fromValue(modificator.substring(2))
                        }

                        if (modificator.startsWith("g")) {
                            gravitySet = true
                            gravity = Gravity.fromValue(modificator.substring(2))
                        }

                        if (modificator.startsWith("f")) {
                            grayscale = true
                        }

                        if (modificator.startsWith("q")) {
                            quality = Integer.parseInt(modificator.substring(2))
                        }

                        if (modificator.startsWith("u")) {
                            this.upscale = Integer.parseInt(modificator.substring(2))
                        }

                        if (modificator.startsWith("b_")) {
                            val numbersString = modificator.substring(2)
                            val numbers = numbersString.split("_".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                            borderTop = Integer.parseInt(numbers[0])
                            borderLeft = Integer.parseInt(numbers[1])
                            borderRight = Integer.parseInt(numbers[2])
                            borderBottom = Integer.parseInt(numbers[3])
                        }
                        if (modificator.startsWith("bg")) {
                            this.background = modificator.substring(3)
                        }
                    }
                }
                return this
            } catch (e: Exception) {
                throw InvalidUrlException("Url $url is in incorrect format", e)
            }
        }
    }
}
