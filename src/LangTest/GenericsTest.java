package LangTest;

class Nums<T extends Number> {
	private T[] nums;
	
	Nums(T[] numbers) {
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
}

public class GenericsTest {
	
	public static void main(String args[]) {
		Integer []an = {1, 2, 3, 4};
		Nums<Integer> io = new Nums<>(an);
		System.out.println(io.getAverage());
	}
}
