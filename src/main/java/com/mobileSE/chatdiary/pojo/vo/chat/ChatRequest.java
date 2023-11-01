package com.mobileSE.chatdiary.pojo.vo.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data
public class ChatRequest {

    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", locale = "CHINA")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date timestamp;

}

class CustomDateDeserializer extends JsonDeserializer<Date> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            return dateFormat.parse(jsonParser.getText());
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}