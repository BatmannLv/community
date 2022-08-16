package com.nowcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author lv
 * @create 2022-08-15 0:55
 */
//Spring没有对kaptcha做整合，所以需要自己使用配置类来配置
@Configuration
public class KaptchaConfig {

    //返回Producer作为Spring管理的一个bean对象
    @Bean
    public Producer kaptchaProducer() {
        //此处使用Properties类对象来装载配置信息，也可以在application.properties中配置
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "32");
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
        //设置随机字符的候选字符库
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYAZ");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //使用干扰类，防止验证码被机器破解
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");

        //实现Producer接口
        DefaultKaptcha kaptcha = new DefaultKaptcha();

        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }

}