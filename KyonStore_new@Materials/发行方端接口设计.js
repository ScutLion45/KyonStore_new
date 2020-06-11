// 发行方端
// 登录-注册：见用户端

// 商品管理
var goods_manage_apis = {
  api_ : { 请求url: '/pub-search-goods', // √：Session
    // user/search.html，pub/goodslist.html
    请求参数: {
      pUid: 'str',
      gType: 'int',           // 商品类型，0表示查全部
      gPubTime: 'str',        // （yyyy-MM）上架年+月，默认空串
      gState: 'int',          // 商品状态，0表示查全部
      gName: 'str',           // 商品名
      isUserSearch: '(0|1)',  // 0-发行方查询，1-用户查询
    },
    响应格式: {
      isLogin: '(0|2)',
      data: [],               // 同/user-load-order
    }
  },
  
  api_ : { 请求url: '/pub-edit-goods-1', // √：判断登录状态
    // pub/goodslist.html
    请求参数: {
      gId: 'str',
      gName: 'int',
      gInfo: 'str',
      gType: 'int',
      gPrice: 'double',   // ！！！联动修改order中ostate=1的gprice
      files: 'fileItem'  //图片
    },
    响应格式: {
      isLogin: '(0|2)',
      success: '(0-失败|1-成功)'
    }
  },
  
  api_ : { 请求url: '/pub-off-goods', // √：判断登录状态
    // pub/goodslist.html
    请求参数: {
      gId: 'str'
    },
    响应格式: {
      isLogin: '(0|2)',
      gState: '( 1 | other )'
    }
  },
  
  // ------------------------------------------------------
  api_ : { 请求url: '/pub-load-order', // √:Session
    // pub/orderlist.html
    请求参数: {
      // pUid: 'str',
    },
    响应格式: {
      isLogin: '(0|2)',
      orderList: [],   // 同/user-load-order
    }
  },
  
  // ------------------------------------------------------
  api_ : { 请求url: '/pub-create-goods', // √：判断登录状态
    // pub/creategoods.html
    请求参数: {
      gName: 'str',
      gInfo: 'str',
      gType: 'int',
      gPrice: 'double',
      // pUid: 'str'
      files: 'fileItem'  //图片
    },
    响应格式: {
      isLogin: '(0|2)',
      success: '(0-失败|1-成功)'
    }
  },
  
  // ------------------------------------------------------
  api_ : { 请求url: '/pub-edit-profile', // √：判断登录状态
    // pub/profile.html
    请求参数: {
      // pUid: 'str',
      pPwd: 'str',
      pInfo: ''
    },
    响应格式: {
      isLogin: '(0|2)',
      success: '(0-失败|1-成功)'
    }
  },
  
  // update-order
}