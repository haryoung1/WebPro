package com.lec.ex;
// ����񱳿����� (���迬����) : ==(����), !=(�ٸ���), >, >=,
public class EX03 {
	public static void main(String[] args) {
		int n1 = 10, n2=5;
		boolean result;
		result = n1>=n2;
		System.out.printf("%d %s %d �� %b\n", n1, ">=", n2, result);
		result = n1==n2;
		System.out.printf("%d %s %d �� %b\n", n1, "==", n2, result);
		result = n1!=n2;
		System.out.printf("%d %s %d �� %b\n", n1, "!=", n2, result);
		
		result = !(n1!=n2);
		System.out.println("!(n1!=n2)�� "+ result);
	}
}