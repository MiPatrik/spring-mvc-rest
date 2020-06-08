package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {
	private static final String FIRST_NAME = "Joe";
	private static final String LAST_NAME = "Buck";

	private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

	@Test
	public void categoryToCategoryDTO() {
		Customer customer = new Customer();
		customer.setFirstName(FIRST_NAME);
		customer.setLastName(LAST_NAME);

		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

		assertEquals(FIRST_NAME, customerDTO.getFirstName());
		assertEquals(LAST_NAME, customerDTO.getLastName());
	}
}