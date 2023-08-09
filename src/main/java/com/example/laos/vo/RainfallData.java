package com.example.laos.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Data
@Getter
@Setter
@NoArgsConstructor
public class RainfallData {
    ArrayList<Double> rainfall;
}
