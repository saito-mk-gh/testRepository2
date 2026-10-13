package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Event;

@Mapper
public interface ListMapper {

	/** 全件取得 */
	List<Event> getAllEvents();

	/** idで取得 */
	Event getEditEventById(int id);

}
