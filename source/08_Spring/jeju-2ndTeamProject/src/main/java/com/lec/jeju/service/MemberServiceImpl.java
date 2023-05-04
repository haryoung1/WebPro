package com.lec.jeju.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lec.jeju.dao.MemberDao;
import com.lec.jeju.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDao memberDao;

	@Autowired
	private JavaMailSender mailSender;

	String backupPath = "D:/TeamProject/jeju-2ndTeamProject/src/main/webapp/memberPhoto/";

	@Override
	public int idConfirm(String mid) {
		return memberDao.idConfirm(mid);
	}

	@Override
	public int emailConfirm(String memail) {
		return memberDao.emailConfirm(memail);
	}

	@Override
	public int joinMember(final Member member, HttpSession httpSession, MultipartHttpServletRequest mRequest) {
		String uploadPath = mRequest.getRealPath("memberPhoto/");
		Iterator<String> params = mRequest.getFileNames();
		String mphoto = "";
		String param = params.next();
		MultipartFile mFile = mRequest.getFile(param); // 파라미터에 첨부된 파일 객체
		mphoto = mFile.getOriginalFilename();
		if (mphoto != null && !mphoto.equals("")) { // 첨부함
			if (new File(uploadPath + mphoto).exists()) {
				// 서버에 같은 파일이름이 있을 경우(첨부파일과)
				mphoto = System.currentTimeMillis() + "_" + mphoto;
			} // if
			try {
				mFile.transferTo(new File(uploadPath + mphoto)); // 서버에 저장
				System.out.println("서버파일 : " + uploadPath + mphoto);
				System.out.println("백업파일 : " + backupPath + mphoto);
				boolean result = fileCopy(uploadPath + mphoto, backupPath + mphoto);
				System.out.println(result ? "백업성공" : "백업실패");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			mphoto = "memberPhoto/NOIMG.JPG";
		} // if
			// 메일전송
		MimeMessagePreparator message = new MimeMessagePreparator() {
			String content = "<div style=\"width:500px; margin: 0 auto\">\n" + " <h1>" + member.getMname()
					+ "님 회원가입 감사합니다!</h1>\n" + " <img src=\"http://localhost:8088/jeju/img/logo_jeju.png\" "
					+ "alt=\"제주도가즈아\" style=\"width: 100%; max-width: 500px;\">\n"
					+ " <p align=\"center\">저희 제주어때 사이트에 오신걸 환영합니다.</p>\n"
					+ " <p align=\"center\">부디 편안하고 안전한 여행 되시길 바랍니다.</p>\n"
					+ " <p align=\"center\">(00000) 제주특별자치도 제주시 JEJU</p>\n"
					+ " <p align=\"center\">관광불편신고 : 제주안내 120콜센터(국번없이 120번)</p>\n" + "</div>";

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				// 받을 메일
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(member.getMemail()));
				// 보낼 메일
				mimeMessage.setFrom(new InternetAddress("jejukang94@gmail.com"));
				// 메일 제목
				mimeMessage.setSubject("[제주어때] " + member.getMname() + "님 제주도에 오신걸 환영합니다 !");
				// 메일 본문
				mimeMessage.setText(content, "utf-8", "html");
			}
		}; // message 객체 생성
		mailSender.send(message); // 메일 전송
		httpSession.setAttribute("mid", member.getMid());
		member.setMphoto(mphoto);
		return memberDao.joinMember(member);
	}

	@Override
	public String loginCheck(String mid, String mpw, HttpSession httpSession) {
		String result = "로그인 성공";
		Member member = memberDao.getDetailMember(mid);
		if (member == null) {
			result = "유효하지 않은 아이디 입니다.";
		} else if (!member.getMpw().equals(mpw)) {
			result = "비밀번호가 맞지 않습니다";
		} else {
			httpSession.setAttribute("member", member);
		}
		return result;
	}

	@Override
	public Member getDetailMember(String mid) {
		return memberDao.getDetailMember(mid);
	}

	@Override
	public int modifyMember(Member member, HttpSession httpSession, MultipartHttpServletRequest mRequest) {
		String uploadPath = mRequest.getRealPath("memberPhoto/");
		Iterator<String> params = mRequest.getFileNames();
		String mphoto = ""; // 빈 문자열로 초기화
		// 기존 멤버 정보를 세션에서 가져옴
		Member sessionMember = (Member) httpSession.getAttribute("member");
		String dbmphoto = member.getMphoto();
		// 새 비밀번호를 가져옴
		String newPassword = member.getMpw();
		// 새 비밀번호가 null 이거나 빈 문자열일 경우, 기존 비밀번호로 설정
		if (newPassword == null || newPassword.isEmpty()) {
			member.setMpw(sessionMember.getMpw());
		}
		if (member.getMname() == null) {
			member.setMname(sessionMember.getMname());
		}
		boolean fileUploaded = false;
		while (params.hasNext()) {
			String paramName = params.next();
			MultipartFile mFile = mRequest.getFile(paramName);
			if (mFile != null && !mFile.isEmpty()) { // 파일이 선택된 경우에만 파일 업로드 로직 실행
				String originalFileName = mFile.getOriginalFilename();
				String saveFileName = System.currentTimeMillis() + originalFileName;
				mphoto = "memberPhoto/" + saveFileName;
				fileUploaded = true;
				try {
					mFile.transferTo(new File(uploadPath + saveFileName));
					System.out.println("서버파일 : " + uploadPath + saveFileName);
					System.out.println("백업파일 : " + backupPath + saveFileName);
					boolean result = fileCopy(uploadPath + saveFileName, backupPath + saveFileName);
					System.out.println(result ? "백업성공" : "백업실패");
					mphoto = "memberPhoto/" + saveFileName; // 새 사진 경로로 설정
					fileUploaded = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				mphoto = dbmphoto;
			}
		}

		mphoto = fileUploaded ? mphoto : dbmphoto; // 파일이 업로드되지 않은 경우, 이전 사진 경로를 사용
		// 새로운 프로필 사진 경로를 member 객체에 저장
		member.setMphoto(mphoto);
		// 세션에 새 멤버 정보 저장
		httpSession.setAttribute("member", member);
		// 데이터베이스에 업데이트 요청
		return memberDao.modifyMember(member);
	}

	public String findPW(final String mid, final String memail) {
		final Member member = new Member();
		member.setMid(mid);
		member.setMemail(memail);
		// 임시 비밀번호 생성 및 업데이트
		final String tempPw = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
		member.setMpw(tempPw);
		memberDao.updatePW(member);
		String result = memberDao.findPW(member);
		MimeMessagePreparator message = new MimeMessagePreparator() {
			String content = "<div style=\"width:500px; margin: 0 auto\">\n"
					+ "<img src=\"http://localhost:8088/jeju/img/logo_jeju.png\" "
					+ "alt=\"제주도가즈아\" style=\"width: 100%; max-width: 500px;\">\n" + " <h2>제주어때 임시 비밀번호 안내 메일</h2>\n"
					+ " <p>" + member.getMid() + "님의 임시 비밀번호는 " + "\n" + tempPw + " 입니다.</p>\n"
					+ "<p> 로그인 후 꼭 비밀번호를 변경해주세요</p>" + "</div>";

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(member.getMemail())); // 받을 메일 주소
				mimeMessage.setFrom(new InternetAddress("jejukang94@gmail.com")); // 보낼 메일 주소
				mimeMessage.setSubject("[제주어때] " + member.getMid() + "님, 임시 비밀번호 안내 메일입니다."); // 메일 제목
				mimeMessage.setText(content, "utf-8", "html"); // 메일 내용
			}
		};
		mailSender.send(message); // 메일 전송
		return result;
	}

	@Override
	public int updatePW(Member member, HttpSession session) {
		return memberDao.updatePW(member);
	}

	@Override
	public int deleteMember(String mid) {
		int result = memberDao.deleteMember(mid);
		return result;
	}

	@Override
	public void logout(HttpSession httpSession) {
		httpSession.invalidate();

	}

	@Override
	public String findID(String memail, HttpSession session) {
		String mid = memberDao.findID(memail); // 회원 아이디 조회
		if (mid == null) {
			throw new RuntimeException("회원 아이디를 찾을 수 없습니다.");
		}
		return mid;
	}

	private boolean fileCopy(String serverFile, String backupFile) {
		boolean isCopy = false;
		InputStream is = null;
		OutputStream os = null;
		try {
			File file = new File(serverFile);
			is = new FileInputStream(file);
			os = new FileOutputStream(backupFile);
			byte[] buff = new byte[(int) file.length()];
			while (true) {
				int nReadByte = is.read(buff);
				if (nReadByte == -1)
					break;
				os.write(buff, 0, nReadByte);
			}
			isCopy = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		return isCopy;
	}

}