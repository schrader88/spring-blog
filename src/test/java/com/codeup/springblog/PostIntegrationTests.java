package com.codeup.springblog;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import javax.servlet.http.HttpSession;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBlogApplication.class)
@AutoConfigureMockMvc
public class PostIntegrationTests {

    // define test user variable
    private User testUser;

    // define HttpSession variable
    private HttpSession httpSession;

    // autowire MockMvc object
    @Autowired
    private MockMvc mvc;

    // autowire UserRepository
    @Autowired
    private UserRepository userRepository;

    // autowire PostRepository
    @Autowired
    private PostRepository postRepository;

    // autowire PasswordEncoder
    @Autowired
    private PasswordEncoder passwordEncoder;

    // ======= before running tests, find test user or create one, then 'login'

    @Before
    public void setup() throws Exception {
        testUser = userRepository.findByUsername("testUser");
        // Creates the test user if not exists
        if(testUser == null){
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("pass"));
            newUser.setEmail("testUser@codeup.com");
            testUser = userRepository.save(newUser);
        }
        // upon logging in, set the session
        httpSession = this.mvc.perform(post("/login").with(csrf())
                        .param("username", "testUser")
                        .param("password", "pass"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/posts"))
                .andReturn()
                .getRequest()
                .getSession();
    }

    // sanity test to assert that the session is not null
    @Test
    public void testSessionNotNull() {
        assertNotNull(httpSession);
    }

    // test post index view
    @Test
    public void testPostIndex() throws Exception{
        Post firstPost = postRepository.findAll().get(0);
        mvc.perform(get("/posts"))
                // expect that status is ok
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("All Posts")))
                .andExpect(content().string(containsString(firstPost.getTitle())));
    }

        // expect content of response to include header title
        // expect content to contain string of a post

    // test post show
    @Test
    public void testPostShow() throws Exception{
        Post firstPost = postRepository.findAll().get(0);
        mvc.perform(get("/posts/" + firstPost.getId()))
                // expect that status is ok
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Individual Post")))
                .andExpect(content().string(containsString(firstPost.getTitle())));
    }

        // expect contents to contain the given post's title

    // test post create
    @Test
    public void testPostCreate() throws Exception{
        mvc.perform(post("/posts/create").with(csrf())
                .session((MockHttpSession) httpSession)
                .param("title", "New Created Post")
                .param("body", "Created post body.")
                .param("urls", "#")
                .param("urls", "#"))
                .andExpect(status().is3xxRedirection());
    }
        // with csrf and set session
        // expect redirection

    // test post delete
    @Test
    public void testDeletePost() throws Exception {
        // Creates a test Post to be deleted
        this.mvc.perform(
                        post("/posts/create").with(csrf())
                                .session((MockHttpSession) httpSession)
                                .param("title", "post to be deleted")
                                .param("body", "won't last long")
                                .param("urls", "#")
                                .param("urls", "#"))
                .andExpect(status().is3xxRedirection());

        // Get the recent Post that matches the title
        Post existingPost = postRepository.findByTitle("post to be deleted");

        // Makes a Post request to /posts/{id}/delete and expect a redirection to the Posts index
        this.mvc.perform(
                        post("/posts/" + existingPost.getId() + "/delete").with(csrf())
                                .session((MockHttpSession) httpSession)
                                .param("id", String.valueOf(existingPost.getId())))
                .andExpect(status().is3xxRedirection());
    }

        // setup similar to creating a post with csrf and session
        // expect redirection

    // test post delete
    @Test
    public void testEditPost() throws Exception {
        // Gets the first Post for tests purposes
        Post existingPost = postRepository.findAll().get(0);

        // Makes a Post request to /posts/{id}/edit and expect a redirection to the Post show page
        this.mvc.perform(
                        post("/posts/" + existingPost.getId() + "/edit").with(csrf())
                                .session((MockHttpSession) httpSession)
                                .param("title", "edited title")
                                .param("body", "edited description"))
                .andExpect(status().is3xxRedirection());

        // Makes a GET request to /posts/{id} and expect a redirection to the Post show page
        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                // Test the dynamic content of the page
                .andExpect(content().string(containsString("edited title")))
                .andExpect(content().string(containsString("edited description")));
    }

        // setup similar to creating a post with csrf and session
        // expect redirection
}
