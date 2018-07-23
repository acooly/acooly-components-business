package com.acooly.module.test;



import com.acooly.core.common.BootApp;
import com.acooly.core.common.boot.Apps;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author qiubo
 */
@BootApp(sysName = "acooly-components-test", httpPort = 8080)
@ComponentScan({"com.acooly.module.account","com.acooly.module.member"})
public class Main {
    public static void main(String[] args) {
        Apps.setProfileIfNotExists("sdev");
        new SpringApplication(Main.class).run(args);
    }
}