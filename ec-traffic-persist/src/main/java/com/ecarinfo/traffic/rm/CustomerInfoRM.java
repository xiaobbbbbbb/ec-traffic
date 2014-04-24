package com.ecarinfo.traffic.rm;
public class CustomerInfoRM {
	public static final String tableName="customer_info";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String code="code";//
	public static final String authCode="auth_code";//
	public static final String name="name";//
	public static final String cityId="city_id";//
	public static final String cityPid="city_pid";//
	public static final String startDate="start_date";//合同起时时间
	public static final String endDate="end_date";//
	public static final String carNums="car_nums";//
	public static final String carAllowchangeNums="car_allowchange_nums";//
	public static final String carFixedNums="car_fixed_nums";//
	public static final String type="type";//客户类型1保险，2 4S,3 油品 ，4 其它
	public static final String isValid="is_valid";//
	public static final String isExpired="is_expired";//
	public static final String opName="op_name";//操作员
	public static final String ctime="ctime";//
	public static final String updateTime="update_time";//
	public static final String url="url";//回调url用于推送违章数据
}
