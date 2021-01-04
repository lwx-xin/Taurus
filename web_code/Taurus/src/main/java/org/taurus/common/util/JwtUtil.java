package org.taurus.common.util;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.taurus.common.code.CheckCode;
import org.taurus.common.result.TokenVerifyResult;
import org.taurus.common.token_entity.UserToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JwtUtil {

	/**
	 * 秘钥
	 */
	private static final String SECRET = "taurus";

	/**
	 * 加密方式
	 */
	private static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

	/**
	 * 过期时间-单位小时
	 */
	private static final int calendarUnit = Calendar.HOUR;
	/**
	 * 过期时间
	 */
	private static final int calendarAmount = 24;

	private static Key getKeyInstance(String secret) {

		byte[] parseBase64Binary = DatatypeConverter.parseBase64Binary(secret);

		SecretKeySpec secretKeySpec = new SecretKeySpec(parseBase64Binary, algorithm.getJcaName());
		return secretKeySpec;
	}

	/**
	 * 创建Token
	 * 
	 * @param token  要转换的令牌实体
	 * @param unit   过期时间单位
	 * @param amount 过期时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String createToken(Object token, int unit, int amount) {

		Key secretKey = getKeyInstance(SECRET);

		// 令牌签发时间
		Date now = new Date();

		// 令牌过期时间
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.setTime(now);
		nowCalendar.add(unit, amount);
		Date expiresDate = nowCalendar.getTime();

		// 令牌签发者
		String issuer = "xin";

		// 令牌id
		String id = StrUtil.getUUID();

		JwtBuilder jwtBuilder = Jwts.builder()

				// Header
				.setHeaderParam("typ", "JWT")// 声明类型

				// Payload 主体部分(标准声明)
//				.setSubject("")//JWT所面向的用户
//				.setAudience("")//接收JWT的一方
//				.setNotBefore(null)//在xxx日期之间，该jwt都是可用的
				.setId(id)// 设置令牌id
				.setIssuer(issuer)// 设置JWT签发者
				.setIssuedAt(now)// 设置JWT签发时间
				.setExpiration(expiresDate);// 设置JWT过期时间

		// Payload 主体部分(公共声明，私有声明)
		Map<String, Object> payloadMap = JsonUtil.toMap(token);
		if (payloadMap != null) {
			payloadMap.forEach((k, v) -> {
				jwtBuilder.claim(k, v);
			});
		}

		// Signature-签证信息 = 加密算法(Header + "." + Payload, 秘钥)
		jwtBuilder.signWith(algorithm, secretKey);

		return jwtBuilder.compact();
	}

	/**
	 * 创建Token<br/>有效时间24h
	 * 
	 * @param token
	 * @return
	 */
	public static String createToken(Object token) {
		return createToken(token, calendarUnit, calendarAmount);
	}
	
	/**
	 * 解析令牌
	 * @param tokenStr
	 * @return
	 */
	public static TokenVerifyResult parseToken(String tokenStr) {
		Claims body = null;
		CheckCode code = null;
		try {
			body = verifyToken(tokenStr);
			code = CheckCode.TOKEN_SUCCESS;
		} catch (ExpiredJwtException e) {
			//令牌过期
			System.err.println(e);
			body = e.getClaims();
			code = CheckCode.TOKEN_OVERDUE;
		} catch (SignatureException e) {
			// 令牌验证失败
			System.err.println(e);
			code = CheckCode.TOKEN_ERR;
		} catch (Exception e) {
			// 令牌验证失败
			System.err.println(e);
			code = CheckCode.TOKEN_ERR;
		}
		//(List<String>) body.get("authList");
		return new TokenVerifyResult(body, code);
	}

	/**
	 * 验证Token
	 * 
	 * @param token
	 * @return 主体部分(Payload)
	 */
	private static Claims verifyToken(String token) throws ExpiredJwtException,SignatureException {

		Key secretKey = getKeyInstance(SECRET);
		
		// 解析Token
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		// 获取Token的主体部分(Payload)
		return claimsJws.getBody();
	}

	public static void main(String[] args) throws InterruptedException {
		UserToken token = new UserToken();
		List<String> authList = new ArrayList<String>();
		authList.add("123");
		authList.add("3333");
		token.setAuthIdList(authList);

		String tokenStr = createToken(token, Calendar.SECOND,3);
		System.err.println(tokenStr);
		System.err.println("========================");
		Thread.sleep(1000);
		System.err.println(parseToken(tokenStr));
	}

}
