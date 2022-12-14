package com.lec.ex07_robot;

public class RobotMain {
	public static void main(String[] args) {
		Robot[] robots = {new DanceRobot(), new DrawRobot(), new SingRobot()};
		// �κ����� �Ͻ�Ű�� ��ü <= ���� : Ŭ������ �޼ҵ�(�Ű������� robot�� �޾� ������ �޼ҵ� ȣ��)
		RobotOrder order = new RobotOrder();
		for(Robot robot : robots) {
			order.action(robot);
		}
		// ��ü.�޼ҵ�(robot) 
		
	}
}