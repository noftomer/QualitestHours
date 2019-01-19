package testCases;

import org.junit.runner.*;
import org.junit.runners.Suite;

@RunWith(Suite.class)
public class testRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Starting execution");
		JUnitCore.main("testCases.Sanity");
		System.out.println("Ending execution");
	}

}
