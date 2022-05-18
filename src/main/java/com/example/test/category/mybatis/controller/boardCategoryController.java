package com.example.test.category.mybatis.controller;

import com.example.test.aop.LoginType;
//import com.example.test.category.jpa.entity.Category;
//import com.example.test.category.jpa.repository.categoryRepository;
//import com.example.test.category.jpa.service.categoryService;
import com.example.test.category.mybatis.dto.CategoryDto;
import com.example.test.category.mybatis.service.boardCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class boardCategoryController {

    @Autowired
    boardCategoryService boardcategoryservice;

   /* @Autowired
    categoryRepository categoryrepository;*/

    @LoginType
    @RequestMapping(value = "/admin/boardList", method = RequestMethod.GET)
    public String adminBoardList(Model model, @PageableDefault(size = 3) Pageable pageable,
                                 @RequestParam(required = false, defaultValue = "") String searchText, HttpSession session) throws Exception {

        List<CategoryDto> cate = boardcategoryservice.categoryList(searchText);

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), cate.size());
        final Page<CategoryDto> list = new PageImpl<>(cate.subList(start, end), pageable, cate.size());

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

        model.addAttribute("totalBoardCount", boardcategoryservice.totalBoardCount());

        return "admin/boardCategory";
    }

    // 게시판 추가
    @LoginType
    @ResponseBody
    @RequestMapping(value = "/admin/boardInsert", method = RequestMethod.POST)
    public void admin(@RequestBody String userInput, CategoryDto cate) throws Exception {

        cate.setBoardName(userInput);
        boardcategoryservice.boardListSave(cate);

    }

    // 게시판 수정
    @LoginType
    @ResponseBody
    @RequestMapping(value = "/admin/boardUpdate/{idx}", method = RequestMethod.POST)
    public void adminBoardUpdate(@PathVariable long idx, @RequestBody String userInput) throws Exception {

        CategoryDto cate = boardcategoryservice.findById(idx);
        String boardUpdateName = userInput;
        String boardName = cate.getBoardName();
        boardcategoryservice.boardNameUpdate(boardUpdateName,boardName);
        cate.setBoardName(userInput);
        boardcategoryservice.boardListSave(cate);
    }

    //  게시판 삭제
    @LoginType
    @RequestMapping(value = "/admin/boardDelete/{idx}", method = RequestMethod.GET)
    public String adminBoardDelete(@PathVariable long idx) throws Exception {

        boardcategoryservice.deleteById(idx);

        return "redirect:/admin/boardList";
    }



}
