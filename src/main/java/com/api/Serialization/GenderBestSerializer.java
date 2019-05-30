package com.api.Serialization;

import com.api.Models.Player;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class GenderBestSerializer extends StdSerializer<Player> {

    public GenderBestSerializer() {
        this(null);
    }

    public GenderBestSerializer(Class<Player> t) {
        super(t);
    }

    @Override
    public void serialize(Player player, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", player.getId());
        jsonGenerator.writeStringField("firstname", player.getFirst_name());
        jsonGenerator.writeStringField("lastname", player.getLast_name());
        jsonGenerator.writeStringField("country", player.getCountry());
        jsonGenerator.writeStringField("club", player.getClub());
        jsonGenerator.writeStringField("city", player.getCity());
        jsonGenerator.writeNumberField("runs_attended", player.getTimes().size());
        jsonGenerator.writeEndObject();
    }
}
