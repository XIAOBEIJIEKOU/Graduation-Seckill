// 	存放主要交互的代码
//	javascript模块化
//	function(result){} 中result指代的是controller返回的XHR

var seckill = {
	/* 存放所有AJAX请求的服务器地址 */
	URL : {
		now : function() {
			return '/seckill/time/now';
		},
		exposer : function(seckillId) {
			return '/seckill/' + seckillId + '/exposer';
		},
		execution : function(seckillId, userPhone) {
			return '/seckill/' + seckillId + '/' + userPhone + '/execution';
		},
	},
	/* 验证手机号 */
	validatePhone : function(userPhone) {
		//	isNAN 表示的是boolean 是否是字符
		if (userPhone && userPhone.length == 11 && !isNaN(userPhone)) {
			return true;
		} else {
			return false;
		}
	},
	/* 秒杀操作 */
	//	node表示的一个html节点
	executeKill : function(seckillId, node) {
		//	hide() 表示的是class='hide',然后在下面设置 show()显示出来
		node.hide().html('<button class="btn btn-primary btn-lg" id="killButton">开始秒杀</button>');
		//	$.post(url,参数,回调函数（执行post请求返回结果之后的操作，result表示形参表示的是post请求返回的东西）)
		// 执行暴露接口操作
		$.post(seckill.URL.exposer(seckillId),{},function(result){
			//	回调函数执行交互逻辑
			if (result && result['success']) {
				var exposer = result['data'];
				// 防止用户PC系统时间和服务器时间不一致
				if (exposer['exposed']) {
					//	开启秒杀，获取秒杀地址
					// var md5 = exposer['md5'];  md5值测试太麻烦了
                    var userPhone = $.cookie('userPhone');
					var killURL = seckill.URL.execution(seckillId, userPhone);
					//	只绑定一次，防止用户多次点击传入后台，后台收到多余重复的指令
					$('#killButton').one('click',function(){
						//	执行秒杀
						//	并且设置点击按钮事件之后的动作：传到后台进行减库存，设置页面跳转
						$(this).addClass('disabled');
						//	注意SeckillResult<SeckillExecution>返回的构造函数中包含的success属性用来进入下面的if判断
						//	若controller中的值为false则进入不了if则显示不出其他的判断
                        $.ajax({
                            type: 'POST',
                            url: killURL,
                            async: false,
                            contentType: 'application/json',
                            data: {},
                            dataType: 'json',
                            success: function (result) {
                                if(result && result['success']){
                                    var killResult = result['data'];
                                    var state = killResult['state'];
                                    var stateInfo = killResult['stateInfo'];
                                    //	显示秒杀结果
                                    node.html('<span class="label label-success">'+stateInfo+'</span>');
                                }
                            },
                            error: function () {
                                $.alert("调用服务失败");
                            }
                        });
					});
					node.show();
				} else {
					//	若PC端时间确实有误则引入服务器时间重新计时
					var now = exposer['now'];
					var start = exposer['startTime'];
					var end = exposer['endTime'];
					seckill.countdown(seckillId, now, start, end);
				}
			} else {
				alert(result['error']);
			}
		});
	},
	/* 倒计时插件  */
	countdown : function(seckillId, nowTime, startTime, endTime) {
		var timeBox = $('#timeBox');
		if (nowTime > endTime) {
			timeBox.html('秒杀结束！');
		} else if (nowTime < startTime) {
			var killTime = new Date(startTime);
			//	倒计时方法，不同的版本需要的参数不同，这里需要的是秒杀开启时间
			timeBox.countdown(killTime, function(event) {
				var format = event.strftime('秒杀倒计时：%D天 	 %H时	%M分		 %S秒');
				timeBox.html(format);
			}).on('finish.countdown', function() {
				/* 倒计时结束之后要进行的操作 */
				seckill.executeKill(seckillId, timeBox);
			});
		} else {
			/* 秒杀开启中，只要时间和用户名判断是正确的则直接在executeKill中暴露秒杀接口*/
			seckill.executeKill(seckillId, timeBox);
		}
	},
	/* */
	detail : {
		// 初始化，页面加载到script直接执行不需要触发
		// 用户手机号验证 ， 计时交互
		init : function(params) {
			// 在cookie中查找userPhone
			var userPhone = $.cookie('userPhone');
			// 验证手机号
			if (!seckill.validatePhone(userPhone)) {
				// 如果没有从cookie中取到userPhone则为false进入方法然后通过Modal让用户输入
				$('#userPhone').modal({
					show : true, // 设置显示
					backdrop : false, // 设置无法通过鼠标点击背景关闭
					keyboard : false
				// 设置无法通过键盘关闭
				});
				$('#submit').click(function() {
									var inputPhone = $('#inputPhone').val();
									//alert(inputPhone);
									if (seckill.validatePhone(inputPhone)) {
										// reload
										// 创建一个cookie并设置cookie的有效路径和时间：
										$.cookie('userPhone', inputPhone, {expires:1,path:'/seckill'});
										window.location.reload();
									} else {
										$('#errorMessage').hide().html('<lable class="label label-danger">手机号有误^o^！</label>').show(300);
									}
								});
			}
			// 若没有进入if，则说明cookie存在
			// 计时交互，获取服务器的时间，而不是客户端的时间，使所有用户时间统一
			var seckillId = params['seckillId'];
			var startTime = Number(params['startTime']);
			var endTime = Number(params['endTime']);
			$.get(seckill.URL.now(), {}, function(result) {
				if (result && result['success']) {
					var nowTime = Number(result['data']);
					// 时间判断
					seckill.countdown(seckillId, nowTime, startTime, endTime);
				} else {
					alert(result);
				}
			})
		} // init
	}
// detail
}