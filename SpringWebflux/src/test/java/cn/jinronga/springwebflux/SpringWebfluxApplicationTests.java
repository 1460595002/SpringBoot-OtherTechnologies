package cn.jinronga.springwebflux;

import cn.jinronga.springwebflux.observer.ObserverDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.Observable;

@SpringBootTest
class SpringWebfluxApplicationTests {
    @Autowired
    private ObserverDemo demo;

    @Test
    void contextLoads() throws NoSuchFieldException, IllegalAccessException {
        demo.addObserver(((o, arg) -> {

            System.out.println("发生变化！");
        }));
        demo.addObserver((o, arg) -> {
            System.out.println("收到被观察者通知，准备改变");
        });
        Field declaredField = ObserverDemo.class.getSuperclass().getDeclaredField("changed");
        declaredField.setAccessible(true);
        declaredField.setBoolean(demo, true);
        demo.notifyObservers();
    }

}
