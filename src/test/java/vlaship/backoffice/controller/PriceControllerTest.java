package vlaship.backoffice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.dto.ProductDto;
import vlaship.backoffice.exception.BadRequestException;
import vlaship.backoffice.exception.DeleteException;
import vlaship.backoffice.facade.impl.PriceFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser(username = "user", password = "123")
@SuppressWarnings("all")
public class PriceControllerTest {

    private static final String PRODUCT = "Product";
    private static final String BYN = "byn";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private final static String URL_API = "/price/";

    @MockBean
    private PriceFacade priceFacade;

    @Test
    public void test_validation_PriceDto_currency() throws Exception {

        final String value = "b";

        final String message = mockMvc.perform(put(URL_API + "update")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(PriceDto.builder().currency(value).amount(BigDecimal.valueOf(100)).build())))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();

        then(message.contains("rejected value [" + value + "]")).isTrue();
        then(message.contains("Currency must be 3 characters")).isTrue();
    }

    @Test
    public void test_validation_PriceDto_amount() throws Exception {

        int value = -100;

        final String message = mockMvc.perform(put(URL_API + "update")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(PriceDto.builder().currency(BYN).amount(BigDecimal.valueOf(value)).build())))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();

        then(message.contains("rejected value [" + value + "]")).isTrue();
        then(message.contains("Amount must be positive")).isTrue();
    }

    @Test
    public void test_delete_400() throws Exception {
        doThrow(new DeleteException("last price in product")).when(priceFacade).delete(Mockito.anyInt());

        final String message = mockMvc.perform(delete(URL_API + "delete/{id}", 2))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();

        then(message.contains("Can't delete last price in product")).isTrue();
    }

    @Test
    public void test_findByCurrency() throws Exception {

        Mockito.when(priceFacade.findAll(Mockito.any(Pageable.class), Mockito.anyString()))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(URL_API + "currency/{currency}", BYN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)));
    }

    @Test
    public void test_findAllByProduct() throws Exception {

        Mockito.when(priceFacade.findAll(Mockito.any(Pageable.class), Mockito.anyInt()))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(URL_API + "product/{id}", 0))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)));
    }

    @Test
    public void test_findAllByProduct_400() throws Exception {

        final int id = 111;

        Mockito.when(priceFacade.findAll(Mockito.any(Pageable.class), Mockito.anyInt()))
                .thenThrow(new BadRequestException(PRODUCT, id));

        final String message = mockMvc.perform(get(URL_API + "product/{id}", id))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();

        then(message.contains("Can't find " + PRODUCT + " with ID = " + id)).isTrue();
    }

    @Test
    public void test_findAllByProductJson() throws Exception {

        Mockito.when(priceFacade.findAll(Mockito.any(Pageable.class), Mockito.anyInt()))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(URL_API + "product")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(ProductDto.builder().id(2).name(PRODUCT).build())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)));
    }

    @Test
    public void test_findAllByProductJson_400() throws Exception {

        final int id = 111;

        Mockito.when(priceFacade.findAll(Mockito.any(Pageable.class), Mockito.anyInt()))
                .thenThrow(new BadRequestException(PRODUCT, id));

        final String message = mockMvc.perform(get(URL_API + "product")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(ProductDto.builder().id(id).name(PRODUCT).build())))
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();

        then(message.contains("Can't find " + PRODUCT + " with ID = " + id)).isTrue();
    }

    @Test
    public void test_findBetween() throws Exception {

        Mockito.when(priceFacade.findAll(Mockito.any(Pageable.class), Mockito.anyString(),
                Mockito.any(BigDecimal.class), Mockito.any(BigDecimal.class)))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(URL_API + "between/{currency}/{from}/{to}", BYN, 0, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)));
    }

}
