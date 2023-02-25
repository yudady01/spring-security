package cn.jpanda.demo.manager;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {
    private static final String TABLE_NAMES = "sys_user";

    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        globalConfig.setAuthor("JPanda");
        globalConfig.setOpen(false);
        globalConfig.setServiceName("%sService");
        globalConfig.setEnableCache(false);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://192.168.1.138:3306/test?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("test");
        dataSourceConfig.setPassword("test");

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("cn.jpanda.demo.manager");
        packageConfig.setController("controller.api");
        packageConfig.setService("service");
        packageConfig.setServiceImpl(packageConfig.getService() + "." + "impl");
        packageConfig.setMapper("dao");
        packageConfig.setXml(packageConfig.getMapper() + "." + "xml");
        packageConfig.setEntity("entity");


        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(TABLE_NAMES.split(","));
        strategy.setControllerMappingHyphenStyle(true);

        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setStrategy(strategy);

        autoGenerator.execute();
    }
}
