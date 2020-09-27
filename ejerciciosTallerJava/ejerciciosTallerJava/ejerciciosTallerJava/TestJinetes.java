package ejerciciosTallerJava;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestJinetes {

	public int jinetes(int[] estaciones) 
	{
		int cantJinetes = 1;
		int sumaMillas = 0;
		
		for(int i = 0; i < estaciones.length; i++)
		{
			if(sumaMillas + estaciones[i] > 100)
			{
				cantJinetes++;
				sumaMillas = 0;
			}
			
			sumaMillas += estaciones[i];				
		}
		
		return cantJinetes;
	}
	
	@Test
	public void test1() {
		assertEquals(1, jinetes(new int[] {18, 15}));
	}

	@Test
	public void test2() {
		assertEquals(2, jinetes(new int[] {43, 23, 40, 13}));
	}
	
	@Test
	public void test3() {
		assertEquals(3, jinetes(new int[] {33, 8, 16, 47, 30, 30, 46}));
	}
	
	@Test
	public void test4() {
		assertEquals(3, jinetes(new int[] {51, 51, 51}));
	}
	
	@Test
	public void test5() {
		assertEquals(4, jinetes(new int[] {6, 24, 6, 8, 28, 8, 23, 47, 17, 29, 37, 18, 40, 49}));
	}
	
}
