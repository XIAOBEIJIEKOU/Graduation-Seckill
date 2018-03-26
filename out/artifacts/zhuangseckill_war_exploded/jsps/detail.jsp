<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/tag.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<title>秒杀商品详情页</title>
<%@include file="common/head.jsp"%>
</head>
<body>
	<h1 align="center">${seckill.name}</h1>

	<h3 align='center' id="timeBox"></h3>
	
	<!-- 模态框（Modal） -->
	<div id="userPhone" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel">秒杀电话</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" id="inputPhone" name="inputPhone" placeholder="请输入手机号^o^!" class="form-control"/>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<span class="glyphicon" id="errorMessage"></span>
					<button type="button" class="btn btn-primary" id="submit">提交更改</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>	
	<!-- CDN 地址 -->
	<%@include file="common/foot.jsp"%>
	<script
		src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
	<script
		src="https://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
	<!-- 交互逻辑 -->
	<script type="text/javascript" src="/resources/script/seckill.js"></script>
	<script type="text/javascript">
	//seckillId:"${seckill.seckillId}"这里要加双引号 因为jsp的解析器不一样导致的
		$(function(){
				//EL表达式传参数
				/*
				秒杀倒计时NaN天NaN时...
				这个问题是你在jsp传参的时候el表达式加了引号
				所以要把startTime转换成数字类型使用方法Number(startTime)即可
				*/
				seckill.detail.init({
					seckillId : "${seckill.seckillId}",
					startTime : "${seckill.startTime.time}",
					endTime : "${seckill.endTime.time}"
				});
			});
 	</script>
</body>
</html>
