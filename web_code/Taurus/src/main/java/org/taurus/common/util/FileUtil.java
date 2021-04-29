package org.taurus.common.util;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.taurus.common.code.Code;
import org.taurus.entity.SFileEntity;

public class FileUtil {

    /**
     * 将文件信息保存到fileEntity
     *
     * @param file
     * @param nowTime
     * @return
     */
    public static SFileEntity getInfoByFile(MultipartFile file, LocalDateTime nowTime) {
        // aa 时间戳
        String timeStamp = String.valueOf(new Date().getTime());
        // aa 文件名
        String fileName = file.getOriginalFilename();
        // aa 后缀
        String extension = FilenameUtils.getExtension(fileName);
        // aa 带时间戳的文件名
        String fileNameTimestamp = FilenameUtils.getBaseName(fileName) + timeStamp + "." + extension;
        // aa 文件大小(B)
        long fileLength = file.getSize();
        // aa 文件大小(KB)保留两位小数，四舍五入
        String fileSize = new BigDecimal(fileLength).divide(new BigDecimal("1024"))
                .setScale(2, BigDecimal.ROUND_HALF_UP).toString();

        SFileEntity fileEntity = new SFileEntity();
        fileEntity.setFileId(StrUtil.getUUID());
        fileEntity.setFileName(fileName);
        fileEntity.setFileNameTimestamp(fileNameTimestamp);
        fileEntity.setFileSize(fileSize);
        fileEntity.setFileType(getFileType(fileName));
        fileEntity.setFileDelFlg(Code.DEL_FLG_1.getValue());
        fileEntity.setFileCreateTime(nowTime);
        fileEntity.setFileModifyTime(nowTime);
//		fileEntity.setFileFolder(folderId);
//		fileEntity.setFileOwner(fileOwner);
//		fileEntity.setFileCreateUser(fileOwner);
//		fileEntity.setFileModifyUser(operator);
//		fileEntity.setFilePath();
        return fileEntity;
    }

    /**
     * 将文件信息保存到fileEntity
     *
     * @param name
     * @param size
     * @param nowTime
     * @return
     */
    public static SFileEntity getInfoByFile(String name, long size, LocalDateTime nowTime) {
        // aa 时间戳
        String timeStamp = String.valueOf(new Date().getTime());
        // aa 文件名
        String fileName = name;
        // aa 后缀
        String extension = FilenameUtils.getExtension(fileName);
        // aa 带时间戳的文件名
        String fileNameTimestamp = FilenameUtils.getBaseName(fileName) + timeStamp + "." + extension;
        // aa 文件大小(B)
        long fileLength = size;
        // aa 文件大小(KB)保留两位小数，四舍五入
        String fileSize = new BigDecimal(fileLength).divide(new BigDecimal("1024"))
                .setScale(2, BigDecimal.ROUND_HALF_UP).toString();

        SFileEntity fileEntity = new SFileEntity();
        fileEntity.setFileId(StrUtil.getUUID());
        fileEntity.setFileName(fileName);
        fileEntity.setFileNameTimestamp(fileNameTimestamp);
        fileEntity.setFileSize(fileSize);
        fileEntity.setFileType(getFileType(fileName));
        fileEntity.setFileDelFlg(Code.DEL_FLG_1.getValue());
        fileEntity.setFileCreateTime(nowTime);
        fileEntity.setFileModifyTime(nowTime);
//		fileEntity.setFileFolder(folderId);
//		fileEntity.setFileOwner(fileOwner);
//		fileEntity.setFileCreateUser(fileOwner);
//		fileEntity.setFileModifyUser(operator);
//		fileEntity.setFilePath();
        return fileEntity;
    }

    /**
     * 获取文件类型
     *
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName) {
        String type = Code.FILE_TYPE_OTHER.getValue();
        // aa 后缀 - 小写
        String extension = FilenameUtils.getExtension(fileName).toLowerCase();
        if (extension != null) {
            switch (extension) {
                case "mp3":
                    type = Code.FILE_TYPE_AUDIO.getValue();
                    break;
                case "mp4":
                case "mkv":
                    type = Code.FILE_TYPE_VIDEO.getValue();
                    break;
                case "text":
                case "txt":
                case "html":
                case "js":
                case "css":
                    type = Code.FILE_TYPE_TXT.getValue();
                    break;
                case "log":
                    type = Code.FILE_TYPE_LOG.getValue();
                    break;
                case "png":
                case "jpg":
                    type = Code.FILE_TYPE_PICTURE.getValue();
                    break;
                default:
                    type = Code.FILE_TYPE_OTHER.getValue();
                    break;
            }
        }
        return type;
    }

    /**
     * 以字符为单位读取文件内容，一次读一个字节
     *
     * @param filePath
     * @return
     */
    public static String getTxtContent(String filePath) {
        if (StrUtil.isEmpty(filePath)) {
            return "";
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        }

        FileReader fileReader = null;
        BufferedReader br = null;
        StringBuffer content = new StringBuffer();
        try {
            fileReader = new FileReader(file);
            br = new BufferedReader(fileReader);
            String line = br.readLine();
            while (line != null) {
                content.append(line);
                content.append(System.getProperty("line.separator"));
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }

    /**
     * 删除文件或者文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            for (int i = 0; i < children.length; i++) {
                deleteFile(children[i]);
            }
        }


        String fileName = file.getName();
        String fileType = getFileType(fileName);
        // 图片文件还要额外删除略缩图文件
        if (Code.FILE_TYPE_PICTURE.getValue().equals(fileType)) {
            // 获取略缩图地址
            String thumbnailsName = ImageUtil.getThumbnailsName(fileName);
            String filePath = file.getPath();
            String thumbnailsPath = filePath.replace(fileName, thumbnailsName);
            File thumbnails = new File(thumbnailsPath);
            if (thumbnails.exists() && thumbnails.isFile()) {
                thumbnails.delete();
            }
        }
        file.delete();
    }

    /**
     * 删除文件或者文件夹
     *
     * @param path
     */
    public static void deleteFile(String path) {
        deleteFile(new File(path));
    }

}
