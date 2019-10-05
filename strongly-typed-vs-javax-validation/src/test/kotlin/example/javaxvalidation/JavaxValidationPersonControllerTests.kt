package example.javaxvalidation

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(JavaxValidationPersonController::class)
internal class JavaxValidationPersonControllerTests(
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

        val request = post("/javaxvalidation/persons")
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

        val request = post("/javaxvalidation/persons")
            .contentType(APPLICATION_JSON)
            .content(requestBody)
        mockMvc.perform(request)
            .andExpect(status().isBadRequest)
            .andExpect(content().string(""))
        // body cannot be asserted because it is created somewhere out of scope of this test :(
    }

}
