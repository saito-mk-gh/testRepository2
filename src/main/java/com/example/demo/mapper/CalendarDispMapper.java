package com.example.demo.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Event;

@Mapper
public interface CalendarDispMapper {

	//	List<Schedule>：
	//	  DBから取得した予定データを複数件格納して返す
	//	findByPeriod
	//	  指定期間の予定を取得するメソッド名
	//	sDate
	//	  取得期間の開始日
	//	eDate
	//	  取得期間の終了日（この日付は含まない）	
	List<Event> findByEvent(
			@Param("startDate") LocalDate sDate,
			@Param("endDate") LocalDate eDate
		);
	
}
