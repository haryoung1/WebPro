package com.lec.ex5_scanner;

import java.util.Scanner;

public class Ex04_ScannerObj {
	public static void main(String[] args) {
		Scanner scMain = new Scanner(System.in);
		System.out.println("이름은 ? ");
		String name = scMain.nextLine();
		System.out.println("입력한 이름은 : " + name);
		// 메소드 : 별명을 입력받아 출력
		getNickName(scMain); // 밑에 메소드에 옮겨줌
		System.out.println("나이는 ?");
		int age = scMain.nextInt();
		System.out.println("입력한 나이는 : " + age);
		scMain.close();
	}

	private static void getNickName(Scanner scNick) {
		System.out.println("별명은 ?");
		String nickName = scNick.nextLine();
		System.out.println("입력한 별명은 : " + nickName);
	}
}