package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class StoreController {

    @GetMapping("/food/list")
    public String list() {

        return "page/foodList";
    }

    @GetMapping("/store/storeInfo")
    public String storeInfoForm() {


        return "store/storeInfoForm";
    }
}
