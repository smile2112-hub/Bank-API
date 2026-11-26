package com.webapp.bankingportal;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class CorsConfigTests extends BaseTest {

    @Test
    void allowsAuthorizationHeaderForCorsPreflight() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.options("/api/users/login")
                .header(HttpHeaders.ORIGIN, "https://example.vercel.app")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "authorization,content-type"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header()
                        .string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*"))
                .andExpect(MockMvcResultMatchers.header()
                        .exists(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS));
    }
}
