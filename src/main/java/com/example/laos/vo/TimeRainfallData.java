package com.example.laos.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class TimeRainfallData {
    private String times;
    @JsonBackReference
    private RainfallData values;
}
