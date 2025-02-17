const infoConfirm = function() {
	var patternMail = /^[a-zA-Z0-9_\.]+@[a-zA-Z0-9_]+(\.\w+){1,2}$/; // macth 함수 사용
	if (join_frm.cid.value < 3) {
		alert('아이디는 반드시 3글자 이상');
		join_frm.cid.focus();
		return;
	}
	if (!join_frm.cpw.value) {
		alert('비밀번호는 필수입력사항 입니다');
		join_frm.cpw.focus();
		return;
	}
	if (join_frm.cpw.value != join_frm.cpwChk.value) {
		alert('비밀번호를 확인하세요');
		join_frm.cpw.value = '';
		join_frm.cpwChk.value = '';
		join_frm.cpw.focus();
		return;
	}
	if (!join_frm.cname.value) {
		alert('이름은 필수입력 사항 입니다');
		join_frm.cname.focus();
		return
	}
	if (join_frm.cemail.value && !join_frm.cemail.value.match(patternMail)) {
		alert('메일 형식을 확인하세요');
		join_frm.cemail.focus();
		return;
	}
	join_frm.submit(); // 강제로 submit 이벤트 발생
};