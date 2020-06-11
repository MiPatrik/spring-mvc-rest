package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
		customer1.setId(1L);
		customer1.setFirstName("Michale");
		customer1.setLastName("Weston");

		Customer customer2 = new Customer();
		customer2.setId(2L);
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
		customer1.setId(1L);
		customer1.setFirstName("Michale");
		customer1.setLastName("Weston");

		when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));

		//when
		CustomerDTO customerDTO = customerService.getCustomerById(1L);

		assertEquals("Michale", customerDTO.getFirstName());
	}

	@Test
	public void createNewCustomer() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("Jim");

		Customer savedCustomer = new Customer();
		savedCustomer.setFirstName(customerDTO.getFirstName());
		savedCustomer.setLastName(customerDTO.getLastName());
		savedCustomer.setId(1L);

		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

		CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

		assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
		assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
	}

	@Test
	public void saveCustomerByDTO() throws Exception {

		//given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstName("Jim");

		Customer savedCustomer = new Customer();
		savedCustomer.setFirstName(customerDTO.getFirstName());
		savedCustomer.setFirstName(customerDTO.getFirstName());
		savedCustomer.setId(1l);

		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

		//when
		CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);

		//then
		assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
		assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
	}

	@Test
	public void deleteCustomerById() {
		Long id = 1L;

		customerRepository.deleteById(id);

		verify(customerRepository, times(1)).deleteById(anyLong());
	}
}