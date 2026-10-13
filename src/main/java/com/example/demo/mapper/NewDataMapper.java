package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Event;

@Mapper
public interface NewDataMapper {

	void addEventData(Event event);

}
