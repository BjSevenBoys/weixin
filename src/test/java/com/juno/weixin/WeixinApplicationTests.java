package com.juno.weixin;

import com.juno.weixin.modules.common.http.HttpsClient;
import com.juno.weixin.modules.common.wx.WxConfig;
import com.juno.weixin.modules.common.wx.WxConstants;
import com.juno.weixin.modules.common.wx.WxUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinApplicationTests {

	@Test
	public void contextLoads() throws  Exception{
		String outTradeNo="001";
		double totalFee=0.01;
		String signType="MD5";
		HashMap<String, String> data = new HashMap<String, String>();
		//公众账号ID
		data.put("appid", WxConfig.appID);
		//商户号
		data.put("mch_id", WxConfig.mchID);
		//随机字符串
		data.put("nonce_str", WxUtil.getNonceStr());
		//商品描述
		data.put("body","测试支付");
		//商户订单号
		data.put("out_trade_no",outTradeNo);
		//标价币种
		data.put("fee_type","CNY");
		//标价金额
		data.put("total_fee",String.valueOf(Math.round(totalFee * 100)));
		//用户的IP
		data.put("spbill_create_ip","123.12.12.123");
		//通知地址
		data.put("notify_url", WxConfig.unifiedorderNotifyUrl);
		//交易类型
		data.put("trade_type","NATIVE");
		//签名类型
		data.put("sign_type",signType);
		//签名
		data.put("sign", WxUtil.getSignature(data, WxConfig.key,signType));

		String requestXML = WxUtil.mapToXml(data);
		String reponseString = HttpsClient.httpsRequestReturnString(WxConstants.PAY_UNIFIEDORDER,HttpsClient.METHOD_POST,requestXML);
		Map<String,String> resultMap = WxUtil.processResponseXml(reponseString,signType);

	}

}
