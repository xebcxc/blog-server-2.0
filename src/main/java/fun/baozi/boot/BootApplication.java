package fun.baozi.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动器
 * @author baozi
 * 2019-07-13
 */
@SpringBootApplication
@EnableTransactionManagement //开启事务支持
@EnableScheduling
@MapperScan(basePackages = { "fun.baozi.boot.dao.mapper" })
public class BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

}
