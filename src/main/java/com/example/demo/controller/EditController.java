package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Event;
import com.example.demo.service.EditDataUpdateService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("list")
@RequiredArgsConstructor
public class EditController {

	// DI
	// ※EditDataUpdateServiceクラスを、このクラスから利用したい為、
	//   その依存オブジェクトを保持するために
	//   private final でフィールドを宣言する。	
	private final EditDataUpdateService editDataUpdateService;
	
	@PostMapping("/edit")
	public String updateEventData(
			@RequestParam("Id") int id,
			@RequestParam("Date") LocalDate date,
			@RequestParam("Type") String type,
			@RequestParam("Title") String title,
			@RequestParam("Detail") String detail,

			//RedirectAttributes:
			//  Spring MVC のコントローラーで、
			//    return "redirect:/user/list";
			//  のようにリダイレクトするときに値を渡すためのもの。
			RedirectAttributes redirectAttributes

			) {

	    Event event = new Event();

	    // edit.htmlから取得した各値をセット
	    // ※作成日(createdAt)は DB更新時に不要な為、取得対象外
	    //   更新日(updatedAt)は Update文でセットする為、取得対象外
	    event.setEventId(id);			// ID
	    event.setEventDate(date);		// 日付
	    event.setEventType(type);		// タイプ
	    event.setEventTitle(title);	// タイトル
	    event.setEventDetail(detail);	// 詳細

	    editDataUpdateService.updateEventData(event);

	    // addFlashAttribute：
	    //   リダイレクト先の次のリクエストで一時的に使える属性
	    //   URLには出さず、リダイレクト先の画面へ一時的に値を渡す
	    // ※addFlashAttributeの「Flash」：
	    //   次のリクエストで一度だけ利用するための一時的なデータ
	    //   というイメージ
	    //
	    redirectAttributes.addFlashAttribute("UpdateSuccess", "更新しました");

	    return "redirect:/list/all";
	}

}
