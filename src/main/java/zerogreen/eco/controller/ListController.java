package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zerogreen.eco.dto.paging.RequestPageSortDto;
import zerogreen.eco.dto.search.SearchCondition;
import zerogreen.eco.dto.search.StoreSearchType;
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.service.user.StoreMemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class ListController {

    private final StoreMemberService storeMemberService;

    @GetMapping("/shop/list")
    public String shopList(RequestPageSortDto requestPageDto, Model model,
                           StoreSearchType searchType, String keyword) {


        getShopList(requestPageDto, searchType, keyword, model);
        return "page/shopList";
    }

    // 더보기
    @PostMapping("/shop/list")
    public String nextShop(@RequestParam(value = "searchType", required = false) StoreSearchType searchType,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           RequestPageSortDto requestPageDto, Model model) {

        getShopList(requestPageDto, searchType, keyword, model);

        return "page/shopList :: #shop-align";
    }

    @GetMapping("/food/list")
    public String foodList(@RequestParam(value = "type", required = false) StoreType storeType,
                           @RequestParam(value = "searchType", required = false) StoreSearchType searchType,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           RequestPageSortDto requestPageDto, Model model) {

        getFoodList(requestPageDto, storeType, searchType, keyword, model);

        return "page/foodList";
    }

    // 더보기
    @PostMapping("/food/list")
    public String nextFood(@RequestParam(value = "type", required = false) StoreType storeType,
                           @RequestParam(value = "searchType", required = false) StoreSearchType searchType,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           RequestPageSortDto requestPageDto, Model model) {

        getFoodList(requestPageDto, storeType, searchType, keyword, model);

        return "page/foodList :: #food-align";
    }

    // Paging List
    private void getShopList(RequestPageSortDto requestPageDto, StoreSearchType searchType, String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        Pageable pageable = requestPageDto.getPageableSort();
        if (searchType == null) {
            model.addAttribute("list", storeMemberService.getShopList(pageable));
        } else {
            model.addAttribute("list", storeMemberService.getShopList(pageable, new SearchCondition(searchType, keyword)));
        }
    }

    private void getFoodList(RequestPageSortDto requestPageDto, StoreType storeType, StoreSearchType searchType,
                             String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        Pageable pageable = requestPageDto.getPageableSort();
        if(storeType == null){
        if (searchType == null) {
            model.addAttribute("list", storeMemberService.getFoodList(pageable));
        }else {
            model.addAttribute("list", storeMemberService.getFoodList(pageable, new SearchCondition(searchType, keyword)));
        }}else {
            model.addAttribute("list", storeMemberService.getFoodTypeList(pageable, storeType));
        }
    }
}