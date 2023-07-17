package com.shop.service;

import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
    @Value("${itemImgLocation}")  // application.properties에 itemImgLocation
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        System.out.println(oriImgName);

        //파일 업로도
        if(!StringUtils.isEmpty(oriImgName)){
            System.out.println("******");
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            System.out.println(imgName);
            imgUrl = "/images/item/" + imgName;
        }
        System.out.println("1111");
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        System.out.println("(((((");
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()){  // 상품 의미지를 수정한 경우 상품 이미지 업데이트
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);  // 기존 엔티티 조회
            //기존에 등록된 상품 이미지 파일이 있는 경우 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }
            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());  // 파일 업로드
            String imgUrl = "/images/item/" + imgName;
            //변경된 상품 이미지 정보를 세팅
            //상품 등록을 하는 경우에는 ItemImgRepository.save() 로직을 호출하지만 호출을 하지 않습니다
            //savedItemImg 엔티티는 현재 영속성 상태이다
            //그래서 데이터를 변경하는 것만으로 변경감지 기능이 동작
            //트랜잭션이 끝날 때 update 쿼리가 실행된다
            //**영속성 상태여야 사용가능
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }
}
