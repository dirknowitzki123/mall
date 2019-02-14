package com.kingyon.chengxin.framework.cache.redis;

public interface CacheConstants {

	/** session 前缀 **/
	public String SESSION_PREFIX="SESSION:";
	/** session 登陆前缀 **/
	public String LOGIN_PREFIX="IS_LOGIN:";
	/** cache 缓存前缀 **/
	public String CACHE_PREFIX="CACHE:";
	/** cache 缓存前缀 **/
	public String CACHE_LOGINCHANNEL_PREFIX="CACHE:CHANNEL:";
	/** promotion 推广前缀 **/
	public String PROMOTION_PREFIX="PROMOTION:";
	/** 验证码缓存key前缀 **/
	public String SENDSMS_EXPIRE_PREFIX="SENDSMS_EXPIRE:";
	/** 频繁发送验证码的手机号拉入redis黑名单key前缀*/
	public String MSG_MOBILE_BLAK_PREFIX="MSG_MOBILE_BLAK:";
	/** 验证码有效时间key前缀*/
	public String SMSNO_EXPIRE_PREFIX="SMSNO_EXPIRE:";
	
	/** 产品编号查询协议列表有效时间key前缀*/
	public String PRODNO_EXPIRE_PREFIX="PRODNO_EXPIRE:";
	
	/** 渠道编号查询协议列表有效时间key前缀*/
	public String CHANNO_EXPIRE_PREFIX="CHANNO_EXPIRE:";
	/** 盟主圈图形验证码有效时间key前缀*/
	public String MZQSMS_PREFIX="MZQSMS_EXPIRE:";
	
	/** 盟主圈用户当天是否签到标识key前缀*/
	public String SIGNIN_FLAG = "SIGNIN_FLAG:";
	/** 盟主圈用户本月是否领取优酷会员标识key前缀*/
	public String YOUKU_MEMBERCODE_FLAG = "YOUKU_MEMBERCODE_FLAG:";
	
	/** 盟主圈产品编号预生成key*/
	public String MZQ_PROD_NO_KEY = "MZQ_PROD_NO_KEY:";
	/** 盟主圈广告编号预生成key*/
	public String MZQ_ADV_NO_KEY = "MZQ_ADV_NO_KEY:";
	
	/** 任性用邀请好友活动每月提现额度前缀*/
	public String RXY_INVITE_ACTIVITY_WITHDRAW_KEY = "RXY_INVITE_ACTIVITY_WITHDRAW_KEY:";

	/** 盟主圈推广渠道用户计数前缀*/
	public String MZQ_PROM_CHANNEL_COUNTER_KEY = "MZQ_PROM_CHANNEL_COUNTER_KEY:";
	
	/** 内部登录服务图形验证码前缀*/
	public String LOGIN_SERVICE_CAPTCHA_PREFIX="LOGIN_SERVICE_CAPTCHA_EXPIRE:";

	/** 外快侠用户本月提现总金额前缀*/
	public String KWX_WITHDRAW_AMT_THIS_MONTH_KEY = "KWX_WITHDRAW_AMT_THIS_MONTH_KEY:";
	/** 官网新闻列表缓存*/
	public String BY_NEWS_EXPIRE_PREFIX="BY_NEWS_EXPIRE_PREFIX:";

	/** 外快侠保存反馈附件前缀*/
	public String WKX_FEEDBACK_ATT_PREFIX = "WKX_FEEDBACK_ATT_PREFIX:";

	/** 外快下微信Token——key前缀 **/
	public String WECHAT_WKX_TOKEN="WECHAT_WKX_TOKEN:";
	
	/** 外快下微信ACCToken——key前缀 **/
	public String WECHAT_ACC_TOKEN="WECHAT_ACC_TOKEN:";
	
	/** 外快下微信Tocket——key前缀 **/
	public String WECHAT_WKX_TICKET="WECHAT_WKX_TICKET:";
	
	
	/** 盟主圈微信ACCToken——key前缀 **/
	public String WECHAT_MZQ_ACC_TOKEN="WECHAT_MZQ_ACC_TOKEN:";
	
	/** 盟主圈微信Tocket——key前缀 **/
	public String WECHAT_MZQ_WKX_TICKET="WECHAT_MZQ_WKX_TICKET:";


	/** 外快侠产品分类编号生成key*/
	public String WKX_PROD_SORT_NO_KEY = "WKX_PROD_SORT_NO_KEY:";
	/** 外快侠活动编号生成key*/
	public String WKX_ACTIV_NO_KEY = "WKX_ACTIV_NO_KEY:";

	/** 外快侠用户当月提现次数计数 */
	public String WKX_USER_MONTH_WITHDRAW_COUNTER_KEY = "WKX_USER_MONTH_WITHDRAW_COUNTER_KEY:";

	/** 外快侠推广渠道短链接key前缀 */
	public String WKX_PROM_CHANNEL_SHORT_URL_KEY = "WKX_PROM_CHANNEL_SHORT_URL_KEY:";

	/** 外快侠用户点赞文章key前缀 */
	public String WKX_USER_LIKE_ARTICAL_KEY = "WKX_USER_LIKE_ARTICAL_KEY:";

	/** 外快侠文章详细信息key前缀 */
	public String WKX_ARTICLE_DETAIL_KEY = "WKX_ARTICLE_DETAIL_KEY:";

	/** 招财猫推广渠道短链接key前缀 */
	public String LUCKYCAT_PROM_CHANNEL_SHORT_URL_KEY = "LUCKYCAT_PROM_CHANNEL_SHORT_URL_KEY:";
}
