package com.example.test.category.jpa.controller;

import com.example.test.aop.LoginType;
import com.example.test.category.jpa.entity.Category;
import com.example.test.category.jpa.repository.categoryRepository;
import com.example.test.category.jpa.service.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

//@Controller
public class AdminCategoryController {

    @Autowired
    categoryService categoryservice;

    @Autowired
    categoryRepository categoryrepository;

    @LoginType
    @RequestMapping(value = "/admin/boardList", method = RequestMethod.GET)
    public String adminBoardList(Model model, @PageableDefault(size = 3) Pageable pageable,
                                 @RequestParam(required = false, defaultValue = "") String searchText, HttpSession session) throws Exception {

        Page<Category> list = categoryservice.categoryPageList(searchText, pageable);
        System.out.println("--------------------------------------------->" + list.toList());

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

        model.addAttribute("totalBoardCount",categoryrepository.totalBoardCount("전체 게시판"));

        return "admin/boardCategory";
    }

    // 게시판 추가
    @LoginType
    @ResponseBody
    @RequestMapping(value = "/admin/boardInsert", method = RequestMethod.POST)
    public void admin(@RequestBody String userInput, Category cate) throws Exception {

        cate.setBoardName(userInput);
        categoryservice.boardListSave(cate);

    }

    // 게시판 수정
    @LoginType
    @ResponseBody
    @RequestMapping(value = "/admin/boardUpdate/{idx}", method = RequestMethod.POST)
    public void adminBoardUpdate(@PathVariable long idx, @RequestBody String userInput) throws Exception {

        Category cate = categoryrepository.findById(idx).get();
        String boardUpdateName = userInput;
        String boardName = cate.getBoardName();
        categoryrepository.boardNameUpdate(boardUpdateName,boardName);
        cate.setBoardName(userInput);
        categoryservice.boardListSave(cate);
    }

    //  게시판 삭제
    @LoginType
    @RequestMapping(value = "/admin/boardDelete/{idx}", method = RequestMethod.GET)
    public String adminBoardDelete(@PathVariable long idx) {

        categoryrepository.deleteById(idx);

        return "redirect:/admin/boardList";
    }



}
