package com.example.demo.login.domain.model;

import javax.validation.GroupSequence;

//バリデーションをグループ実行するには、実行順序を設定するインターフェースに@GroupSequenceを付け各classを指定（左から順番）
@GroupSequence({ValidationGroup1.class, ValidationGroup2.class, ValidationGroup3.class})
public interface GroupOrder {

}
