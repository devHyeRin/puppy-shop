package com.shop.service;

import com.shop.dto.*;
import com.shop.entity.ItemImg;
import com.shop.entity.Notice;
import com.shop.entity.NoticeImg;
import com.shop.repository.NoticeImgRepository;
import com.shop.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeImgRepository noticeImgRepository;
    private final NoticeImgService noticeImgService;

    public Long saveNotice(NoticeFormDto noticeFormDto, List<MultipartFile> noticeImgFileList) throws Exception {
        //상품등록
        Notice notice = noticeFormDto.createNotice();
        noticeRepository.save(notice);
        //이미지 등록
        for (int i = 0; i < noticeImgFileList.size(); i++) {
            NoticeImg noticeImg = new NoticeImg();
            noticeImg.setNotice(notice);

            noticeImgService.saveNoticeImg(noticeImg, noticeImgFileList.get(i));
        }
        return notice.getId();
    }

    /*공지사항 수정을 위한 데이터 가져오기*/
    @Transactional(readOnly = true)
    public NoticeFormDto getNoticeDtl(Long noticeId) {
        List<NoticeImg> noticeImgList = noticeImgRepository.findByNoticeIdOrderByIdAsc(noticeId);

        List<NoticeImgDto> noticeImgDtoList = new ArrayList<>();

        for (NoticeImg noticeImg : noticeImgList) {
            NoticeImgDto noticeImgDto = NoticeImgDto.of(noticeImg);
            noticeImgDtoList.add(noticeImgDto);
        }

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(EntityNotFoundException::new);
        NoticeFormDto noticeFormDto = NoticeFormDto.of(notice);
        noticeFormDto.setNoticeImgDtoList(noticeImgDtoList);
        return noticeFormDto;
    }

    /*공지사항 수정 업데이트*/
    public Long updateNotice(NoticeFormDto noticeFormDto, List<MultipartFile> noticeImgFileList) throws Exception {
        Notice notice = noticeRepository.findById(noticeFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        notice.updateNotice(noticeFormDto);

        List<Long> noticeImgIds = noticeFormDto.getNoticeImgIds();

        for (int i = 0; i < noticeImgFileList.size(); i++) {
            noticeImgService.updateNoticeImg(noticeImgIds.get(i), noticeImgFileList.get(i));
        }
        return notice.getId();
    }

    /*공지사항(관리자) 데이터 조회*/
    @Transactional(readOnly = true)
    public Page<Notice> getAdminNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable) {
        return noticeRepository.getAdminNoticePage(noticeSearchDto, pageable);
    }

    /*공지사항(사용자) 데이터 조회*/
    @Transactional(readOnly = true)
    public Page<Notice> getUserNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable) {
        return noticeRepository.getUserNoticePage(noticeSearchDto, pageable);
    }

    /*공지사항 각각 조회 (이미지도 조회)*/
    @Transactional(readOnly = true)
    public NoticeFormDto getUserNoticeDtl(Long noticeId) {
        List<NoticeImg> noticeImgList = noticeImgRepository.findByNoticeIdOrderByIdAsc(noticeId);

        List<NoticeImgDto> noticeImgDtoList = new ArrayList<>();

        for(NoticeImg noticeImg : noticeImgList){
            NoticeImgDto noticeImgDto = NoticeImgDto.of(noticeImg);
            noticeImgDtoList.add(noticeImgDto);
        }
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(EntityNotFoundException::new);
        NoticeFormDto noticeFormDto = NoticeFormDto.of(notice);
        noticeFormDto.setNoticeImgDtoList(noticeImgDtoList);
        return noticeFormDto;
    }
}
