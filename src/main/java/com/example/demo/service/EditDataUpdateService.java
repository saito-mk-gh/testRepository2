package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.mapper.EditMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditDataUpdateService {

	// DI
	// ※EditMapperインターフェイスを、このクラスから利用したい為、
	//   その依存オブジェクトを保持するために
	//   private final でフィールドを宣言する。	
	private final EditMapper editMapper;
	
	public void updateEventData(Event event) {
	    editMapper.updateEventData(event);
	}

}
