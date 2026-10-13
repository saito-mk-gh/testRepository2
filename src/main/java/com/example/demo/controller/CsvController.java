package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.CsvDataImportService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/csv")
@RequiredArgsConstructor
public class CsvController {

	// DI
	// ※CsvDataImportServiceクラスを、このクラスから利用したい為、
	//   その依存オブジェクトを保持するために
	//   private final でフィールドを宣言する。	
	private final CsvDataImportService csvDataImportService;

    @PostMapping("/import")
    public String importCsvData(
            @RequestParam("csvFileName") MultipartFile csvFile,
            Model model) {

        try {

		    // エラーチェックの順番：
		    // ① ファイルオブジェクトの存在確認
		    // ② 空ファイル確認
		    // ③ ファイル名確認
		    // ④ CSV確認
	        	
	        // ① ファイルオブジェクトの存在確認
	        // ※このcsvFileについて：
	        //   SpringがHTTPリクエストに含まれているファイルを
	        //   受け取って作った MultipartFile オブジェクト。
	        //
	        //   csvFile == null という状態は、
	        //   そもそもSpring側に「ファイルを表すオブジェクト」が
	        //   渡ってきていない、という状態（想定外の事態）
	        // 
		    if (csvFile == null) {
		       		model.addAttribute(
		                   "error",
		                   "①CSVファイルを選択してください。"
		                   );
		       	return "csv";
		    }
        	
		    // ② 空ファイル確認（ 下記 a) b) の確認）
		    //    a) ファイルが未選択
		    //    b) ファイルは選択したが、中身が0バイト
		    //
		    //    【補足】
		    //    今回、csv.html において、<input type="file"> に
		    //    required 属性を付けているので[CSV取込]ボタンを押下時、
		    //      a) ファイルが未選択の判定について
		    //   の処理は、この判定式ではなくブラウザ標準の仕様が
		    //   有効となり
        	//     「ファイルを選択してください。」
        	//   と吹き出し表示される。
		    //   ※ b)のパターンの場合は、required 属性の有無に関係なく
		    //      ここでの判定処理が有効。
		    //
		    if (csvFile.isEmpty()) {
	        		model.addAttribute(
	        				"error",
	                        "②CSVファイルを選択してください。"
	        				);
	            return "csv";
	        }

	        // ③ ファイル名確認 と ④ CSV確認
	        //    
            // 選択したファイルの名前を取得。
            String fileName = csvFile.getOriginalFilename();

            // ③ ファイル名確認
            // ※①では、オブジェクトの存在確認をしているだけで
            //   ファイル名の有無は確認していない為、
            //   ここでの確認は必要。
            if (fileName == null) {
            	// ファイル名が取得できない
                model.addAttribute(
                        "error",
                        "③ファイル名を取得できませんでした。");
            	return "csv";
            }
            
            // ④ CSV確認（拡張子チェック）
            if (!fileName.toLowerCase().endsWith(".csv")) {

            	// 選択したファイルがcsvファイルでない場合
                model.addAttribute(
                        "error",
                        "④CSVファイルを選択してください。");
                return "csv";
            }
        	
          int count =
        		  csvDataImportService.importCsvData(csvFile);
          
          model.addAttribute(
        		  "message",
        		  count + "件のデータを登録しました。");

        } catch (IllegalArgumentException e) {

        	// 不正な引数による例外
        	model.addAttribute(
                    "error",
                    e.getMessage());

        } catch (Exception e) {

        	// 何らかの例外
            model.addAttribute(
                    "error",
                    "CSV取込中にエラーが発生しました。");
        }

        return "csv";
    }

}
