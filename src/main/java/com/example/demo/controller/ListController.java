package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@GetMapping("/edit/{selectId}")
	public String showEditEvent(
			@PathVariable("selectId") int id,
			Model model
			) {
		model.addAttribute("message", "休祝日編集");
		model.addAttribute("editEvent", listDispService.getEditEventById(id));
		return "edit";
	}
	
}
