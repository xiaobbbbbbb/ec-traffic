package com.ecarinfo.traffic.rm;
public class CarInfoRM {
	public static final String tableName="car_info";//表名
	public static final String pk="id";//主键
	public static final String id="id";//对应数据库主键,
	public static final String userName="user_name";//
	public static final String carNo="car_no";//
	public static final String carFrameNo="car_frame_no";//
	public static final String carEngineNo="car_engine_no";//
	public static final String carRegistNo="car_regist_no";//机动车登记证书编号
	public static final String trafficNums="traffic_nums";//最后一次违章次数,该值为-1时代表无违章
	public static final String trafficObjects="traffic_objects";//存放traffic_code+traffic_time
	public static final String lastResponseDate="last_response_date";//
	public static final String trafficTimes="traffic_times";//
	public static final String trafficIds="traffic_ids";//
	public static final String ctime="ctime";//
	public static final String utime="utime";//
	public static final String isValid="is_valid";//是否审核 1是，0否
	public static final String interfaceId="interface_id";//是否已经有接口绑定
}
