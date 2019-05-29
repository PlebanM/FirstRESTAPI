package com.api.Serialization;

import com.api.Models.Time;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class TimeSerialization extends StdSerializer<Time> {


    protected TimeSerialization() {
        this(null);
    }

    public TimeSerialization(Class<Time> timeClass) {
        super(timeClass);
    }



    @Override
    public void serialize(Time time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("idContest", time.getContest().getId());
        jsonGenerator.writeNumberField("time", time.getTime());
        jsonGenerator.writeEndObject();


    }
}
