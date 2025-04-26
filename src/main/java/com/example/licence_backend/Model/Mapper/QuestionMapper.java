package com.example.licence_backend.Model.Mapper;

import com.example.licence_backend.Model.Test.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public class QuestionMapper {
    QuestionMapper INSTANCE= Mappers.getMapper(QuestionMapper.class);
}
