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
  <title>KyonStore template</title>
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
  <!-- link rel="stylesheet" href="css/xxx.css" -->
</head>
<body>
  <div id="app">
    {{msg}}
  </div>
</body>
<script>
new Vue({
  el: '#app',
  data () {
    return {
      msg: 'template'
    }
  },
  methods: {
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
  }
})
</script>
</html>