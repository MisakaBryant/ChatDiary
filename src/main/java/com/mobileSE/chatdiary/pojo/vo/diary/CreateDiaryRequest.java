package com.mobileSE.chatdiary.pojo.vo.diary;

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
public class CreateDiaryRequest {

    private String title;
    private String content;
    private String position;
    private String type;
    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a", locale = "en_US")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date timestamp;

}

class CustomDateDeserializer extends JsonDeserializer<Date> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.US);

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            return dateFormat.parse(jsonParser.getText());
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}