package cn.csg.restdocs.controller;

import cn.csg.restdocs.RestdocsApplication;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TestController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class TestControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation =
            new JUnitRestDocumentation();

    @Resource
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(this.context)
                        .apply(documentationConfiguration(this.restDocumentation))
                        .build();
    }

    @Test
    public void testRestDocs() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/test/{id}",10))
                .andExpect(status().isOk())
                .andDo(document("restDocs",
                        pathParameters(
                                parameterWithName("id").description("the user's id"))));
        System.out.println("test完成");
    }

    @Test
    public void testUser() throws Exception {
        this.mockMvc.perform(get("/user?page=2&per_page=100"))
                .andExpect(status().isOk())
                .andDo(document("user",
                        requestParameters(parameterWithName("page").description("the user's page"),
                                parameterWithName("per_page").description("the user's per_page"))));
        System.out.println("test完成");
    }

    @Test
    public void testHello() throws Exception {
        this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("hello"
//                        links(
//                        linkWithRel("/alpha").description("Link to the alpha resource"),
//                        linkWithRel("/bravo").description("Link to the bravo resource")
                ));//TODO 存在问题的测试
        System.out.println("测试完成");
    }

    @Test
    public void testGetUser() throws Exception{
        this.mockMvc.perform(get("/getUser"))
                .andExpect(status().isOk())
                .andDo(document("getUser",
                        responseFields(fieldWithPath("id").description("the user's id"),
                                fieldWithPath("name").description("the user's name"),
                                fieldWithPath("email").description("用户邮箱"))
                        ));
        System.out.println("获取用户信息成功");
    }
}