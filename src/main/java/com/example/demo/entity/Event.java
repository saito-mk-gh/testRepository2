package com.example.demo.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Event {

	/** イベントID */
	private int eventId;

	/** イベント日付 */
	private LocalDate eventDate;

	/** タイプ */
	private String eventType;

	/** タイトル */
	private String eventTitle;

	/** 詳細 */
	private String eventDetail;

	/** 作成日 */
	private LocalDate createdAt;

	/** 更新日 */
	private LocalDate updatedAt;

}
