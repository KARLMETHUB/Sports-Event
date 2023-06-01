package com.karl.ms9liveupdatesservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karl.ms9liveupdatesservice.dto.GameEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class CustomSerializer implements Serializer<GameEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, GameEvent data) {
        try {
            if (data == null){
                System.out.println("Null received at serializing");
                return null;
            }
            System.out.println("Serializing..." + data.getEventLog());
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing GameEvent to byte[]");
        }
    }

    @Override
    public void close() {
    }
}
