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
import zerogreen.eco.entity.userentity.StoreType;
import zerogreen.eco.service.user.StoreMemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class ListController {

    private final StoreMemberService storeMemberService;

    @GetMapping("/shop/list")
    public String shopList(Model model, RequestPageSortDto requestPageDto) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("like").descending());
        model.addAttribute("list", storeMemberService.getShopList(pageable));

        return "page/shopList";
    }

    // 더보기
    @PostMapping("/shop/list")
    public String nextShop(RequestPageSortDto requestPageDto, Model model) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("like").descending());
        model.addAttribute("list", storeMemberService.getShopList(pageable));

        return "page/shopList :: #shop-align";
    }

    @GetMapping("/food/list")
    public String foodList(@RequestParam(value = "type", required = false) StoreType storeType,
                           Model model, RequestPageSortDto requestPageDto) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("like").descending());
        if (storeType == null) {
            model.addAttribute("list", storeMemberService.getFoodList(pageable));
        } else {
            model.addAttribute("list", storeMemberService.getFoodTypeList(pageable, storeType));
        }

        return "page/foodList";
    }

    // 더보기
    @PostMapping("/food/list")
    public String nextFood(RequestPageSortDto requestPageDto, Model model) {

        Pageable pageable = requestPageDto.getPageableSort(Sort.by("like").descending());
        model.addAttribute("list", storeMemberService.getShopList(pageable));

        return "page/foodList :: #food-align";
    }
}
