package com.shenma.top.aliutil;

import static org.junit.Assert.*;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import org.junit.Before;
import org.junit.Test;

public class ReTest {

	@Before
	public void setUp() throws Exception {
	}

	//@Test
	public void test() {
		Object referent = new Object();
		Object strongReference = referent;
		referent = null;
		System.gc();
		assertNotNull(strongReference);
	}

	//@Test
	public void testSoftReference() {
		String str = "test";
		SoftReference<String> softreference = new SoftReference<String>(str);
		str = null;
		System.gc();
		assertNotNull(softreference.get());
	}

	//@Test
	public void testWeakReference() {
		String str = "test";
		WeakReference<String> weakReference = new WeakReference<String>(str);
		str = null;
		System.gc();
		assertNull(weakReference.get());
	}
}
