package com.sport.demo.dto.event;

import lombok.Data;

@Data
public class EventResponse {
    private Integer id;
    private String name;
    private String description;
    private String date;
    private String status;
    private Boolean recommended;
    private String organizerName;
    private String eventType;
}
