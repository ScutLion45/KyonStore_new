// 全局注册函数,日期格式化
Date.prototype.format = function(fmt) { 
  var o = { 
    "M+" : this.getMonth()+1,                 // 月份 
    "d+" : this.getDate(),                    // 日 
    "h+" : this.getHours(),                   // 小时 
    "m+" : this.getMinutes(),                 // 分 
    "s+" : this.getSeconds(),                 // 秒 
    "q+" : Math.floor((this.getMonth()+3)/3), // 季度 
    "S"  : this.getMilliseconds()             // 毫秒 
  } 
  if(/(y+)/.test(fmt)) {
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length))
  }
  for(var k in o) {
    if(new RegExp("("+ k +")").test(fmt)){
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)))
    }
  }
  return fmt
}
// 全局注册函数,获取当前系统日期(yyyy-MM-dd),以'-'为分隔符,返回类型为string
Vue.prototype.$getLocaleDate = function () {
  var localDate = new Date().format('yyyy-MM-dd hh:mm:ss')
  return localDate.split(' ')[0]
}
// 全局注册函数,获取当前系统时间(HH:mm:ss),以':'为分隔符,返回类型为string
Vue.prototype.$getLocaleTime = function () {
  var localDate = new Date().format('yyyy-MM-dd hh:mm:ss')
  return localDate.split(' ')[1]
}
// 全局注册函数,获取当前系统年份(返回类型为number)
Vue.prototype.$getLocaleYear = function () {
  return new Date().getFullYear()
}
// 全局注册函数,输入(yyyy-MM-dd HH:mm:ss)格式日期,返回(yyyy-MM-dd HH:mm)格式
Vue.prototype.$getDateTime = function (time) {
  return time.split(':').slice(0, 2).join(':')
}
// 全局注册函数,将字符串剪切成指定长度,并拼接后缀
Vue.prototype.$getShortStr = function (str, len, suffix) {
  if(typeof str === 'string' && str.length > len && typeof len === 'number') {
    var suf = '...'
    if(typeof suffix === 'string')
      suf = suffix
    return str.slice(0, len) + suf
  } else
    return str
}
const shortenStr = (str, len, suffix) => {
  if(typeof str === 'string' && str.length > len && typeof len === 'number') {
    var suf = '...'
    if(typeof suffix === 'string')
      suf = suffix
    return str.slice(0, len) + suf
  } else
    return str
}
// 全局注册函数,传入参数获取[minNum, maxNum)之间的随机整数
Vue.prototype.$getRandomNum = function(minNum, maxNum) {
  // 如果只有minNum,则随机数范围为 [1, minNum]
  // 如果minNum和maxNum都有,随机数范围为 [minNum, maxNum]
  switch(arguments.length){ 
    case 1: 
      return parseInt(Math.random()*minNum+1,10)
    case 2: 
      return parseInt(Math.random()*(maxNum-minNum+1)+minNum,10) 
    default: 
      return 0 
  } 
}

// 全局注册函数,生成随机No
Vue.prototype.$generateRandomNo = function() {
  var no = ''
  var str = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
  var strLen = str.length

  var fmtDateTime = new Date().format('yyyy-MM-dd hh:mm:ss')
  var fmtDateArr = fmtDateTime.split(' ')[0].split('-')
  var fmtTimeArr = fmtDateTime.split(' ')[1].split(':')

  var MM = fmtDateArr[1]
  var dd = fmtDateArr[2]
  var hhmmss = fmtTimeArr.join('')

  no = MM
       + str.charAt(this.$getRandomNum(strLen))
       + dd
       + str.charAt(this.$getRandomNum(strLen))
       + hhmmss

  console.log('In Vue.prototype.$generateRandomNo')
  console.log(fmtDateTime)
  console.log('RandomNo: [' + no + ']')

  return no
}

// ------------ ----------------------------- -------------------------------
// 全局注册函数,将buffer转为base64data
Vue.prototype.$Base64Encode = function (buffer) {
  var binary = '';
  var bytes = new Uint8Array( buffer );
  var len = bytes.byteLength;
  for (var i = 0; i < len; i++) {
      binary += String.fromCharCode( bytes[ i ] );
  }
  return window.btoa( binary );
}
// 全局注册函数,验证图片是否为JPG/JPEG/PNG格式且大小<2M
Vue.prototype.$limitFile = function (file) {
  var type = file.name.substring(file.name.lastIndexOf('.')+1)
  const isJPNG = (type === 'jpg' || type === 'jpeg' || type === 'png')
  const isLt2M = file.size / 1024 / 1024 < 2
  if (!isJPNG) {
    // this.$message.error('上传图片只能是 JPG 或 PNG 格式!')
    return 1
  }
  if (!isLt2M) {
    // this.$message.error('上传图片大小不能超过 2MB!')
    return 2
  }
  // return isJPNG && isLt2M
  return 0
}

// ------------ 全局注册函数: 操作sessionStorage -------------------------------
// ------------ user/pub共用
const userLogin = function (user) {
  // 用户登录
  // sessionStorage存数据对象的stringify，下同
  if(typeof user==='object') {
    sessionStorage.setItem('user', JSON.stringify(user))
  }
}
const getSessUser = function () {
  // 获取sessionStorage中存的user
  // 调用JSON.parse()转化为对象
  return JSON.parse(sessionStorage.getItem('user')) 
}
const userLogout = function () {
  // 用户登出
  var user = getSessUser()
  sessionStorage.removeItem('user')
  return user
}
// ------------- 存取要浏览的商品 --------------------------------
const recordGoods = function (goods) {
  if(typeof goods==='object') {
    sessionStorage.setItem('goodsRecord', JSON.stringify(goods))
  }
}
const getGoodsRecord = function () {
  return JSON.parse(sessionStorage.getItem('goodsRecord')) 
}
const unRecordGoods = function () {
  var goods = getGoodsRecord()
  sessionStorage.removeItem('goodsRecord')
  return goods
}
// ------------- 存取要浏览的发行方 --------------------------------
const recordPub = function (pub) {
  if(typeof pub==='object') {
    sessionStorage.setItem('pubRecord', JSON.stringify(pub))
  }
}
const getPubRecord = function () {
  return JSON.parse(sessionStorage.getItem('pubRecord')) 
}
const unRecordPub = function () {
  var pub = getPubRecord()
  sessionStorage.removeItem('pubRecord')
  return pub
}

// ------------ ----------------------------- -------------------------------
// 全局注册函数,从Object对象生成FormData()
Vue.prototype.$axiosParams = function (obj) {
  if(typeof obj === 'object') {
    var params = new URLSearchParams()
    for( var key in obj) {
      params.append(key, obj[key])
    }
    return params
  } else {
    return null
  }
}

// ------------ -------- 定义全局函数和常量 ------- -------------------------------
// 全局注册函数,检查是否符合「价格」类型
const checkPrice = (rule, value, callback) => {
  var reg = /^\d+(\.\d{1,2})?$/
  if(!value) { callback(new Error('请输入商品单价')) }
  else if(!reg.test(value)) {
    callback(new Error('商品单价应为非负数字，且不超过两位小数'))
  } else if(Number(value)<=0) {
    callback(new Error('商品单价应大于0'))
  } else {
    callback()  // 深渊巨坑！！！不加上的话this.$refs[formName].validate一直不执行
  }
}

// 定义常量
const gTypeOptions = ['所有类型','电影','剧集','单集','专辑','单曲']
const gStateOptions = ['全部','上架中','已下架']


// ------------ ----------------------------- -------------------------------
// 配置axios
axios.defaults.timeout = 5000 // 请求超时
axios.defaults.baseURL = '/KyonStore_new/'
