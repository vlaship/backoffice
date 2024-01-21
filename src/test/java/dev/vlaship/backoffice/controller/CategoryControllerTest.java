package dev.vlaship.backoffice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import dev.vlaship.backoffice.dto.CategoryDto;
import dev.vlaship.backoffice.facade.impl.CategoryFacade;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser(username = "user", password = "123")
@SuppressWarnings("all")
public class CategoryControllerTest {

	private static final String CATEGORY = "category";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	private final static String URL_API = "/category/";

	@MockBean
	private CategoryFacade categoryFacade;

	@Test
	public void test_create() throws Exception {

		final CategoryDto category = CategoryDto.builder().id(2l).name(CATEGORY).parentId(1l)
				.subCategories(List.of(3l, 4l, 5l)).build();

		Mockito.when(categoryFacade.create(Mockito.any(CategoryDto.class))).thenReturn(category);

		mockMvc.perform(post(URL_API + "create").contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(CategoryDto.builder().name(CATEGORY).parentId(1l)
						.subCategories(List.of(3l, 4l, 5l)).build())))

				.andExpect(status().isCreated()).andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(category.id()))).andExpect(jsonPath("$.name", is(category.name())))
				.andExpect(jsonPath("$.parentId", is(category.parentId())))
				.andExpect(jsonPath("$.subCategories", hasSize(category.subCategories().size())));
	}

	@Test
	public void test_validation_ProductDto_name() throws Exception {

		final String message = mockMvc
				.perform(put(URL_API + "update").contentType(APPLICATION_JSON)
						.content(mapper.writeValueAsString(CategoryDto.builder().name("").build())))
				.andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

		then(message.contains("Category's name must be not empty")).isTrue();
	}

	// @Test
	// public void test_findByName() throws Exception {
	//
	// final CategoryDto category = CategoryDto.builder().id(2).name(CATEGORY).parentId(1)
	// .subCategories(List.of(3, 4, 5))).build();
	//
	// Mockito.when(categoryFacade.find(Mockito.anyString()))
	// .thenReturn(category);
	//
	// mockMvc.perform(get(URL_API + "name/{name}", category.getName()))
	// .andExpect(status().isOk())
	// .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
	// .andExpect(jsonPath("$.id", is(category.id())))
	// .andExpect(jsonPath("$.name", is(category.getName())))
	// .andExpect(jsonPath("$.parentId", is(category.getParentId())))
	// .andExpect(jsonPath("$.subCategories",
	// hasSize(category.getSubCategories().size())));
	// }
	//
	// @Test
	// public void test_findByName_400() throws Exception {
	//
	// final String model = "Category";
	//
	// Mockito.when(categoryFacade.find(Mockito.anyString()))
	// .thenThrow(new NotFoundException(model, CATEGORY));
	//
	// final String message = mockMvc.perform(get(URL_API + "name/{name}", CATEGORY))
	// .andExpect(status().isNotFound())
	// .andReturn().getResolvedException().getMessage();
	//
	// then(message.contains("Can't find " + model + " with NAME = " +
	// CATEGORY)).isTrue();
	// }

}
