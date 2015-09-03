package com.mycompany.myapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.myapp.dto.Board;
import com.mycompany.myapp.service.BoardService;

@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/list")
	public String list(@RequestParam(defaultValue="1") int pageNo, HttpServletRequest request) {
		logger.info("list()");
		
		//����¡�� ���� ���� ����
		int rowsPerPage = 10;
		int pagesPerGroup = 5;
		
		//��ü �Խù� ��
		int totalBoardNo = boardService.getTotalBoardNo();
		
		//��ü ������ ��
		int totalPageNo = totalBoardNo / rowsPerPage;
		if(totalBoardNo%rowsPerPage != 0) { totalPageNo++; }
		
		//��ü �׷� ��
		int totalGroupNo = totalPageNo / pagesPerGroup;
		if(totalPageNo%pagesPerGroup != 0) { totalGroupNo++; }
		
		//���� �׷��ȣ, ������������ȣ, ����������ȣ
		int groupNo = (pageNo-1)/pagesPerGroup + 1;
		int startPageNo = (groupNo-1)*pagesPerGroup + 1;
		int endPageNo = startPageNo + pagesPerGroup - 1;
		if(groupNo==totalGroupNo) { endPageNo = totalPageNo; }
		
		//���� ������ �Խù� ����Ʈ
		List<Board> list = boardService.getPage(pageNo, rowsPerPage);
		request.setAttribute("list", list);
		
		//View�� �ѱ� ������
		request.setAttribute("pagesPerGroup", pagesPerGroup);
		request.setAttribute("totalPageNo", totalPageNo);
		request.setAttribute("totalGroupNo", totalGroupNo);
		request.setAttribute("groupNo", groupNo);
		request.setAttribute("startPageNo", startPageNo);
		request.setAttribute("endPageNo", endPageNo);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("list", list);
		
		return "board/list";
	}
	
	@RequestMapping("/board/writeForm")
	public String writeForm() {
		logger.info("writeForm()");
		return "board/writeForm";
	}
	
	@RequestMapping("/board/updateForm")
	public String updateForm() {
		logger.info("updateForm()");
		return "board/updateForm";
	}
	
	@RequestMapping("/board/write")
	public String write(String title, String writer, String content) {
		logger.info("write()");
		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		boardService.add(board);
		return "redirect:/board/list";
	}
	
	@RequestMapping("/board/update")
	public String update() {
		logger.info("update()");
		return "redirect:/board/list";
	}
}








