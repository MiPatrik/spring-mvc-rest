package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static guru.springfamework.controllers.v1.AbstractRestContollerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {

	private static final String NAME1 = "Name1";
	private static final String NAME2 = "Name2";

	@Mock
	VendorService vendorService;

	@InjectMocks
	VendorController vendorController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
				.setControllerAdvice(RestResponseEntityExceptionHandler.class)
				.build();
	}

	@Test
	public void testListCustomers() throws Exception {

		//given
		VendorDTO vendor1 = new VendorDTO();
		vendor1.setName(NAME1);
		vendor1.setVendorUrl(VendorController.BASE_URL + "/1");

		VendorDTO vendor2 = new VendorDTO();
		vendor2.setName(NAME2);
		vendor2.setVendorUrl(VendorController.BASE_URL + "/2");

		when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendor1, vendor2));

		mockMvc.perform(get(VendorController.BASE_URL + "/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.vendors", hasSize(2)));
	}

	@Test
	public void testGetCustomerById() throws Exception {

		//given
		VendorDTO vendor1 = new VendorDTO();
		vendor1.setName(NAME1);
		vendor1.setVendorUrl(VendorController.BASE_URL + "/1");

		when(vendorService.getVendorById(anyLong())).thenReturn(vendor1);

		//when
		mockMvc.perform(get(VendorController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME1)));
	}

	@Test
	public void createNewCustomer() throws Exception {
		//given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME1);

		VendorDTO returnDTO = new VendorDTO();
		returnDTO.setName(vendorDTO.getName());
		returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");

		when(vendorService.createNewVendor(vendorDTO)).thenReturn(returnDTO);

		//when/then
		mockMvc.perform(post(VendorController.BASE_URL + "/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendorDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", equalTo(NAME1)))
				.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
	}

	@Test
	public void testUpdateCustomer() throws Exception {
		//given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME1);

		VendorDTO returnDTO = new VendorDTO();
		returnDTO.setName(vendorDTO.getName());
		returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");

		when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

		//when/then
		mockMvc.perform(put(VendorController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendorDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME1)))
				.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
	}

	@Test
	public void testPatchCustomer() throws Exception {

		//given
		VendorDTO customer = new VendorDTO();
		customer.setName(NAME1);

		VendorDTO returnDTO = new VendorDTO();
		returnDTO.setName(customer.getName());
		returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");

		when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

		mockMvc.perform(patch(VendorController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME1)))
				.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
	}

	@Test
	public void testDeleteCustomer() throws Exception {
		mockMvc.perform(delete(VendorController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(vendorService).deleteVendorById(anyLong());
	}

	@Test
	public void testNotFoundException() throws Exception {

		when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(VendorController.BASE_URL + "/222")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
