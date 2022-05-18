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

    // 쿠팡 카테고리 추가
    @LoginType
    @ResponseBody
    @RequestMapping(value = "/admin/CateInsert", method = RequestMethod.POST)
    public void adminCateInsert(@RequestParam("CateName") String CateName, @RequestParam("pIdx") Long pIdx, CateEntity cate) throws Exception {    // 부모 idx , 이름

        if (pIdx == 0) {                                                            // 새 항목 추가
            System.out.println("새 항목");

            cate.setCateName(CateName);                                             // 입력받은 값
            cate.setDepth(0);                                                       // 새 글이기 때문에 깊이 0
            cate.setGroupIdx(0);                                                    //                순서 0
            cate.setPIdx(0);                                                        //                부모글 0(없음)
            if (cateRepo.findGroupNum().isEmpty()) {
                cate.setGroupNum(1);
            } else {
                cate.setGroupNum(Collections.max(cateRepo.findGroupNum()) + 1);         // 기존 값 중 최대값에서 +1
            }
        } else {                                                                    // 하위 항목 추가
            System.out.println("하위 항목");

            CateEntity cateList = cateRepo.getById(pIdx);                           // 부모글 정보 - 깊이 그룹 순서 가져오게


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


        System.out.println("----등록 정보----->" + cate);

        cateRepo.save(cate);
    }

    // 쿠팡 카테고리 수정
    @LoginType
    @ResponseBody
    @RequestMapping(value = "/admin/CateUpdate", method = RequestMethod.POST)
    public void adminCateUpdate(@RequestParam("CateName") String CateName, @RequestParam("idx") Long idx, Model model) throws Exception {

        CateEntity cateList = cateRepo.getById(idx);

        String cateUpdateName = CateName;                                          // 새로 입력받은 값
        String cateName = cateList.getCateName();                                  // 기존에 저장된 값
        cateRepo.cateNameUpdate(cateUpdateName, cateName);
        //cateRepo.productCateUpdate(cateUpdateName, cateName);

    }

    // 쿠팡 카테고리 삭제
    @LoginType
    @RequestMapping(value = "/admin/CateDelete", method = RequestMethod.POST)
    @ResponseBody
    public void adminCateDelete(@RequestParam("idx") Long idx) {

        cateRepo.cateDelete(idx);

    }
}
