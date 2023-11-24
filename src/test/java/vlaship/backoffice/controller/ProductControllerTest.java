package vlaship.backoffice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import vlaship.backoffice.dto.CategoryDto;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.dto.ProductCreationDto;
import vlaship.backoffice.dto.ProductDto;
import vlaship.backoffice.exception.DeleteException;
import vlaship.backoffice.facade.impl.ProductFacade;
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
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser(username = "user", password = "123")
@SuppressWarnings("all")
public class ProductControllerTest {

    private static final String BYN = "byn";

    private static final String PRODUCT = "product";

    private static final String CATEGORY = "category";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private final static String URL_API = "/product/";

    @MockBean
    private ProductFacade productFacade;

    @Test
    public void test_create() throws Exception {

        var id = 1L;

        final ProductDto productDto = ProductDto.builder().id(id).name(PRODUCT)
                .prices(List.of(id))
                .categories(List.of(id))
                .build();

        Mockito.when(productFacade.create(Mockito.any(ProductCreationDto.class))).thenReturn(productDto);

        mockMvc.perform(post(URL_API + "/create").contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ProductCreationDto.builder().name(PRODUCT)
                                .amount(BigDecimal.valueOf(1)).categoryId(1L).currency(BYN).build())))
                .andExpect(status().isCreated()).andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(productDto.id())))
                .andExpect(jsonPath("$.name", is(productDto.name())))
                .andExpect(jsonPath("$.prices", hasSize(productDto.prices().size())))
                .andExpect(jsonPath("$.categories", hasSize(productDto.categories().size())));
    }

    @Test
    public void test_validation_ProductDto_name() throws Exception {

        final String message = mockMvc
                .perform(put(URL_API + "update").contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CategoryDto.builder().name("").build())))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

        then(message.contains("Product's name must be not empty")).isTrue();
    }

    @Test
    public void test_validation_ProductCreationDto_name() throws Exception {

        final ProductCreationDto dto = ProductCreationDto.builder().name("").amount(BigDecimal.valueOf(1)).categoryId(1L)
                .currency(BYN).build();

        final String message = mockMvc
                .perform(post(URL_API + "create").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

        then(message.contains("Product's name must be not empty")).isTrue();
    }

    @Test
    public void test_validation_ProductCreationDto_amount() throws Exception {

        final ProductCreationDto dto = ProductCreationDto.builder().name(PRODUCT).amount(BigDecimal.valueOf(-1))
                .categoryId(1L).currency(BYN).build();

        final String message = mockMvc
                .perform(post(URL_API + "create").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

        then(message.contains("Amount must be positive")).isTrue();
    }

    @Test
    public void test_validation_ProductCreationDto_currency() throws Exception {

        final ProductCreationDto dto = ProductCreationDto.builder().name(PRODUCT).amount(BigDecimal.valueOf(1))
                .categoryId(1L).currency("dollars").build();

        final String message = mockMvc
                .perform(post(URL_API + "create").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

        then(message.contains("Currency must be 3 characters")).isTrue();
    }

    @Test
    public void test_validation_ProductCreationDto_categoryId() throws Exception {

        final ProductCreationDto dto = ProductCreationDto.builder().name(PRODUCT).amount(BigDecimal.valueOf(1))
                .categoryId(-1L).currency(BYN).build();

        final String message = mockMvc
                .perform(post(URL_API + "create").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

        then(message.contains("Category's ID must be positive")).isTrue();
    }

    // @Test
    // public void test_findByName() throws Exception {
    //
    // final int id = 1;
    //
    // final ProductDto productDto = ProductDto.builder()
    // .id(id)
    // .name(PRODUCT)
    // .prices(List.of(id)))
    // .categories(List.of(id)))
    // .build();
    //
    // Mockito.when(productFacade.find(Mockito.anyString()))
    // .thenReturn(productDto);
    //
    // mockMvc.perform(get(URL_API + "name/{name}", PRODUCT))
    // .andExpect(status().isOk())
    // .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
    // .andExpect(jsonPath("$.id", is(productDto.getId())))
    // .andExpect(jsonPath("$.name", is(productDto.name())))
    // .andExpect(jsonPath("$.prices", hasSize(productDto.prices().size())))
    // .andExpect(jsonPath("$.categories", hasSize(productDto.getCategories().size())));
    // }
    //
    // @Test
    // public void test_findByName_400() throws Exception {
    //
    // final String model = "Product";
    //
    // Mockito.when(productFacade.find(Mockito.anyString()))
    // .thenThrow(new NotFoundException(model, PRODUCT));
    //
    // final String message = mockMvc.perform(get(URL_API + "name/{name}", PRODUCT))
    // .andExpect(status().isNotFound())
    // .andReturn().getResolvedException().getMessage();
    //
    // then(message.contains("Can't find " + model + " with NAME = " + PRODUCT)).isTrue();
    // }

    @Test
    public void test_findAllByCategory() throws Exception {

        Mockito.when(productFacade.findAll(Mockito.any(Pageable.class), Mockito.anyLong()))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(URL_API + "category/{id}", 1)).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)));
    }

    @Test
    public void test_findAllByPriceJson() throws Exception {

        Mockito.when(productFacade.findAll(Mockito.any(Pageable.class), Mockito.any(PriceDto.class)))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(URL_API + "price").contentType(APPLICATION_JSON).content(
                        mapper.writeValueAsString(PriceDto.builder().currency(BYN).amount(BigDecimal.valueOf(100)).build())))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)));
    }

    @Test
    public void test_add_category() throws Exception {

        final ProductDto productDto = ProductDto.builder().id(1L).name(PRODUCT)
                .prices(List.of(1l, 2l)).categories(List.of(1l, 2l)).build();

        Mockito.when(productFacade.add(Mockito.anyLong(), Mockito.anyLong())).thenReturn(productDto);

        mockMvc.perform(put(URL_API + "{id}/add/category/2", productDto.id()).contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CategoryDto.builder().name(CATEGORY).build())))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(productDto.id())))
                .andExpect(jsonPath("$.name", is(productDto.name())))
                .andExpect(jsonPath("$.prices", hasSize(productDto.prices().size())))
                .andExpect(jsonPath("$.categories", hasSize(productDto.categories().size())));
    }

    @Test
    public void test_remove_category() throws Exception {

        final ProductDto productDto = ProductDto.builder().id(1L).name(PRODUCT)
                .prices(List.of(1L, 2L)).categories(List.of(1L, 2L)).build();

        Mockito.when(productFacade.removeCategory(Mockito.anyLong(), Mockito.anyLong())).thenReturn(productDto);

        mockMvc.perform(put(URL_API + "{id}/remove/category/3", productDto.id()).contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CategoryDto.builder().name(CATEGORY).build())))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(productDto.id())))
                .andExpect(jsonPath("$.name", is(productDto.name())))
                .andExpect(jsonPath("$.prices", hasSize(productDto.prices().size())))
                .andExpect(jsonPath("$.categories", hasSize(productDto.categories().size())));
    }

    @Test
    public void test_remove_category_400() throws Exception {

        Mockito.when(productFacade.removeCategory(Mockito.anyLong(), Mockito.anyLong()))
                .thenThrow(new DeleteException("last Category in Product"));

        final String message = mockMvc
                .perform(put(URL_API + "{id}/remove/category/999", 1).contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CategoryDto.builder().name(CATEGORY).build())))
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

        then(message.contains("Can't delete last Category in Product")).isTrue();
    }

    @Test
    public void test_add_price() throws Exception {

        final ProductDto productDto = ProductDto.builder().id(1L).name(PRODUCT)
                .prices(List.of(1L, 2L)).categories(List.of(1L, 2L)).build();

        Mockito.when(productFacade.add(Mockito.any(PriceDto.class), Mockito.anyLong())).thenReturn(productDto);

        mockMvc.perform(put(URL_API + "{id}/add/price", productDto.id()).contentType(APPLICATION_JSON).content(
                        mapper.writeValueAsString(PriceDto.builder().currency(BYN).amount(BigDecimal.valueOf(1)).build())))
                .andExpect(status().isCreated()).andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(productDto.id())))
                .andExpect(jsonPath("$.name", is(productDto.name())))
                .andExpect(jsonPath("$.prices", hasSize(productDto.prices().size())))
                .andExpect(jsonPath("$.categories", hasSize(productDto.categories().size())));
    }

}
