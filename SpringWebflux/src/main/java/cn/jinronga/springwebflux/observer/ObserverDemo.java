package cn.jinronga.springwebflux.observer;

import org.springframework.stereotype.Service;

import java.util.Observable;

/**
 * @ClassName ObserverDemo
 * @Author 郭金荣
 * @Date 2021/6/26 11:15
 * @Description ObserverDemo
 * @Version 1.0
 */
@Service
public class ObserverDemo extends Observable {
//    public static void main(String[] args) {
//        ObserverDemo observerDemo = new ObserverDemo();
//        observerDemo.addObserver((o,arg)->{
//            System.out.println("发生变化 = " + arg);
//        });
//
//       observerDemo.addObserver(((o, arg) -> {
//           System.out.println("收到被观察者通知，准备改变" + arg);
//       }));
//       observerDemo.setChanged();
//       observerDemo.notifyObservers();
//
//    }


}
