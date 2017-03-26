package com.gafker.www.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gafker.www.util.CollectionUtil;
import com.gafker.www.util.PropsUtil;

/**
 * 数据库操作助手类
 * 
 * @author gafker
 *
 */
public class DatabaseHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
	private static final String DRIVER;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;
	private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
	private static final QueryRunner QUERY_RUNNER = new QueryRunner();
	static {
		Properties props = PropsUtil.loadProps("jdbc.properties");
		DRIVER = PropsUtil.getString(props, "jdbc.driver");
		URL = PropsUtil.getString(props, "jdbc.url");
		USERNAME = PropsUtil.getString(props, "jdbc.username");
		PASSWORD = PropsUtil.getString(props, "jdbc.password");
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error("can not load jdbc driver", e);
		}
	}

	/**
	 * 查询实体List
	 * 
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static final <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
		List<T> resultList;
		try {
			Connection conn = getConnection();
			resultList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
		} catch (SQLException e) {
			LOGGER.error("query entity list failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return resultList;
	}

	/**
	 * 查询实体对象
	 * 
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static final <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
		T result;
		try {
			Connection conn = getConnection();
			result = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
		} catch (SQLException e) {
			LOGGER.error("query entity list failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return result;
	}

	/**
	 * 执行查询语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
		List<Map<String, Object>> result;
		try {
			Connection conn = getConnection();
			result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
		} catch (SQLException e) {
			LOGGER.error("query entity list failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return result;
	}

	/**
	 * 执行更新语句（包括update,insert,delete都用）--公用方法
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int executeUpdate(String sql, Object... params) {
		int rows = 0;
		try {
			Connection conn = getConnection();
			rows = QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e) {
			LOGGER.error("execute update failure", e);
			throw new RuntimeException(e);
		} finally {
			closeConnection();
		}
		return rows;
	}

	/**
	 * 插入实体
	 * 
	 * @param entityClass
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
		if (CollectionUtil.isEmpty(fieldMap)) {
			LOGGER.error("can not insert entity:fieldMap is empty");
			return false;
		}
		String sql = "INSERT INTO " + getTableName(entityClass);
		StringBuilder columns = new StringBuilder("(");
		StringBuilder values = new StringBuilder("(");
		for (String fieldName : fieldMap.keySet()) {
			columns.append(fieldName).append(",");
			values.append("?, ");
		}
		columns.replace(columns.lastIndexOf(","), columns.length(), ",");
		values.replace(values.lastIndexOf(","), values.length(), ",");
		sql += columns + " VALUES " + values;
		Object[] params = fieldMap.values().toArray();
		return executeUpdate(sql, params) == 1;
	}

	/**
	 * 更新实体
	 * 
	 * @param entityClass
	 * @param id
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
		if (CollectionUtil.isEmpty(fieldMap)) {
			LOGGER.error("can not insert entity:fieldMap is empty");
			return false;
		}
		String sql = "UPDATE " + getTableName(entityClass) + " SET ";
		StringBuilder columns = new StringBuilder();
		for (String fieldName : fieldMap.keySet()) {
			columns.append(fieldName).append("=?, ");
		}
		sql += columns.substring(0, columns.lastIndexOf(", ")) + " WEHRE id=?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.addAll(fieldMap.values());
		paramList.add(id);
		Object[] params = paramList.toArray();
		return executeUpdate(sql, params) == 1;
	}

	/**
	 * 删除实体
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
		String sql = "DELETE * FORM " + getTableName(entityClass) + " WHERE id=?";
		return executeUpdate(sql, id) == 1;
	}

	private static String getTableName(Class<?> entityClass) {
		return entityClass.getSimpleName();
	}

	/**
	 * 获取数据库连接
	 */
	public static Connection getConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			} catch (SQLException e) {
				LOGGER.error("get connection failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.set(conn);
			}
		}
		return conn;
	}

	/**
	 * 关闭数据库连接
	 */
	public static void closeConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("close connection failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}

}
