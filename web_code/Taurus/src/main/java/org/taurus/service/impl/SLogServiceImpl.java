package org.taurus.service.impl;

import org.taurus.entity.SFileEntity;
import org.taurus.entity.SFolderEntity;
import org.taurus.entity.SLogEntity;
import org.taurus.common.code.Code;
import org.taurus.common.code.DateFormat;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.FileUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.config.load.properties.TaurusProperties;
import org.taurus.dao.SLogDao;
import org.taurus.service.SFileService;
import org.taurus.service.SFolderService;
import org.taurus.service.SLogService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.File;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 欣
 * @since 2021-01-13
 */
@Service
public class SLogServiceImpl extends ServiceImpl<SLogDao, SLogEntity> implements SLogService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private TaurusProperties taurusProperties;

	@Resource
	private SFolderService folderService;

	@Resource
	private SFileService fileService;

	@Override
	public void saveLogFile(Exception exception, String userId) throws CustomException {
		if (exception == null || StrUtil.isEmpty(userId)) {
			return;
		}

		// aa 获取根目录
		SFolderEntity rootFolderEntity_query = new SFolderEntity();
		rootFolderEntity_query.setFolderName(userId);
		rootFolderEntity_query.setFolderOwner(userId);
		rootFolderEntity_query.setFolderParent("");
		rootFolderEntity_query.setFolderDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SFolderEntity> rootQueryWrapper = new QueryWrapper<>(rootFolderEntity_query);
		SFolderEntity rootFolderEntity = folderService.getOne(rootQueryWrapper);
		if (rootFolderEntity == null) {
			return;
		}

		// aa 获取系统资源目录
		SFolderEntity systemFolderEntity_query = new SFolderEntity();
		systemFolderEntity_query.setFolderName(taurusProperties.getFolderSystemName());
		systemFolderEntity_query.setFolderOwner(userId);
		systemFolderEntity_query.setFolderParent(rootFolderEntity.getFolderId());
		systemFolderEntity_query.setFolderDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SFolderEntity> systemQueryWrapper = new QueryWrapper<>(systemFolderEntity_query);
		SFolderEntity systemFolderEntity = folderService.getOne(systemQueryWrapper);
		if (systemFolderEntity == null) {
			return;
		}

		// aa 获取日志文件目录
		SFolderEntity logFolderEntity_query = new SFolderEntity();
		logFolderEntity_query.setFolderName(taurusProperties.getFolderLogName());
		logFolderEntity_query.setFolderOwner(userId);
		logFolderEntity_query.setFolderParent(systemFolderEntity.getFolderId());
		logFolderEntity_query.setFolderDelFlg(Code.DEL_FLG_1.getValue());
		QueryWrapper<SFolderEntity> logQueryWrapper = new QueryWrapper<>(logFolderEntity_query);
		SFolderEntity logFolderEntity = folderService.getOne(logQueryWrapper);
		if (logFolderEntity == null) {
			return;
		}

		// aa 保存日志文件信息
		String logFolderId = logFolderEntity.getFolderId();
		String folderPath = folderService.getFolderPath(logFolderId, userId);

		String formatDate = DateUtil.formatDate(new Date(), DateFormat.LOG_NAME);
		String logFileName = formatDate + ".log";

		// aa 新建一个文件
		File file = new File(folderPath + logFileName);
		file.setReadOnly();
		// aa 当前时间
		LocalDateTime nowTime = DateUtil.getLocalDateTime();

		SFileEntity fileEntity = FileUtil.getInfoByFile(file, nowTime);
		fileEntity.setFileFolder(logFolderId);
		fileEntity.setFileOwner(userId);
		fileEntity.setFilePath(
				folderService.getFolderRelativePath(logFolderId, userId) + fileEntity.getFileNameTimestamp());
		fileEntity.setFileCreateUser(userId);
		fileEntity.setFileModifyUser(userId);
		if (fileService.save(fileEntity)) {
			throw new CustomException(ExecptionType.FILE, null, "记录日志文件信息失败");
		}

		// aa 保存日志信息
		SLogEntity logEntity = new SLogEntity();
		logEntity.setLogId(StrUtil.getUUID());
		logEntity.setLogHappenTime(nowTime);
		logEntity.setLogFile(fileEntity.getFileId());
		logEntity.setLogStatus(Code.LOG_STATUS_0.getValue());
		logEntity.setLogUser(userId);
		if (exception instanceof CustomException) {
			CustomException customException = (CustomException) exception;
			logEntity.setLogRemark(customException.getRemark());
			logEntity.setLogModule(customException.getExecptionType().getModuleName());
		} else {
			logEntity.setLogRemark(exception.getMessage());
			logEntity.setLogModule(ExecptionType.SYSTEM.getModuleName());
		}
		if (!save(logEntity)) {
			throw new CustomException(ExecptionType.SYSTEM, null, "记录日志信息失败");
		}

		PrintStream stream = null;
		try {
			// aa 创建文件的输出流
			stream = new PrintStream(file);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExecptionType.SYSTEM_LOG, null, "记录日志文件失败");
		}
		exception.printStackTrace(stream);
		// aa 关闭输出流
		stream.flush();
		stream.close();
	}

}
