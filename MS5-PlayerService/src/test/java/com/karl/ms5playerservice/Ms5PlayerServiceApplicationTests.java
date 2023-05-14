package com.karl.ms5playerservice;

import com.karl.ms5playerservice.controller.PlayerController;
import com.karl.ms5playerservice.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class Ms5PlayerServiceApplicationTests {

	@Autowired
	private PlayerController playerController;

	@Autowired
	private PlayerService playerService;
	@Test
	void contextLoads() {
		assertThat(playerController).isNotNull();
		assertThat(playerService).isNotNull();
	}

}
