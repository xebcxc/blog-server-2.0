package pro.meisen.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动器
 * @author meisen
 * 2019-07-13
 */
@SpringBootApplication
@EnableTransactionManagement //开启事务支持
@MapperScan(basePackages = { "pro.meisen.boot.dao.mapper" })
public class BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

}
