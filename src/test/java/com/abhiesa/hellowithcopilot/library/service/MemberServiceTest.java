package com.abhiesa.hellowithcopilot.library.service;

import com.abhiesa.hellowithcopilot.library.model.Member;
import com.abhiesa.hellowithcopilot.library.repository.MemberRepository;
import com.abhiesa.hellowithcopilot.library.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    @Test
    void testCreateMember() {
        MemberRepository repo = Mockito.mock(MemberRepository.class);
        MemberService service = new MemberService(repo);
        Member member = new Member();
        Mockito.when(repo.save(member)).thenReturn(member);
        assertEquals(member, service.create(member));
    }

    @Test
    void testGetMemberFound() {
        MemberRepository repo = Mockito.mock(MemberRepository.class);
        MemberService service = new MemberService(repo);
        Member member = new Member();
        member.setId(1L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(member));
        assertEquals(member, service.get(1L));
    }

    @Test
    void testGetMemberNotFound() {
        MemberRepository repo = Mockito.mock(MemberRepository.class);
        MemberService service = new MemberService(repo);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.get(1L));
    }

    @Test
    void testListMembers() {
        MemberRepository repo = Mockito.mock(MemberRepository.class);
        MemberService service = new MemberService(repo);
        List<Member> members = List.of(new Member());
        Mockito.when(repo.findAll()).thenReturn(members);
        assertEquals(members, service.list());
    }

    @Test
    void testUpdateMember() {
        MemberRepository repo = Mockito.mock(MemberRepository.class);
        MemberService service = new MemberService(repo);
        Member member = new Member();
        member.setId(1L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(member));
        Mockito.when(repo.save(member)).thenReturn(member);
        assertEquals(member, service.update(member));
    }

    @Test
    void testDeleteMember() {
        MemberRepository repo = Mockito.mock(MemberRepository.class);
        MemberService service = new MemberService(repo);
        service.delete(1L);
        Mockito.verify(repo).deleteById(1L);
    }
}

