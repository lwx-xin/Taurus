package org.taurus;

import javax.imageio.stream.FileImageOutputStream;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.audio.mp4.Mp4InfoReader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.taurus.common.LogManager;
import org.taurus.config.load.data.InitAdminUserData;
import org.taurus.config.load.properties.TaurusProperties;

import java.io.File;
import java.io.RandomAccessFile;

@SpringBootTest
class TaurusApplicationTests {
	
	@Autowired
	private LogManager service;

	@Autowired
	private TaurusProperties taurusProperties;

	@Autowired
	private InitAdminUserData initAdminUserData;

	@Test
	void initData(){
		initAdminUserData.initData();
	}

	@Test
	void contextLoads() {
	}
}
