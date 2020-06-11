package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
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

public class VendorServiceTest {
	@Mock
	VendorRepository vendorRepository;

	private VendorMapper vendorMapper = VendorMapper.INSTANCE;

	private VendorService vendorService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
	}

	@Test
	public void getAllVendors() {
		//given
		Vendor vendor1 = new Vendor();
		vendor1.setId(1L);
		vendor1.setName("Name1");

		Vendor vendor2 = new Vendor();
		vendor2.setId(2L);
		vendor2.setName("Name2");

		when(vendorRepository.findAll()).thenReturn(Arrays.asList(vendor1, vendor2));

		//when
		List<VendorDTO> customerDTOS = vendorService.getAllVendors();

		//then
		assertEquals(2, customerDTOS.size());

	}

	@Test
	public void getVendorById() {
		//given
		Vendor vendor1 = new Vendor();
		vendor1.setId(1L);
		vendor1.setName("Name1");

		when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(vendor1));

		//when
		VendorDTO customerDTO = vendorService.getVendorById(1L);

		assertEquals("Name1", customerDTO.getName());
	}

	@Test
	public void createNewVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName("Name1");

		Vendor savedVendor = new Vendor();
		savedVendor.setName(vendorDTO.getName());
		savedVendor.setId(1L);

		when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

		VendorDTO savedDto = vendorService.createNewVendor(vendorDTO);

		assertEquals(vendorDTO.getName(), savedDto.getName());
		assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());
	}

	@Test
	public void saveVendorByDTO() throws Exception {

		//given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName("Name1");

		Vendor savedVendor = new Vendor();
		savedVendor.setName(vendorDTO.getName());
		savedVendor.setId(1L);

		when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

		//when
		VendorDTO savedDto = vendorService.saveVendorByDTO(1L, vendorDTO);

		//then
		assertEquals(vendorDTO.getName(), savedDto.getName());
		assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());
	}

	@Test
	public void deleteVendorById() {
		Long id = 1L;

		vendorRepository.deleteById(id);

		verify(vendorRepository, times(1)).deleteById(anyLong());
	}
}
