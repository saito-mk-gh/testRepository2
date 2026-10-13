-- イベントテーブル（CSVファイルからデータ含む）
CREATE TABLE calendar_event (
    event_id INT PRIMARY KEY AUTO_INCREMENT,
    event_date DATE NOT NULL,
    event_type VARCHAR(20) NOT NULL,
    event_title VARCHAR(255) NOT NULL,
    event_detail TEXT,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP

);
