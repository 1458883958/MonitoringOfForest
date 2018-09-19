package com.wdl.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.wdl.utils.StreamUtil.close;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.utils
 * 创建者：   wdl
 * 创建时间： 2018/8/11 19:08
 * 描述：    图像处理工具类
 */
@SuppressWarnings("unused")
public class BitmapUtil {
    public static Bitmap bitmap2Gray(Bitmap bmSrc) {
        // 得到图片的长和宽
        int width = bmSrc.getWidth();
        int height = bmSrc.getHeight();
        // 创建目标灰度图像
        Bitmap bmpGray = null;
        bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        // 创建画布
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);
        return bmpGray;
    }

    // 该函数实现对图像进行二值化处理
    public static Bitmap gray2Binary(Bitmap graymap, Progress progress) {
        //得到图形的宽度和长度
        int width = graymap.getWidth();
        int height = graymap.getHeight();
        int total = width * height;
        //创建二值化图像
        Bitmap binarymap = null;
        binarymap = graymap.copy(Bitmap.Config.RGB_565, true);
        //依次循环，对图像的像素进行处理
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //得到当前像素的值
                int col = binarymap.getPixel(i, j);
                //得到alpha通道的值
                int alpha = col & 0xFF000000;
                //得到图像的像素RGB的值
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                //对图像进行二值化处理
                if (gray <= 95) {
                    gray = 0;
                } else {
                    gray = 255;
                }
                // 新的ARGB
                int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                //设置新图像的当前像素值
                binarymap.setPixel(i, j, newColor);
            }
        }
        return binarymap;
    }

    public static double result(Bitmap binaryMap, Progress progress) {
        //得到图形的宽度和长度
        int width = binaryMap.getWidth();
        int height = binaryMap.getHeight();
        int total = width * height;
        //创建二值化图像
        int black = 0;
        int current = 0;
        //依次循环，对图像的像素进行处理
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //得到当前像素的值
                int col = binaryMap.getPixel(i, j);
                if (col == -16777216) {
                    black++;
                }
                current++;
                if (progress != null && current % 100 == 0) {
                    progress.progress(current, total);
                }
            }
        }

        return (double) black / total;
    }

    public static Bitmap compress(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bitmap;
    }


    /**
     * 转为二值图像
     *
     * @param bmp 原图bitmap
     * @return
     */
    public static Bitmap convertToBMW(Bitmap bmp, int tmp) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组
        // 设定二值化的域值，默认值为100
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];
                // 分离三原色
                alpha = ((grey & 0xFF000000) >> 24);
                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);
                if (red > tmp) {
                    red = 255;
                } else {
                    red = 0;
                }
                if (blue > tmp) {
                    blue = 255;
                } else {
                    blue = 0;
                }
                if (green > tmp) {
                    green = 255;
                } else {
                    green = 0;
                }
                pixels[width * i + j] = alpha << 24 | red << 16 | green << 8
                        | blue;
                if (pixels[width * i + j] == -1) {
                    pixels[width * i + j] = -1;
                } else {
                    pixels[width * i + j] = -16777216;
                }
            }
        }
        // 新建图片
        Bitmap newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        // 设置图片数据
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        Bitmap resizeBmp = null;
        resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, width, height);
        if (resizeBmp==null)
            resizeBmp = newBmp;
        if (newBmp!=resizeBmp)
            newBmp.recycle();
        return resizeBmp;
    }

    public interface Progress {
        void progress(int current, int total);
    }


    /**
     * 获取旋转角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                default:
                    degree = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static class VX{
        Bitmap bitmap;
        String path;

        public VX(Bitmap bitmap, String path) {
            this.bitmap = bitmap;
            this.path = path;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public static VX rotate(Bitmap bmp, String imagePath) {
        Matrix matrix = new Matrix();
        matrix.setRotate(90,bmp.getWidth()/2,bmp.getHeight()/2);
        Bitmap bitmap = null;
        File imgFile = new File(imagePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(imgFile);
            bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if (fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (bitmap==null){
            bitmap = bmp;
        }
        if (bmp!=bitmap)
            bmp.recycle();
        return new VX(bitmap,imagePath);
    }

    public static VX rotateX(Bitmap bmp, String imagePath) {
        // 创建一个和原图一样大小的图片
        // 设置画笔，消除锯齿
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap afterBitmap = Bitmap.createBitmap(bmp.getWidth(),
                bmp.getHeight(), bmp.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();

        // 根据原图的中心位置旋转
        matrix.setRotate(90, bmp.getWidth() / 2,
                bmp.getHeight() / 2);
        canvas.drawBitmap(bmp, matrix, paint);
        File imgFile = new File(imagePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(imgFile);
            afterBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if (fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new VX(afterBitmap,imagePath);
    }

    /**
     * A default size to use to increase hit rates when the required size isn't defined.
     * Currently 64KB.
     */
    public final static int DEFAULT_BUFFER_SIZE = 64 * 1024;

    /**
     * 创建一个图片处理Options
     *
     * @return {@link BitmapFactory.Options}
     */
    public static BitmapFactory.Options createOptions() {
        return new BitmapFactory.Options();
    }

    /**
     * 把一个{@link BitmapFactory.Options}进行参数复原操作，
     * 避免重复创建新的 {@link BitmapFactory.Options}
     *
     * @param options {@link BitmapFactory.Options}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void resetOptions(BitmapFactory.Options options) {
        options.inTempStorage = null;
        options.inDither = false;
        options.inScaled = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        options.inDensity = 0;
        options.inTargetDensity = 0;
        options.outWidth = 0;
        options.outHeight = 0;
        options.outMimeType = null;

        if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT) {
            options.inBitmap = null;
            options.inMutable = true;
        }
    }

    /**
     * 获取图片的真实后缀
     *
     * @param filePath 图片存储地址
     * @return 图片类型后缀
     */
    public static String getExtension(String filePath) {
        BitmapFactory.Options options = createOptions();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        String mimeType = options.outMimeType;
        return mimeType.substring(mimeType.lastIndexOf("/") + 1);
    }

    public static Bitmap decodeBitmap(final File file,
                                      final int maxWidth,
                                      final int maxHeight,
                                      byte[] byteStorage,
                                      BitmapFactory.Options options,
                                      boolean exactDecode) {
        InputStream is;
        try {
            // In this, we can set the buffer size
            is = new BufferedInputStream(new FileInputStream(file),
                    byteStorage == null ? DEFAULT_BUFFER_SIZE : byteStorage.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        if (options == null)
            options = createOptions();
        else
            resetOptions(options);

        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;

        // 5MB. This is the max image header size we can handle, we preallocate a much smaller buffer
        // but will resize up to this amount if necessary.
        is.mark(5 * 1024 * 1024);
        BitmapFactory.decodeStream(is, null, options);

        // Reset the inputStream
        try {
            is.reset();
        } catch (IOException e) {
            e.printStackTrace();
            close(is);
            resetOptions(options);
            return null;
        }

        // Calculate inSampleSize
        calculateScaling(options, maxWidth, maxHeight, exactDecode);

        // Init the BitmapFactory.Options.inTempStorage value
        if (byteStorage == null)
            byteStorage = new byte[DEFAULT_BUFFER_SIZE];
        options.inTempStorage = byteStorage;

        // Decode bitmap with inSampleSize set FALSE
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

        // Close the Stream
        close(is);
        // And Reset the option
        resetOptions(options);

        // To scale bitmap to user set
        bitmap = scaleBitmap(bitmap, maxWidth, maxHeight, true);

        return bitmap;

    }

    /**
     * 按长宽比缩小一个Bitmap
     *
     * @param source        待缩小的{@link Bitmap}
     * @param scale         缩放比0～1，1代表不缩放
     * @param recycleSource 是否释放Bitmap源
     * @return 一个缩小后的Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap source, float scale, boolean recycleSource) {
        if (scale <= 0 || scale >= 1)
            return source;
        Matrix m = new Matrix();
        final int width = source.getWidth();
        final int height = source.getHeight();
        m.setScale(scale, scale);
        Bitmap scaledBitmap = Bitmap.createBitmap(source, 0, 0, width, height, m, false);
        if (recycleSource)
            source.recycle();
        return scaledBitmap;
    }

    /**
     * 按照长宽比缩小一个Bitmap到指定尺寸，
     * 当传入的高宽都大于原始值时将不做缩小操作
     *
     * @param source          待缩小的{@link Bitmap}
     * @param targetMaxWidth  目标宽度
     * @param targetMaxHeight 目标高度
     * @param recycleSource   是否释放Bitmap源
     * @return 一个缩小后的Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap source, int targetMaxWidth, int targetMaxHeight, boolean recycleSource) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        Bitmap scaledBitmap = source;
        if (sourceWidth > targetMaxWidth || sourceHeight > targetMaxHeight) {
            float minScale = Math.min(targetMaxWidth / (float) sourceWidth,
                    targetMaxHeight / (float) sourceHeight);
            scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap,
                    (int) (sourceWidth * minScale),
                    (int) (sourceHeight * minScale), false);
            if (recycleSource)
                source.recycle();
        }

        return scaledBitmap;
    }

    /**
     * 通过{@link BitmapFactory.Options}计算图片的缩放比,
     * 并将缩放后的信息存储在传入的{@link BitmapFactory.Options}中，
     * 以便后续的 {@link BitmapFactory#decodeStream(InputStream)}等操作
     *
     * @param options            一个图片的{@link BitmapFactory.Options}， 含有图片的基础信息
     * @param requestedMaxWidth  目标宽度
     * @param requestedMaxHeight 目标高度
     * @param exactDecode        是否精确计算，该参数只在 {@link Build.VERSION#SDK_INT} 大于 Api19 时有效
     */
    private static BitmapFactory.Options calculateScaling(BitmapFactory.Options options,
                                                          final int requestedMaxWidth,
                                                          final int requestedMaxHeight,
                                                          boolean exactDecode) {
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;

        if (sourceWidth <= requestedMaxWidth && sourceHeight <= requestedMaxHeight) {
            return options;
        }

        final float maxFloatFactor = Math.max(sourceHeight / (float) requestedMaxHeight,
                sourceWidth / (float) requestedMaxWidth);
        final int maxIntegerFactor = (int) Math.floor(maxFloatFactor);
        final int lesserOrEqualSampleSize = Math.max(1, Integer.highestOneBit(maxIntegerFactor));

        options.inSampleSize = lesserOrEqualSampleSize;
        // Density scaling is only supported if inBitmap is null prior to KitKat. Avoid setting
        // densities here so we calculate the final Bitmap size correctly.
        if (exactDecode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            float scaleSize = sourceWidth / (float) lesserOrEqualSampleSize;
            float outSize = sourceWidth / maxFloatFactor;

            options.inTargetDensity = 1000;
            options.inDensity = (int) (1000 * (scaleSize / outSize) + 0.5);

            // If isScaling
            if (options.inTargetDensity != options.inDensity) {
                options.inScaled = true;
            } else {
                options.inDensity = options.inTargetDensity = 0;
            }
        }
        return options;
    }

    public final static class Compressor {

        public static File compressImage(final File sourceFile, final long maxSize,
                                         final int minQuality, final int maxWidth,
                                         final int maxHeight) {
            return compressImage(sourceFile, maxSize, minQuality, maxWidth, maxHeight, true);
        }

        public static File compressImage(final File sourceFile, final long maxSize,
                                         final int minQuality, final int maxWidth,
                                         final int maxHeight, boolean exactDecode) {
            return compressImage(sourceFile, maxSize, minQuality, maxWidth, maxHeight, null, null, exactDecode);
        }

        /**
         * 压缩图片
         *
         * @param sourceFile  原图地址
         * @param maxSize     最大文件地址byte
         * @param minQuality  最小质量
         * @param maxWidth    最大宽度
         * @param maxHeight   最大高度
         * @param byteStorage 用于批量压缩时的buffer，不必要为null，
         *                    需要时，推荐 {{@link #DEFAULT_BUFFER_SIZE}}
         * @param options     批量压缩时复用参数，可调用 {{@link #createOptions()}} 得到
         * @param exactDecode 是否精确解码， TRUE： 在4.4及其以上机器中能更节约内存
         * @return 返回压缩后的图片文件，该图片存储在原图同级目录下，以compress.temp结尾
         */
        public static File compressImage(final File sourceFile,
                                         final long maxSize,
                                         final int minQuality,
                                         final int maxWidth,
                                         final int maxHeight,
                                         byte[] byteStorage,
                                         BitmapFactory.Options options,
                                         boolean exactDecode) {
            // build source file
            if (sourceFile == null || !sourceFile.exists() || !sourceFile.canRead())
                return null;

            // create new temp file
            final File tempFile = new File(sourceFile.getParent(),
                    String.format("compress_%s.temp", System.currentTimeMillis()));

            if (!tempFile.exists()) {
                try {
                    if (!tempFile.createNewFile())
                        return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            // build to bitmap
            Bitmap bitmap = decodeBitmap(sourceFile, maxWidth, maxHeight, byteStorage, options, exactDecode);
            if (bitmap == null)
                return null;

            // Get the bitmap format
            Bitmap.CompressFormat compressFormat = bitmap.hasAlpha() ?
                    Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;

            // Write to out put file
            boolean isOk = false;
            for (int i = 1; i <= 10; i++) {
                // In this we change the quality start 92%
                int quality = 92;
                for (; ; ) {
                    BufferedOutputStream outputStream = null;
                    try {
                        outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
                        bitmap.compress(compressFormat, quality, outputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // on IOException we need recycle the bitmap
                        bitmap.recycle();
                        return null;
                    } finally {
                        close(outputStream);
                    }
                    // Check file size
                    long outSize = tempFile.length();
                    if (outSize <= maxSize) {
                        isOk = true;
                        break;
                    }
                    if (quality < minQuality)
                        break;
                    quality--;
                }

                if (isOk) {
                    break;
                } else {
                    // If not ok, we need scale the Bitmap to small
                    // In this, once subtract 2%, most 20%
                    bitmap = scaleBitmap(bitmap, 1 - (0.2f * i), true);
                }
            }
            // recycle bitmap
            bitmap.recycle();

            // The end, If not success, return false
            if (!isOk)
                return null;

            // Rename to out file
            return tempFile;
        }
    }

}
