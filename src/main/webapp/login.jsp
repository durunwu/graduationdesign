<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>健身房管理系统</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/HTmoban/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/HTmoban/css/font.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/HTmoban/css/xadmin.css">
<%--    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>--%>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/jquery@3.2.1/dist/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/HTmoban/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/HTmoban/js/xadmin.js"></script>
<%--    <script src="https://cdn.staticfile.org/layui/2.6.8/layui.min.js"></script>--%>
<%--    <script>--%>
<%--        layui.use('form', function(){--%>
<%--            var form = layui.form;--%>
<%--            form.render(); // 渲染表单元素--%>
<%--        });--%>
<%--    </script>--%>
</head>
<body class="login-bg">
    
    <div class="login layui-anim layui-anim-up">
        <div  class="message">健身房管理系统</div>
        <div id="darkbannerwrap"></div>
        
        <form method="post" class="layui-form" action="${pageContext.request.contextPath}/dl/yz">
            <span style="color: red;">${msg}</span>
<%--            <span style="color: red;">${msg}</span>--%>
            <select name="type" lay-verify="required" class="layui-input">
                <option >请选择用户类型</option>
                <option value="members">会员</option>
                <option value="coaches">教练</option>
                <option value="administrators">管理员</option>
            </select>
            <hr class="hr15">
            <input name="username" placeholder="用户名"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="password" lay-verify="required" placeholder="密码"  type="password" class="layui-input">
            <hr class="hr15">
            <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
            <hr class="hr20" >
            <!-- 添加用户注册链接 -->
            <a href="${pageContext.request.contextPath}/register">用户注册</a>

        </form>
    </div>
    <script>
        if (window.top !== window.self) {
            window.top.location = window.location
        }
    </script>
    <%--<script>--%>
        <%--$(function  () {--%>
            <%--layui.use('form', function(){--%>
              <%--var form = layui.form;--%>

              <%--// layer.msg('玩命卖萌中', function(){--%>
              <%--//   //关闭后的操作--%>
              <%--//   });--%>
              <%--//监听提交--%>
              <%--form.on('submit(login)', function(data){--%>
                 <%--alert(888);--%>
                <%--layer.msg(JSON.stringify(data.field),function(){--%>
                    <%--location.href='${pageContext.request.contextPath}/dl/yz'--%>
                <%--});--%>
                <%--return false;--%>
              <%--});--%>
            <%--});--%>
        <%--})--%>

        <%----%>
    <%--</script>--%>


    <!-- 底部结束 -->
    <div class="footer">
        <div align="center">
            <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11010602007158" style="display:inline-block;text-decoration:none;color: #fff;"><img src="/static/HTmoban/images/备案图标.png">京公网安备 1123</a>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <a style=" text-decoration: none; color: #fff;" href="http://www.beian.miit.gov.cn/" target="_blank"> 京ICP备225555号-1</a>
        </div>
    </div>
</body>

<!-- 引入 Layui 的 JS -->
<script src="path/to/layui/layui.js"></script>
<script>
    // 初始化 Layui 表单组件
    layui.use('form', function(){
        var form = layui.form;
        form.render(); // 关键：重新渲染表单
    });
</script>
</html>
