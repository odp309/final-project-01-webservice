package com.bni.finalproject01webservice.utility;

import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class ProblemDetailToJson {

    @Autowired
    private DateTimeInterface dateTimeService;

    public String convert(ProblemDetail problemDetail) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"type\":\"").append(problemDetail.getType()).append("\",");
        sb.append("\"title\":\"").append(problemDetail.getTitle()).append("\",");
        sb.append("\"status\":").append(problemDetail.getStatus()).append(",");
        sb.append("\"detail\":\"").append(problemDetail.getDetail()).append("\",");
        sb.append("\"timestamp\":\"").append(dateTimeService.getCurrentDateTimeInJakarta()).append("\"");

        // Append properties
        for (Map.Entry<String, Object> entry : Objects.requireNonNull(problemDetail.getProperties()).entrySet()) {
            sb.append(",\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
        }

        sb.append("}");
        return sb.toString();
    }
}
