package com.api.Serialization;

import com.api.Models.Time;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class TimeWinnersSerializer extends StdSerializer<Time> {

    public TimeWinnersSerializer() {
        this(null);
    }

    public TimeWinnersSerializer(Class<Time> t) {
        super(t);
    }

    @Override
    public void serialize(Time time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("player_id", time.getPlayer().getId());
        jsonGenerator.writeNumberField("time", time.getTime());
        jsonGenerator.writeEndObject();
    }
}
