package example.stronglytyped

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

@Import(AdditionalBeans::class)
@WebMvcTest(StronglyTypedPersonController::class)
internal class StronglyTypedPersonControllerTests(
    @Autowired val mockMvc: MockMvc
) {

    @Test
    fun `posting valid Person responds with HTTP 200`() {
        val requestBody = """
            {
              "firstName": "Max",
              "lastName": "Mustermann",
              "age": 51,
              "address": {
                "street": "Musterstraße 132A",
                "zipCode": "12345",
                "city": "Musterstad"
              }
            }
            """

        val request = post("/stronglytyped/persons")
            .contentType(APPLICATION_JSON)
            .content(requestBody)
        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
            .andExpect(content().json(requestBody))
    }

    @Test
    fun `posting invalid Person responds with HTTP 400`() {
        val requestBody = """
            {
              "firstName": "",
              "lastName": "Mustermann",
              "age": 51,
              "address": {
                "street": "Musterstraße 132A",
                "zipCode": "12345",
                "city": "Musterstad"
              }
            }
            """

        val request = post("/stronglytyped/persons")
            .contentType(APPLICATION_JSON)
            .content(requestBody)

        val expectedResponse = """
            {
              "timestamp": "2019-10-05T12:34:56.789Z",
              "status": 400,
              "path": "/stronglytyped/persons",
              "error": "Bad Request",
              "message": "Value of First Name must not be blank!"
            }
            """
        mockMvc.perform(request)
            .andExpect(status().isBadRequest)
            .andExpect(content().json(expectedResponse))
    }

    // and some more ...

}

private class AdditionalBeans {

    @Bean
    @Primary
    fun testClock(): Clock = Clock.fixed(Instant.parse("2019-10-05T12:34:56.789Z"), ZoneId.of("UTC"))

}
