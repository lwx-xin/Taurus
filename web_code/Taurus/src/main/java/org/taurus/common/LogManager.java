package org.taurus.common;

import java.io.File;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.taurus.common.code.Code;
import org.taurus.common.code.DateFormat;
import org.taurus.common.code.ExecptionType;
import org.taurus.common.exception.CustomException;
import org.taurus.common.util.DateUtil;
import org.taurus.common.util.FileUtil;
import org.taurus.common.util.LoggerUtil;
import org.taurus.common.util.StrUtil;
import org.taurus.config.load.properties.TaurusProperties;
import org.taurus.entity.SFileEntity;
import org.taurus.entity.SFolderEntity;
import org.taurus.service.SFileService;
import org.taurus.service.SFolderService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Configuration
public class LogManager {}
