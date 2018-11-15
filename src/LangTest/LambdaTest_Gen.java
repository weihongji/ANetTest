package LangTest;

import java.util.*;

class LNums<T extends Number> {
	private T[] nums;
	
	LNums(T[] numbers) {
		nums = numbers;
	}
	
	public double getAverage() {
		double t = 0;
		if (nums != null && nums.length>0) {
			for(Number n : nums) {
				t+= n.doubleValue();
			}
			t = t/nums.length;
		}
		return t;
	}
	
	public String getString(DisplayText<T> f) {
		return f.getText(nums);
	}
}

interface DisplayText<T> {
	String getText(T[] list);
}

class DisplayTest<T> implements DisplayText<T> {
	public String getText(T[] list) {
		List<String> strings = new LinkedList<>();
		for(T s: list) {
			strings.add(s.toString());
		}
		return "{" + (list == null ? "" : String.join(",", strings)) + "} (DisplayTest)";
	}
}

class MethodTest {
	public static <T> String getText1(T[] list) {
		List<String> strings = new LinkedList<>();
		for(T s: list) {
			strings.add(s.toString());
		}
		return "{" + (list == null ? "" : String.join(",", strings)) + "} (MethodTest)";
	}
}

public class LambdaTest_Gen {
	
	private static <T> String op(DisplayText<T> f, T[]list) {
		return f.getText(list);
	}
	
	public static void main(String args[]) {
		Integer []an = {1, 2, 3, 4};
		
		DisplayTest<Integer> d = new DisplayTest<>();
		System.out.println(d.getText(an));
		
		System.out.println(op(MethodTest::<Integer>getText1, an));
		
		DisplayText<Integer> f = (Integer[] list)-> {
			String s = "";
			if (list == null || list.length == 0) {
				// nothing
			}
			else {
				for (Integer i : list) {
					if (s.length() > 0) { s+=","; }
					s += i.toString();
				}
			}
			return String.format("{%s} (Lambda)", s);
		};
		
		LNums<Integer> io = new LNums<>(an);
		System.out.println(io.getString(f));
		System.out.println(io.getAverage());
	}
}
