package com.safaricom.msuseraccountservice.listener;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserAccountChangeListener {

    @KafkaListener(topics = "dbserver1.db_user_account.UserAccount", groupId = "user-account-service-group")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Received record: " + record.value());
        // Process the change event (e.g., update your application state)
    }
}
