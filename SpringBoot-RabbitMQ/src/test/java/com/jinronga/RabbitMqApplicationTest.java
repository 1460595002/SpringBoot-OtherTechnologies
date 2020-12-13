package com.jinronga;

import com.jinronga.service.SenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitMqApplicationTest {
    @Autowired
    SenService senService;

    @Test
    void sendMessageTest02() {
      // senService.createQueue();
          //senService.createBinding();
           // senService.createExchange();
        senService.Message();

    }


}
