package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String itemNm;
    private Integer price;
    private String itemDetail;
    private String itemQuest;
    private String sellStatCd;        // item 클래스 확인해보기!
    private LocalDateTime regTime;
    private  LocalDateTime updateTime;
}
