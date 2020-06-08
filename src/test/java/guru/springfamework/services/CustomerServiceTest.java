package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

	@Mock
	CustomerRepository customerRepository;

	private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

	private CustomerService customerService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		customerService = new CustomerServiceImpl(customerMapper, customerRepository);
	}

	@Test
	public void getAllCustomers() {
		//given
		Customer customer1 = new Customer();
		customer1.setId(1l);
		customer1.setFirstName("Michale");
		customer1.setLastName("Weston");

		Customer customer2 = new Customer();
		customer2.setId(2l);
		customer2.setFirstName("Sam");
		customer2.setLastName("Axe");

		when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

		//when
		List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

		//then
		assertEquals(2, customerDTOS.size());

	}

	@Test
	public void getCustomerById() {
		//given
		Customer customer1 = new Customer();
		customer1.setId(1l);
		customer1.setFirstName("Michale");
		customer1.setLastName("Weston");

		when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));

		//when
		CustomerDTO customerDTO = customerService.getCustomerById(1L);

		assertEquals("Michale", customerDTO.getFirstName());
	}
}