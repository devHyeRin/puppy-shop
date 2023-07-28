package com.shop.controller;

import com.shop.dto.*;
import com.shop.entity.Item;
import com.shop.entity.Notice;
import com.shop.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    /*공지사항 등록*/
    @GetMapping(value = "/admin/notice/new")
    public String noticeForm(Model model){
        model.addAttribute("noticeFormDto",new NoticeFormDto());
        return "notice/noticeForm";
    }
    /*공지사항 등록 처리*/
    @PostMapping(value = "/admin/notice/new")
    public String noticeNew(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult, Model model, @RequestParam("noticeImgFile") List<MultipartFile> noticeImgFileList){
        if(bindingResult.hasErrors()){
            return "notice/noticeForm";
        }
//        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 공지사항 이미지는 필수 입력 값입니다.");
//            return "notice/noticeForm";
//        }
        try{
            noticeService.saveNotice(noticeFormDto, noticeImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage", "공지사항 등록 중 에러가 발생하였습니다.");
            return "notice/noticeForm";
        }
        return "redirect:/";
    }

    /*공지사항 수정 url -> ok*/
    @GetMapping(value = "/admin/notice/{noticeId}")
    public String noticeDtl(@PathVariable("noticeId")Long noticeId, Model model){
        try {
            NoticeFormDto noticeFormDto = noticeService.getNoticeDtl(noticeId);
            model.addAttribute("noticeFormDto", noticeFormDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 공지사항입니다.");
            model.addAttribute("noticeFormDto", new NoticeFormDto());
            return "notice/noticeForm";
        }
        return "notice/noticeForm";
    }
    /*공지사항 수정 업데이트 -> ok*/
    @PostMapping(value = "/admin/notice/{noticeId}")
    public String noticeUpdate(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult,
                               @RequestParam("noticeImgFile") List<MultipartFile> noticeImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "notice/noticeForm";
        }
//        if(noticeImgFileList.get(0).isEmpty() && noticeFormDto.getId() == null){
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
//            return "notice/noticeForm";
//        }
        try {
            noticeService.updateNotice(noticeFormDto, noticeImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "notice/noticeForm";
        }
        return "redirect:/";
    }

    /*상품 조회*/
    @GetMapping(value = {"/admin/notices", "/admin/notices/{page}"})
    public String noticeManage(NoticeSearchDto noticeSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<Notice> notices = noticeService.getAdminNoticePage(noticeSearchDto, pageable);
        model.addAttribute("notices", notices);
        model.addAttribute("noticeSearchDto", noticeSearchDto);
        model.addAttribute("maxPage", 5);
        return "notice/noticeMng";
    }


    /*사용자 공지사항 조회*/
    @GetMapping(value = {"/notices", "/notices/{page}"})
    public String noticeUser(NoticeSearchDto noticeSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<Notice> notices = noticeService.getUserNoticePage(noticeSearchDto, pageable);
        model.addAttribute("notices", notices);
        model.addAttribute("noticeSearchDto", noticeSearchDto);
        model.addAttribute("maxPage", 5);
        return "notice/noticeUser";
    }

//    @GetMapping(value = "/notices/notice/{noticeId}")
//    public String noticeUserDtl(Model model, @PathVariable("noticeId")Long noticeId){
//        NoticeFormDto noticeFormDto = noticeService.getNoticeDtl(noticeId);
//        model.addAttribute("notice", noticeFormDto);
//        return "notice/noticeDtl";
//    }
    @GetMapping(value = "/notices/notice/{noticeId}")
    public String noticeUserDtl(Model model, @PathVariable("noticeId") Long noticeId) {
        NoticeFormDto noticeFormDto = noticeService.getUserNoticeDtl(noticeId);
        model.addAttribute("notice", noticeFormDto);
        return "notice/noticeDtl";
    }

}
