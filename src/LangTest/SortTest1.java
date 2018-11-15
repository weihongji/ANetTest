package LangTest;

import java.util.*;

public class SortTest1 {

	public static List<SortObject> getList() {
		List<SortObject> list = new ArrayList<SortObject>();
		list.add(new SortObject(4, "Nova", 150.00));
		list.add(new SortObject(1, "Jesse", 100.00));
		list.add(new SortObject(2, "Erik", 80.00));
		list.add(new SortObject(3, "Thomas", 120.00));
		return list;
	}
	
	public static void main(String[] args) {
		List<SortObject> list = getList();
		
		System.out.println("Original list...");		
		for (SortObject o : list) {
			System.out.println(o);
		}
		
		System.out.println("");
		System.out.println("Sort by default(Comparable<T>)...");
		list.sort(null);
		
		for (SortObject o : list) {
			System.out.println(o);
		}

		System.out.println("");
		System.out.println("Sort by id...");
		list.sort(SortObject::byId);
		
		for (SortObject o : list) {
			System.out.println(o);
		}
		
		System.out.println("");
		System.out.println("Sort by name...");
		list.sort(SortObject::byName);
		
		for (SortObject o : list) {
			System.out.println(o);
		}

		System.out.println("");
		System.out.println("Sort by balance...");
		list.sort(SortObject::byBalance);
		
		for (SortObject o : list) {
			System.out.println(o);
		}

	}
}

class SortObject implements Comparable<SortObject> {
	private int id;
	private String name;
	private double balance;
	
	public SortObject(int id, String name, double balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;
	}
	
	@Override
	public int compareTo(SortObject arg0) {
		return this.id - arg0.id;
	}
	
	@Override
	public String toString() {
		return String.format("{%d, %s, %10.2f}", id, name, balance);
	}
	
	public static int byId(SortObject o1, SortObject o2) {
		if (o1 == null || o2 == null) {
			return 0;
		}
		else {
			return o1.id - o2.id;
		}
	}
	
	public static int byName(SortObject o1, SortObject o2) {
		if (o1 == null || o2 == null) {
			return 0;
		}
		else {
			return o1.name.compareTo(o2.name);
		}
	}
	
	public static int byBalance(SortObject o1, SortObject o2) {
		if (o1 == null || o2 == null) {
			return 0;
		}
		else {
			if (o1.balance == o2.balance) {
				return 0;
			}
			else {
				return o1.balance > o2.balance ? 1:-1;
			}
		}
	}
}