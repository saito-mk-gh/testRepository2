package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.mapper.NewDataMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewDataImportService {

	// DI
	// ※NewDataMapperインターフェイスを、このクラスから利用したい為、
	//   その依存オブジェクトを保持するために
	//   private final でフィールドを宣言する。	
	private final NewDataMapper newDataMapper;
	
	public void addEventData(Event event) {
	    newDataMapper.addEventData(event);
	}

}
