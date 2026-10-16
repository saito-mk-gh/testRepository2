package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.ListDispService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/list")
@RequiredArgsConstructor
public class ListController {

	// DI
	// ※ListDispServiceクラスを、このクラスから利用したい為、
	//   その依存オブジェクトを保持するために
	//   private final でフィールドを宣言する。	
	private final ListDispService listDispService;
	
	// 全ての休祝日を表示する。
	@GetMapping("/all")
	public String showAllEvents(Model model) {
		model.addAttribute("message", "休祝日一覧");
		model.addAttribute("allEvents", listDispService.getAllEvents());
		return "list";
	}

	// 選択した休祝日を編集出来るように表示する。
	// ※URLに出力されている「?from=●●」の部分も取得出来るようにする。
	//     @RequestParam：URLのクエリパラメータ（? 以降）から値を取得する
	//     @PathVariable：URLのパスの一部から値を取得する
	//
	//   @GetMapping()の中で「?from」の記載は不要。
	//   →@RequestParamで取得する為。
	//
	@GetMapping("/edit/{selectId}")
	public String showEditEvent(
			@PathVariable("selectId") int id,
			@RequestParam("from") String strFrom,

			// 遷移元が一覧表示の場合：
			//   year/monthのパラメータは無い。
			//   その場合は null がセットされるようにする。
			//   nullをセット可にする為、int ではなく Integer としている。
			@RequestParam(value = "selectYear", required = false) Integer year,
			@RequestParam(value = "selectMonth", required = false) Integer month,
			
			Model model
			) {
		
		// 画面に渡すデータを追加する
		// ※キーと値をセットにして、画面で使えるように追加する
		model.addAttribute("message", "休祝日編集");
		model.addAttribute("editEvent", listDispService.getEditEventById(id));
		model.addAttribute("ReferringPage", strFrom);		// 遷移元ページの情報
		model.addAttribute("ReferringYear", year);			// 遷移元ページの情報(年)
		model.addAttribute("ReferringMonth", month);		// 遷移元ページの情報(月)
		return "edit";
	}
	
}
