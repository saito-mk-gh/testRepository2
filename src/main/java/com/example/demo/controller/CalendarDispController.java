package com.example.demo.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.CalendarDayDto;
import com.example.demo.service.CalendarDispService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CalendarDispController {

	// DI
	// ※CalendarDispServiceクラスを、このクラスから利用したい為、
	//   その依存オブジェクトを保持するために
	//   private final でフィールドを宣言する。	
	private final CalendarDispService calendarDispService;

	// 例：
	//   2026年10月を表示したい場合は、次のURLにアクセス
	//   http://localhost:8080/calendar?year=2026&month=10
	//
	//   @RequestParam(required = false) Integer year の場合：
	//   ※required = false：URLに year がなくてもエラーにしないという指定
	//     Integer year：数値を扱う。null も入れられる。
	//     int year    ：数値を扱う。null は入れなれない。
	//
	//   URL：/calendar?year=2026 の場合：Java変数 year に 2026 が入る。
	//   URL：/calendar           の場合：Java変数 year に null が入る。
	//
	//   @RequestParam Integer year の場合：
	//   URL：/calendar?year=2026 の場合：Java変数 year に 2026 が入る。
	//   URL：/calendar           の場合：エラー
	//   →required = false を指定しない場合、通常はパラメータが
	//     必須となるため、year がないURLではエラーになる。
	//
	//  今回は、URLにyearが指定されない場合に null を受け取りたいので
	//  Integerを使用。
	//
	@GetMapping("/calendar")
    public String showCalendar(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model) {

		// デフォルトのタイムゾーンのシステム・クロックから現在の日付を取得
        LocalDate today = LocalDate.now();

        // 三項演算子で記述
        // 変数 year/month を確認：
        //   nullでない場合(trueの場合)は 変数 year/month の値を使う
        //   nullの場合(falseの場合)は、今年の年/月を使う
        //
        int targetYear = year != null ? year : today.getYear();
        int targetMonth = month != null ? month : today.getMonthValue();

        // 表示する年月を作成
        YearMonth target = YearMonth.of(targetYear, targetMonth);

        // カレンダー表示のみ → 休祝日データ表示込みに変更
        // 表示する年月のカレンダーを作成
//        List<List<LocalDate>> weeks =
//        		calendarDispService.createCalendar(
//                        target.getYear(),
//                        target.getMonthValue());

        // 表示する年月のカレンダーを作成（休祝日データ入り）
        List<List<CalendarDayDto>> weeks =
        		calendarDispService.createCalendar(
                        target.getYear(),
                        target.getMonthValue());
        
        // 表示年月の
        //   日付データ(weeks)
        //   表示年(target.getYear())
        //   表示月(target.getMonthValue())
        // をControllerからHTMLへデータを渡す
        model.addAttribute("weeks", weeks);
        model.addAttribute("year", target.getYear());
        model.addAttribute("month", target.getMonthValue());

        // 前月、翌月の値を格納
        // ※年をまたぐ場合も自動的に計算される。
        YearMonth previous = target.minusMonths(1);
        YearMonth next = target.plusMonths(1);

        // 表示年月の前月/翌月の
        //   表示年(target.getYear())
        //   表示月(target.getMonthValue())
        // をControllerからHTMLへデータを渡す
        model.addAttribute("prevYear", previous.getYear());
        model.addAttribute("prevMonth", previous.getMonthValue());
        model.addAttribute("nextYear", next.getYear());
        model.addAttribute("nextMonth", next.getMonthValue());

        // 表示するHTMLテンプレートの名前を返す
        return "calendar";
    }	
	
}
