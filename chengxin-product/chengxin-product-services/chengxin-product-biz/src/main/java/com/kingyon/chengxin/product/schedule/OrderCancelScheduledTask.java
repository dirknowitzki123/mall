package com.kingyon.chengxin.product.schedule;

import com.kingyon.chengxin.product.mapper.OmOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
@Slf4j
public class OrderCancelScheduledTask {
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	OmOrderMapper orderMapper;

	@Scheduled(cron = "0 0 0 * * ?")
	public void checkOrder() {
		//自动取消未付款订单
		log.info("订单未支付自动取消订单 执行时间: " + formatter.format(new Date()));
		orderMapper.autoCancelMallOrder();
	}
}
