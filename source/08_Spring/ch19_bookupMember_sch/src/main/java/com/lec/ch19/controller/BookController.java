package com.lec.ch19.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lec.ch19.service.BookService;
import com.lec.ch19.util.Paging;
import com.lec.ch19.vo.Book;

@Controller
@RequestMapping(value = "book")
public class BookController {
	@Autowired
	private BookService bookService;

	// book.do?method=list&pageNum=1&schitem=all&schWord=J
	@RequestMapping(params = "method=list", method = { RequestMethod.GET, RequestMethod.POST }) // book.schItem/book.schWord
	public String list(String pageNum, Book book, Model model) {
		model.addAttribute("bookList", bookService.bookList(pageNum, book));
		model.addAttribute("paging", new Paging(bookService.totCntBook(book), pageNum, 3, 3));
		return "book/list";
	}

	@RequestMapping(params = "method=register", method = RequestMethod.GET)
	public String register() {
		return "book/register";
	}

	@RequestMapping(params = "method=register", method = RequestMethod.POST)
	public String register(MultipartHttpServletRequest mRequest, @ModelAttribute("bDto") Book book, Model model) {
		model.addAttribute("registerResult", bookService.registerBook(mRequest, book));
		return "book/register";
	}

	@RequestMapping(params = "method=detail", method = RequestMethod.GET)
	public String detail(int bnum, Model model) {
		model.addAttribute("bookDto", bookService.getDetailBook(bnum));
		return "book/detail";
	}

	@RequestMapping(params = "method=modify", method = RequestMethod.GET)
	public String modifyForm(int bnum, Model model) {
		model.addAttribute("bookDto", bookService.getDetailBook(bnum));
		return "book/modify";
	}

	@RequestMapping(params = "method=modify", method = RequestMethod.POST)
	public String modify(MultipartHttpServletRequest mRequest, @ModelAttribute("bDto") Book book, Model model,
			String pageNum) {
//		model.addAttribute("modifyResult", bookService.modifyBook(mRequest, book));
//		return "redirect:book.do?method=list";		
		int modifyResult = bookService.modifyBook(mRequest, book);
		return "redirect:book.do?method=list&pageNum=" + pageNum + "&modifyResult=" + modifyResult;
	}
}
