package me.nexters.chop;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ChopApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("hello");
		Integer a = 3;
		String s = Base64.getEncoder().encodeToString(String.valueOf(a).getBytes());
		System.out.println(s);
	}

}

