package com.zhd;

import com.zhd.mapper.AreaMapper;
import com.zhd.mapper.BicycleMapper;
import com.zhd.mapper.SupplierMapper;
import com.zhd.pojo.Area;
import com.zhd.pojo.BicycleDe;
import com.zhd.pojo.BicycleSupplier;
import com.zhd.pojo.Supplier;
import com.zhd.service.IBicycleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BssApplicationTests {

	@Autowired
	public MockMvc mockMvc;
//	@Autowired
//	private AreaMapper areaMapper;
	@Autowired
	private BicycleMapper bicycleMapper;
	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private IBicycleService bicycleService;

	@Test
	public void contextLoads() {
        System.out.println("成功启动测试环境");
    }

    @Test
	public void instantDemos(){
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Instant.now().getEpochSecond());
		System.out.println(Instant.now().getNano());
//		1519268498
		System.out.println(LocalDateTime.now().getNano());
		System.out.println(LocalDateTime.now());
		System.out.println(System.currentTimeMillis());
		System.out.println(new Date());
		System.out.println(new Date(Instant.now().getEpochSecond() * 1000));
	}
//
//	@Test
//	public void insertAreaCollection(){
//		List<Area> areas = new ArrayList<>();
//		areas.add(Area.builder().name("name1").northPoint(BigDecimal.valueOf(1.1))
//				.southPoint(BigDecimal.valueOf(2.2)).eastPoint(BigDecimal.valueOf(3.3))
//				.westPoint(BigDecimal.valueOf(4.4)).type(2).build());
//		areas.add(Area.builder().name("name2").northPoint(BigDecimal.valueOf(2.22))
//				.southPoint(BigDecimal.valueOf(3.33)).eastPoint(BigDecimal.valueOf(4.44))
//		.westPoint(BigDecimal.valueOf(5.55)).type(2).build());
//		areas.add(Area.builder().name("name3").northPoint(BigDecimal.valueOf(3.333))
//				.southPoint(BigDecimal.valueOf(4.444)).eastPoint(BigDecimal.valueOf(5.555))
//				.westPoint(BigDecimal.valueOf(6.666)).type(2).build());
//		areaMapper.insertCollection(areas);
//	}

	@Test
	public void insertSupp(){
		Supplier supplierA = Supplier.builder().name("供应商A").address("供应商A的地址").build();
		Supplier supplierB = Supplier.builder().name("供应商B").address("供应商B的地址").build();
		Supplier supplierC = Supplier.builder().name("供应商C").address("供应商C的地址").build();
		BicycleSupplier bicycleSupplier = BicycleSupplier.builder().supplierList(Arrays.asList(supplierA,supplierB,supplierC)).batch("1122334455").build();
		bicycleService.insertBicycleSupplier(bicycleSupplier);

	}

	@Test
	public void selectBicycleSupplier(){
//		System.out.println(bicycleService.selectBicycleSupplier("1122334455"));
		System.out.println(bicycleMapper.selectByBatch("1122334455"));
	}

	@Test
	public void insertBicycleDe(){
		List suppliers = Arrays.asList(22,33,44);
		bicycleMapper.insertBicycleDe(BicycleDe.builder().supplier(suppliers).batch("444234").build());
	}

}
