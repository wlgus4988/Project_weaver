package com.example.test.coupang.jpa.controller;

import com.example.test.aop.LoginType;
import com.example.test.coupang.jpa.entity.ListEntity;
import com.example.test.coupang.jpa.entity.OptionEntity;
import com.example.test.coupang.jpa.repository.CateRepository;
import com.example.test.coupang.jpa.repository.CoupangFileRepository;
import com.example.test.coupang.jpa.repository.ListRepository;
import com.example.test.coupang.jpa.repository.OptionRepository;
import com.example.test.coupang.jpa.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Controller
public class StockController {

    @Autowired
    CateRepository caterepo;

    @Autowired
    ListRepository listrepo;

    @Autowired
    ListService listservice;

    @Autowired
    CoupangFileRepository coupangRepo;

    @Autowired
    OptionRepository optionRepo;

    @LoginType
    @RequestMapping("/cate/productStock/{cateIdx}/{productIdx}")
    public String stockDetail(Model model, @PathVariable long cateIdx, @PathVariable long productIdx) throws Exception {

        ListEntity productList = listrepo.getById(productIdx);
        model.addAttribute("productList", productList);

        List<String> color = Arrays.asList(productList.getOptionColor().split(","));
        List<String> size = Arrays.asList(productList.getOptionSize().split(","));
        model.addAttribute("optionColor", color);
        model.addAttribute("optionSize", size);

        model.addAttribute("productIdx", productIdx);
        model.addAttribute("cateIdx", cateIdx);

        List<OptionEntity> optionList = optionRepo.findByProductIdxOrderByOptionColorAscOptionSizeDesc(productIdx);
        model.addAttribute("optionList", optionList);

        List<String> option = new ArrayList<>();
        for (int i = 0; i < optionList.size(); i++) {
            option.add(optionList.get(i).getOptionColor().trim() + '/' + optionList.get(i).getOptionSize().trim());
        }
        model.addAttribute("option", option);

        return "coupang/stockDetail";
    }

    @LoginType
    @ResponseBody
    @RequestMapping(value = "/cate/productStockSave", method = RequestMethod.POST)
    public void stockOptionSave(@RequestParam("productStock") long productStock, @RequestParam("optionColor") String optionColor,
                                @RequestParam("optionSize") String optionSize, @RequestParam(required = false) Long optionIdx,
                                @RequestParam("productIdx") long productIdx, OptionEntity option) throws Exception {

        System.out.println("---->" + optionIdx);
        if (optionIdx != null) {
            option.setIdx(optionIdx);
            System.out.println("수정");
        }

        option.setProductIdx(productIdx);
        option.setProductStock(productStock);
        option.setOptionSize(optionSize);
        option.setOptionColor(optionColor);

        optionRepo.save(option);
    }

    @LoginType
    @ResponseBody
    @RequestMapping(value = "/cate/productStockDelete", method = RequestMethod.POST)
    public void stockOptionDelete(@RequestParam long optionIdx) throws Exception {

        optionRepo.deleteById(optionIdx);
        System.out.println(optionIdx + " 삭제");
    }

    @LoginType
    @ResponseBody
    @RequestMapping(value = "/cate/productStock", method = RequestMethod.POST)
    public String stockDelete(@RequestParam("productIdx") long productIdx, @RequestParam("optionColor") String optionColor, @RequestParam("optionSize") String optionSize) throws Exception {

        List<OptionEntity> optionList = optionRepo.findByProductIdxOrderByOptionColorAscOptionSizeDesc(productIdx);
        List<String> option = new ArrayList<>();
        for (int i = 0; i < optionList.size(); i++) {
            option.add(optionList.get(i).getOptionColor().trim() + '/' + optionList.get(i).getOptionSize().trim());
        }
        if (option.contains(optionColor + '/' + optionSize) == true && optionRepo.stock(productIdx, optionColor, optionSize) > 0) {
            optionRepo.stockCount(productIdx, optionColor, optionSize);
            return "OK";
        } else {
            return "FAIL";
        }
    }
}
