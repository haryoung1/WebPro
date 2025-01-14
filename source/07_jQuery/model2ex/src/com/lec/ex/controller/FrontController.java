package com.lec.ex.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.lec.ex.service.*;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		actionDo(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		actionDo(request, response);

	}

	private void actionDo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String command = uri.substring(conPath.length());
		String viewPage = null;
		Service service = null;
		if (command.equals("/main.do")) { // 첫 실행화면
			viewPage = "main/main.jsp";
			
		/* * * * * * * * * * * * * * * * * * * * * * * * * * *  *
		 * * * * * * * * * * * member 관련요청 * * * * * * * *  * 
		 * * * * * * * * * * * * * * * * * * * * * * * * * * *  */
		}else if (command.equals("/midConfirm.do")) { // 아이디 중복체크
			service = new MidConfirmService();
			service.execute(request, response);
			viewPage = "member/midConfirm.jsp";
		}else if (command.equals("/memailConfirm.do")) { // 이메일 중복체크
			service = new MemailConfirmService();
			service.execute(request, response);
			viewPage = "member/mmailConfirm.jsp";
		}else if (command.equals("/joinView.do")) { // 회원가입 화면
			viewPage = "member/join.jsp";
		}else if(command.equals("/join.do")) { // 회원가입 db처리
			service = new JoinService();
			service.execute(request, response);
			viewPage = "loginView.do";
		}else if (command.equals("/loginView.do")) { // 로그인 화면
			viewPage = "member/login.jsp";
		}else if (command.equals("/login.do")) { // 로그인 db및 세션처리
			service = new MLoginService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
		}else if (command.equals("/logout.do")) { // 로그아웃 및 세션 날리기
			service = new MLogoutService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
		}else if (command.equals("/modifyView.do")) { // 정보수정을 하기위한 view
			viewPage = "member/modify.jsp";
		}else if (command.equals("/modify.do")) { // DB 정보수정
			service = new MModifyService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
		}else if (command.equals("/withdrawal.do")) { // 회원탈퇴
			service = new MWithdrawalService();
			service.execute(request, response);
			viewPage = "main/main.jsp";
		}else if (command.equals("/allView.do")) { // 회원목록
			service = new MAllViewService();
			service.execute(request, response);
			viewPage = "member/mAllView.jsp";
		}
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * 어드민 관련요청 * * * * * * * * * * *  
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		else if (command.equals("/adminLoginView.do")) { // 어드민 로그인 화면
			viewPage = "admin/adminLogin.jsp";
		}else if (command.equals("/adminLogin.do")) { // 로그인 db 및 세션처리
			service = new ALoginService();
			service.execute(request, response);
			viewPage = "allView.do";
		}
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * 파일첨부게시판 관련요청 * * * * * * * * * 
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * */	
		else if (command.equals("/boardList.do")) {
			service = new BoardListService();
			service.execute(request, response);
			viewPage = "fileBoard/boardList.jsp";
		}else if (command.equals("/boardWriteView.do")) {
			viewPage = "fileBoard/boardWrite.jsp";
		}else if (command.equals("/boardWrite.do")) {
			service = new BoardWriteService();
			service.execute(request, response);
			viewPage = "boardList.do";
		}else if (command.equals("/boardContent.do")) {
			service = new BoardContentService();
			service.execute(request, response);
			viewPage = "fileBoard/boardContent.jsp";
		}else if (command.equals("/boardModifyView.do")) {
			service = new BoardModifyViewService();
			service.execute(request, response);
			viewPage = "fileBoard/boardModify.jsp";
		}else if (command.equals("/boardModify.do")) {
			service = new BoardModifyService();
			service.execute(request, response);
			viewPage = "boardList.do";
		}else if (command.equals("/boardDelete.do")) {
			service = new BoardDeleteService();
			service.execute(request, response);
			viewPage = "boardList.do";
			
		}else if (command.equals("/boardReplyView.do")) {
			service = new BoardReplyViewService();
			service.execute(request, response);
			viewPage = "fileBoard/boardReply.jsp";
			
		}else if (command.equals("/boardReply.do")) {
			service = new BoardReplyService();
			service.execute(request, response);
			viewPage = "boardList.do";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	} // actuonDo
} // Controller
