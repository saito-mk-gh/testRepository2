package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.SelectDataDeleteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/list")
@RequiredArgsConstructor
public class DeleteController {

	// DI
	// ※SelectDatadeleteServiceクラスを、このクラスから利用したい為、
	//   その依存オブジェクトを保持するために
	//   private final でフィールドを宣言する。	
	private final SelectDataDeleteService selectDataDeleteService;

	@PostMapping("/delete")
	public String deleteEventData(
			@RequestParam("Id") int id,

			//RedirectAttributes:
			//  Spring MVC のコントローラーで、
			//    return "redirect:/user/list";
			//  のようにリダイレクトするときに値を渡すためのもの。
			RedirectAttributes redirectAttributes
			
			) {

		selectDataDeleteService.deleteEventData(id);

	    // addFlashAttribute：
	    //   リダイレクト先の次のリクエストで一時的に使える属性
	    //   URLには出さず、リダイレクト先の画面へ一時的に値を渡す
	    // ※addFlashAttributeの「Flash」：
	    //   次のリクエストで一度だけ利用するための一時的なデータ
	    //   というイメージ
	    //
	    redirectAttributes.addFlashAttribute("DeleteSuccess", "削除しました");

	    return "redirect:/list/all";
	}
	
}
