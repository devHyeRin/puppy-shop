package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeDto {
    private Long id;
    private String noticeTitle;
    private Integer noticeContent;
    private LocalDateTime createTime;
    private  LocalDateTime updateTime;
}
