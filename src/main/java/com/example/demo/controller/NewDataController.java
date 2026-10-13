package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Event;
import com.example.demo.service.NewDataImportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("list")
@RequiredArgsConstructor
public class NewDataController {

	// DI
	// ※NewDataImportServiceクラスを、このクラスから利用したい為、
	//   その依存オブジェクトを保持するために
	//   private final でフィールドを宣言する。	
	private final NewDataImportService newDataImportService;
	
	@GetMapping("/new")
    public String newEventData(
    		Model model
    		) {

		model.addAttribute("newData", new Event());

		return "new";
    }

	@PostMapping("/new")
    public String addEventData(
    		@ModelAttribute("addData") Event event,

    		//RedirectAttributes:
			//  Spring MVC のコントローラーで、
			//    return "redirect:/list/all";
			//  のようにリダイレクトするときに値を渡すためのもの。
			RedirectAttributes redirectAttributes
    		
    		) {

		newDataImportService.addEventData(event);

	    // addFlashAttribute：
	    //   リダイレクト先の次のリクエストで一時的に使える属性
	    //   URLには出さず、リダイレクト先の画面へ一時的に値を渡す
	    // ※addFlashAttributeの「Flash」：
	    //   次のリクエストで一度だけ利用するための一時的なデータ
	    //   というイメージ
	    //
	    redirectAttributes.addFlashAttribute("AddSuccess", "登録しました");

        return "redirect:/list/all";
    }


}
