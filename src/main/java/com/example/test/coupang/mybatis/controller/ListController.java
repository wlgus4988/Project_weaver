package com.example.test.coupang.mybatis.controller;

import com.example.test.aop.LoginType;
/*import com.example.test.coupang.jpa.entity.CateEntity;
import com.example.test.coupang.jpa.entity.FileEntity;
import com.example.test.coupang.jpa.entity.ListEntity;
import com.example.test.coupang.jpa.entity.OptionEntity;
import com.example.test.coupang.jpa.repository.CateRepository;
import com.example.test.coupang.jpa.repository.CoupangFileRepository;
import com.example.test.coupang.jpa.repository.ListRepository;
import com.example.test.coupang.jpa.repository.OptionRepository;*/
import com.example.test.coupang.mybatis.dto.CateDto;
import com.example.test.coupang.mybatis.dto.CoupangFileDto;
import com.example.test.coupang.mybatis.dto.ListDto;
import com.example.test.coupang.mybatis.dto.OptionDto;
import com.example.test.coupang.mybatis.service.CateService;
import com.example.test.coupang.mybatis.service.CoupangFileService;
import com.example.test.coupang.mybatis.service.ListService;
import com.example.test.coupang.mybatis.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ListController {

    /*@Autowired
    CateRepository caterepo;

    @Autowired
    ListRepository listrepo;

    @Autowired
    CoupangFileRepository coupangRepo;

    @Autowired
    OptionRepository optionRepo;*/


    @Autowired
    ListService listservice;

    @Autowired
    OptionService optionservice;

    @Autowired
    CoupangFileService coupangfileservice;

    @Autowired
    CateService cateservice;

    List<OptionDto> optionList = new ArrayList<OptionDto>();


    @LoginType
    @RequestMapping(value = "/cate/List/{cateIdx}", method = {RequestMethod.GET, RequestMethod.POST})
    public String productList(Model model,
                              @PageableDefault(size = 5) Pageable pageable
            , @RequestParam(required = false, defaultValue = "") String searchText, @RequestParam(required = false) Long proCateNum, @PathVariable long cateIdx) throws Exception {

        // 상단에 나올 최하위 카테고리 리스트
        List<CateDto> cateMemberList = cateservice.findByPIdx(cateIdx);
        model.addAttribute("cateList", cateMemberList);
        model.addAttribute("cateName", cateservice.findById(cateIdx).getCateName());
        model.addAttribute("cateNum", cateIdx);


        CateDto pCateName = cateservice.findById(cateIdx);
        System.out.println(pCateName.getGroupNum());
        model.addAttribute("pCateName", cateservice.pCateName(pCateName.getGroupNum(), pCateName.getPIdx()));
        model.addAttribute("pPCateName", cateservice.pCateName(pCateName.getGroupNum(), cateservice.findById(pCateName.getPIdx()).getPIdx()));

        System.out.println(proCateNum);
        List<ListDto> productList;

        // 클릭 시 해당
       /* if (proCateNum == null) {
            productList = listservice.findAll(searchText, cateIdx);
        } else {
            productList = listservice.findAll2(searchText, cateIdx, proCateNum);
        }*/
        productList = listservice.findAll(searchText, cateIdx, proCateNum);

        // 게시글 리스트 불러와서 페이징 처리
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), productList.size());
        final Page<ListDto> list = new PageImpl<>(productList.subList(start, end), pageable, productList.size());
        System.out.println(list);


        model.addAttribute("list", list);
        model.addAttribute("listSize", list.getSize());

        // 페이지 정보
        int startPage = Math.max(1, list.getPageable().getPageNumber() - 5);
        int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 5);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("page", pageable.getPageSize() * pageable.getPageNumber());
        model.addAttribute("num", list.getTotalElements());


        //model.addAttribute("fileLi", coupangfileservice.findAll());

        return "coupang/productList";
    }

    @LoginType
    @RequestMapping(value = "/cate/Product/{cateIdx}/{productIdx}", method = {RequestMethod.GET, RequestMethod.POST})
    public String boardDetail(Model model, @PathVariable long cateIdx, @PathVariable long productIdx, HttpSession session,
                              @RequestParam(required = false) Long page, @RequestParam(required = false) String search) throws Exception {

        try {

            ListDto productList = listservice.findById(productIdx);
            model.addAttribute("productList", productList);
            model.addAttribute("cateNum", cateIdx);
            System.out.println(productList.getImageSrc());

            List<CoupangFileDto> fileLi = coupangfileservice.findByIdx(productIdx);
            model.addAttribute("fileLi", fileLi);

            List<String> color = Arrays.asList(productList.getOptionColor().split(","));
            List<String> size = Arrays.asList(productList.getOptionSize().split(","));
            model.addAttribute("optionColor", color);
            model.addAttribute("optionSize", size);

            if (productList.getProductType().equals("-")) {
                model.addAttribute("type", productList.getProductType());
            } else {
                String[] typeNum = productList.getProductType().split(" ");
                List<String> productType = new ArrayList<String>();
                for (int i = 0; i < typeNum.length; i++) {
                    productType.add(cateservice.CateName(Long.parseLong(typeNum[i])));
                }
                model.addAttribute("type", productType);
            }

            session.setAttribute("LPage", page);
            session.setAttribute("LSearchText", search);
            session.setAttribute("cateIdx", cateIdx);


            return "coupang/productDetail";

        } catch (NullPointerException e) {

            return "index";
        }
    }

    @LoginType
    @RequestMapping(value = "/cate/productWrite", method = {RequestMethod.GET, RequestMethod.POST})
    public String Write(Model model, @RequestParam(required = false) Long cateIdx) throws Exception {

        if (cateIdx == null) {                                              // 새 글
            model.addAttribute("cateNum", 0);
            System.out.println("새 상품");

        } else {
            List<CateDto> cateMemberList = cateservice.findByPIdx(cateIdx);
            model.addAttribute("cateMemberList", cateMemberList);

            model.addAttribute("cateNum", cateIdx);
            model.addAttribute("cateName", cateservice.findById(cateIdx).getCateName());
            System.out.println("카테고리 상품");
        }
        return "coupang/productWrite";
    }

    @LoginType
    @RequestMapping(value = "/cate/productInsert/{cateIdx}", method = RequestMethod.POST)
    public String Insert_admin(ListDto list, HttpSession httpSession, CoupangFileDto files,
                               @RequestParam("file") MultipartFile singleFile, @RequestParam("files") List<MultipartFile> multiFiles, @PathVariable long cateIdx, @RequestParam String cateName) throws Exception {

        if (cateIdx == 0) {

            list.setUsername("admins");
            System.out.println(list);
            cateIdx = list.getProductCategoryNum();
            listservice.productSave(list, files, singleFile, multiFiles);
            return "redirect:/cate/List/" + cateIdx;

        } else {

            list.setProductCategoryNum(cateIdx);
            list.setUsername("admins");

            listservice.productSave(list, files, singleFile, multiFiles);

            return "redirect:/cate/List/{cateIdx}";
        }
    }


    @LoginType
    @ResponseBody
    @RequestMapping(value = "/cate/productWrite/total", method = RequestMethod.POST)
    public List<CateDto> select(@RequestParam long depth, HttpSession session) throws Exception {

        List<CateDto> list = cateservice.findByPIdx(depth);
        System.out.println(list);
        return list;

    }

    @LoginType
    @RequestMapping(value = "/cate/productUpdate/{cateIdx}/{productIdx}", method = RequestMethod.GET)
    public String updateAdmin(Model model, @PathVariable long cateIdx, @PathVariable long productIdx) throws Exception {

        try {
            List<CateDto> cateMemberList = cateservice.findByPIdx(cateIdx);
            model.addAttribute("cateMemberList", cateMemberList);

            ListDto productList = listservice.findById(productIdx);
            model.addAttribute("productList", productList);
            model.addAttribute("cateNum", cateIdx);
            if (productList.getProductType().equals("-")) {
                model.addAttribute("type", productList.getProductType());
            } else {
                String[] typeNum = productList.getProductType().split(" ");
                List<String> productType = new ArrayList<String>();
                for (int i = 0; i < typeNum.length; i++) {
                    productType.add(cateservice.CateName(Long.parseLong(typeNum[i])));
                }
                model.addAttribute("productType", productType.toString());
                model.addAttribute("productTypeOrigin", productList.getProductType());
            }


            List<CoupangFileDto> fileLi = coupangfileservice.findByIdx(productIdx);
            model.addAttribute("fileLi", fileLi);

            return "coupang/productUpdate";

        } catch (NullPointerException e) {
            return "login/signIn";
        }
    }

    @LoginType
    @RequestMapping(value = "/cate/productUpdate/{cateIdx}/{productIdx}", method = RequestMethod.POST)
    public String update_Admin(Model model, @PathVariable long productIdx, @PathVariable long cateIdx, ListDto list, CoupangFileDto files,
                               @RequestParam("file") MultipartFile singleFile, @RequestParam("files") List<MultipartFile> multiFiles,
                               @RequestParam(required = false) String fileMulti, @RequestParam(required = false) String fileSingle) throws Exception {


        System.out.println(fileMulti);
        if (fileMulti.isEmpty() == false) {
            String[] li = fileMulti.split(",");
            for (int i = 0; i < li.length; i++) {

                long fileIdx = Long.parseLong(li[i]);
                coupangfileservice.deleteById(fileIdx);
            }
        }
        if (fileSingle.isEmpty() == false) {
            coupangfileservice.deleteById(Long.parseLong(fileSingle));
        }
        ListDto productList = listservice.findById(productIdx);
        list.setProductIdx(productIdx);
        list.setProductCategoryNum(cateIdx);
        list.setUsername(productList.getUsername());
        System.out.println(list.getProductType() + productList.getProductType());
        list.setImageSrc(productList.getImageSrc());

        listservice.productSave(list, files, singleFile, multiFiles);

        return "redirect:/cate/Product/{cateIdx}/{productIdx}";
    }

    @LoginType
    @RequestMapping(value = "/cate/productDelete/{cateIdx}/{productIdx}", method = RequestMethod.GET)
    public String Delete_Admin(Model model, @PathVariable long cateIdx, @PathVariable long productIdx) throws Exception {

        listservice.deleteById(productIdx);

        List<CoupangFileDto> fileList = coupangfileservice.findByIdx(productIdx);
        for (int i = 0; i < fileList.size(); i++) {

            System.out.println(fileList.get(i).getIdx());
            coupangfileservice.deleteById(fileList.get(i).getIdx());
        }

        List<OptionDto> optionList = optionservice.findByProductIdx(productIdx);
        for (int i = 0; i < optionList.size(); i++) {

            System.out.println(optionList.get(i).getIdx());
            optionservice.deleteById(optionList.get(i).getIdx());
        }

        return "redirect:/cate/List/{cateIdx}";
    }

}
