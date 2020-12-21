package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Admin;
import com.example.demo.pojo.Customer;
import com.example.demo.pojo.ServiceOrder;
import com.example.demo.pojo.ServicingType;
import com.example.demo.pojo.SparePart;
import com.example.demo.pojo.Staff;
import com.example.demo.pojo.Vehicle;
import com.example.demo.service.IAdminService;
import com.example.demo.service.ICustomerService;
import com.example.demo.service.IServiceOrderService;
import com.example.demo.service.IServicingTypeService;
import com.example.demo.service.ISparePartService;
import com.example.demo.service.IStaffService;
import com.example.demo.service.IVehicleService;


@RestController
@RequestMapping("/admins")
public class AdminController {

	@Autowired
	private IAdminService adminService;
	
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired
	private IStaffService staffService;
	
	@Autowired
	private IServicingTypeService serviceType;
	
	@Autowired
	private ISparePartService spareService;
	
	@Autowired
	private IServiceOrderService orderService;
	
	
	
	public AdminController() {
		System.out.println("In the Constructor "+getClass().getName());
	} 
	
	@GetMapping
	public ResponseEntity<?> getAllAdmin() {
		System.out.println("in the"+getClass().getName());
		List<Admin> Admins= adminService.showAllAdmin();
		if(Admins.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
			return new ResponseEntity<>(Admins,HttpStatus.OK);
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<?>getAdmin(@PathVariable String name){
		System.out.println("in the "+getClass().getName());
		Optional<Admin> admin=adminService.findByName(name);
		
		if(admin.isPresent())
			return new ResponseEntity<>(admin.get(),HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<?>addAdmin(@RequestBody Admin entry)
	{
		System.out.println("in the "+getClass().getName());
		try {
			Admin admin =adminService.addAdmin(entry);
			return new ResponseEntity<>(admin,HttpStatus.OK);
		}
		catch(RuntimeException e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{adminId}")
	public ResponseEntity<?> updateAdmin(@PathVariable int adminId,@RequestBody Admin oldDetails)
	{
		System.out.println("in the "+getClass().getName());
		try {
			Admin updatingDetails=adminService.updateAdminDetails(adminId, oldDetails);
			return new ResponseEntity<>(updatingDetails, HttpStatus.OK);
		}catch(RuntimeException e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/{adminId}")
	public ResponseEntity<?> deRegisterAdmin(@PathVariable int adminId)
	{
		System.out.println("in the "+getClass().getName());
		try {
			adminService.deleteAdmin(adminId);
		return new ResponseEntity<>(HttpStatus.OK);
		}
			catch(RuntimeException e)
		{
		e.printStackTrace();
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
		
	//customer related admin operations...
	//1)admin can see all customers..
	
	@GetMapping("/allCustomers")
	public ResponseEntity<?> listAllCustomers()
	{
		System.out.println("inside list all method of customer controller");
		List<Customer> allCustomers=customerService.getAllCustomers();
		if(allCustomers.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(allCustomers, HttpStatus.OK);
	}
	
	//2)admin can update customers..
	@PutMapping("/updateCustomer/{customerId}")
	public ResponseEntity<?> updateCustomer(@PathVariable int customerId,@RequestBody Customer oldDetails)
	{
		System.out.println("inside updating the customer");
		try {
			Customer updatingDetails=customerService.updateCustomerDetails(customerId, oldDetails);
			return new ResponseEntity<>(updatingDetails, HttpStatus.OK);
		}catch(RuntimeException e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//3)admin can delete a customer...
	@DeleteMapping("/deleteCustomer/{custId}")
	public Customer  deleteCustomer(@PathVariable int custId)
	{
		System.out.println("deleteing customer methiod of controller");
		return customerService.deleteCustomer(custId);
	}
	
	//4)admin can see particular customer details...
	@GetMapping("/CustomerDetails/{userName}")
	public ResponseEntity<?> getCustomerDetails(@PathVariable String userName)
	{
		System.out.println("Inside the customer details method");
		Optional<Customer> customerDetails = customerService.findUserDetails(userName);
		if(customerDetails.isPresent())
			return new ResponseEntity<>(customerDetails, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//vehicle related admin operations...........
	
	//1) admin can see all vehicles...
	
	@GetMapping("/allVehicles")
	public ResponseEntity<?> allVehicle() {
		System.out.println("in the"+getClass().getName());
		List<Vehicle> vehicles= vehicleService.allVehicle();
		if(vehicles.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			
			return new ResponseEntity<>(vehicles,HttpStatus.OK);
	}
	
	@GetMapping("/getVehicle/{id}")
	public ResponseEntity<?>getVehicle(@PathVariable int id){
		System.out.println("in the "+getClass().getName());
		Optional<Vehicle>vehicle=vehicleService.findById(id);
		
		if(vehicle.isPresent())
			return new ResponseEntity<>(vehicle.get(),HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}//remaining is delete vehicle
	
	
	//2)admin can update particular vehicle...
	
	@PutMapping("/updateVehicle/{vehicleId}")
	public ResponseEntity<?>updateVehicle(@PathVariable int vehicleId, @RequestBody Vehicle vehicle)
	{
		System.out.println("in updateVehicle"+vehicleId+" "+vehicle);
		try {
			Vehicle  v=vehicleService.updateVehicle(vehicleId, vehicle);
			return new ResponseEntity<>(v,HttpStatus.OK);
		}catch(RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
		
	@DeleteMapping("/deleteVehicle/{id}")
	public String deleteVehicle(@PathVariable int id) {
		System.out.println("in the "+getClass().getName());
		return vehicleService.deleteVehicle(id);
	}
	
	
	
	// admin can see all Staff
	@GetMapping("/staffs")
	public ResponseEntity<?> listAllStaff()
	{
		System.out.println("in the "+getClass().getName());
		List<Staff> allStaff=staffService.getAllStaff();
		if(allStaff.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(allStaff, HttpStatus.OK);
	}
	
	//admin can view particular staff
	@GetMapping("/staff/{userName}")
	public ResponseEntity<?> getStaffDetails(@PathVariable String userName)
	{
		System.out.println("in the "+getClass().getName());
		Optional<Staff> staffDetails = staffService.findByUsername(userName);
		if(staffDetails.isPresent())
			return new ResponseEntity<>(staffDetails, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//admin can add new staff
	@PostMapping("/staff")
	public ResponseEntity<?> addNewStaff(@RequestBody Staff newStaff)
	{
		System.out.println("in the "+getClass().getName());
		try {
			Staff newlyAdded = staffService.addNewStaff(newStaff);
			return new ResponseEntity<>(newlyAdded, HttpStatus.OK);
		}catch(RuntimeException e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//admin can update staff details
	@PutMapping("/staff/{staffId}")
	public ResponseEntity<?> updateStaff(@PathVariable int staffId,@RequestBody Staff oldDetails)
	{
		System.out.println("in the "+getClass().getName());
		try {
			Staff updatingDetails=staffService.updateStaffDetails(staffId, oldDetails);
			return new ResponseEntity<>(updatingDetails, HttpStatus.OK);
		}catch(RuntimeException e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//admin can fire the staff
	@DeleteMapping("/staff/{staffId}")
	public ResponseEntity<?> deleteStaff(@PathVariable int staffId)
	{
		System.out.println("in the "+getClass().getName());
		try {
			staffService.deleteStaff(staffId);
		return new ResponseEntity<>(HttpStatus.OK);
		}
			catch(RuntimeException e)
		{
		e.printStackTrace();
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	//Servicing Type  manipulation by admin
	
		@GetMapping("/allServicingType")
		public ResponseEntity<?> listAllServicingType()
		{
			System.out.println("in the "+getClass().getName());
			List<ServicingType> allType=serviceType.getAllServicingType();
			if(allType.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(allType, HttpStatus.OK);
		}
		
		@GetMapping("/getServicingType/{typeName}")
		public ResponseEntity<?> getServicingType(@PathVariable String typeName)
		{
			System.out.println("in the "+getClass().getName());
			Optional<ServicingType> type = serviceType.findByType(typeName);
			if(type.isPresent())
				return new ResponseEntity<>(type, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	
		@PostMapping("/addServiceType")
		public ResponseEntity<?> addServiceType(@RequestBody ServicingType newServiceType)
		{
			System.out.println("in the "+getClass().getName());
			try {
				ServicingType newlyAdded = serviceType.addNewServicingType(newServiceType);
				return new ResponseEntity<>(newlyAdded, HttpStatus.OK);
			}catch(RuntimeException e)
			{
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		
		//response not found in postman
		@PutMapping("/updateServicingType/{serviceId}")
		public ResponseEntity<?> updateServicingTypeDetails(@PathVariable int serviceId,@RequestBody ServicingType oldDetails)
		{
			System.out.println("in the "+getClass().getName());
			try {
				ServicingType updatingDetails=serviceType.updateServicingTypeDetails(serviceId, oldDetails);
				return new ResponseEntity<>(updatingDetails, HttpStatus.OK);
			}catch(RuntimeException e)
			{
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		
		
		
		@DeleteMapping("/deleteServicingType/{serviceId}")
		public ResponseEntity<?> deleteServicingType(@PathVariable int serviceId)
		{
			System.out.println("in the "+getClass().getName());
			try {
				serviceType.deleteServicingType(serviceId);
			return new ResponseEntity<>(HttpStatus.OK);
			}
				catch(RuntimeException e)
			{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		}
	
		
		//SparePart manipulation
		
		@GetMapping("/getAllSparePart")
		public ResponseEntity<?> getAllSparePart()
		{
			System.out.println("in the "+getClass().getName());
			List<SparePart> allSparePart=spareService.getAllSparePart();
			if(allSparePart.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(allSparePart, HttpStatus.OK);
		}
		
		@GetMapping("/findByPartName/{partName}")
		public ResponseEntity<?> findByPartName(@PathVariable String partName)
		{
			System.out.println("in the "+getClass().getName());
			Optional<SparePart> partDetails = spareService.findByPartName(partName);
			if(partDetails.isPresent())
				return new ResponseEntity<>(partDetails, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		@PostMapping("/addNewSparePart")
		public ResponseEntity<?> addNewSparePart(@RequestBody SparePart newSparePart)
		{
			System.out.println("in the "+getClass().getName());
			try {
				SparePart newlyAdded = spareService.addNewSparePart(newSparePart);
				return new ResponseEntity<>(newlyAdded, HttpStatus.OK);
			}catch(RuntimeException e)
			{
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@PutMapping("/updateSparePartDetails/{spareId}")
		public ResponseEntity<?> updateSparePartDetails(@PathVariable int spareId,@RequestBody SparePart oldDetails)
		{
			System.out.println("in the "+getClass().getName());
			try {
				SparePart updatingDetails=spareService.updateSparePartDetails(spareId, oldDetails);
				return new ResponseEntity<>(updatingDetails, HttpStatus.OK);
			}catch(RuntimeException e)
			{
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		@DeleteMapping("/deleteSparePart/{spareId}")
		public ResponseEntity<?> deleteSparePart(@PathVariable int spareId)
		{
			System.out.println("in the "+getClass().getName());
			try {
				spareService.deleteSparePart(spareId);
			return new ResponseEntity<>(HttpStatus.OK);
			}
				catch(RuntimeException e)
			{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		}
		
		
		//Service Order 
		
		@GetMapping("/allOrders")
		public ResponseEntity<?> listAllOrders()
		{
			List<ServiceOrder> allOrders = orderService.listAllOrders();
			if(allOrders.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(allOrders, HttpStatus.OK);
		}
		
		@GetMapping("/getOrderDetails/{orderId}")
		public ResponseEntity<?> getOrderDetails(@PathVariable int orderId)
		{
			System.out.println("Inside the customer details method");
			Optional<ServiceOrder> orderDetails = orderService.findOrderDetails(orderId);
			if(orderDetails.isPresent())
				return new ResponseEntity<>(orderDetails, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		
		
}
