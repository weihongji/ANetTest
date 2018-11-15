package LangTest;

import java.util.function.*;

public class LambdaTest_Simple {
	
	//Function<T, R> is defined in java.util.function
	private static <T, R> R test_apply(Function<T, R> f, T t) {
		return f.apply(t);
	}
	
	private static <T> boolean test_test(Predicate<T> f, T t) {
		return f.test(t);
	}
	
	public static void main(String[] args) {
		System.out.println(test_apply((Integer n)-> n*10, 5));
		
		System.out.println(test_test((Integer n)-> n < 100, 5));
	}
}
