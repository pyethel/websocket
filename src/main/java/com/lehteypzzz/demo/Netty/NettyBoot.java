package com.lehteypzzz.demo.Netty;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class NettyBoot implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            try{
                NettyStarter.getInstance().start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
