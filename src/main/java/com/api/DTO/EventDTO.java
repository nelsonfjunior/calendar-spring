package com.api.DTO;

import java.util.Date;

public record EventDTO(
    String summary,
    String location,
    String description,
    Date startTime,
    Date endTime
) {
    
}
