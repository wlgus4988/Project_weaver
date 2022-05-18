package com.example.test.coupang.mybatis.controller;

import com.example.test.aop.LoginType;
//import com.example.test.coupang.jpa.entity.ListEntity;
//import com.example.test.coupang.jpa.entity.OptionEntity;
import com.example.test.coupang.jpa.repository.CateRepository;
import com.example.test.coupang.jpa.repository.CoupangFileRepository;
import com.example.test.coupang.jpa.repository.ListRepository;
import com.example.test.coupang.jpa.repository.OptionRepository;
import com.example.test.coupang.mybatis.dto.ListDto;
import com.example.test.coupang.mybatis.dto.OptionDto;
import com.example.test.coupang.mybatis.mapper.OptionMapper;
import com.example.test.coupang.mybatis.service.CateService;
import com.example.test.coupang.mybatis.service.CoupangFileService;
import com.example.test.coupang.mybatis.service.ListService;
import com.example.test.coupang.mybatis.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class StockController {

   /* @Autowired
    CateRepository caterepo;

    @Autowired
    ListRepository listrepo;

    @Autowired
    ListService listservice;

    @Autowired
    CoupangFileRepository coupangRepo;

    @Autowired
    OptionRepository optionRepo;*/

    @Autowired
    ListService listservice;

    @Autowired
    OptionService optionservice;

    @Autowired
    OptionMapper optionmapper;

    @Autowired
    CoupangFileService coupangfileservice;

    @Autowired
    CateService cateservice;

    @LoginType
    @RequestMapping("/cate/productStock/{cateIdx}/{productIdx}")
    public String stockDetail(Model model, @PathVariable long cateIdx, @PathVariable long productIdx) throws Exception {

        ListDto productList = listservice.findById(productIdx);
        model.addAttribute("productList", productList);

        List<String> color = Arrays.asList(productList.getOptionColor().split(","));
        List<String> size = Arrays.asList(productList.getOptionSize().split(","));
        model.addAttribute("optionColor", color);
        model.addAttribute("optionSize", size);

        model.addAttribute("productIdx", productIdx);
        model.addAttribute("cateIdx", cateIdx);

        List<OptionDto> optionList = optionservice.findByProductIdx(productIdx);
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
    @RequestMapping(value = "/cate/productStockSave", method = {RequestMethod.POST,RequestMethod.GET})
    public void stockOptionSave(@RequestParam("productStock") long productStock, @RequestParam("optionColor") String optionColor,
                                @RequestParam("optionSize") String optionSize, @RequestParam(required = false) Long optionIdx,
                                @RequestParam("productIdx") long productIdx, OptionDto option) throws Exception {

        System.out.println("---->" + optionIdx);
        if (optionIdx != null) {
            option.setIdx(optionIdx);
            System.out.println("수정");
        } else {
            option.setIdx(optionmapper.seq());
        }

        option.setProductIdx(productIdx);
        option.setProductStock(productStock);
        option.setOptionSize(optionSize);
        option.setOptionColor(optionColor);

        optionservice.optionSave(option);
    }

    @LoginType
    @ResponseBody
    @RequestMapping(value = "/cate/productStockDelete", method = RequestMethod.POST)
    public void stockOptionDelete(@RequestParam long optionIdx) throws Exception {

        optionservice.deleteById(optionIdx);
        System.out.println(optionIdx + " 삭제");
    }

    @LoginType
    @ResponseBody
    @RequestMapping(value = "/cate/productStock", method = RequestMethod.POST)
    public String stockDelete(@RequestParam("productIdx") long productIdx, @RequestParam("optionColor") String optionColor, @RequestParam("optionSize") String optionSize) throws Exception {

        List<OptionDto> optionList = optionservice.findByProductIdx(productIdx);
        List<String> option = new ArrayList<>();
        for (int i = 0; i < optionList.size(); i++) {
            option.add(optionList.get(i).getOptionColor().trim() + '/' + optionList.get(i).getOptionSize().trim());
        }
        if (option.contains(optionColor + '/' + optionSize) == true && optionservice.stock(productIdx, optionColor, optionSize) > 0) {
            optionservice.stockCount(productIdx, optionColor, optionSize);
            return "OK";
        } else {
            return "FAIL";
        }
    }
}
