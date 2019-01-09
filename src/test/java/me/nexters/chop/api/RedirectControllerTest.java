package me.nexters.chop.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author junho.park
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class RedirectControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private String shortenUrl;

    @BeforeEach
    public void setUp() {
        shortenUrl = "a";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void redirect_isSuccess() throws Exception {
        mockMvc.perform(get("/" + shortenUrl))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
}