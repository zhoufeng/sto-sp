package com.shenma.top.aliutil;

import static org.junit.Assert.*;

public class Test {
	public static void main(String[] args) {
		Test.testStrongReference();
	}
	
	public static void testStrongReference() {
		Object referent = new Object();
		Object strongReference = referent;
		referent = null;
		System.gc();
		assertNotNull(strongReference);
	}
}
