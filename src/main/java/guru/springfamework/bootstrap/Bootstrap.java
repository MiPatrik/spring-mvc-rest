package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;
	private VendorRepository vendorRepository;

	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override public void run(String... args) throws Exception {
		addCategories();
		addCustomers();
		addVendor();
	}

	private void addCategories() {
		Category fruits = new Category();
		fruits.setName("Fruits");

		Category dried = new Category();
		dried.setName("Dried");

		Category fresh = new Category();
		fresh.setName("Fresh");

		Category exotic = new Category();
		exotic.setName("Exotic");

		Category nuts = new Category();
		nuts.setName("Nuts");

		categoryRepository.save(fruits);
		categoryRepository.save(dried);
		categoryRepository.save(fresh);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);

		log.info("Categories Loaded = " + categoryRepository.count());
	}

	private void addCustomers() {

		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstName("Joe");
		customer1.setLastName("Newman");

		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setFirstName("Michael");
		customer2.setLastName("Lachappele");

		Customer customer3 = new Customer();
		customer3.setId(3L);
		customer3.setFirstName("David");
		customer3.setLastName("Winter");

		Customer customer4 = new Customer();
		customer4.setId(4L);
		customer4.setFirstName("Anne");
		customer4.setLastName("Hine");

		Customer customer5 = new Customer();
		customer5.setId(5L);
		customer5.setFirstName("Alice");
		customer5.setLastName("Eastman");

		customerRepository.save(customer1);
		customerRepository.save(customer2);
		customerRepository.save(customer3);
		customerRepository.save(customer4);
		customerRepository.save(customer5);

		log.info("Customers loaded = " + customerRepository.count());
	}

	private void addVendor() {
		Vendor vendor1 = new Vendor();
		vendor1.setId(1L);
		vendor1.setName("Name1");

		Vendor vendor2 = new Vendor();
		vendor2.setId(2L);
		vendor2.setName("Name2");

		Vendor vendor3 = new Vendor();
		vendor3.setId(3L);
		vendor3.setName("Name3");

		Vendor vendor4 = new Vendor();
		vendor4.setId(4L);
		vendor4.setName("Name4");

		Vendor vendor5 = new Vendor();
		vendor5.setId(5L);
		vendor5.setName("Name5");

		vendorRepository.save(vendor1);
		vendorRepository.save(vendor2);
		vendorRepository.save(vendor3);
		vendorRepository.save(vendor4);
		vendorRepository.save(vendor5);

		log.info("Vendors loaded = " + vendorRepository.count());
	}
}
