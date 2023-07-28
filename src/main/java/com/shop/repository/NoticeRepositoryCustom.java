package com.shop.repository;

import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainNoticeDto;
import com.shop.dto.NoticeDto;
import com.shop.dto.NoticeSearchDto;
import com.shop.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepositoryCustom {
    Page<Notice> getAdminNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable);

    Page<Notice> getUserNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable);

}
