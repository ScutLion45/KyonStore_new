<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//System.out.println(basePath);
HttpSession hs = request.getSession();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
  <base href="<%=basePath%>">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>KyonStore Top</title>
  <!-- import CSS -->
  <link rel="stylesheet" href="css/elem-index.css">
  <!-- import jQuery -->
  <script src="js/jquery-3.3.1.min.js"></script>
  <!-- import Vue before Element -->
  <script src="js/vue-2.6.10.min.js"></script>
  <!-- import axios -->
  <script src="js/axios-0.19.0.min.js"></script>
  <!-- import JavaScript -->
  <script src="js/elem-index.js"></script>
  <!-- import global.js -->
  <script src="js/global.js"></script>
  <!-- import CSS -->
  <link rel="stylesheet" href="css/user/top.css">
</head>
<body>
  <!-- [top]当前登录用户${sessionScope.user.uName} -->
  <el-container id="user-top">
    <el-aside width="25%"><span id="KyonStoreLOGO"></span></el-aside>
    <el-main>
      <span :class="{
        'top-tab': true,
        'top-tab--active': '${sessionScope.page}'==='home'
      }" @click="toHome">主页</span>
      <span :class="{
        'top-tab': true,
        'top-tab--active': '${sessionScope.page}'==='search'
      }" @click="toSearch">搜索</span>
      <span :class="{
        'top-tab': true,
        'top-tab--active': '${sessionScope.page}'==='shoppingcart'
      }" @click="toShoppingCart">购物车</span>
    </el-main>
    <el-aside width="25%" class="top-aside-R">
      <span class="uName">${sessionScope.user.uName}</span>
      <span class="logout"><i class="el-icon-switch-button"></i>登出</span>
    </el-aside>
  </el-container>
</body>
<script>
new Vue({
  el: '#user-top',
  data () {
    return {
      page: ''
    }
  },
  methods: {
    // 页面跳转
    toHome() {},
    toSearch() {
      <% System.out.println((String)hs.getAttribute("page")); %>
      if('${sessionScope.page}' !== 'search') {
        <%
          if(!"search".equals((String)hs.getAttribute("page")))
            hs.setAttribute("page", "search");
        %>
        // window.location.href('/userJSP/search.jsp')
        // $('#topFrame').attr('src', 'userJSP/search.jsp')
        // 改为a标签
      }
    },
    toShoppingCart() {},
    axiosUtil() {
      var ctx =  this
      var params = new URLSearchParams({
        xxx: 0
      })
      axios.post('/...', params)
      .then(function(res){
        // console.log(res.data)
        // ctx.$nextTick(()=>{})
      }).catch(function(err) {
        console.log(err)
        ctx.$message({ type: 'error', message: '请求失败', duration: 1000 })
        // ctx.$nextTick(()=>{})
      })
    }
  },
  created() {
  }
})
</script>
</html>