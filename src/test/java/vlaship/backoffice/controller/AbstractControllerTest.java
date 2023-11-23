package vlaship.backoffice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.exception.NotFoundException;
import vlaship.backoffice.facade.impl.PriceFacade;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser(username = "user", password = "123")
@SuppressWarnings("all")
public class AbstractControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	private final static String URL_API = "/price/";

	private static List<PriceDto> priceList;

	@BeforeAll
	public static void setupPrices() {
		priceList = new ArrayList<>(Arrays.asList(
				PriceDto.builder().id(1).amount(BigDecimal.valueOf(1500)).currency("byn").productId(1).build(),
				PriceDto.builder().id(2).amount(BigDecimal.valueOf(2500)).currency("eur").productId(1).build(),
				PriceDto.builder().id(3).amount(BigDecimal.valueOf(3500)).currency("usd").productId(1).build()));
	}

	@MockBean
	private PriceFacade priceFacade;

	@Test
	public void test_list() throws Exception {
		Mockito.when(priceFacade.findAll(Mockito.any(Pageable.class))).thenReturn(priceList);
		final PriceDto priceDto = priceList.get(1);

		mockMvc.perform(get(URL_API + "list")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
				.andExpect(jsonPath("$.*", hasSize(priceList.size())))
				.andExpect(jsonPath("$.*.id", hasItem(is(priceDto.getId())))).andExpect(jsonPath("$.*.amount").exists())
				.andExpect(jsonPath("$.*.productId", hasItem(is(priceDto.getProductId()))))
				.andExpect(jsonPath("$.*.currency", hasItem(is(priceDto.getCurrency()))));
	}

	@Test
	public void test_id() throws Exception {
		Mockito.when(priceFacade.find(Mockito.anyInt())).thenReturn(priceList.get(1));

		final PriceDto priceDto = priceList.get(1);

		mockMvc.perform(get(URL_API + "{id}", 2)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(priceDto.getId())))
				.andExpect((jsonPath("$.amount").value(priceDto.getAmount())))
				.andExpect(jsonPath("$.productId", is(priceDto.getProductId())))
				.andExpect(jsonPath("$.currency", is(priceDto.getCurrency())));
	}

	@Test
	public void test_id_404() throws Exception {

		final int id = 111;
		final String model = "Price";

		Mockito.when(priceFacade.find(Mockito.anyInt())).thenThrow(new NotFoundException(model, id));

		final String message = mockMvc.perform(get(URL_API + "{id}", id)).andExpect(status().isNotFound()).andReturn()
				.getResolvedException().getMessage();

		then(message.contains("Can't find " + model + " with ID = " + id)).isTrue();
	}

	@Test
	public void test_update() throws Exception {

		final PriceDto priceDto = PriceDto.builder().amount(BigDecimal.valueOf(100)).currency("byn").id(1).productId(1)
				.build();

		Mockito.when(priceFacade.update(Mockito.any(PriceDto.class))).thenReturn(priceDto);

		mockMvc.perform(
				put(URL_API + "update").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(priceDto)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(priceDto.getId())))
				.andExpect(jsonPath("$.amount").value(priceDto.getAmount().toString()))
				.andExpect(jsonPath("$.productId", is(priceDto.getProductId())))
				.andExpect(jsonPath("$.currency", is(priceDto.getCurrency())));
	}

	@Test
	public void test_delete() throws Exception {
		doNothing().when(priceFacade).delete(Mockito.anyInt());

		mockMvc.perform(delete(URL_API + "delete/{id}", 2)).andExpect(status().isNoContent());
	}

}
