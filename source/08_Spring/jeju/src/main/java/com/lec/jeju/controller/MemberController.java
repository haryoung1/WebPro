package com.lec.jeju.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lec.jeju.service.MemberService;
import com.lec.jeju.vo.Member;

@Controller
@RequestMapping(value = "member")
public class MemberController {
	@Autowired
	private MemberService memberService;

	// 아이디 중복체크
	@RequestMapping(params = "method=idConfirm", method = RequestMethod.GET)
	public String idConfirm(String mid, Model model) {
		model.addAttribute("idConfirmResult", memberService.idConfirm(mid));
		return "member/idConfirm";
	}

	@RequestMapping(params = "method=memailConfirm", method = RequestMethod.GET)
	public String mailConfirm(String memail, Model model) {
		model.addAttribute("memailConfirmResult", memberService.emailConfirm(memail));
		return "member/memailConfirm";
	}

	// 회원가입 약관
	@RequestMapping(params = "method=joinAgreePage", method = RequestMethod.GET)
	public String joinAgreePage() {
		return "member/joinAgreePage";
	}

	// 회원가입 View
	@RequestMapping(params = "method=join", method = RequestMethod.GET)
	public String join() {
		return "member/join";
	}

	// 회원가입 처리
	@RequestMapping(params = "method=join", method = RequestMethod.POST)
	public String join(@ModelAttribute("mDto") Member member, Model model, HttpSession httpSession,
			MultipartHttpServletRequest mRequest) {
		model.addAttribute("joinResult", memberService.joinMember(member, httpSession, mRequest));
		return "member/login";
	}

	// 로그인 뷰
	@RequestMapping(params = "method=login", method = RequestMethod.GET)
	public String login() {
		return "member/login";
	}

	// 로그인 처리
	@RequestMapping(params = "method=login", method = RequestMethod.POST)
	public String login(String mid, String mpw, String after, HttpSession httpSession, Model model) {
		String loginResult = memberService.loginCheck(mid, mpw, httpSession);
		if (loginResult.equals("로그인 성공")) {
			return "redirect:" + after;
		} else {
			model.addAttribute("loginResult", loginResult); // redirect 사용불가
			model.addAttribute("mid", mid);
			model.addAttribute("mpw", mpw);
			return "member/login";
		}
	}

	// 로그아웃
	@RequestMapping(params = "method=logout", method = RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		httpSession.invalidate();
		return "redirect:main.do";
	}

	// 로그인 후 정보수정으로 갈때
	@RequestMapping(value = "method=modify", method = RequestMethod.GET)
	public String modify1() {
		return "member/modify";
	}

	// 정보수정 뷰
	@RequestMapping(params = "method=modify", method = RequestMethod.GET)
	public String modify() {
		return "member/modify";
	}

	// 정보수정 처리
	@RequestMapping(params = "method=modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute("mDto") Member member, HttpSession httpSession, Model model,
			MultipartHttpServletRequest mRequest) {
		model.addAttribute("modifyResult", memberService.modifyMember(member, httpSession, mRequest));
		return "forward:main.do";
	}

	// 회원탈퇴

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete() {
		return "member/delete";
	}

	// 회원탈퇴 처리
	@RequestMapping(params = "method=delete", method = RequestMethod.POST)
	public String deleteMember(HttpSession session, String mpw, Model model, RedirectAttributes redirectAttributes) {
		Member member = (Member) session.getAttribute("member");
		String msg = null;
		if (member.getMpw().equals(mpw)) {
			int result = memberService.deleteMember(member.getMid());
			if (result > 0) {
				session.invalidate();
				redirectAttributes.addFlashAttribute("msg", "회원탈퇴가 완료되었습니다.");
				return "redirect:main.do";
			} else {
				msg = "회원탈퇴 처리가 실패 했습니다.";
			}
		} else {
			msg = "비밀번호가 일치하지 않습니다";
		}
		model.addAttribute("msg", msg);
		return "member/delete";
	}

}