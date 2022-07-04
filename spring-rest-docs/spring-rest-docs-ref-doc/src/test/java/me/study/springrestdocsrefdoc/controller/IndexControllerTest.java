package me.study.springrestdocsrefdoc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(IndexController.class)
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void index() throws Exception {
        mockMvc.perform(get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("index"));
    }

    @Test
    void getUser1() throws Exception {
        mockMvc.perform(get("/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-user-1",
                        responseFields(
                                fieldWithPath("contact.email").type(JsonFieldType.STRING).description("The user's email address"),
                                fieldWithPath("contact.name").description("The user's name")
                        )
                ));
    }

    @Test
    void getUser2() throws Exception {
        mockMvc.perform(get("/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-user-2",
                        responseFields(
                                subsectionWithPath("contact").description("The user's contact details")
                        )
                ));
    }

    FieldDescriptor[] book = new FieldDescriptor[] {
            fieldWithPath("title").description("Title of the book"),
            fieldWithPath("author").description("Author of the book")
    };

    @Test
    void getBook() throws Exception {
        mockMvc.perform(get("/book")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-book",
                        responseFields(
                                book
                        )
                ));
    }

    @Test
    void getBooks() throws Exception {
        mockMvc.perform(get("/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-books",
                        responseFields(
                                fieldWithPath("[]").description("An array of books")
                        ).andWithPrefix("[].", book)
                ));
    }

    @Test
    void getLocation() throws Exception {
        mockMvc.perform(get("/location")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-location",
                        responseBody(beneathPath("weather.temperature").withSubsectionId("temp")),
                        responseFields(beneathPath("weather.temperature").withSubsectionId("temp"),
                                fieldWithPath("high").description("The forecast high in degrees celcius"),
                                fieldWithPath("low").description("The forecast low in degrees celcius"))
                ));
    }

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get("/users?page=2&perPage=100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-users",
                        requestParameters(
                                parameterWithName("page").description("The page to retrieve"),
                                parameterWithName("perPage").description("Entries per page")
                        )
                ));
    }

    @Test
    void postUser() throws Exception {
        mockMvc.perform(post("/user")
                        .param("username", "Tester")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-user",
                        requestParameters(
                                parameterWithName("username").description("The user's username")
                        )
                ));
    }

    @Test
    void getLocations() throws Exception {
        mockMvc.perform(get("/locations/{latitude}/{longitude}", 51.5072, 0.1275)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-locations",
                        pathParameters(
                                parameterWithName("latitude").description("The location's latitude"),
                                parameterWithName("longitude").description("The location's longitude")
                        )
                ));
    }

}