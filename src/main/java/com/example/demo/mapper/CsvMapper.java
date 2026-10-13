package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Event;

@Mapper
public interface CsvMapper {

		// insertCsvData：メソッド名
		//         Csvファイルから読み取ったデータに対する
		//         インサート処理用としている為、分かりやすい名称にしている
		//         このメソッド名は
		//           CsvMapper(xml)の <insert id="insertCsvData">
		//         と紐付いている。
		// Event ：Event型（Eventクラスの型）
		// event ：変数名
		int insertCsvData(Event event);

}
