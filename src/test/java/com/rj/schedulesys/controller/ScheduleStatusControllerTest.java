package com.rj.schedulesys.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.rj.schedulesys.config.TestConfiguration;
import com.rj.schedulesys.util.TestUtil;
import com.rj.schedulesys.view.model.ScheduleStatusViewModel;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestConfiguration.class)
public class ScheduleStatusControllerTest {

	
private @Autowired WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.build();
	}
	
	@Test
	public void test_findAll() throws Exception{
		mockMvc.perform(get("/schedule-statuses"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	public void test_findOne_WithNonExistingScheduleStatusId() throws Exception{
		mockMvc.perform(get("/schedule-statuses/{id}", 0))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$", is("No schedule status found with id or status : 0")));
	}
	
	@Test
	public void test_findOne_WithExistingScheduleStatus() throws Exception{
		mockMvc.perform(get("/schedule-statuses/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status", is("CONFIRMED")));
	}
	
	@Test
	public void test_createWithExisingScheduleStatus() throws Exception{
		
		ScheduleStatusViewModel viewModel = TestUtil.aNewScheduleStatusViewModel(null, "CONFIRMED");
		
		mockMvc.perform(post("/schedule-statuses")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(viewModel)))
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$", is("A schedule status with status : CONFIRMED already exists")));
	}
	
	@Test
	public void test_create_WithNewStatus() throws Exception{
		
		ScheduleStatusViewModel viewModel = TestUtil.aNewScheduleStatusViewModel(null, "YET A NEW ONE");
		
		mockMvc.perform(post("/schedule-statuses")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(viewModel)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$", is("Schedule status successfully created")));
	}
	
	@Test
	public void test_update_WithNonExistingScheduleStatusId() throws Exception{
		
		ScheduleStatusViewModel viewModel = TestUtil.aNewScheduleStatusViewModel(0L, "NEW ONE HERE");
		
		mockMvc.perform(put("/schedule-statuses/{id}", 0)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(viewModel)))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$", is("No schedule status found with id : 0")));
	}
	
	@Test
	@DirtiesContext
	public void test_update_WithExistingScheduleStatusIdButExistingStatus() throws Exception{
		
		ScheduleStatusViewModel viewModel = TestUtil.aNewScheduleStatusViewModel(1L, "NOT CONFIRMED");
		
		mockMvc.perform(put("/schedule-statuses/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(viewModel)))
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$", is("A schedule status with status : NOT CONFIRMED already exists")));
	}
	
	@Test
	public void test_update_WithExistingScheduleStatusAndANewStatus() throws Exception{
		
		ScheduleStatusViewModel viewModel = TestUtil.aNewScheduleStatusViewModel(3L, "THIS IS A NEW NAME");
		
		mockMvc.perform(put("/schedule-statuses/{id}", 3)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(viewModel)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", is("Schedule status successfully updated")));
	}
	
	@Test
	public void test_delete_WithNonExistingScheduleStatusId() throws Exception{
		mockMvc.perform(delete("/schedule-statuses/{id}", 0)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$", is("No schedule status found with id : 0")));
	}
	
	
	@Test
	public void test_delete_WithExistingScheduleStatusThatHasSchedules() throws Exception{
		mockMvc.perform(delete("/schedule-statuses/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$", is("Schedule status with id : 1 can not be deleted")));
	}
	
	@Test
	public void test_delete_WithExistingScheduleStatusThatHasNoSchedules() throws Exception{
		mockMvc.perform(delete("/schedule-statuses/{id}", 3)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", is("Schedule status successfully deleted")));
	}
	
}