/* ユーザーマスタのデータ（ADMIN権限） */
INSERT INTO user_master (user_id, password, user_name, master, role)
VALUES('yamada@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田', 1, 'ROLE_ADMIN');
 /*ユーザーマスタのデータ（一般権限） */
INSERT INTO user_master (user_id, password, user_name, master, role)
VALUES('tamura@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '田村', 0, 'ROLE_GENERAL');

INSERT INTO attendance_information (user_id, punch, attendance_date, start_time, end_time)
VALUES ('yamada@xxx.co.jp', 1, '2022-02-20', '12:00', '20:00');

INSERT INTO attendance_information (user_id, punch, attendance_date, start_time, end_time)
VALUES ('tamura@xxx.co.jp', 0, '2022-02-10', '22:00', '12:00');