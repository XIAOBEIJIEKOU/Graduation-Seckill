package org.seckill.controller;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.StateEnums;
import org.seckill.exception.RepeatException;
import org.seckill.exception.SeckillClose;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * url:/模块/资源/{id}/细分功能
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
	
	@Autowired
	private SeckillService seckillService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list", list);
		return "list";
	}

	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
		if (seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getSeckill(seckillId);
		if (seckill == null) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}

	/*
	 * 响应前台jQuery请求，Ajax返回Json
	 */
	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable Long seckillId) {
		// 定义一个返回的封装数据结果
		SeckillResult<Exposer> result;
		try {
			//暴露秒杀接口
			Exposer exposer = seckillService.exportSeckillUrlNoRedis(seckillId);
//			Exposer exposer = seckillService.exportSeckillUrlWithRedis(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}

    /**
     * Jmeter测试url：取消了md5的检验，取消了从@Cookie中获取参数userPhone，取消了seckill/{}/{}/execuition 的Restful路由，可以使用Get请求
     * @param seckillId
     * @param userPhone
     * @return
     */
	@RequestMapping(value = "/execution", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<SeckillExecution> execute (@RequestParam("seckillId") Long seckillId, @RequestParam("userPhone") Long userPhone) {

//	@RequestMapping(value = "/{seckillId}/{userPhone}/execution", method = RequestMethod.POST, produces = {
//			"application/json;charset=UTF-8" })
//	@ResponseBody
//	public SeckillResult<SeckillExecution> execute (@PathVariable("seckillId") Long seckillId, @PathVariable("userPhone") Long userPhone) {
		//内部逻辑处理userphone，否则报错
		if (userPhone == null) {
			return new SeckillResult<SeckillExecution>(false,"未注册");
		}
		SeckillResult<SeckillExecution> result;
		//try-catch将所有的错误处理掉并且组成返回结果集返回
		try {
			//秒杀顺利进行
//			SeckillExecution execution = seckillService.executeSeckillWithRedis(seckillId, userPhone);
//			SeckillExecution execution = seckillService.executeSeckillNoRedis(seckillId,userPhone);
			SeckillExecution execution = seckillService.executeSeckillAllRedis(seckillId,userPhone);
			return new SeckillResult<SeckillExecution>(true,execution);
		} catch (RepeatException e) {
			//重复秒杀
			SeckillExecution execution = new SeckillExecution(seckillId, StateEnums.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true,execution);
		} catch (SeckillClose e) {
			//秒杀结束
			SeckillExecution execution = new SeckillExecution(seckillId, StateEnums.END);
			return new SeckillResult<SeckillExecution>(true,execution);
		} catch (SeckillException e) {
			//未知的系统错误
			SeckillExecution execution = new SeckillExecution(seckillId, StateEnums.SYSTEM_ERROR);
			return new SeckillResult<SeckillExecution>(true,execution);
		}
	}
	
	@RequestMapping(value="/time/now",method=RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time() {
		Date now = new Date();
		return new SeckillResult<Long>(true, now.getTime());
	}
}
