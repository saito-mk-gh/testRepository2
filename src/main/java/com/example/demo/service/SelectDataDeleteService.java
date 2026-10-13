package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.DeleteMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SelectDataDeleteService {

	// DI
	// ※DeleteMapperインターフェイスを、このクラスから利用したい為、
	//   その依存オブジェクトを保持するために
	//   private final でフィールドを宣言する。	
	private final DeleteMapper deleteMapper;
	
	public void deleteEventData(int id) {
	    deleteMapper.deleteEventData(id);
	}
	
}
