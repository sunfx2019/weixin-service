package com.yimai.idgenerator;

public class UUID {

	private UUID() {

	}

	public synchronized static String randomUUID() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}

	public synchronized static String randomUUIDUp() {
		return randomUUID().toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().toUpperCase());
		System.out.println(UUID.randomUUID().toString().length());
		System.out.println(UUID.randomUUID().length());
		System.out.println(UUID.randomUUID());
		System.out.println(UUID.randomUUIDUp());
		
		long begin = System.nanoTime();
		for (int i = 0; i < 10; i++) {
			String id = UUID.randomUUID();
			System.out.println(id);
		}
		System.out.println("time=" + (System.nanoTime() - begin)/1000.0 + " us");
		
	}
}