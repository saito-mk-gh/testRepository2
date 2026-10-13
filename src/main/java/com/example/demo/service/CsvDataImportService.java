package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Event;
import com.example.demo.mapper.CsvMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CsvDataImportService {

	// DI
	// ※CsvMapperインターフェイスを、このクラスから利用したい為、
	//   その依存オブジェクトを保持するために
	//   private final でフィールドを宣言する。	
	private final CsvMapper csvMapper;

	// @Transactional
	// ・IllegalArgumentException
	//     (非チェック例外/RuntimeException系)：ロールバック対象
	// ・NumberFormatException 
	//     (非チェック例外/RuntimeException系)：ロールバック対象
	// ・IOException
	//     (RuntimeExceptionではないチェック例外)：ロールバック対象外
	//
	// @Transactional(rollbackFor = IOException.class)
	// ・IllegalArgumentException：ロールバック対象
	// ・NumberFormatException　 ：ロールバック対象
	// ・IOException			 ：ロールバック対象
	//
	// 今回は、下記条件の為、
	//   @Transactional(rollbackFor = IOException.class)
	// とすることで、CSV処理が途中で失敗したら、DB更新も含めて
	// CSVインポート全体をなかったことにすることが出来る。
	//　・CSVファイルを読み込む処理などでIOExceptionが発生する可能性を考慮。
	//
	@Transactional(rollbackFor = IOException.class)
    public int importCsvData(MultipartFile csvFile) throws IOException {

        int count = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        csvFile.getInputStream(),			// 読み取り専用として開いている
//                        StandardCharsets.UTF_8)			// 選択したファイルのエンコード方式：UTF-8 の場合
//                											// ※import java.nio.charset.StandardCharsets のインポートが必要

                        Charset.forName("Windows-31J"))		// 選択したファイルのエンコード方式：ANSI(Windows-31J系) の場合
                										 	// ※import java.nio.charset.Charset のインポートが必要
                )) {

            // 1行目はヘッダー
            String header = reader.readLine();

            if (header == null) {

            	// throw new IllegalArgumentException()
            	// ※ IllegalArgumentException（例外を発生させる）
            	//   ↓
            	// 同メソッド内に該当catchがない
            	//   ↓
            	// 呼び出し元（Controller）へ投げる
            	// 
            	// 例外を発生させて、呼び出し元へ投げる
            	// ※メソッドに「throws IOException」が付いているので
            	//   呼び出し元へ投げることが出来る
            	throw new IllegalArgumentException(
                        "CSVファイルが空です。");
            }

            String line;

            while ((line = reader.readLine()) != null) {

                // 空行を無視
                if (line.isBlank()) {
                    continue;
                }

                String[] columns = line.split(",", -1);

                // カラム数チェック
                if (columns.length != 2) {

                	// throw new IllegalArgumentException()
                	// ※ IllegalArgumentException（例外を発生させる）
                	//   ↓
                	// 同メソッド内に該当catchがない
                	//   ↓
                	// 呼び出し元（Controller）へ投げる
                	// 
                	// 例外を発生させて、呼び出し元へ投げる
                	// ※メソッドに「throws IOException」が付いているので
                	//   呼び出し元へ投げることが出来る
                	throw new IllegalArgumentException(
                            "CSVのカラム数が不正です。");
                }

                
                Event event = new Event();

                try {
                	
                	// DateTimeFormatter：日付・時刻の書式を扱うクラス
                	// formatter：作成したフォーマッタを格納する変数名
                	// ofPattern("yyyy/M/d")：日付の書式を "yyyy/M/d" に指定                	
                	//
                	// → LocalDate.parseメソッドの引数の型を指定しない場合「yyyy-MM-dd」形式でないとエラーになる
                	//    CSVファイル内での日付は "yyyy/M/d" 形式の為、指定する必要がある。
                	//
                	DateTimeFormatter formatter =
                	        DateTimeFormatter.ofPattern("yyyy/M/d");
                	
                    event.setEventDate(
                    		LocalDate.parse(columns[0].trim(), formatter)
                    		);

                    event.setEventTitle(
                            columns[1].trim()
                            );

                } catch (NumberFormatException e) {

                	// NumberFormatExceptionについて：
                	// catchで捕捉（ここではimportCsvData()内で処理している）
                	// しかし、
                	//    throw new IllegalArgumentException()
                	// として、他と同様に呼び出し元（Controller）へ投げる
                	// ※メソッドに「throws IOException」が付いているので
                	//   呼び出し元へ投げることが出来る
                	//   また、第2引数をセットすることで
                	//   元の例外 e を原因（cause）として引き継いでいる
                    throw new IllegalArgumentException(
                            "CSVの数値項目が不正です。", e);
                }

                csvMapper.insertCsvData(event);

                count++;
            }
        }

        return count;
    }
	
}
