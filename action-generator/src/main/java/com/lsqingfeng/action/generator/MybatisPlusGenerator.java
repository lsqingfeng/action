package com.lsqingfeng.action.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class MybatisPlusGenerator {
    private static final String databaseURL = "jdbc:mysql://127.0.0.1:3306/action?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    private static final String userName = "root";
    private static final String password = "root";
    private static String projectPath = System.getProperty("user.dir");
    private static String basePackage = "com.lsqingfeng.action.core";

    public static void main(String[] args) {
        //可按模块生成，如果不同业务的mapper想要放到各自的module下，可采用这种方式。如果都放到core下面，直接按ACTION_TEST配置即可
//        generate(ConfigEnum.CREDIT);
//        generate(ConfigEnum.RISK);
//        generate(ConfigEnum.SYS);
//        generate(ConfigEnum.MAN);
        generate(ConfigEnum.ACTION_TEST);
    }

    public enum ConfigEnum {
        // 注意： mac使用  /action-core   windows使用 \\action-core
//        ACTION_TEST("lsqingfeng","/action-core",basePackage,"","man_map_role_menu,man_map_user_role,man_menu,man_role,man_user"),
        ACTION_TEST("lsqingfeng","/action-core",basePackage,"","quartz_info"),

        /**
         * 授信模块
         */
        CREDIT("sh.Liu", "\\action-credit", basePackage+".credit", "",
                "credit_report_req_log,credit_report_scene,credit_report_supplier,credit_report_supplier_service"),
        /**
         * 风控反欺诈模块
         */
        RISK("sh.Liu", "\\action-risk", basePackage+".risk", "",
                "risk_engine,risk_engine_result_rule,risk_engine_result_score,risk_engine_rule,risk_engine_score,risk_engine_user_feature,risk_model,risk_model_req_log,risk_scheme,risk_scheme_map"),

        SYS("sunfeilong", "\\action-core", basePackage, "sys",
                ""),

        MAN("sunfeilong","\\action-core",basePackage+".man","",
                "man_user_channel_relation"),

        BIZ("sunfeilong","\\action-core",basePackage,"biz",
                "biz_topic" );


        private final String author;
        /**
         * 项目路径
         */
        private final String projectPath;
        /**
         * 项目包名
         */
        private final String packageName;
        /**
         * 模块儿名
         */
        private final String modelName;
        /**
         * 数据库表明，多个用逗号分隔
         */
        private String tables;

        ConfigEnum(String author, String projectPath, String packageName, String modelName, String tables) {
            this.author = author;
            this.projectPath = projectPath;
            this.packageName = packageName;
            this.modelName = modelName;
            this.tables = tables;
        }

        /**
         * 设置生成的表，多表以逗号分隔
         *
         * @param tables 表，多个用逗号分隔
         */
        public void setTables(String tables) {
            this.tables = tables;
        }
    }

    /**
     * 根据配置生成Mapper等文件
     *
     * @param generateConfig 初始化配置
     */
    public static void generate(ConfigEnum generateConfig) {
        AutoGenerator mpg = new AutoGenerator();
        GlobalConfig config = config(generateConfig);
        mpg.setGlobalConfig(config);
        mpg.setDataSource(datasource());
        PackageConfig packageInfo = packageConfig(generateConfig);
        mpg.setPackageInfo(packageInfo);
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String path = projectPath + generateConfig.projectPath + "/src/main/resources/mapper/" + packageInfo.getModuleName() + "/";
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return path + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        InjectionConfig injectionConfig = injectionConfig();
        injectionConfig.setFileOutConfigList(focList);
        mpg.setCfg(injectionConfig);


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude(generateConfig.tables.split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(packageInfo.getModuleName() + "_");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    private static GlobalConfig config(ConfigEnum generateConfig) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath + generateConfig.projectPath + "/src/main/java");
        globalConfig.setAuthor(generateConfig.author);
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setFileOverride(false);
        globalConfig.setDateType(DateType.TIME_PACK);
        globalConfig.setOpen(false);
        return globalConfig;
    }

    private static DataSourceConfig datasource() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(databaseURL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername(userName);
        dataSourceConfig.setPassword(password);
        return dataSourceConfig;
    }

    private static PackageConfig packageConfig(ConfigEnum generateConfig) {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(generateConfig.modelName);
        packageConfig.setParent(generateConfig.packageName);
        return packageConfig;
    }

    private static InjectionConfig injectionConfig() {
        return new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
    }
}
