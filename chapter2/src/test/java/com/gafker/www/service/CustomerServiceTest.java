/**
 * 
 */
package com.gafker.www.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.MXBean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gafker.www.model.Customer;

/**
 * @author gafker
 *
 */
public class CustomerServiceTest {
	private final CustomerService customerService;

	public CustomerServiceTest() {
		this.customerService = new CustomerService();
	}

	@Before
	public void init() {
		// TODO 初始化数据库
	}

	/**
	 * Test method for
	 * {@link com.gafker.www.service.CustomerService#getCustomerList(java.lang.String)}.
	 */
	@Test
	public void testGetCustomerList() {
		List<Customer> customerList = customerService.getCustomerList();
		Assert.assertEquals(2, customerList.size());
	}

	/**
	 * Test method for
	 * {@link com.gafker.www.service.CustomerService#getCustomer(long)}.
	 */
	@Test
	public void testGetCustomer() {
		long id = 1;
		Customer customer = customerService.getCustomer(id);
		Assert.assertNotNull(customer);
	}

	/**
	 * Test method for
	 * {@link com.gafker.www.service.CustomerService#createCustomer(java.util.Map)}.
	 */
	@Test
	public void testCreateCustomer() {
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		fieldMap.put("userName", "customer100");
		fieldMap.put("contact", "John");
		fieldMap.put("telephone", "18859986191");
		boolean result = customerService.createCustomer(fieldMap);
		Assert.assertTrue(result);
	}

	/**
	 * Test method for
	 * {@link com.gafker.www.service.CustomerService#updateCustomer(long, java.util.Map)}.
	 */
	@Test
	public void testUpdateCustomer() {
		long id = 1;
		Map<String, Object> fieldMap = new HashMap<String, Object>();
		fieldMap.put("contact", "GaoJohn");
		boolean result = customerService.updateCustomer(id, fieldMap);
		Assert.assertTrue(result);
	}

	/**
	 * Test method for
	 * {@link com.gafker.www.service.CustomerService#deleteCustomer(long)}.
	 */
	@Test
	public void testDeleteCustomer() {
		long id = 1;
		boolean result = customerService.deleteCustomer(id);
		Assert.assertTrue(result);
	}

}
