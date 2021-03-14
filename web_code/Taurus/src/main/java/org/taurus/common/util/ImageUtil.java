package org.taurus.common.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.vfs2.FileUtil;

import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

    /**
     * 略缩图后缀
     */
    public static final String EXT = "_thumbnails";

    /**
     * 生成略缩图
     *
     * @param imageStream 图片流
     * @param scale       缩放比例
     * @param path        略缩图存放路径(带文件名) E:/aaa/bbb.jpg
     * @throws IOException
     */
    public static void createThumbnails(InputStream imageStream, double scale, String path) throws IOException {
        Thumbnails.of(imageStream).scale(scale).toFile(path);
    }

    /**
     * 生成略缩图
     *
     * @param imageStream 图片流
     * @param width       略缩图宽度
     * @param height      略缩图高度
     * @param path        略缩图存放路径(带文件名) E:/aaa/bbb.jpg
     * @throws IOException
     */
    public static void createThumbnails(InputStream imageStream, int width, int height, String path) throws IOException {
        Thumbnails.of(imageStream).width(width).height(height).toFile(path);
    }

    /**
     * 获取略缩图名称
     * @param imageName 图片名称
     * @return 略缩图名称
     */
    public static String getThumbnailsName(String imageName) {
        // aa 文件名
        String name = FilenameUtils.getBaseName(imageName);
        // aa 后缀
        String extension = FilenameUtils.getExtension(imageName);
        return name + EXT + "." + extension;
    }

}
