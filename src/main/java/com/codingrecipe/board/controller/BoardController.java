package com.codingrecipe.board.controller;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.dto.CommentDTO;
import com.codingrecipe.board.dto.PageDTO;
import com.codingrecipe.board.service.BoardService;
import com.codingrecipe.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) {
        int saveResult = boardService.save(boardDTO);
        if (saveResult > 0) {
            return "redirect:/board/paging";
        } else {
            return "save";
        }
    }

    @GetMapping("/")
    public String findAll(Model model) {
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @GetMapping
    public String findById(@RequestParam("id") Long id,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           Model model) {
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", page);
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        return "detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        boardService.delete(id);
        return "redirect:/board/";
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        boardService.update(boardDTO);
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board", dto);
        return "detail";
//        return "redirect:/board?id="+boardDTO.getId();
    }

    // /board/paging?page=2
    // 처음 페이지 요청은 1페이지를 보여줌
    @GetMapping("/paging")
    public String paging(Model model,
                         @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        System.out.println("page = " + page);
        // 해당 페이지에서 보여줄 글 목록
        List<BoardDTO> pagingList = boardService.pagingList(page);
        System.out.println("pagingList = " + pagingList);
        PageDTO pageDTO = boardService.pagingParam(page);
        model.addAttribute("boardList", pagingList);
        model.addAttribute("paging", pageDTO);
        return "paging";
    }

}












