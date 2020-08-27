package com.example.demo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import org.junit.Test;

public class CodeGenerator {

    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("zhengxingqu");
        gc.setOpen(false);
        gc.setFileOverride(false);

        gc.setServiceName("%sService");
        gc.setIdType(IdType.ID_WORKER_STR);
        gc.setDateType(DateType.ONLY_DATE);
//        gc.setSwagger2(true);
        autoGenerator.setGlobalConfig(gc);

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://121.37.24.197:3306/blog?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);
        autoGenerator.setDataSource(dsc);

        PackageConfig pc = new PackageConfig();
        pc.setModuleName("xnservice");
        pc.setParent("com.example.demo");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        autoGenerator.setPackageInfo(pc);

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude("ucenter_member");
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setTablePrefix(pc.getModuleName() + "_");
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setInclude("script_info");
//        strategyConfig.setEntityLombokModel(true);

        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setControllerMappingHyphenStyle(true);

        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.execute();
    }

//    @Test
//    public void Test(){
//        AutoGenerator autoGenerator = new AutoGenerator();
//        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        gc.setOutputDir(projectPath + "/src/main/java");
//        gc.setAuthor("zhengxingqu");
//        gc.setOpen(false);
//        gc.setFileOverride(false);
//
//        gc.setServiceName("%sService");
//        gc.setIdType(IdType.ID_WORKER_STR);
//        gc.setDateType(DateType.ONLY_DATE);
//        gc.setSwagger2(true);
//        autoGenerator.setGlobalConfig(gc);
//
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://121.37.24.197:3306/guli?serverTimezone=GMT%2B8");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("123456");
//        dsc.setDbType(DbType.MYSQL);
//        autoGenerator.setDataSource(dsc);
//
//        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("eduservice");
//        pc.setParent("com.atguigu");
//        pc.setController("controller");
//        pc.setEntity("entity");
//        pc.setService("service");
//        pc.setMapper("mapper");
//        autoGenerator.setPackageInfo(pc);
//
//        StrategyConfig strategyConfig = new StrategyConfig();
//        strategyConfig.setInclude("ucenter_member");
//        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
//        strategyConfig.setTablePrefix(pc.getModuleName() + "_");
//        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategyConfig.setEntityLombokModel(true);
//
//        strategyConfig.setRestControllerStyle(true);
//        strategyConfig.setControllerMappingHyphenStyle(true);
//
//        autoGenerator.setStrategy(strategyConfig);
//        autoGenerator.execute();
//    }

}
