package com.api.Serialization;

import com.api.Models.Contest;
import com.api.Models.Time;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ContestsParseSerializer extends StdSerializer<Contest> {

    public ContestsParseSerializer() {
        this(null);
    }

    public ContestsParseSerializer(Class<Contest> t) {
        super(t);
    }

    @Override
    public void serialize(Contest contest, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", contest.getId());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = format.format(contest.getDate());

        jsonGenerator.writeStringField("date", formattedDate);
        jsonGenerator.writeNumberField("prizePool", contest.getPrizePool());
        jsonGenerator.writeStringField("city", contest.getCity());
        jsonGenerator.writeNumberField("maxPlayers", contest.getMaxPlayers());

        String site = contest.getSite();
        if (site == null) {
            jsonGenerator.writeStringField("site", "");
        } else {
            jsonGenerator.writeStringField("site", contest.getSite());
        }
        jsonGenerator.writeArrayFieldStart("winners");
        int i = 0;
        for (Time time : contest.getTime()) {
            if (i == 3) {
                break;
            }
            jsonGenerator.writeObject(contest.getTime().get(i));
            i++;
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}