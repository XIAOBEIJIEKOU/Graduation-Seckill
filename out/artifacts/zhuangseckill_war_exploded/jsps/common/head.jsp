		<meta name="viewport" content="width=device-width, initial-scale=1.0">
      <!-- 引入 Bootstrap -->
      <link href="http://cdn.static.runoob.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
 
      <!-- HTML5 Shim 和 Respond.js 用于让 IE8 支持 HTML5元素和媒体查询 -->
      <!-- 注意： 如果通过 file://  引入 Respond.js 文件，则该文件无法起效果 -->
      <!--[if lt IE 9]>
         <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
         <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
      <![endif]-->
      <!-- 新 Bootstrap 核心 CSS 文件
		<link href="https://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet"> -->
      
      
      <!-- 
      
      MDZZ
      1.首先是是   jquery  .val()取不到 input 标签的值，原因在于Jquery的版本不对，之前用的是 本地的 js和bootstrap
        	后来又用cdn的Jquery和bootstrap，试了好多遍都不行！然后看了好几遍视频最终发现，坑啊！用的是是另一个版本的！
      
      2.再就是出现屏幕全为灰色   查了好几遍就是没有注意到最上方的 bootstrap.css的引入改为3.3.0
      
      3.视频中居然也没有注意一下提示，版本的问题
      
      
       -->