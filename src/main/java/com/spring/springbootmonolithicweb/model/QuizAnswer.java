package com.spring.springbootmonolithicweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizAnswer {
    private Integer id;
    private String response;
}
