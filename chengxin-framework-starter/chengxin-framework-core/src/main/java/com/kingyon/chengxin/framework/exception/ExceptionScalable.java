package com.kingyon.chengxin.framework.exception;

/**
 * @Description: 抽象和扩展Exception信息
 * @ClassName: ExceptionScalable
 *
 */
public interface ExceptionScalable{

	/**
	 * 错误对象
	 * @return
	 */
	ErrorCode getError();

	/**
	 * 引起执行异常的数据, 如输入数据，或执行语句等
	 * @return
     */
	Object getData();
}
