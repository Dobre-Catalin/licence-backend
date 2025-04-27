package com.example.licence_backend.Model.Mapper;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class SubmitTestDTO {
    private int timePerQuestion;
    private List<Long> questionIds;
    private Map<Integer, List<String>> responses;
}