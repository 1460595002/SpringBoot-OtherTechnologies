package com.jinronga.service;

public interface SenService {
    //发送消息
    void Message();

    //创建队列
    void createQueue();

    //创建交换机
    void createExchange();

    //交换机队列进行绑定
    void createBinding();

}
