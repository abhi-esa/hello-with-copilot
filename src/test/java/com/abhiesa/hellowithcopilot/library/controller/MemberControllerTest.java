package com.abhiesa.hellowithcopilot.library.controller;

import com.abhiesa.hellowithcopilot.library.model.Member;
import com.abhiesa.hellowithcopilot.library.service.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private MemberService memberService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testCreateMember() throws Exception {
    Member member = new Member();
    member.setId(1L);
    member.setName("John Doe");

    Mockito.when(memberService.create(Mockito.any(Member.class))).thenReturn(member);

    mockMvc.perform(post("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(member)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("John Doe"));
  }

  @Test
  void testGetMember() throws Exception {
    Member member = new Member();
    member.setId(1L);
    member.setName("John Doe");
    Mockito.when(memberService.get(1L)).thenReturn(member);

    mockMvc.perform(get("/api/members/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("John Doe"));
  }

  @Test
  void testListMembers() throws Exception {
    Member member = new Member();
    member.setId(1L);
    member.setName("John Doe");
    Mockito.when(memberService.list()).thenReturn(List.of(member));

    mockMvc.perform(get("/api/members"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("John Doe"));
  }

  @Test
  void testUpdateMember() throws Exception {
    Member member = new Member();
    member.setId(1L);
    member.setName("Jane Doe");
    Mockito.when(memberService.update(Mockito.any(Member.class))).thenReturn(member);

    mockMvc.perform(put("/api/members/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(member)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Jane Doe"));
  }

  @Test
  void testDeleteMember() throws Exception {
    mockMvc.perform(delete("/api/members/1"))
        .andExpect(status().isNoContent());
  }
}

