package com.shop.controller;


import com.shop.constant.ItemCategory;
import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final ItemService itemService;

    @GetMapping(value = "/food")
    public String categoryFood(Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        ItemSearchDto itemSearchDto = new ItemSearchDto();
        itemSearchDto.setSearchCategory(ItemCategory.FOOD);
        Page<MainItemDto> getItemCategoryPage = itemService.getCategoryItemPage(itemSearchDto, pageable);

        model.addAttribute("items", getItemCategoryPage);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);
        return "category/itemFood";
    }


    @GetMapping(value = "/snack")
    public String categorySnack(Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        ItemSearchDto itemSearchDto = new ItemSearchDto();
        itemSearchDto.setSearchCategory(ItemCategory.SNACK);
        Page<MainItemDto> getItemCategoryPage = itemService.getCategoryItemPage(itemSearchDto, pageable);

        model.addAttribute("items", getItemCategoryPage);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);
        return "category/itemSnack";
    }
    @GetMapping(value = "/bath")
    public String categoryBath(Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        ItemSearchDto itemSearchDto = new ItemSearchDto();
        itemSearchDto.setSearchCategory(ItemCategory.BATH);
        Page<MainItemDto> getItemCategoryPage = itemService.getCategoryItemPage(itemSearchDto, pageable);

        model.addAttribute("items", getItemCategoryPage);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);
        return "category/itemBath";
    }
    @GetMapping(value = "/clothes")
    public String categoryClothes(Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        ItemSearchDto itemSearchDto = new ItemSearchDto();
        itemSearchDto.setSearchCategory(ItemCategory.CLOTHES);
        Page<MainItemDto> getItemCategoryPage = itemService.getCategoryItemPage(itemSearchDto, pageable);

        model.addAttribute("items", getItemCategoryPage);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);
        return "category/itemClothes";
    }
    @GetMapping(value = "/stuff")
    public String categoryStuff(Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        ItemSearchDto itemSearchDto = new ItemSearchDto();
        itemSearchDto.setSearchCategory(ItemCategory.STUFF);
        Page<MainItemDto> getItemCategoryPage = itemService.getCategoryItemPage(itemSearchDto, pageable);

        model.addAttribute("items", getItemCategoryPage);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);
        return "category/itemStuff";
    }

}
