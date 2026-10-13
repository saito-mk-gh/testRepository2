package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.mapper.ListMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListDispService {

	// DI
	// ※ListMapperインターフェイスを、このクラスから利用したい為、
	//   その依存オブジェクトを保持するために
	//   private final でフィールドを宣言する。	
	private final ListMapper listMapper;
	
	// 全件取得用
	public List<Event> getAllEvents(){
		return listMapper.getAllEvents();
	}

	// 選択したid取得用
	public Event getEditEventById(int id){
		return listMapper.getEditEventById(id);
	}
	
}
