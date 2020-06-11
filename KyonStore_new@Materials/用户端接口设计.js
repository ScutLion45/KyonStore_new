// 用户端
// 登录-注册-登出（globalServlet）
var login_reg_logout_apis = {
  api_ : { 请求url: '/check-name', // √：不需要修改
    // login.html
    // 注册时name不能重复
    请求参数: {
      name: 'str',
      arg: 1,       // '(1-用户|2-发行方)'
    },
    响应格式: {
      exist: '(0-不存在|1-存在)'
    }
  },

  api_ : { 请求url: '/check-umail', // √：不需要修改
    // login.html
    // 注册时umail不能重复
    请求参数: {
      umail: 'str'
    },
    响应格式: {
      exist: '(0-不存在|1-存在)'
    }
  },

  api_ : { 请求url: '/login', // √：Session
    // login.html
    // 如果登录成功，后台将Gson(user)写入session
    请求参数: {
      name: 'str',
      pwd: 'str',
      arg: 1,       // '(1-用户登录|2-发行方登录)'
    },
    响应格式: {
      // 如果密码匹配，返回user
      // 否则返回null
    }
  },

  api_ : { 请求url: '/reg', // √：不需要修改
    // login.html
    请求参数: {
      name: 'str',
      pwd: 'str',
      attach: 'str', // 'umail | pinfo(空串)'
      arg: 1,        // '(1-用户注册|2-发行方注册)'
    },
    响应格式: {
      success: '(0-失败|1-成功)'
    }
  },
  
  // new ------------------------------------------------------------
  api_ : { 请求url: '/logout', // √：Session
    // user/index.html，pub/index.html
    // 用户登出
    请求参数: {
      arg: 1,       // '(1-用户|2-发行方)'
    },
    响应格式: {
      isLogin: '(0|1)',
      // 返回后台处理完后是否还有用户登录中
    }
  },
  api_ : { 请求url: '/get-current-user', // √：Session
    // login.html，user/index.html，user/my.html，pub/index.html，pub/profile.html
    // 查询登录中的用户，如果有，返回user；否则返回null
    请求参数: {
      arg: 1,       // '(1-用户|2-发行方|3-全局查询)'
    },
    响应格式: {
      isLogin: '(0|1|2)',  // 分别对应无登录，用户登录中和发行方登录中
      user: '(${user} | null)',
      // 如果有用户存储在Session，响应{ isLogin: 1, user: ${user} }
      // 否则响应{ isLogin: 1, user: ${user} }
    }
  },
}

// 首页
var home_apis = {
  // home.html
  api_ : { 请求url: '/load-latest-goods', // √：修改res.data
    // user/home.html
    // 加载指定类别的上架中最新商品，按页显示，每页5条
    请求参数: {
      // uid: 'str',
      gtype: 'int',       // 默认1
      //page: 'int'         // 1开始的整数，每页5条 limit((page-1)*5,5)，下同
    },
    响应格式: {
      // isLogin: '(0|1'),
      data: [   // 商品卡片列表（访问：res.data.data，下同）
        { gId: 'g001',
          gName: 'A-TEEN-1 EP.1 不平凡，其实是不想平凡',
          gInfo: '',
          gType: 3,
          gPubTime: '2019-08-31 13:10:09',
          gPrice: 45.00, gview: 1, gsell: 1,
          gState: 1,
          gImg: 
          pub: {    // 发行商信息
            pUid: 'playlist1',
            pName: 'PLAYLIST',
            pPwd: '',
            pInfo: 'playlist_official'
          }
        },
      ]
    }
  }
}

// 搜索页
var search_apis = {  
  api_ : { 请求url: '/search-goods', // √：修改res.data
    // user/search.html
    请求参数: {
      // uid: 'str',
      gName: 'str',           // 商品名称，默认空串
      gType: 'int',           // 商品类型，默认1
      gPubTime: 'str',        // 上架时间，默认空串
    },
    响应格式: {
        // isLogin: '(0|1)',
        data: [],             // 同'/load-latest-goods'
    }
  },
  
  // 调pub_search_goods查发行方的商品 
}

// 商品详情页
var goods_apis = {  
  api_ : { 请求url: '/browse-goods', // √：Session
    // 更新/插入商品浏览记录（如果用户已登录）
    // user/home.html；user/search.html；user/my.html
    请求参数: {
      // uId: 'str',
      gId: 'str'
    },
    响应格式: {
      // isLogin: '(0|1)',
      success: '(0-失败|1-成功)'
    }
  },
  
  // 加入购物车：/user-create-order?arg=1 => 无需考虑是否在购物车，直接新加order// 判断商品是否已经在购物车，如果是，调修改订单
  // 立即购买：  /user-create-order?arg=2 => 无需考虑是否在购物车
  api_ : { 请求url: '/user-create-order', // √：Session
    // user/goods.html
    请求参数: {
      oNum: 'int',
      gId: 'str',
      gPrice: 'double',
      // uId: 'str',
      arg: '(1-加入购物车 | 2-立即购买)'
    },
    响应格式: {
      isLogin: '(0|1)',   // new：根据是否登录中，判断是否跳转到登录页
      success: '(0-失败|1-成功)',
      failedInfo: '',     // 如果success === 0（失败），回传失败信息
    }
  },
  
  // new ---------------------------------------------
  api_ : { 请求url: '/browse-goods-stay', // √：Session
    // 更新累积商品浏览时间
    // user/goods.html
    请求参数: {
      // uId: 'str',
      gId: 'str'
      stay_add: 'int',
    },
    响应格式: {
      isLogin: '(0|1)',   // new：根据是否登录中，判断是否清除计时器
      success: '(0-失败|1-成功)'
    }
  },
  
}

// 购物车页
var shoopingcart_apis = {
  api_ : { 请求url: '/user-load-order', // √：Session
    // 加载购物车（包括有效和失效商品）
    // user/shoppingcart.html
    请求参数: {
      // uId: 'str',
      arg: 1
    },
    响应格式: { // new
      isLogin: '(0|1)',
      uBalance: 'double',
      orderList: [],
      // orderList( ostate=1/4 )
    }
  },
  
  api_ : { 请求url: '/user-edit-order', // √：判断用户登录状态
    // 用户从购物车修改余额
    // user/shoppingcart.html
    请求参数: {
      oId: 'str',
      oNum: 'int'
    },
    响应格式: {
      isLogin: '(0|1)',
      success: '(0-失败|1-成功)'
    }
  },
  
  api_ : { 请求url: '/user-remove-order', // √：判断用户登录状态
    // 用户从购物车删除商品
    // user/shoppingcart.html
    请求参数: {
      oId: 'str'
    },
    响应格式: {
      isLogin: '(0|1)',
      success: '(0-失败|1-成功)'
    }
  },
  
  api_ : { 请求url: '/update-order', // √：修改uBalance相关、判断用户登录状态
    // 用户从购物车结算=>判断余额
    // user/shoppingcart.html
    // 发行方发货
    请求参数: {
      oId: 'str',
      arg: 2,
      // new：用于更新session的uBalance
      gPrice: row.goodsPrice,
      oNum: row.oNum,
    },
    响应格式: {
      isLogin: '(0|1)',
      success: '(0-失败|1-成功)'
    }
  },
  
}

// 个人页
var my_apis = {
  // 个人资料 ---------------------------------------------------
  api_ : { 请求url: '/user-edit-profile', // √：Session
    // 用户修改个人信息
    // user/my.html：submitEditProfile()
    请求参数: {
      // uId: 'str',
      uMail: 'str',
      uPwd: 'str',
      uBalance: 'double'
    },
    响应格式: {
      isLogin: '(0|1)',
      success: '(0-失败|1-成功)',
    }
  },
  
  // 我的足迹 --------------------------------------------------
  api_ : { 请求url: '/user-load-browse', // √：Session
    // 用户加载浏览记录
    // user/my.html：loadBrowse()
    请求参数: {
      // uId: 'str'
    },
    响应格式: {
      isLogin: '(0|1)',
      browseList: [],
    }
  },
  
  // 历史订单 --------------------------------------------------
  api_ : { 请求url: '/user-load-order',
    // 加载历史订单（包括有效和失效商品）
    // user/my.html：loadOrder()
    请求参数: {
      // uId: 'str',
      arg: 2  // 历史订单
    },
    响应格式: {
      isLogin: '(0|1)',
      uBalance: 'double',
      orderList: [],
      // orderList( ostate=2/3 )
    }
  },
}


