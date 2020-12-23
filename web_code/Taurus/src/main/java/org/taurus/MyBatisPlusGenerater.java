package org.taurus;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MyBatisPlusGenerater {
    static String packageName = "org.taurus"; // 当前包名
    static String author = "欣"; // 作者
    static String sqlUrl = "mysql://localhost:3306/"; // 数据库类型及地址
    static String sqlDb = "taurus"; // 数据库名
    static String sqlUser = "root";
    static String sqlPassword = "root";
    static String table = ""; // 表，用逗号隔开

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setServiceName("%sService");
        gc.setMapperName("%sDao");
        gc.setEntityName("%sEntity");
        gc.setFileOverride(false); // 是否覆盖
        mpg.setGlobalConfig(gc);
        

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:" + sqlUrl + sqlDb + "?serverTimezone=UTC&useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(sqlUser);
        dsc.setPassword(sqlPassword);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName);
        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);
        mpg.setPackageInfo(pc);



        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController("templates/controller.java.vm");
        templateConfig.setService("templates/service.java.vm");
        templateConfig.setXml("templates/xml.java.vm");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setRestControllerStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);

        if (!"".equals(table)) {
            strategy.setInclude(table.split(","));
		}
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix("t_"); // 表前缀，如t_user，没有就注释掉此行
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
