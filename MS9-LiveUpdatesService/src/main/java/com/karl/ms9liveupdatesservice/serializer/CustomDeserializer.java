package com.karl.ms9liveupdatesservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karl.ms9liveupdatesservice.dto.GameEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class CustomDeserializer implements Deserializer<GameEvent> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public GameEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), GameEvent.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to GameEvent");
        }
    }

    @Override
    public void close() {
    }
}