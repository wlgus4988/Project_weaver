package com.example.test.coupang.jpa.controller;

import com.example.test.aop.LoginType;
import com.example.test.coupang.jpa.entity.CateEntity;
import com.example.test.coupang.jpa.repository.CateRepository;
import com.example.test.coupang.jpa.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

//@Controller
public class CateController {

    @Autowired
    CateRepository cateRepo;

    @Autowired
    CateService cateService;

    @LoginType
    @RequestMapping(value = "/admin/CateList", method = RequestMethod.GET)
    public String adminBoardList(Model model, @PageableDefault(size = 20) Pageable pageable,
                                 @RequestParam(required = false, defaultValue = "") String searchText, HttpSession session) throws Exception {

        List<CateEntity> cateList = cateRepo.findAll(searchText);

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), cateList.size());
        final Page<CateEntity> list = new PageImpl<>(cateList.subList(start, end), pageable, cateList.size());

        int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
        int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", pageable.getPageSize() * pageable.getPageNumber());

        model.addAttribute("list", list);
        model.addAttribute("num", list.getTotalElements());

        session.setAttribute("page", pageable.getPageNumber());
        session.setAttribute("searchText", searchText);
        System.out.println((searchText.isEmpty()) ? "" : searchText);

        return "coupang/admin/coupangCategory";
    }

    // ?????? ???????????? ??????
    @LoginType
    @ResponseBody
    @RequestMapping(value = "/admin/CateInsert", method = RequestMethod.POST)
    public void adminCateInsert(@RequestParam("CateName") String CateName, @RequestParam("pIdx") Long pIdx, CateEntity cate) throws Exception {    // ?????? idx , ??????

        if (pIdx == 0) {                                                            // ??? ?????? ??????
            System.out.println("??? ??????");

            cate.setCateName(CateName);                                             // ???????????? ???
            cate.setDepth(0);                                                       // ??? ????????? ????????? ?????? 0
            cate.setGroupIdx(0);                                                    //                ?????? 0
            cate.setPIdx(0);                                                        //                ????????? 0(??????)
            if (cateRepo.findGroupNum().isEmpty()) {
                cate.setGroupNum(1);
            } else {
                cate.setGroupNum(Collections.max(cateRepo.findGroupNum()) + 1);         // ?????? ??? ??? ??????????????? +1
            }
        } else {                                                                    // ?????? ?????? ??????
            System.out.println("?????? ??????");

            CateEntity cateList = cateRepo.getById(pIdx);                           // ????????? ?????? - ?????? ?????? ?????? ????????????


            cate.setDepth(cateList.getDepth() + 1);
            if (cateRepo.findGroupIdx(pIdx).isEmpty()) {
                cate.setGroupIdx(0);
            }else {
                cate.setGroupIdx(Collections.max(cateRepo.findGroupIdx(pIdx)) + 1);
            }
            cate.setGroupNum(cateList.getGroupNum());
            cate.setPIdx(pIdx);
            cate.setCateName(CateName);
        }


        System.out.println("----?????? ??????----->" + cate);

        cateRepo.save(cate);
    }

    // ?????? ???????????? ??????
    @LoginType
    @ResponseBody
    @RequestMapping(value = "/admin/CateUpdate", method = RequestMethod.POST)
    public void adminCateUpdate(@RequestParam("CateName") String CateName, @RequestParam("idx") Long idx, Model model) throws Exception {

        CateEntity cateList = cateRepo.getById(idx);

        String cateUpdateName = CateName;                                          // ?????? ???????????? ???
        String cateName = cateList.getCateName();                                  // ????????? ????????? ???
        cateRepo.cateNameUpdate(cateUpdateName, cateName);
        //cateRepo.productCateUpdate(cateUpdateName, cateName);

    }

    // ?????? ???????????? ??????
    @LoginType
    @RequestMapping(value = "/admin/CateDelete", method = RequestMethod.POST)
    @ResponseBody
    public void adminCateDelete(@RequestParam("idx") Long idx) {

        cateRepo.cateDelete(idx);

    }
}
