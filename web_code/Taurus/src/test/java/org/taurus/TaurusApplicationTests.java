package org.taurus;


import javax.annotation.Resource;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.audio.mp4.Mp4InfoReader;
import org.jaudiotagger.audio.mp4.Mp4TagReader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.jaudiotagger.tag.mp4.Mp4FieldKey;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.taurus.common.LogManager;
import org.taurus.config.load.properties.TaurusProperties;
import org.taurus.entity.SFileEntity;

import java.awt.*;
import java.io.File;
import java.io.RandomAccessFile;

@SpringBootTest
class TaurusApplicationTests {
	
	@Autowired
	private LogManager service;

	@Autowired
	private TaurusProperties taurusProperties;

	@Test
	void contextLoads() {
		String adminId = "00000000-0000-0000-0000-000000000000";
		try {
			getMusicInfo(new File("E:\\111.wav"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void getVideoInfo(File file) throws Exception {
		Mp4InfoReader reader = new Mp4InfoReader();
		GenericAudioHeader r = reader.read(new RandomAccessFile(file, "r"));
		System.err.println(r.toString());
	}

	/**
	 * 获取歌曲信息构造Music对象
	 *
	 * @param file 歌曲路径
	 * @return Music对象
	 * @throws Exception Exception
	 */
	public static void getMusicInfo(File file) throws Exception {

		//mp3文件
		MP3File mp3File = (MP3File) AudioFileIO.read(file);
		AudioHeader audioHeader = mp3File.getAudioHeader();
		//标签
		Tag tag = mp3File.getID3v2TagAsv24();

		if (tag != null) {
			//歌曲名
			String musicName = tag.getFirst(FieldKey.TITLE);
			//歌手
			String artist = tag.getFirst(FieldKey.ARTIST);
			//专辑
			String album = tag.getFirst(FieldKey.ALBUM);
			//时长
//			String duration = transDuration(audioHeader.getTrackLength());
			//封面
			String coverPath = "E:\\1.png";
			byte[] imageData = getMp3Image(file);
			if (imageData != null) {
				// byte数组到图片到硬盘上
				try{
					FileImageOutputStream imageOutput = new FileImageOutputStream(new File(coverPath));//打开输入流
					imageOutput.write(imageData, 0, imageData.length);//将byte写入硬盘
					imageOutput.close();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}

			System.err.println(musicName);
			System.err.println(artist);
			System.err.println(album);
			System.err.println(coverPath);
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

}
