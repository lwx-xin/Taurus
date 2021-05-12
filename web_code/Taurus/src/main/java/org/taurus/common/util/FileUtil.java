package org.taurus.common.util;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.springframework.web.multipart.MultipartFile;
import org.taurus.common.code.Code;
import org.taurus.config.load.properties.TaurusProperties;
import org.taurus.entity.SFileEntity;

import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;

public class FileUtil {

    /**
     * 封面图后缀
     */
    public static final String COVER_EXT = "_cover_img";

    /**
     * 略缩图后缀
     */
    public static final String IMG_EXT = "_thumbnails";

    /**
     * 将文件信息保存到fileEntity
     *
     * @param file
     * @param nowTime
     * @param folderPath 文件夹的路径
     * @return
     */
    public static SFileEntity getInfoByFile(MultipartFile file, LocalDateTime nowTime, String folderPath) {
        // 时间戳
        String timeStamp = String.valueOf(new Date().getTime());
        // 文件名
        String fileName = file.getOriginalFilename();
        // 后缀
        String extension = FilenameUtils.getExtension(fileName);
        // 带时间戳的文件名(不带文件后缀)
        String fileBaseNameTimestamp = FilenameUtils.getBaseName(fileName) + timeStamp;
        // 带时间戳的文件名
        String fileNameTimestamp = fileBaseNameTimestamp + "." + extension;
        // 文件大小(B)
        long fileLength = file.getSize();
        // 文件大小(KB)保留两位小数，四舍五入
        String fileSize = new BigDecimal(fileLength).divide(new BigDecimal("1024"))
                .setScale(2, BigDecimal.ROUND_HALF_UP).toString();

        String fileType = getFileType(fileName);

        SFileEntity fileEntity = new SFileEntity();
        fileEntity.setFileId(StrUtil.getUUID());
        fileEntity.setFileName(fileName);
        fileEntity.setFileNameTimestamp(fileNameTimestamp);
        fileEntity.setFileSize(fileSize);
        fileEntity.setFileType(fileType);
        fileEntity.setFileDelFlg(Code.DEL_FLG_1.getValue());
        fileEntity.setFileCreateTime(nowTime);
        fileEntity.setFileModifyTime(nowTime);

        // 设置文件详细信息
        if (Code.FILE_TYPE_AUDIO.getValue().equals(fileType)) {
            // 音频
            File audioFile = multipartFileToFile(file);
            if (audioFile != null) {
                SFileEntity.Audio audioFileInfo = getAudioFileInfo(audioFile, folderPath, fileNameTimestamp);
                fileEntity.setFileDetailInfo(JsonUtil.toJson(audioFileInfo));
            }
        } else if (Code.FILE_TYPE_PICTURE.getValue().equals(fileType)) {
            // 图片
            SFileEntity.Image imageFileInfo = new SFileEntity.Image();
            imageFileInfo.setCover(getFileCoverImgName(fileNameTimestamp));
            fileEntity.setFileDetailInfo(JsonUtil.toJson(imageFileInfo));
        }

        return fileEntity;
    }

    /**
     * 获取音频文件详细信息<br/>歌名，歌手，专辑，封面
     *
     * @param file
     * @param folderPath    文件夹相对路径
     * @param timestampName 带时间戳的文件名
     * @return
     */
    private static SFileEntity.Audio getAudioFileInfo(File file, String folderPath, String timestampName) {
        SFileEntity.Audio audioInfo = new SFileEntity.Audio();
        try {
            MP3File mp3File = (MP3File) AudioFileIO.read(file);
            //标签
            Tag tag = mp3File.getID3v2TagAsv24();
            if (tag != null) {
                //歌曲名
                String musicName = tag.getFirst(FieldKey.TITLE);
                //歌手
                String artist = tag.getFirst(FieldKey.ARTIST);
                //专辑
                String album = tag.getFirst(FieldKey.ALBUM);
                // 封面图片文件名
                String coverImgName = getFileCoverImgName(timestampName);
                //封面 = 文件夹路径 + 文件名
                String coverPath = folderPath + coverImgName;
                byte[] imageData = getMp3Image(file);
                if (imageData != null) {
                    // byte数组到图片到硬盘上
                    try {
                        FileImageOutputStream imageOutput = new FileImageOutputStream(new File(coverPath));//打开输入流
                        imageOutput.write(imageData, 0, imageData.length);//将byte写入硬盘
                        imageOutput.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                audioInfo.setSong(musicName);
                audioInfo.setSinger(artist);
                audioInfo.setAlbum(album);
                audioInfo.setCover(coverImgName);
            }
        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return audioInfo;
        }
    }

    /**
     * 获取MP3封面图片
     *
     * @param mp3File mp3文件
     * @return 图片字节数组
     */
    private static byte[] getMp3Image(File mp3File) {
        byte[] imageData;
        try {
            MP3File mp3file = new MP3File(mp3File);
            AbstractID3v2Tag tag = mp3file.getID3v2Tag();
            AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");
            FrameBodyAPIC body;
            if (frame != null && !frame.isEmpty()) {
                body = (FrameBodyAPIC) frame.getBody();
                imageData = body.getImageData();
                return imageData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        // 时间戳
        String timeStamp = String.valueOf(new Date().getTime());
        // 文件名
        String fileName = name;
        // 后缀
        String extension = FilenameUtils.getExtension(fileName);
        // 带时间戳的文件名
        String fileNameTimestamp = FilenameUtils.getBaseName(fileName) + timeStamp + "." + extension;
        // 文件大小(B)
        long fileLength = size;
        // 文件大小(KB)保留两位小数，四舍五入
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
        // 后缀 - 小写
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
            String coverImgName = getFileCoverImgName(fileName);
            String filePath = file.getPath();
            String coverImgPath = filePath.replace(fileName, coverImgName);
            File coverImgFile = new File(coverImgPath);
            if (coverImgFile.exists() && coverImgFile.isFile()) {
                coverImgFile.delete();
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

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) {
        File toFile = null;
        try {
            if (file.equals("") || file.getSize() <= 0) {
                file = null;
            } else {
                InputStream ins = null;
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                ins.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toFile;
    }

    /**
     * 获取流文件
     *
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
     * 获取封面请求地址
     *
     * @param filePath         文件相对路径
     * @param fileType         文件类型
     * @param fileDetailInfo   文件详细信息
     * @param taurusProperties
     * @return 略缩图请求地址
     */
    public static String getFileCoverImgUrl(String filePath, String fileType, String fileDetailInfo, TaurusProperties taurusProperties) {
        String coverUrl = "";
        if (StrUtil.isNotEmpty(filePath) && StrUtil.isNotEmpty(fileType)) {
            if (Code.FILE_TYPE_PICTURE.getValue().equals(fileType)) {
                SFileEntity.Image image = JsonUtil.toEntity(fileDetailInfo, SFileEntity.Image.class);
                if (image != null) {
                    // 图片略缩图的请求路径
                    coverUrl = getFileCoverImgUrl(image.getCover(), filePath, taurusProperties);
                }
            } else if (Code.FILE_TYPE_AUDIO.getValue().equals(fileType)) {
                SFileEntity.Audio audio = JsonUtil.toEntity(fileDetailInfo, SFileEntity.Audio.class);
                if (audio != null) {
                    // 音频封面图的请求路径
                    coverUrl = getFileCoverImgUrl(audio.getCover(), filePath, taurusProperties);
                }
            }
        }
        return coverUrl;
    }

    private static String getFileCoverImgUrl(String cover, String filePath, TaurusProperties taurusProperties) {
        String virtualPath = taurusProperties.getFolderRootVirtual().substring(0,
                taurusProperties.getFolderRootVirtual().lastIndexOf("**"));
        //不带文件名的路径
        String fullPath = FilenameUtils.getFullPath(filePath);
        //不带路径的文件名
        String name = FilenameUtils.getName(filePath);
        //封面图片文件名
        String coverImgName = FileUtil.getFileCoverImgName(name);
        return virtualPath + fullPath + coverImgName;
    }

    /**
     * 获取粉末图片名
     *
     * @param timestampName 带时间戳的文件名
     * @return
     */
    public static String getFileCoverImgName(String timestampName) {
        String baseName = FilenameUtils.getBaseName(timestampName);
        return baseName + COVER_EXT + ".png";
    }

}
