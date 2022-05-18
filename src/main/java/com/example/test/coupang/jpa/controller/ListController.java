package com.example.test.coupang.jpa.controller;

import com.example.test.aop.LoginType;
import com.example.test.coupang.jpa.entity.CateEntity;
import com.example.test.coupang.jpa.entity.FileEntity;
import com.example.test.coupang.jpa.entity.ListEntity;
import com.example.test.coupang.jpa.entity.OptionEntity;
import com.example.test.coupang.jpa.repository.CateRepository;
import com.example.test.coupang.jpa.repository.CoupangFileRepository;
import com.example.test.coupang.jpa.repository.ListRepository;
import com.example.test.coupang.jpa.repository.OptionRepository;
import com.example.test.coupang.jpa.service.ListService;
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

//@Controller
public class ListController {

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

    List<OptionEntity> optionList = new ArrayList<OptionEntity>();


    @LoginType
    @RequestMapping(value = "/cate/List/{cateIdx}", method = {RequestMethod.GET, RequestMethod.POST})
    public String productList(Model model,
                              @PageableDefault(size = 5) Pageable pageable
            , @RequestParam(required = false, defaultValue = "") String searchText, @RequestParam(required = false) Long proCateNum, @PathVariable long cateIdx) throws Exception {

        // 상단에 나올 최하위 카테고리 리스트
        List<CateEntity> cateMemberList = caterepo.findByPIdx(cateIdx);
        model.addAttribute("cateList", cateMemberList);
        model.addAttribute("cateName", caterepo.findById(cateIdx).get().getCateName());
        model.addAttribute("cateNum", cateIdx);


        CateEntity pCateName = caterepo.getById(cateIdx);
        model.addAttribute("pCateName", caterepo.pCateName(pCateName.getGroupNum(), pCateName.getPIdx()));
        model.addAttribute("pPCateName", caterepo.pCateName(pCateName.getGroupNum(), caterepo.getById(pCateName.getPIdx()).getPIdx()));

        System.out.println(proCateNum);
        List<ListEntity> productList;

        // 클릭 시 해당
        if (proCateNum == null) {
            productList = listrepo.findAll(searchText, cateIdx);
        } else {
            productList = listrepo.findAll2(searchText, cateIdx, proCateNum);
        }


        // 게시글 리스트 불러와서 페이징 처리
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), productList.size());
        final Page<ListEntity> list = new PageImpl<>(productList.subList(start, end), pageable, productList.size());
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


        model.addAttribute("fileLi", coupangRepo.findAll());

        return "coupang/productList";
    }

    @LoginType
    @RequestMapping(value = "/cate/Product/{cateIdx}/{productIdx}", method = {RequestMethod.GET, RequestMethod.POST})
    public String boardDetail(Model model, @PathVariable long cateIdx, @PathVariable long productIdx, HttpSession session,
                              @RequestParam(required = false) Long page, @RequestParam(required = false) String search) throws Exception {


        ListEntity productList = listrepo.getById(productIdx);
        model.addAttribute("productList", productList);
        model.addAttribute("cateNum", cateIdx);
        System.out.println(productList.getImageSrc());

        List<FileEntity> fileLi = coupangRepo.findByIdx(productIdx);
        model.addAttribute("fileLi", fileLi);

        List<String> color = Arrays.asList(productList.getOptionColor().split(","));
        List<String> size = Arrays.asList(productList.getOptionSize().split(","));
        model.addAttribute("optionColor", color);
        model.addAttribute("optionSize", size);

        String[] typeNum = productList.getProductType().split(" ");
        List<String> productType = new ArrayList<String>();
        for (int i = 0; i < typeNum.length; i++) {
            productType.add(caterepo.CateName(Long.parseLong(typeNum[i])));
        }
        model.addAttribute("type", productType);

        session.setAttribute("LPage", page);
        session.setAttribute("LSearchText", search);
        session.setAttribute("cateIdx", cateIdx);


        return "coupang/productDetail";
    }


/*    @LoginType
    @RequestMapping(value = "/cate/productWrite/{cateIdx}", method = RequestMethod.GET)
    public String Write_admin(Model model, @PathVariable long cateIdx) throws Exception {

        List<CateEntity> cateMemberList = caterepo.findByPIdx(cateIdx);
        model.addAttribute("cateMemberList", cateMemberList);

        model.addAttribute("cateNum", cateIdx);
        model.addAttribute("cateName", caterepo.findById(cateIdx).get().getCateName());

        //model.addAttribute("optionList", optionList);

        return "coupang/productWrite";
    }*/

    @LoginType
    @RequestMapping(value = "/cate/productWrite", method = {RequestMethod.GET, RequestMethod.POST})
    public String Write(Model model, @RequestParam(required = false) Long cateIdx) throws Exception {

        if (cateIdx == null) {                                              // 새 글
            model.addAttribute("cateNum", 0);
            System.out.println("새 상품");

        } else {
            List<CateEntity> cateMemberList = caterepo.findByPIdx(cateIdx);
            model.addAttribute("cateMemberList", cateMemberList);

            model.addAttribute("cateNum", cateIdx);
            model.addAttribute("cateName", caterepo.findById(cateIdx).get().getCateName());
            System.out.println("카테고리 상품");
        }
        return "coupang/productWrite";
    }

    @LoginType
    @RequestMapping(value = "/cate/productInsert/{cateIdx}", method = RequestMethod.POST)
    public String Insert_admin(ListEntity list, HttpSession httpSession, FileEntity files,
                               @RequestParam("file") MultipartFile singleFile, @RequestParam("files") List<MultipartFile> multiFiles, @PathVariable long cateIdx, @RequestParam String cateName) throws Exception {

        if (cateIdx == 0) {

            list.setUsername("admins");
            System.out.println(list);
            cateIdx = list.getProductCategoryNum();
            listservice.productSave(list, files, singleFile, multiFiles);
            return "redirect:/cate/List/"+cateIdx;

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
    public List<CateEntity> select(@RequestParam long depth, HttpSession session) throws Exception {

        List<CateEntity> list = caterepo.findByPIdx(depth);
        System.out.println(list);
        return list;

    }

    @LoginType
    @RequestMapping(value = "/cate/productUpdate/{cateIdx}/{productIdx}", method = RequestMethod.GET)
    public String updateAdmin(Model model, @PathVariable long cateIdx, @PathVariable long productIdx) throws Exception {

        try {
            List<CateEntity> cateMemberList = caterepo.findByPIdx(cateIdx);
            model.addAttribute("cateMemberList", cateMemberList);

            ListEntity productList = listrepo.getById(productIdx);
            model.addAttribute("productList", productList);
            model.addAttribute("cateNum", cateIdx);

            String[] typeNum = productList.getProductType().split(" ");
            List<String> productType = new ArrayList<String>();
            for (int i = 0; i < typeNum.length; i++) {
                productType.add(caterepo.CateName(Long.parseLong(typeNum[i])));
            }
            model.addAttribute("productType", productType.toString());
            model.addAttribute("productTypeOrigin", productList.getProductType());

            List<FileEntity> fileLi = coupangRepo.findByIdx(productIdx);
            model.addAttribute("fileLi", fileLi);

            return "coupang/productUpdate";

        } catch (NullPointerException e) {
            return "login/signIn";
        }
    }

    @LoginType
    @RequestMapping(value = "/cate/productUpdate/{cateIdx}/{productIdx}", method = RequestMethod.POST)
    public String update_Admin(Model model, @PathVariable long productIdx, @PathVariable long cateIdx, ListEntity list, FileEntity files,
                               @RequestParam("file") MultipartFile singleFile, @RequestParam("files") List<MultipartFile> multiFiles,
                               @RequestParam(required = false) String fileMulti, @RequestParam(required = false) String fileSingle) throws Exception {


        System.out.println(fileMulti);
        if (fileMulti.isEmpty() == false) {
            String[] li = fileMulti.split(",");
            for (int i = 0; i < li.length; i++) {

                long fileIdx = Long.parseLong(li[i]);
                coupangRepo.deleteById(fileIdx);
            }
        }
        if (fileSingle.isEmpty() == false) {
            coupangRepo.deleteById(Long.parseLong(fileSingle));
        }
        ListEntity productList = listrepo.getById(productIdx);
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

        listrepo.deleteById(productIdx);

        List<FileEntity> fileList = coupangRepo.findByIdx(productIdx);
        for (int i = 0; i < fileList.size(); i++) {

            System.out.println(fileList.get(i).getIdx());
            coupangRepo.deleteById(fileList.get(i).getIdx());
        }

        return "redirect:/cate/List/{cateIdx}";
    }

}
