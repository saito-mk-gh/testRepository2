package com.example.demo.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Event;

import lombok.Data;

@Data
public class CalendarDayDto {

	// カレンダーの日付を格納
	private LocalDate targetDate;
	
	// 表示対象月の日付かどうかの判定フラグ
    private boolean blnCurrentMonth;

 // その日に該当するDBの予定データ一覧を格納
    private List<Event> eventsForDate = new ArrayList<>();

    // コンストラクタを生成
    // ※必要となるものが date と blnCurrentMonth の 2つのみの為
    //   アノテーションを付けずにコンストラクタを生成することとする。
    public CalendarDayDto(LocalDate targetDate, boolean blnCurrentMonth) {
        this.targetDate = targetDate;
        this.blnCurrentMonth = blnCurrentMonth;
    }

}
