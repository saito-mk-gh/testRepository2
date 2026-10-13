package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	/** メニューページ表示 */
	@GetMapping("/")
	public String ShowIndex() {
		return "event/index";
	}

	// 休祝日一覧表示ページ
	// ※DBから取得したデータを表示させるので
	//   同コントローラ内での@GetMappingアノテーションを付けた
	//   メソッドの作成不要。
	//   （休祝日一覧表示ページ表示用のコントローラ側で作成）
	
	/** CSV取込ページ表示 */
    @GetMapping("/csv")
    public String showCsvPage() {
        return "csv";
    }
    
}
