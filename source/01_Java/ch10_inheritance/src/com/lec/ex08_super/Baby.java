package com.lec.ex08_super;
// private(name, character) public(intro())
public class Baby extends Person{
	public Baby() {
		super(); // == Person()
		System.out.println("�Ű����� ���� Baby ������");
	}
	public Baby(String name, String character) { //����,ĳ���� �ʱ�ȭ �۾�
		super(name, character); // == Person(name, character);
//		setName(name);
//		setCharacter(character);
		System.out.println("�Ű����� �ִ� Baby ������");
	}
	@Override
	public void intro() {
		System.out.print("���̻� �Ʊ�");
		super.intro(); // �θ�Ŭ������ intro()
	}
}