package com.lec.ex10_final;
// speed, running(), stop(final) - override 못함.
public class Dog extends Animal {
	@Override
	public void running() {
		speed += 10;
		System.out.println("강아지가 뛰면서 꼬리를 흔들어요. 현재속도 : " + speed);
	}
}