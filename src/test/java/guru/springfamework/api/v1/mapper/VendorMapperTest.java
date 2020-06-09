package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {
	private static final String NAME = "Western Tasty Fruits Ltd.";

	private VendorMapper vendorMapper = VendorMapper.INSTANCE;

	@Test
	public void categoryToCategoryDTO() {
		Vendor vendor = new Vendor();
		vendor.setName(NAME);

		VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

		assertEquals(NAME, vendorDTO.getName());
	}
}
