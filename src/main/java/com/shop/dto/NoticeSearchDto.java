package com.shop.dto;

import com.shop.constant.NoticeCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeSearchDto {
    private String searchDateType;

    private NoticeCategory searchCategory;

    private String searchBy;

    private String searchQuery = "";
}
