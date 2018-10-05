package com.stylefeng.guns;

import com.stylefeng.guns.rest.OrderApplication;
import com.stylefeng.guns.rest.common.util.FtpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class GunsRestApplicationTests {

//	 注入
    @Autowired
	private FtpUtil ftpUtil;
	@Test
	public void contextLoads() {
		// ftp 读取配置
		String fileStrByAddress = ftpUtil.getFileStrByAddress("seats/cgs.json");

		System.out.println(fileStrByAddress);
	}

}
