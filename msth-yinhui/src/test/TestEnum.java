package test;

enum Signal {
	GREEN, YELLOW, RED
	} 
public class TestEnum {
	//用法一：常量
	public static enum test1{
		RED, GREEN, BLANK, YELLOW
	}
	
	public static void main(String[] args) {
		System.out.println(test1.RED);
	}
}
