/* 利用者マスタテーブル */
CREATE TABLE IF NOT EXISTS user_master (
    user_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100),
    user_name VARCHAR(50),
    master INT,
    role VARCHAR(50)
);
/* アクセス履歴テーブル */
CREATE TABLE IF NOT EXISTS general_history (
    user_id VARCHAR(50),
    ip_address INT UNSIGNED,
    login_time TIMESTAMP
);

/* アクセス履歴管理者テーブル */
CREATE TABLE IF NOT EXISTS admin_history (
    user_id VARCHAR(50),
    ip_address INTEGER UNSIGNED,
    login_time TIMESTAMP
);

/* 勤怠情報テーブル */
CREATE TABLE IF NOT EXISTS attendance_information (
    user_id VARCHAR(50),
    punch INT,
    attendance_date DATE,
    start_time TIME,
    end_time TIME,
    PRIMARY KEY(user_id)
);