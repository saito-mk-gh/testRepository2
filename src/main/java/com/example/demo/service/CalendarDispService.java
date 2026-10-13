package com.example.demo.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CalendarDispService {

	
	// List<List<LocalDate>> の構造について：
	//   外側の List<>：
	//     複数週間分をまとめたもの
	//	 内側の List（List<LocalDate>）：
	//     1週間分など、日付をまとめたもの
	//	 LocalDate  ：年月日を表すデータ
	//	 null       ：日付が存在しない空欄
	// →週ごとに7個の日付をまとめたリストを返している
	public List<List<LocalDate>> createCalendar(
            int year,
            int month) {

		// 指定された年月を作成
		// YearMonthクラス：
		//   年と月だけを扱うためのクラス。  
		// YearMonth.of(year, month)
		//   →年月を指定して生成
		// 例：YearMonth.of(2026, 2)の出力結果は 2026-02。
		//     
        YearMonth yearMonth = YearMonth.of(year, month);

        // その月の1日
        LocalDate firstDay = yearMonth.atDay(1);
        
        // その月の最終日（28日、29日、30日、31日）
        int lastDay = yearMonth.lengthOfMonth();

        // ①曜日を取得する
        //  例：firstDay が 2026年10月01日の場合
        //      取得する値は THURSDAY/木曜日。
        DayOfWeek dayOfWeek = firstDay.getDayOfWeek();
        
	    // ②取得した曜日を数値に変換する
        //   getDayOfWeek()で取得した曜日を数値に変換
        //     月曜日/MONDAYは「1」
        //     日曜日/SUNDAYは「7」
        //   例：firstDay が 2026年10月01日の場合
        //       取得する値は THURSDAY/木曜日を数値変換して「4」。
        int value = dayOfWeek.getValue();		
        
        // 取得した値(value)を7で割った余りを算出
        // この余りの値が後述の for文 で使われる
        //   例：2026年10月01日の場合
        //       4 ÷ 7 となり、余り「4」となる。
        int startOffset = value % 7;
        
        // ①と②を1行で表現：
        // int startOffset = firstDay.getDayOfWeek().getValue() % 7;
        
        // カレンダー全体
        List<List<LocalDate>> weeks = new ArrayList<>();
        
        // 1週間分のデータ
        List<LocalDate> week = new ArrayList<>();

        // 1日より前の空欄を追加
        // 例：2026年10月01日の場合
        //     startOffsetは 4 となるので、このfor分は4回ループする。
        for (int i = 0; i < startOffset; i++) {
            week.add(null);
        }

        // 1日から月末日まで追加
        // 例：2026年10月の場合
        //     2026年10月01日から2026年10月31日までを追加。
        for (int day = 1; day <= lastDay; day++) {
            week.add(yearMonth.atDay(day));

            // 7日分になったら1週間を完成させる
            // 例：2026年10月第1週は 空白4つ + 日付3日分
            if (week.size() == 7) {
                
            	weeks.add(week);			// 1週間分(7日分)のデータ(week)を
                							// 複数週分を管理する weeks に追加

                week = new ArrayList<>();	// 次の週を入れるための
                							// 新しいリスト(week)を作成
            }
        }

        // 最終週の空欄を追加
        // ※week.isEmpty()メソッドはデータが0件かどうかを確認。
        //	 「!」が付いているので判定条件はデータが0件でない場合となる。
        //
        //   week.size()は、weekに格納されている件数を確認
        //   
        // 例：2026年9月の場合
        //     上記for文にて、week には
        //		 2026/09/27～2026/09/30
        //     の4日分が追加(add)された状態で抜けている。
        //     week内のデータ 0 では無い（空ではない）為、true判定となる。
        //     week.size()が「7」即ち、1週間分のデータになったら 
        //     while文から抜けて、複数週分を管理する weeks に追加している
        if (!week.isEmpty()) {
            while (week.size() < 7) {
                week.add(null);
            }
            weeks.add(week);
        }

        return weeks;
    }
	
}
